package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.utils.ToursException;
import unlp.info.bd2.model.User;
import java.util.Optional;
import org.hibernate.Session;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ToursRepositoryImpl() {
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(supplier);
        return supplier;
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
    public Service saveService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(service);
        return service;
    }


    @Override
    public Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery(
                "FROM Service s WHERE s.name = :name AND s.supplier.id = :supplierId", Service.class);
        query.setParameter("name", name);
        query.setParameter("supplierId", supplierId);
        Optional<Service> result = query.uniqueResultOptional();
        return result;
    }

    @Override
    public Service updatePriceService(Long id, float newPrice) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();

            // Obtener el servicio desde la base de datos
            Service existingService = session.get(Service.class, id);

            if (existingService == null) {
                throw new ToursException("El servicio con ID " + id + " no existe.");
            }

            // Actualizar el precio
            existingService.setPrice(newPrice);

            // No es necesario llamar a update() si el objeto está ya en estado persistente
            // Hibernate sincronizará automáticamente los cambios al hacer commit

            return existingService;

        } catch (Exception e) {
            throw new ToursException("Error al actualizar el precio del servicio");
        }
    }
    }
    @Override
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

    @Override
    public void deleteUser(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
        } catch (Exception e) {
            throw new ToursException("Error deleting user");
        }
    }
    @Override
    public User updateUser(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(user);
            return user;
        } catch (Exception e) {
            throw new ToursException("Error updating user");
        }
}
