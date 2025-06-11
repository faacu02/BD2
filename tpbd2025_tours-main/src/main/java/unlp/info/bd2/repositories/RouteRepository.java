package unlp.info.bd2.repositories;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Route;

import unlp.info.bd2.model.Stop;

@Repository
public interface RouteRepository extends  MongoRepository<Route, ObjectId> {
    List<Route> findByPriceLessThan(float price);


    @Aggregation(pipeline = {
            // Traer las compras donde esta ruta fue utilizada
            "{ $lookup: { from: 'purchase', localField: '_id', foreignField: 'route._id', as: 'purchases' } }",
            // Desenrollamos las compras
            "{ $unwind: '$purchases' }",
            // Traemos los reviews cuyo purchase tiene el mismo code que la purchase actual
            "{ $lookup: { from: 'review', localField: 'purchases.code', foreignField: 'purchase.code', as: 'reviews' } }",
            "{ $unwind: '$reviews' }",
            // Agrupamos por ruta y calculamos el promedio
            "{ $group: { _id: '$_id', route: { $first: '$$ROOT' }, avgRating: { $avg: '$reviews.rating' } } }",
            // Ordenamos por rating promedio descendente
            "{ $sort: { avgRating: -1 } }",
            // Limitamos a los 3 mejores
            "{ $limit: 3 }",
            // Reemplazamos el root por la ruta
            "{ $replaceRoot: { newRoot: '$route' } }"
    })
    List<Route> findTop3RoutesByAverageRating();


    List<Route> findByStopsContaining(Stop stop);


    @Aggregation(pipeline = {
            "{ $project: { stopCount: { $size: '$stops' } } }",
            "{ $group: { _id: null, maxStops: { $max: '$stopCount' } } }",
            "{ $project: { _id: 0, maxStops: 1 } }"
    })
    Long findMaxStopOfRoutes();

    @Aggregation(pipeline = {
            "{ $lookup: { from: 'purchase', localField: '_id', foreignField: 'routeId', as: 'purchase_docs' } }",
            "{ $match: { purchase_docs: { $size: 0 } } }"
    })
    List<Route> findRoutesNotSold();

    @Aggregation(pipeline = {
            "{ $lookup: { from: 'review', localField: 'review', foreignField: '_id', as: 'reviewDoc' } }",
            "{ $unwind: '$reviewDoc' }",
            "{ $match: { 'reviewDoc.rating': 1 } }",
            "{ $replaceWith: '$route' }"
    })
    List<Route> findRoutesInPurchasesWithReviewRatingOne();

    @Query("""
    SELECT r, COUNT(s) AS stopCount
    FROM Route r
    JOIN r.stops s
    GROUP BY r
    ORDER BY stopCount DESC
    LIMIT 3
""")
    List<Route> getTop3RoutesWithMoreStops();



    @Query("SELECT r FROM Route r LEFT JOIN Purchase p ON p.route = r GROUP BY r ORDER BY COUNT(p) DESC")
    Page<Route> getMostBoughtRoute(Pageable pageable);




}
