package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Supplier;

import java.util.Optional;

@Repository
@Transactional // Aplica a todos los métodos de la clase
public class  SupplierRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public Supplier save(Supplier supplier) {
        return getSession().merge(supplier); // Hibernate 6+
    }


    public Optional<Supplier> findByAuthorizationNumber(String authorizationNumber) {
        Query<Supplier> query = getSession().createQuery(
                "FROM Supplier s WHERE s.authorizationNumber = :auth", Supplier.class);
        query.setParameter("auth", authorizationNumber);
        return query.uniqueResultOptional();
    }


    public boolean existsByAuthorizationNumber(String authorizationNumber) {
        Query<Long> query = getSession().createQuery(
                "SELECT COUNT(s.id) FROM Supplier s WHERE s.authorizationNumber = :auth", Long.class);
        query.setParameter("auth", authorizationNumber);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }


    public Optional<Supplier> findById(Long id) {
        Supplier supplier = getSession().get(Supplier.class, id);
        return Optional.ofNullable(supplier);
    }
}
