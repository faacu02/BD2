package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Service;

import java.util.List;

@Repository
public interface ItemServiceRepository extends CrudRepository<ItemService, Long> {

    @Query("SELECT isv.service.supplier AS supplier, SUM(isv.quantity) AS total " +
            "FROM ItemService isv " +
            "GROUP BY isv.service.supplier " +
            "ORDER BY total DESC")
    List<Object[]> findTopSuppliersByItemsSold();

    @Query("SELECT DISTINCT i.purchase FROM ItemService i WHERE i.service = :service")
    List<Purchase> findPurchasesByService(@Param("service") Service service);

    @Query("SELECT s.supplier, COUNT(i) as total " +
            "FROM ItemService i JOIN i.service s " +
            "GROUP BY s.supplier " +
            "ORDER BY total DESC")
    List<Object[]> findSuppliersOrderedByItemsSoldDesc();



}
