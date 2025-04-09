package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import unlp.info.bd2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(user);
            return user;
        } catch (Exception e) {
            throw new ToursException("Error saving user");
        }
    }

    @Override
    public Optional<User> findById(Long id) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            throw new ToursException("Error finding user by id");
        }
    }
    
    @Override
    public Optional<User> findByUsername(String username) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM User WHERE username = :username", User.class)
                          .setParameter("username", username)
                          .uniqueResultOptional();
        } catch (Exception e) {
            throw new ToursException("Error finding user by username");
        }
    }
    
    @Override
    public void delete(User user) throws ToursException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
        } catch (Exception e) {
            throw new ToursException("Error deleting user");
        }
    }

}
