package unlp.info.bd2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.SupplierRepository;
import unlp.info.bd2.repositories.ServiceRepository;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        if (supplierRepository.existsByAuthorizationNumber(authorizationNumber)) {
            throw new ToursException("Supplier with this authorization number already exists.");
        }

        Supplier supplier = new Supplier();
        supplier.setBusinessName(businessName);
        supplier.setAuthorizationNumber(authorizationNumber);
        return supplierRepository.save(supplier);
    }

    public unlp.info.bd2.model.Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        if (supplier == null || supplier.getId() == null) {
            throw new ToursException("Supplier is null or not persisted.");
        }

        unlp.info.bd2.model.Service service = new unlp.info.bd2.model.Service();
        service.setName(name);
        service.setPrice(price);
        service.setDescription(description);
        service.setSupplier(supplier);
        return serviceRepository.save(service);
    }

    public unlp.info.bd2.model.Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setPrice(newPrice);
                    return serviceRepository.save(service);
                })
                .orElseThrow(() -> new ToursException("Service not found with ID: " + id));
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierRepository.findByAuthorizationNumber(authorizationNumber);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException {
        return serviceRepository.findByNameAndSupplierId(name, supplierId);
    }
}
