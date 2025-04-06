package unlp.info.bd2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.Service;

import java.util.Optional;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> findSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierRepository.findByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public boolean existsSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierRepository.existsByAuthorizationNumber(authorizationNumber);
    }

    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Optional<Service> findServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public Optional<Service> findServiceByNameAndSupplierId(String name, Long supplierId) {
        return serviceRepository.findByNameAndSupplierId(name, supplierId);
    }
}
