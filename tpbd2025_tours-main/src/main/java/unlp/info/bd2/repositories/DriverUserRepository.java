package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverUserRepository extends CrudRepository<DriverUser, Long> {
    Optional<DriverUser> findByUsername(String username);


}
