package unlp.info.bd2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.repositories.ServiceDAO;

import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceDAO serviceDAO;

    public unlp.info.bd2.model.Service createService(unlp.info.bd2.model.Service service) {
        return serviceDAO.save(service);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceById(Long id) {
        return serviceDAO.findById(id);
    }

    public Optional<unlp.info.bd2.model.Service> getServiceByNameAndSupplier(String name, Long supplierId) {
        return serviceDAO.findByNameAndSupplierId(name, supplierId);
    }
}
