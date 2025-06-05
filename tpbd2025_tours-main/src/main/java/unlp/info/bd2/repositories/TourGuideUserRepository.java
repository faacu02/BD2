package unlp.info.bd2.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.TourGuideUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourGuideUserRepository extends MongoRepository<TourGuideUser, Long> {
    Optional<TourGuideUser> findByUsername(String username);

    @Query("""
        SELECT DISTINCT tg
        FROM TourGuideUser tg
        JOIN tg.routes r
        JOIN Purchase p ON p.route = r
        JOIN Review rev ON rev.purchase = p
        WHERE rev.rating = 1
    """)
    List<TourGuideUser> findTourGuidesWithRating1();

}
