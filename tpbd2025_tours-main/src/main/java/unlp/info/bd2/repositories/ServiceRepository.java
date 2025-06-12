package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;


import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, ObjectId> {

    Optional<Service> findByNameAndSupplierId(String name, ObjectId supplierId);


}
