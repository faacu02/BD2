package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;

public interface ToursRepository {

    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Supplier> findSupplierById(Long id);

    Service saveService(Service service);
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId);
    Service updatePriceService(Long id, float newPrice) throws ToursException;


}
