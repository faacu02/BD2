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

}
