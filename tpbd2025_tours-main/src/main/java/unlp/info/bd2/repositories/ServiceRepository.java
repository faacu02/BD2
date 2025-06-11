package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, ObjectId> {

    Optional<Service> findByNameAndSupplierId(String name, ObjectId supplierId);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$name', count: { $sum: 1 }, serviceId: { $first: '$_id' } } }",
            "{ $sort: { count: -1 } }",
            "{ $limit: 1 }",
            "{ $lookup: { from: 'service', localField: 'serviceId', foreignField: '_id', as: 'service' } }",
            "{ $unwind: '$service' }",
            "{ $replaceRoot: { newRoot: '$service' } }"
    })
    List<Service> findMostDemandedService(Pageable pageable);

    List<Service> findByItemServiceListIsEmpty();

}
