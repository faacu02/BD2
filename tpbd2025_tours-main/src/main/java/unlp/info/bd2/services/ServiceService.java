package unlp.info.bd2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.repositories.ServiceRepository;

import java.util.Optional;

@Service
@Transactional
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public unlp.info.bd2.model.Service createService(unlp.info.bd2.model.Service service) {
        return serviceRepository.save(service);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceByNameAndSupplier(String name, Long supplierId) {
        return serviceRepository.findByNameAndSupplierId(name, supplierId);
    }
}
