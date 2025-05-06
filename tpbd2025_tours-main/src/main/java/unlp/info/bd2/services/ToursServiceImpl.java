package unlp.info.bd2.services;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Review;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.StopRepository;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.repositories.UserRepository;
import unlp.info.bd2.utils.ToursException;



@org.springframework.stereotype.Service

public class ToursServiceImpl implements ToursService {

    private ToursRepository toursRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ItemServiceRepository itemServiceRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public ToursServiceImpl(ToursRepository toursRepository) {
        this.toursRepository = toursRepository;
    }
    public ToursServiceImpl (){

    }

    @Transactional
    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
       try {
           User user = new User(username, password, fullName, email, birthdate, phoneNumber);
           return this.userRepository.save(user);
       } catch (Exception e) {
           throw new ToursException("Error Guardando Usuario");
       }
    }

    @Transactional
    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser user = new DriverUser(username, password, fullName, email, birthdate, phoneNumber,expedient);
        return (DriverUser) this.userRepository.save(user);
    }

    @Transactional
    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser user = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber,education);
        return (TourGuideUser) this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
        return this.userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return this.userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public User updateUser(User user) throws ToursException {
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(User user) throws ToursException {
        if(user.isActive()) {
            if(user.canBeDeleted()) {
                if (user.getPurchaseList().isEmpty()) {
                    this.userRepository.delete(user);
                } else {
                        user.setActive(false);
                        this.updateUser(user);
                }
            } else {
                throw new ToursException("User has routes");
            }
        }else {
            throw new ToursException("User is not active");
        }
    }

    @Transactional
    @Override
    public Stop createStop(String name, String description) throws ToursException {
        Stop stop = new Stop(name,description);
        return this.stopRepository.save(stop);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stop> getStopByNameStart(String name) {
        return this.stopRepository.getStopByNameStart(name);
    }

    @Transactional
    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        Route route = new Route(name,price,totalKm,maxNumberOfUsers,stops);
        return this.routeRepository.save(route);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Route> getRouteById(Long id) {
        return this.routeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return this.routeRepository.findByPriceLessThan(price);
    }

    @Transactional
    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        DriverUser user = this.userRepository.findDriverUserByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));

        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        user.addRoute(route);
        route.addDriver(user);
        this.updateUser(user);
    }

    @Transactional
    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        TourGuideUser user = this.userRepository.findTourGuideUserByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));


        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        user.addRoute(route);
        route.addTourGuide(user);
        this.updateUser(user);
    }

    @Transactional
    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try {
            Supplier supplier = new Supplier(businessName, authorizationNumber);
            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new ToursException("Error al crear el supplier");
        }
    }


    @Transactional
    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        try {
            Service service = new Service(name, price, description, supplier);
            supplier.addService(service);
            return this.serviceRepository.save(service);
        }
        catch (Exception e) {
            throw new ToursException("Error al crear el service");
        }
    }

    @Transactional
    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        try {
            Service existingService = serviceRepository.findById(id)
                    .orElseThrow(() -> new ToursException("No existe el servicio"));

            existingService.setPrice(newPrice);
            return serviceRepository.save(existingService);

        } catch (Exception e) {
            throw new ToursException("Error al actualizar el precio del servicio");
        }
    }






    @Transactional(readOnly = true)
    @Override
    public Optional<Supplier> getSupplierById(Long id){
        return this.supplierRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return this.supplierRepository.findByAuthorizationNumber(authorizationNumber);
    }



    @Transactional(readOnly = true)
    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        try {
            return serviceRepository.findByNameAndSupplierId(name, id);
        } catch (Exception e) {
            throw new ToursException("Error al buscar el servicio");
        }
    }


    @Transactional
    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        try {
            Purchase purchase = new Purchase(code, route, user);
            user.addPurchase(purchase);
            return this.purchaseRepository.save(purchase);
        }
        catch (Exception e) {
            throw new ToursException("Error al crear el purchase");
        }
    }

    @Transactional
    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        try{
            if(this.purchaseRepository.getCountOfPurchasesInRouteAndDate(route,date) < route.getMaxNumberUsers()){
                Purchase purchase = new Purchase(code, date, route, user);
                user.addPurchase(purchase);
                return this.purchaseRepository.save(purchase);
            } else{
                throw new ToursException("No hay lugares disponibles");
            }
        } catch (Exception e){
            throw new ToursException("No puede realizarse la compra");
        }
    }

    @Transactional
    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        try {
            ItemService item = new ItemService(quantity, purchase, service);
            purchase.addItemService(item);
            service.addItemService(item);
            return this.itemServiceRepository.save(item);
        }
        catch (Exception e){
            throw new ToursException("No se puede agregar el item");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Purchase> getPurchaseByCode(String code){
        return this.purchaseRepository.findByCode(code);
    }

    @Transactional
    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        try {
            System.out.println("va el delte");
            this.purchaseRepository.delete(purchase);
            System.out.println("El purchase ha sido eliminado");
        }
        catch (Exception e){
            throw new ToursException("Error al eliminar el purchase");
        }
    }

    @Transactional
    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        try {
            Review review = new Review(rating, comment, purchase);
            purchase.setReview(review);
            return this.reviewRepository.save(review);
        }catch (Exception e){
            throw new ToursException("No se puede agregar la reseña");
        }
    }

    // CONSULTAS HQL

    @Transactional(readOnly = true)
    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        Optional<User> user;
        try {
            user = this.getUserByUsername(username);
        }catch (ToursException e){
            return null;
        }
            if(user.isEmpty()){
                return null;
            }else{
                return user.get().getPurchaseList();
            }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUserSpendingMoreThan(float amount) {
        return this.userRepository.findUsersWithAnyPurchaseOverAmount(amount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.supplierRepository.findTopSuppliers(PageRequest.of(0, n));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesWithServices() { // le saque In le puse With
        Pageable pageable = PageRequest.of(0, 10);
        return this.purchaseRepository.getTop10MoreExpensivePurchasesInService(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getTop5UsersMorePurchases() {
        //return this.toursRepository.getTop5UsersMorePurchases();
        return this.userRepository.findTop5UsersWithMostPurchases(Pageable.ofSize(5));
    }

    @Transactional(readOnly = true)
    @Override
    public Long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return this.purchaseRepository.countByDateBetween(start, end);
    }

    //Routes
    @Transactional(readOnly = true)
    //@Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return this.routeRepository.findRoutesWithStop(stop.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Long getMaxStopOfRoutes() {
        return this.toursRepository.findMaxStopOfRoutes();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Route> getRoutsNotSell() {
        return this.toursRepository.findRoutsNotSells();
    }


    @Transactional(readOnly = true)
    @Override
    public Service getMostDemandedService() {
        return this.serviceRepository.findMostDemandedService(PageRequest.of(0, 1)).get(0);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.serviceRepository.findServicesNotInAnyPurchase();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.userRepository.findTourGuidesWithRating1();
    }
    @Override
    public DriverUser getDriverUserWithMoreRoutes() {
        List<Object[]> results = this.userRepository.findDriverWithMostRoutes();
        return results.isEmpty() ? null : (DriverUser) results.get(0)[0];
    }
    @Override
    public Route getMostBestSellingRoute() {
        return null;
    }
    @Override
    public List<Supplier> getTopNSuppliersItemsSold(int n) {
        List<Object[]> results = itemServiceRepository.findTopSuppliersByItemsSold();
        return results.stream()
                .limit(n)
                .map(result -> (Supplier) result[0])
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> getPurchaseWithService(Service service) {
        return this.itemServiceRepository.findPurchasesByService(service);
    }
    @Override
    public Long getMaxServicesOfSupplier() {
        return this.supplierRepository.findMaxServicesPerSupplier();
    }

    @Override
    public List<Route> getTop3RoutesWithMoreStops() {
        return null;
    }
    @Override
    public List<Route> getRoutesWithMinRating() {
        return null;
    }
    @Override
    public List<Route> getTop3RoutesWithMaxAverageRating() {
        List<Object[]> results = routeRepository.findTop3RoutesByAverageRating();
        return results.stream()
                .map(result -> (Route) result[0])
                .collect(Collectors.toList());
    }
    @Override
    public List<User> getUsersWithNumberOfPurchases(int number) {
        return userRepository.findUsersByPurchaseCount(number);
    }
}
