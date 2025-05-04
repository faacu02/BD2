package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Review;
import org.springframework.stereotype.Repository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

}
