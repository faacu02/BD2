package unlp.info.bd2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;

import java.util.Optional;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    public ToursRepositoryImpl(SupplierDAO supplierDAO) {

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
}
