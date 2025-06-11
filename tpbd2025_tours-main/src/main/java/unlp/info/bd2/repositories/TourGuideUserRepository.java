package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.TourGuideUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourGuideUserRepository extends MongoRepository<TourGuideUser, ObjectId> {
    Optional<TourGuideUser> findByUsername(String username);

    @Aggregation(pipeline = {
            "{ '$match': { 'userType': 'Guide' } }",
            "{ '$lookup': { 'from': 'route', 'localField': '_id', 'foreignField': 'tourGuideList.$id', 'as': 'routes' } }",
            "{ '$unwind': '$routes' }",
            "{ '$lookup': { 'from': 'purchase', 'localField': 'routes._id', 'foreignField': 'route._id', 'as': 'purchases' } }",
            "{ '$unwind': '$purchases' }",
            "{ '$lookup': { 'from': 'review', 'localField': 'purchases.review.$id', 'foreignField': '_id', 'as': 'review' } }",
            "{ '$unwind': '$review' }",
            "{ '$match': { 'review.rating': 1 } }",
            "{ '$group': { '_id': '$_id', 'name': { '$first': '$name' }, 'email': { '$first': '$email' }, 'phoneNumber': { '$first': '$phoneNumber' }, 'birthdate': { '$first': '$birthdate' }, 'username': { '$first': '$username' }, 'active': { '$first': '$active' } } }"
    })
    List<TourGuideUser> findTourGuidesWithRating1();

}
