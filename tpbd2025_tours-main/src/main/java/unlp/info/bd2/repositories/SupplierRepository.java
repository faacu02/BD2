package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.utils.ToursException;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);

    @Query("SELECT s.service.supplier AS supplier " +
            "FROM ItemService s " +
            "GROUP BY s.service.supplier " +
            "ORDER BY COUNT(s.id) DESC")
    List<Supplier> findTopSuppliers(Pageable pageable);

    @Query("SELECT MAX(SIZE(s.services)) FROM Supplier s")
    Long findMaxServicesPerSupplier();


}
