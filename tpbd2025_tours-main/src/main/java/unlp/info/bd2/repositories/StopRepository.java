package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Stop;

@Repository
public interface StopRepository extends CrudRepository<Stop, Integer> {
    @Query("SELECT s FROM Stop s WHERE s.name LIKE ?1%")
    List<Stop> getStopByNameStart(String name);

}
