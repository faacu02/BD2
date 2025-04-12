package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ToursRepositoryImpl() {
    }

    @Override
    @Transactional
    public Supplier saveSupplier(Supplier supplier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(supplier);
        return supplier;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query<Supplier> query = session.createQuery(
                "FROM Supplier s WHERE s.authorizationNumber = :auth", Supplier.class);
        query.setParameter("auth", authorizationNumber);
        Optional<Supplier> result = query.uniqueResultOptional();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> findSupplierById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Supplier supplier = session.find(Supplier.class, id);
        return Optional.ofNullable(supplier);
    }

    @Override
    @Transactional
    public Service saveService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(service);
        return service;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public Service updatePriceService(Long id, float newPrice) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Service existingService = session.get(Service.class, id);

            if (existingService == null) {
                throw new ToursException("El servicio con ID " + id + " no existe.");
            }

            existingService.setPrice(newPrice);
            return existingService;

        } catch (Exception e) {
            throw new ToursException("Error al actualizar el precio del servicio");
        }
    }

    @Override
    @Transactional
    public User saveUser(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(user);
            return user;
        } catch (Exception e) {
            throw new ToursException("Error saving user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new ToursException("Error finding user by id");
        }
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public void deleteUser(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
        } catch (Exception e) {
            throw new ToursException("Error deleting user");
        }
    }


    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(user);
            return user;
        } catch (Exception e) {
            throw new ToursException("Error updating user");
        }
    }

    //Stops
    @Transactional
    @Override
    public Stop saveStop(Stop stop) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(stop);
            return stop;
        } catch (Exception e) {
            throw new ToursException("Error updating stop");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Stop> findStopByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Stop WHERE name LIKE :name", Stop.class)
                .setParameter("name", name + "%")
                .list();
    }

    //Route
    @Transactional
    @Override
    public Route saveRoute(Route route) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(route);
            return route;
        } catch (Exception e) {
            throw new ToursException("Error updating route");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Route> findRouteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Route.class, id));

    }

    @Transactional(readOnly = true)
    @Override
    public List<Route> findRouteBelowPrice(float price) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Route WHERE price < :price", Route.class)
                .setParameter("price", price)
                .list();
    }

    //HQL
    //ROUTES
    @Transactional(readOnly = true)
    @Override
    public List<Route> findRoutesWithStop(Stop stop) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT r FROM Route r JOIN r.stops s WHERE s.id = :stopId", Route.class)
                .setParameter("stopId", stop.getId())
                .list();
    }

    @Transactional(readOnly = true)
    @Override
    public Long findMaxStopOfRoutes() {
        Session session = sessionFactory.getCurrentSession();
        Integer result = session.createQuery(
                "SELECT MAX(size(r.stops)) FROM Route r", Integer.class
        ).uniqueResult();

        return result != null ? result.longValue() : 0L;
    }

    @Transactional(readOnly = true)
    @Override
    public Service getMostDemandedService() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT isv.service " +
                "FROM ItemService isv " +
                "GROUP BY isv.service " +
                "ORDER BY COUNT(isv.id) DESC";

        Query<Service> query = session.createQuery(hql, Service.class);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT s FROM Service s WHERE s.itemServiceList IS EMPTY";
        Query<Service> query = session.createQuery(hql, Service.class);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
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


}
