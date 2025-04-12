package unlp.info.bd2.repositories;

import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface ToursRepository {

    //Supplier
    Supplier saveSupplier(Supplier supplier) throws ToursException;
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) ;
    Optional<Supplier> findSupplierById(Long id);

    //Service
    Service saveService(Service service) throws ToursException;
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException;
    Service updatePriceService(Long id, float newPrice) throws ToursException;

    //User
    User saveUser(User user) throws ToursException;
    Optional<User> findUserById(Long id) throws ToursException;
    Optional<User> findUserByUsername(String username) throws ToursException;
    void deleteUser(User user) throws ToursException;
    User updateUser(User user) throws ToursException;

}
