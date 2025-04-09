package unlp.info.bd2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.repositories.SupplierDAO;
import unlp.info.bd2.repositories.ServiceDAO;
import unlp.info.bd2.utils.ToursException;

import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        if (supplierDAO.existsByAuthorizationNumber(authorizationNumber)) {
            throw new ToursException("Supplier with this authorization number already exists.");
        }

        Supplier supplier = new Supplier();
        supplier.setBusinessName(businessName);
        supplier.setAuthorizationNumber(authorizationNumber);
        return supplierDAO.save(supplier);
    }

    public unlp.info.bd2.model.Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        if (supplier == null || supplier.getId() == null) {
            throw new ToursException("Supplier is null or not persisted.");
        }

        unlp.info.bd2.model.Service newService = new unlp.info.bd2.model.Service();
        newService.setName(name);
        newService.setPrice(price);
        newService.setDescription(description);
        newService.setSupplier(supplier);

        // Guardás el service
        unlp.info.bd2.model.Service saved = serviceDAO.save(newService);

        // 💡 Refrescás el supplier desde la base de datos
        supplier = supplierDAO.findById(supplier.getId()).orElseThrow(() -> new ToursException("Supplier not found after saving service"));

        return saved;
    }






    public unlp.info.bd2.model.Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        return serviceDAO.findById(id)
                .map(service -> {
                    service.setPrice(newPrice);
                    return serviceDAO.save(service);
                })
                .orElseThrow(() -> new ToursException("Service not found with ID: " + id));
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierDAO.findById(id);
    }

    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierDAO.findByAuthorizationNumber(authorizationNumber);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceByNameAndSupplierId(String name, Long supplierId) throws ToursException {
        return serviceDAO.findByNameAndSupplierId(name, supplierId);
    }
}
