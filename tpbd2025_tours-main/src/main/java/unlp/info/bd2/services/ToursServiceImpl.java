package unlp.info.bd2.services;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.repositories.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@org.springframework.stereotype.Service

public class ToursServiceImpl implements ToursService {

    private ToursRepository toursRepository;
    public ToursServiceImpl(ToursRepository toursRepository) {
        this.toursRepository = toursRepository;
    }

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User user = new User(username, password, fullName, email, birthdate, phoneNumber);
        return (User) this.toursRepository.save(user);
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser user = new DriverUser(username, password, fullName, email, birthdate, phoneNumber,expedient);
        return (DriverUser) this.toursRepository.save(user);
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser user = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber,education);
        return (TourGuideUser) this.toursRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
        return this.toursRepository.findUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return this.toursRepository.findUserByUsername(username);
    }

    @Override
    public User updateUser(User user) throws ToursException {
        return (User) this.toursRepository.update(user);
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        if(user.isActive()) {
            if(user.canBeDeleted()) {
                if (user.getPurchaseList().isEmpty()) {
                    this.toursRepository.delete(user);
                } else {
                    if(user.canBeDeactivated()) { //Va esto?
                        user.setActive(false);
                        this.updateUser(user);
                    }
                }
            } else {
                throw new ToursException("TourGuideUser has no routes");
            }
        }else {
            throw new ToursException("User is not active");
        }
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        Stop stop = new Stop(name,description);
        return (Stop) this.toursRepository.save(stop);
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return this.toursRepository.findStopByName(name);
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        Route route = new Route(name,price,totalKm,maxNumberOfUsers,stops);
        return (Route) this.toursRepository.save(route);
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return this.toursRepository.findRouteById(id);
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return this.toursRepository.findRouteBelowPrice(price);
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        User user = this.getUserByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));

        if (!user.getUserType().equals("Driver")) { //esta bien
            throw new ToursException("User is not a driver");
        }

        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        DriverUser userD = (DriverUser) user;
        userD.addRoute(route);
        route.addDriver(userD);
    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        User user = this.getUserByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));

        if (!user.getUserType().equals("Guide")) {
            throw new ToursException("User is not a tour guide");
        }

        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        TourGuideUser tourGuide = (TourGuideUser) user;
        tourGuide.addRoute(route);
        route.addTourGuide(tourGuide);
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        return (Supplier) toursRepository.save(supplier);
    }


    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description, supplier);
        supplier.addService(service);
        return (Service) this.toursRepository.save(service);
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return this.toursRepository.updatePriceService(id, newPrice);
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id){
        return this.toursRepository.findSupplierById(id);
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return this.toursRepository.findSupplierByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return this.toursRepository.findServiceByNameAndSupplierId(name, id);
    }

    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        Purchase purchase = new Purchase(code, route, user);
        user.addPurchase(purchase);
        return (Purchase) this.toursRepository.save(purchase);
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        try{
            if(this.toursRepository.getCountOfPurchasesInRouteAndDate(route,date) < route.getMaxNumberUsers()){
                Purchase purchase = new Purchase(code, date, route, user);
                user.addPurchase(purchase);
                return (Purchase) this.toursRepository.save(purchase);
            } else{
                throw new ToursException("No hay lugares disponibles");
            }
        } catch (Exception e){
            throw new ToursException("No puede realizarse la compra");
        }
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        ItemService item = new ItemService(quantity, purchase, service);
        purchase.addItemService(item);
        service.addItemService(item);
        return (ItemService) this.toursRepository.save(item);

    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code){
        return this.toursRepository.findPurchaseByCode(code);
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.toursRepository.delete(purchase);
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        try {
            Review review = new Review(rating, comment, purchase);
            purchase.setReview(review);
            return (Review) this.toursRepository.save(review);
        }catch (Exception e){
            throw new ToursException("No se puede agregar la reseña");
        }
    }

    // CONSULTAS HQL

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

    @Override
    public List<User> getUserSpendingMoreThan(float amount) {
        return this.toursRepository.getUserSpendingMoreThan(amount);
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.toursRepository.getTopNSuppliersInPurchases(n);
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return this.toursRepository.findTop10MoreExpensivePurchasesInServices();
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        return this.toursRepository.getTop5UsersMorePurchases();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return this.toursRepository.getCountOfPurchasesBetweenDates(start, end);
    }
    //Routes
    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return this.toursRepository.findRoutesWithStop(stop);
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return this.toursRepository.findMaxStopOfRoutes();
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return this.toursRepository.findRoutsNotSells();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.toursRepository.getTop3RoutesWithMaxRating();
    }

    @Override
    public Service getMostDemandedService() {
        return this.toursRepository.getMostDemandedService();
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.toursRepository.getServiceNoAddedToPurchases();
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.toursRepository.getTourGuidesWithRating1();
    }
}
