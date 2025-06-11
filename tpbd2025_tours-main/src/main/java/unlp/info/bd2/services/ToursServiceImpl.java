package unlp.info.bd2.services;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;
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
import unlp.info.bd2.repositories.UserRepository;


@org.springframework.stereotype.Service

public class ToursServiceImpl implements ToursService {

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
    @Autowired
    private DriverUserRepository driverUserRepository;
    @Autowired
    private TourGuideUserRepository tourGuideUserRepository;

    public ToursServiceImpl (){

    }

    @Transactional
    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        try {
            // Verifica si el username ya existe
            if (userRepository.existsByUsername(username)) {
                throw new ToursException("El nombre de usuario ya está en uso.");
            }

            // Verifica si el email ya existe
            if (userRepository.existsByEmail(email)) {
                throw new ToursException("El correo electrónico ya está en uso.");
            }

            User user = new User(username, password, fullName, email, birthdate, phoneNumber);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ToursException("Error guardando el usuario.");
        }
    }


    @Transactional
    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        try {
            DriverUser user = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
            return (DriverUser) this.userRepository.save(user);
        }
        catch (Exception e) {
            throw new ToursException("Error Guardando Usuario");
        }
    }

    @Transactional
    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        try {
            TourGuideUser user = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
            return (TourGuideUser) this.userRepository.save(user);
        }
        catch (Exception e) {
            throw new ToursException("Error Guardando Usuario");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserById(ObjectId id) throws ToursException {
        try {
            return this.userRepository.findById(id);
        }
        catch (Exception e) {
            throw new ToursException("Error buscando Usuario");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return this.userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public User updateUser(User user) throws ToursException {
        try {
            return this.userRepository.save(user);
        }
        catch (Exception e) {
            throw new ToursException("Error guardando Usuario");
        }
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
        try {
            Stop stop = new Stop(name, description);
            return this.stopRepository.save(stop);
        }
        catch (Exception e) {
            throw new ToursException("Error guardando Stop");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stop> getStopByNameStart(String name) {
        return this.stopRepository.findByNameStartingWith(name);
    }

    @Transactional
    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        try {
            Route route = new Route(name, price, totalKm, maxNumberOfUsers, stops);
            return this.routeRepository.save(route);
        }
        catch (Exception e) {
            throw new ToursException("Error guardando Route");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Route> getRouteById(ObjectId id) {
        return this.routeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return this.routeRepository.findByPriceLessThan(price);
    }

    @Transactional
    @Override
    public void assignDriverByUsername(String username, ObjectId idRoute) throws ToursException {
        DriverUser user = this.driverUserRepository.findByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));

        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        user.addRoute(route);
        route.addDriver(user);
        this.routeRepository.save(route);//Al no haber cascade hay q guardar manual
        this.updateUser(user);
    }

    @Transactional
    @Override
    public void assignTourGuideByUsername(String username, ObjectId idRoute) throws ToursException {
        TourGuideUser user = this.tourGuideUserRepository.findByUsername(username)
                .orElseThrow(() -> new ToursException("User not found"));


        Route route = this.getRouteById(idRoute)
                .orElseThrow(() -> new ToursException("Route not found"));

        user.addRoute(route);
        route.addTourGuide(user);
        this.routeRepository.save(route);
        this.updateUser(user);
    }

    @Transactional
    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        // 1. Validación explícita
        if (supplierRepository.existsByAuthorizationNumber(authorizationNumber)) {
            throw new ToursException("Ya existe un proveedor con este número de autorización");
        }

        // 2. Validación de parámetros
        if (businessName == null || businessName.trim().isEmpty()) {
            throw new ToursException("El nombre del proveedor no puede estar vacío");
        }

        try {
            Supplier supplier = new Supplier(businessName, authorizationNumber);
            return supplierRepository.save(supplier);
        } catch (DataIntegrityViolationException e) {
            throw new ToursException("Error de integridad de datos: ");
        } catch (Exception e) {
            throw new ToursException("Error inesperado al crear el proveedor");
        }
    }


    @Transactional
    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        try {
            Service service = new Service(name, price, description, supplier);
            this.serviceRepository.save(service);
            supplier.addService(service);
            this.supplierRepository.save(supplier);
            return service;
        }
        catch (Exception e) {
            throw new ToursException("Error al crear el service");
        }
    }

    @Transactional
    @Override
    public Service updateServicePriceById(ObjectId id, float newPrice) throws ToursException {
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
    public Optional<Supplier> getSupplierById(ObjectId id){
        return this.supplierRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return this.supplierRepository.findByAuthorizationNumber(authorizationNumber);
    }



    @Transactional(readOnly = true)
    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, ObjectId id) throws ToursException {
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
            return this.createPurchase(code, new Date(), route, user);
        }
        catch (Exception e) {
            throw new ToursException("Error al crear el purchase");
        }
    }

    @Transactional
    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        try {
            if (this.purchaseRepository.existsByCode(code)) {
                throw new ToursException("El code ya existe");
            }
            if (this.purchaseRepository.countByRouteAndDate(route, date) < route.getMaxNumberUsers()) {
                Purchase purchase = new Purchase(code, date, route, user);

                user.addPurchase(purchase); // actualizar referencia en usuario

                this.purchaseRepository.save(purchase); // guardar compra
                this.updateUser(user);         // guardar usuario con referencia actualizada

                return purchase;
            } else {
                throw new ToursException("No hay lugares disponibles");
            }
        } catch (Exception e) {
            throw new ToursException("No puede realizarse la compra");
        }
    }

    @Transactional
    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        try {
            ItemService item = new ItemService(quantity, purchase, service);
            ItemService itemService = (ItemService) this.itemServiceRepository.save(item);
            purchase.addItemService(item);
            service.addItemService(item);
            this.purchaseRepository.save(purchase);
            this.serviceRepository.save(service);
            return itemService;
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
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
            this.purchaseRepository.delete(purchase);
        }
        catch (Exception e){
            throw new ToursException("Error al eliminar el purchase");
        }
    }

    @Transactional
    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        if (purchase == null) {
            throw new ToursException("La compra no puede ser nula.");
        }

        if (purchase.getReview() != null) {
            throw new ToursException("La compra ya tiene una reseña asociada.");
        }

        if (rating < 1 || rating > 5) {
            throw new ToursException("La puntuación debe estar entre 1 y 5.");
        }

        try {
            Review review = new Review(rating, comment, purchase);
            Review reviewPersisted = this.reviewRepository.save(review);
            purchase.setReview(reviewPersisted); // Asocia la review a la compra
            this.purchaseRepository.save(purchase);
            return reviewPersisted; // Guarda la review

        } catch (Exception e) {
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
        return this.userRepository.findByPurchaseListTotalPriceGreaterThanEqual(amount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.supplierRepository.findTopSuppliers(PageRequest.of(0, n));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesWithServices() {
        return this.purchaseRepository.findTop10ByItemServiceListIsNotEmptyOrderByTotalPriceDesc();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getTop5UsersMorePurchases() {
        return this.userRepository.findTop5UsersWithMostPurchases();

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
        return this.routeRepository.findByStopsContaining(stop);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getMaxStopOfRoutes() {

        return this.routeRepository.findMaxStopOfRoutes();


    }

    @Transactional(readOnly = true)
    @Override
    public List<Route> getRoutsNotSell() {
        List<ObjectId> soldRouteIds = purchaseRepository.findAll().stream()
                .map(p -> p.getRoute().getId())
                .distinct()
                .collect(Collectors.toList());

        return routeRepository.findByIdNotIn(soldRouteIds);
    }


    @Transactional(readOnly = true)
    @Override
    public Service getMostDemandedService() {
        Pageable pageable = PageRequest.of(0, 1);
        return this.serviceRepository.findMostDemandedService(pageable).get(0);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.serviceRepository.findByItemServiceListIsEmpty();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.tourGuideUserRepository.findTourGuidesWithRating1();
    }
    @Override
    public DriverUser getDriverUserWithMoreRoutes() {
        return this.driverUserRepository.findTopDriverByRouteCount().orElse(null);
    }

    @Override
    public Route getMostBestSellingRoute() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<Route> route = this.routeRepository.getMostBoughtRoute(pageRequest);
        return route.isEmpty() ? null : route.get().iterator().next();

    }
    @Override
    public List<Supplier> getTopNSuppliersItemsSold(int n) {
        PageRequest pageRequest = PageRequest.of(0, n);
        Page<Supplier> results = this.supplierRepository.findTopSuppliersByItemsSold(pageRequest);
        return results.getContent();
    }

    @Override
    public List<Purchase> getPurchaseWithService(Service service) {
        return this.purchaseRepository.findByItemServiceListServiceId(service.getId());
    }
    @Override
    public Long getMaxServicesOfSupplier() {
        return this.supplierRepository.findMaxServicesPerSupplier();
    }

    @Override
    public List<Route> getTop3RoutesWithMoreStops() {
        return this.routeRepository.getTop3RoutesWithMoreStops();
    }
    @Override
    public List<Route> getRoutesWithMinRating() {
        return this.routeRepository.findRoutesWithBadReviews();
    }
    @Override
    public List<Route> getTop3RoutesWithMaxAverageRating() {
        return this.routeRepository.findTop3RoutesByAverageRating();
    }
    @Override
    public List<User> getUsersWithNumberOfPurchases(int number) {
        return userRepository.findUsersWithAtLeastNumberOfPurchases(number);
    }
}
