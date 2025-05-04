package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Optional<Purchase> findByCode(String code);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.route = :route AND p.date = :date")
    long getCountOfPurchasesInRouteAndDate(@Param("route") Route route, @Param("date") Date date);
}
