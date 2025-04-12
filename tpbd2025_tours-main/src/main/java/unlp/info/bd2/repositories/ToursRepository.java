package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface ToursRepository {

    Supplier saveSupplier(Supplier supplier);
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber);
    Optional<Supplier> findSupplierById(Long id);

    Service saveService(Service service);
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId);
    Service updatePriceService(Long id, float newPrice) throws ToursException;

    //User
    User saveUser(User user) throws ToursException;
    Optional<User> findUserById(Long id) throws ToursException;
    Optional<User> findUserByUsername(String username) throws ToursException;
    void deleteUser(User user) throws ToursException;
    User updateUser(User user) throws ToursException;

    //Stops
    Stop saveStop(Stop stop) throws ToursException;
    List<Stop> findStopByName(String name);

    //Routes
    Route saveRoute(Route route) throws ToursException;
    Optional<Route> findRouteById(Long id);
    List<Route> findRouteBelowPrice(float price);

    //HQL

    //ROUTES
    List<Route> findRoutesWithStop(Stop stop);
    Long findMaxStopOfRoutes();

    //PURCHASES
    Purchase savePurchase(Purchase purchase);
    Optional<Purchase> findPurchaseByCode(String code) throws ToursException;
    void deletePurchase(Purchase purchase);
}
