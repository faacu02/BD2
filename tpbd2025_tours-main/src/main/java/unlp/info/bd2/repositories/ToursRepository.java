package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
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

    //User
    User save(User user) throws ToursException;
    Optional<User> findById(Long id) throws ToursException;
    Optional<User> findByUsername(String username) throws ToursException;
    void delete(User user) throws ToursException;
    

}
