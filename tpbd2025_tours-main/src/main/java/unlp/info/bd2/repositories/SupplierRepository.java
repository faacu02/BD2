package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByAuthorizationNumber(String authorizationNumber);

    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);
}
