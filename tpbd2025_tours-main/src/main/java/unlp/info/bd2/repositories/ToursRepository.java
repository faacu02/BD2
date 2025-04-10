package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.model.Service;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface ToursRepository {

    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber);
    boolean existsSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Supplier> findSupplierById(Long id);

    Service saveService(Service service);
    Optional<Service> findServiceById(Long id);
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId);

    //User
    User saveUser(User user) throws ToursException;
    Optional<User> findUserById(Long id) throws ToursException;
    Optional<User> findUserByUsername(String username) throws ToursException;
    void deleteUser(User user) throws ToursException;
    User updateUser(User user) throws ToursException;

}
