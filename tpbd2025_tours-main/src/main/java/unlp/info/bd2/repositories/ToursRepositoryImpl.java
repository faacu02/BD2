package unlp.info.bd2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import java.util.Optional;

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
    public User save(User user) throws ToursException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ToursException("Error saving user");
        }
    }

    @Override
    public Optional<User> findById(Long id) throws ToursException {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new ToursException("Error finding user by ID");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws ToursException {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new ToursException("Error finding user by username");
        }
    }

    @Override
    public void delete(User user) throws ToursException {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new ToursException("Error deleting user");
        }
    }
}
