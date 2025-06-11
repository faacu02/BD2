package unlp.info.bd2.repositories;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, ObjectId> {
    Optional<Purchase> findByCode(String code);

    Long countByRouteAndDate(Route route, Date date);

    Long countByDateBetween(Date start, Date end);

    List<Purchase> findTop10ByItemServiceListIsNotEmptyOrderByTotalPriceDesc();

    List<Purchase> findByItemServiceListServiceId(ObjectId id);

    Boolean existsByCode(String code);
}
