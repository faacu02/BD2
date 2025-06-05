package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Stop;



@Repository
public interface StopRepository extends MongoRepository<Stop, String> {
    List<Stop> findByNameStartingWith(String name);
}
