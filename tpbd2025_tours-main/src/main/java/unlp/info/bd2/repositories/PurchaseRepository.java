package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Optional<Purchase> findById(Long id);
}
