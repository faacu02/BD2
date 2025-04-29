package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface ToursRepository {

    Object save(Object object) throws ToursException;
    void delete(Object object) throws ToursException;
    Object update(Object object) throws ToursException;
    //Supplier
    Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) ;
    Optional<Supplier> findSupplierById(Long id);

    //Service
    Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException;
    Optional<Service> findServiceById(Long id) ;

    //User
    Optional<User> findUserById(Long id) throws ToursException;
    Optional<User> findUserByUsername(String username) throws ToursException;

    //Stops
    List<Stop> findStopByName(String name);

    //Routes
    Optional<Route> findRouteById(Long id);
    List<Route> findRouteBelowPrice(float price);

    //PURCHASES
    Optional<Purchase> findPurchaseByCode(String code);
    int getCountOfPurchasesInRouteAndDate(Route route, Date date);

    //REVIEWS

    //ITEM SERVICE


    //HQL
    //Users
    List<User> getTop5UsersMorePurchases();
    List<User> getUserSpendingMoreThan(float mount);

    //ROUTES
    List<Route> findRoutesWithStop(Stop stop);
    Long findMaxStopOfRoutes();
    List<Route> getTop3RoutesWithMaxRating();
    List<Route> findRoutsNotSells();


    //Service
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();

    //Supplier
    List<Supplier> getTopNSuppliersInPurchases(int n);

    //Purchase
    long getCountOfPurchasesBetweenDates(Date start, Date end);
    List<Purchase> findTop10MoreExpensivePurchasesInServices();

    //TourGuide
    List<TourGuideUser> getTourGuidesWithRating1();

}
