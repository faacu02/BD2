package unlp.info.bd2.repositories;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import java.util.Optional;
import org.hibernate.Session;

@Repository
@Transactional
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private UserRepositoryImpl userRepository;

    public ToursRepositoryImpl(SupplierDAO supplierDAO, ServiceDAO serviceDAO, UserRepositoryImpl userRepository) {

    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierDAO.save(supplier);
    }

    @Override
    public Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierDAO.findByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public boolean existsSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierDAO.existsByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        return supplierDAO.findById(id);
    }

    @Override
    public Service saveService(Service service) {
        return serviceDAO.save(service);
    }

    @Override
    public Optional<Service> findServiceById(Long id) {
        return serviceDAO.findById(id);
    }

    @Override
    public Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) {
        return serviceDAO.findByNameAndSupplierId(name, supplierId);
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
    p
}
