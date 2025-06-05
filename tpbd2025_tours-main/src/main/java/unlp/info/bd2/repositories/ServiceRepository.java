package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, ObjectId> {

    Optional<Service> findByNameAndSupplierId(String name, ObjectId supplierId);

    @Query("SELECT s.service FROM ItemService s " +
            "GROUP BY s.service " +
            "ORDER BY SUM(s.quantity) DESC")
    List<Service> findMostDemandedService(Pageable pageable);

    List<Service> findByItemServiceListIsEmpty();





}
