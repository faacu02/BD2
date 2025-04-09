package unlp.info.bd2.services;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.Date;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.UserRepositoryImpl;
import unlp.info.bd2.utils.ToursException;

@Service
public class UserService {

    @Autowired
    private UserRepositoryImpl userRepository;
    
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User user = new User(username, password, fullName, email, birthdate, phoneNumber);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws ToursException {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) throws ToursException {
        return userRepository.findByUsername(username);
    }
    public User updateUser(User user) throws ToursException {
        return userRepository.save(user);
    }

    public void deleteUser(User user) throws ToursException {
        userRepository.delete(user);
    }
}