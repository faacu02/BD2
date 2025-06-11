package unlp.info.bd2.repositories;

import java.util.List;

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


    @Query("""
        SELECT r, AVG(rev.rating) as avgRating
        FROM Route r
        JOIN Purchase p ON p.route = r
        JOIN Review rev ON rev.purchase = p
        GROUP BY r
        ORDER BY avgRating DESC
        LIMIT 3
    """)
    List<Route> findTop3RoutesByAverageRating();

    List<Route> findByStopsContaining(Stop stop);


    @Query("SELECT MAX(size(r.stops)) FROM Route r")
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
