package unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findUsersByPurchaseListTotalPriceGreaterThan(float amount);

    @Query("SELECT u FROM User u ORDER BY size(u.purchaseList) DESC")
    List<User> findTop5UsersWithMostPurchases(Pageable pageable);


    List<User> findByPurchaseListTotalPriceGreaterThanEqual(float amount);

    @Query("""
    SELECT u
    FROM User u
    JOIN u.purchaseList p
    GROUP BY u
    HAVING COUNT(p) >= :number
""")
    List<User> findUsersWithExactlyNumberOfPurchases(@Param("number") long number);
}
