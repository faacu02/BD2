package unlp.info.bd2.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import unlp.info.bd2.model.DriverUser;


import java.util.Optional;

@Repository
public interface DriverUserRepository extends MongoRepository<DriverUser, ObjectId> {
    Optional<DriverUser> findByUsername(String username);

    @Query("SELECT d FROM DriverUser d LEFT JOIN d.routes r GROUP BY d ORDER BY COUNT(r) DESC")
    Page<DriverUser> findTopDriverByRouteCount(Pageable pageable);









}
