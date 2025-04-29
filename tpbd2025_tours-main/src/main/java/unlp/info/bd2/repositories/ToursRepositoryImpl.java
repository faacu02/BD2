package unlp.info.bd2.repositories;

import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.utils.ToursException;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ToursRepositoryImpl() {
    }

    @Override
    public Object save(Object entity) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(entity);
            return entity;
        } catch (Exception e) {
            throw new ToursException("Error al guardar el objeto.");
        }
    }

    public Object update(Object entity) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(entity);
            return entity;
        } catch (Exception e) {
            throw new ToursException("Error al actualizar el objeto.");
        }
    }

    public void delete(Object entity) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(entity);
        } catch (Exception e) {
            throw new ToursException("Error al eliminar el objeto.");
        }
    }

    @Override
    public Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query<Supplier> query = session.createQuery(
                "FROM Supplier s WHERE s.authorizationNumber = :auth", Supplier.class);
        query.setParameter("auth", authorizationNumber);
        Optional<Supplier> result = query.uniqueResultOptional();
        return result;
    }

    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Supplier supplier = session.find(Supplier.class, id);
        return Optional.ofNullable(supplier);
    }

    @Override
    public Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query<Service> query = session.createQuery(
                    "FROM Service s WHERE s.name = :name AND s.supplier.id = :supplierId", Service.class);
            query.setParameter("name", name);
            query.setParameter("supplierId", supplierId);
            Optional<Service> result = query.uniqueResultOptional();
            return result;
        } catch (Exception e) {
            throw new ToursException("Error al encontrar el servicio por nombre y ID del proveedor");
        }
    }


    @Override
    public Service updatePriceService(Long id, float newPrice) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Service existingService = session.get(Service.class, id);

            if (existingService == null) {
                throw new ToursException("El servicio con ID " + id + " no existe.");
            }

            existingService.setPrice(newPrice);
            session.merge(existingService);
            return existingService;

        } catch (Exception e) {
            throw new ToursException("Error al actualizar el precio del servicio");
        }
    }


    @Override
    public Optional<User> findUserById(Long id) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new ToursException("Error finding user by id");
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new ToursException("Error finding user by username");
        }
    }

    //Stops
    @Override
    public List<Stop> findStopByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Stop WHERE name LIKE :name", Stop.class)
                .setParameter("name", name + "%")
                .list();
    }

    //Route
    @Override
    public Optional<Route> findRouteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Route.class, id));

    }

    @Override
    public List<Route> findRouteBelowPrice(float price) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Route WHERE price < :price", Route.class)
                .setParameter("price", price)
                .list();
    }




    //HQL
    //ROUTES
    @Override
    public List<Route> findRoutesWithStop(Stop stop) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT r FROM Route r JOIN r.stops s WHERE s.id = :stopId", Route.class)
                .setParameter("stopId", stop.getId())
                .list();
    }

    @Override
    public Long findMaxStopOfRoutes() {
        Session session = sessionFactory.getCurrentSession();
        Integer result = session.createQuery(
                "SELECT MAX(size(r.stops)) FROM Route r", Integer.class
        ).uniqueResult();

        return result != null ? result.longValue() : 0L;
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        String hql = """
        SELECT r
        FROM Review rev
        JOIN rev.purchase p
        JOIN p.route r
        GROUP BY r.id
        ORDER BY AVG(rev.rating) DESC
    """;

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Route.class)
                .setMaxResults(3)
                .list();
    }

    @Override
    public Service getMostDemandedService() {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT i.service FROM ItemService i GROUP BY i.service ORDER BY SUM(i.quantity) DESC";
        return session.createQuery(hql, Service.class)
                .setMaxResults(1)
                .uniqueResultOptional()
                .orElse(null);
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT s FROM Service s WHERE s.itemServiceList IS EMPTY";
        Query<Service> query = session.createQuery(hql, Service.class);
        return query.list();
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
            SELECT isv.service.supplier
            FROM ItemService isv
            GROUP BY isv.service.supplier
            ORDER BY COUNT(isv.id) DESC
        """;

        Query<Supplier> query = session.createQuery(hql, Supplier.class);
        query.setMaxResults(n);
        return query.list();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        String hql = "SELECT COUNT(p) FROM Purchase p WHERE p.date BETWEEN :start AND :end";
        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Long.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        String hql = """
        SELECT DISTINCT tgu
        FROM Review r
        JOIN r.purchase p
        JOIN p.route rt
        JOIN rt.tourGuideList tgu
        WHERE r.rating = 1
    """;

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, TourGuideUser.class)
                .list();
    }

    @Override
    public List<Purchase> findTop10MoreExpensivePurchasesInServices() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p FROM Purchase p JOIN FETCH p.route JOIN FETCH p.itemServiceList";
        List<Purchase> purchases = session.createQuery(hql, Purchase.class).getResultList();
        return purchases.stream()
                .sorted(Comparator.comparingDouble(Purchase::getTotalPrice).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Purchase> findPurchaseByCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        Query<Purchase> query = session.createQuery("FROM Purchase p WHERE p.code = :code", Purchase.class);
        query.setParameter("code", code);
        return query.uniqueResultOptional();
    }

    @Override
    public int getCountOfPurchasesInRouteAndDate(Route route, Date date) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(p) FROM Purchase p WHERE p.route = :route AND p.date = :date";
        return session.createQuery(hql, Long.class)
                .setParameter("route", route)
                .setParameter("date", date)
                .uniqueResult()
                .intValue();
    }

    //REVIEW
    @Override
    public List<User> getTop5UsersMorePurchases() {
        Session session = sessionFactory.getCurrentSession();
        List<User> allUsers = session.createQuery("FROM User", User.class).getResultList();
        return allUsers.stream()
                .sorted((u1, u2) -> Integer.compare(u2.getPurchaseList().size(), u1.getPurchaseList().size()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUserSpendingMoreThan(float amount) {
        Session session = sessionFactory.getCurrentSession();
        List<User> allUsers = session.createQuery("FROM User", User.class).getResultList();
        return allUsers.stream()
                .filter(user -> user.getPurchaseList().stream()
                        .anyMatch(purchase -> purchase.getTotalPrice() >= amount))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> findRoutsNotSells() {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT r FROM Route r LEFT JOIN Purchase p ON r = p.route WHERE p.id IS NULL";

        return session.createQuery(hql, Route.class).list();
    }


}
