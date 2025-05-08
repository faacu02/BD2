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

    @Query("""
        SELECT DISTINCT tg
        FROM TourGuideUser tg
        JOIN tg.routes r
        JOIN Purchase p ON p.route = r
        JOIN Review rev ON rev.purchase = p
        WHERE rev.rating = 1
    """)
    List<TourGuideUser> findTourGuidesWithRating1();

    @Query("""
        SELECT d, COUNT(r) as routeCount
        FROM DriverUser d
        LEFT JOIN d.routes r
        GROUP BY d
        ORDER BY routeCount DESC
        LIMIT 1
    """)
    List<Object[]> findDriverWithMostRoutes();

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.purchaseList p
        WHERE p.totalPrice >= :amount
    """)
    List<User> findUsersWithAnyPurchaseOverAmount(@Param("amount") float amount);

    @Query("""
    SELECT u
    FROM User u
    JOIN u.purchaseList p
    GROUP BY u
    HAVING COUNT(p) >= :number
""")
    List<User> findUsersWithExactlyNumberOfPurchases(@Param("number") long number);
}
