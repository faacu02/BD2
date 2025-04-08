package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;

import java.util.Optional;

public interface ToursRepository {

    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber);
    boolean existsSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Supplier> findSupplierById(Long id);

    Service saveService(Service service);
    Optional<Service> findServiceById(Long id);
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId);


}
