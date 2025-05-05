package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    Optional<Service> findByNameAndSupplierId(String name, Long supplierId);

    @Query("SELECT s.service FROM ItemService s " +
            "GROUP BY s.service " +
            "ORDER BY SUM(s.quantity) DESC")
    List<Service> findMostDemandedService(Pageable pageable);

    @Query("SELECT s FROM Service s LEFT JOIN ItemService isv ON s = isv.service WHERE isv.id IS NULL")
    List<Service> findServicesNotInAnyPurchase();



}
