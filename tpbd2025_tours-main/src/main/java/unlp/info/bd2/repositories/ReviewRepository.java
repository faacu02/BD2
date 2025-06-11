package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Review;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Route;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
    @Aggregation(pipeline = {
            // 1. Filtrar reviews con rating = 1
            "{ $match: { rating: 1 } }",

            // 2. Lookup para traer purchase referenciado
            "{ $lookup: { from: 'purchase', localField: 'purchase', foreignField: '_id', as: 'purchaseDoc' } }",
            "{ $unwind: '$purchaseDoc' }",

            // 3. Lookup para traer la ruta referenciada dentro de purchase
            "{ $lookup: { from: 'route', localField: 'purchaseDoc.route', foreignField: '_id', as: 'routeDoc' } }",
            "{ $unwind: '$routeDoc' }",

            // 4. Reemplazar con la ruta (para devolver solo rutas)
            "{ $replaceWith: '$routeDoc' }"
    })
    List<Route> findRoutesFromReviewsWithRatingOne();
}
