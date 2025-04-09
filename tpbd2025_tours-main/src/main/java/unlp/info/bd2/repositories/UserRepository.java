package unlp.info.bd2.repositories;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import java.util.Optional;
public interface UserRepository {
    User save(User user) throws ToursException;
    Optional<User> findById(Long id) throws ToursException;
    Optional<User> findByUsername(String username) throws ToursException;
    void delete(User user) throws ToursException;
}