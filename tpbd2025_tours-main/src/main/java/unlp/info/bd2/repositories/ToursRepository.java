package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
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

    //Stops
    Stop saveStop(Stop stop) throws ToursException;
    List<Stop> findStopByName(String name);

    //Routes
    Route saveRoute(Route route) throws ToursException;
    Optional<Route> findRouteById(Long id);
    List<Route> findRouteBelowPrice(float price);

    //PURCHASES
    Purchase savePurchase(Purchase purchase) throws ToursException;
    Optional<Purchase> findPurchaseByCode(String code) throws ToursException;
    void deletePurchase(Purchase purchase);
    Purchase updatePurchase(Purchase purchase);
    int getCountOfPurchasesInRouteAndDate(Route route, Date date);

    //REVIEWS
    Review saveReview(Review review);

    //ITEM SERVICE
    ItemService saveItemService(ItemService itemService) throws ToursException;

    //HQL
    //Users
    List<User> getTop5UsersMorePurchases();
    List<User> getUserSpendingMoreThan(float mount);

    //ROUTES
    List<Route> findRoutesWithStop(Stop stop);
    Long findMaxStopOfRoutes();
    List<Route> getTop3RoutesWithMaxRating();


    //Service
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();

    //Supplier
    List<Supplier> getTopNSuppliersInPurchases(int n);

    //Purchase
    long getCountOfPurchasesBetweenDates(Date start, Date end);

    //TourGuide
    List<TourGuideUser> getTourGuidesWithRating1();

}
