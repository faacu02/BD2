package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

    Optional<Service> findByNameAndSupplierId(String name, Long supplierId);

}
