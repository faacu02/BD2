package unlp.info.bd2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, Long> {

    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);

    @Query("SELECT s.service.supplier AS supplier " +
            "FROM ItemService s " +
            "GROUP BY s.service.supplier " +
            "ORDER BY COUNT(s.id) DESC")
    List<Supplier> findTopSuppliers(Pageable pageable);

    @Query("SELECT MAX(SIZE(s.services)) FROM Supplier s")
    Long findMaxServicesPerSupplier();

    @Query("""
    SELECT s 
    FROM Supplier s
    JOIN s.services srv
    JOIN ItemService isv ON isv.service = srv
    GROUP BY s
    ORDER BY SUM(isv.quantity) DESC
""")
    Page<Supplier> findTopSuppliersByItemsSold(Pageable pageable);


}
