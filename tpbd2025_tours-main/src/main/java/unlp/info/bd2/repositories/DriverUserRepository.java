package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import unlp.info.bd2.model.DriverUser;


import java.util.Optional;

@Repository
public interface DriverUserRepository extends MongoRepository<DriverUser, ObjectId> {
    Optional<DriverUser> findByUsername(String username);

    @Aggregation(pipeline = {
            "{ $match: { userType: 'Driver' } }",
            "{ $addFields: { routeCount: { $size: '$routes' } } }",
            "{ $sort: { routeCount: -1 } }",
            "{ $limit: 1 }"
    })
    Optional<DriverUser> findTopDriverByRouteCount();









}
