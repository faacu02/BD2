package unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import unlp.info.bd2.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);

    List<User> findUsersByPurchaseListTotalPriceGreaterThan(float amount);

    @Aggregation(pipeline = {
            "{ $project: { username: 1, purchaseCount: { $size: { $ifNull: ['$purchaseList', []] } } } }",
            "{ $sort: { purchaseCount: -1 } }",
            "{ $limit: 5 }"
    })
    List<User> findTop5UsersWithMostPurchases();



    List<User> findByPurchaseListTotalPriceGreaterThanEqual(float amount);

    @Aggregation(pipeline = {
            "{ $project: { username: 1, purchaseList: 1, purchaseCount: { $size: { $ifNull: ['$purchaseList', []] } } } }",
            "{ $match: { purchaseCount: { $gte: ?0 } } }"
    })
    List<User> findUsersWithAtLeastNumberOfPurchases(long number);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
