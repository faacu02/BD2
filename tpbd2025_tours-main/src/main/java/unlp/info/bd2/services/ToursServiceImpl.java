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
        return this.toursRepository.saveUser(user);
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        DriverUser user = new DriverUser(username, password, fullName, email, birthdate, phoneNumber,expedient);
        return (DriverUser) this.toursRepository.saveUser(user);
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser user = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber,education);
        return (TourGuideUser) this.toursRepository.saveUser(user);
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
        return this.toursRepository.updateUser(user);
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        this.toursRepository.deleteUser(user);
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        return null;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return List.of();
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        return null;
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return List.of();
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        // No hace nada
    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        // No hace nada
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try {
            Supplier supplier = new Supplier(businessName, authorizationNumber);
            return toursRepository.saveSupplier(supplier);
        } catch (PersistenceException e) {
            // 🔁 Convertirla en una excepción propia
            throw new ToursException("El proveedor ya existe con ese número de autorización");
        }
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        Service service = new Service(name, price, description, supplier);
        supplier.getServices().add(service);
        return this.toursRepository.saveService(service);
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return this.toursRepository.updatePriceService(id, newPrice);
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
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
        return null;
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        return null;
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        return null;
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return Optional.empty();
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        // No hace nada
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        return null;
    }

    // CONSULTAS HQL

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return List.of();
    }

    @Override
    public List<User> getUserSpendingMoreThan(float amount) {
        return List.of();
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return List.of();
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return List.of();
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        return List.of();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return 0;
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return List.of();
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return 0L;
    }

    @Override
    public List<Route> getRoutsNotSell() {
        return List.of();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return List.of();
    }

    @Override
    public Service getMostDemandedService() {
        return null;
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return List.of();
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return List.of();
    }
}
