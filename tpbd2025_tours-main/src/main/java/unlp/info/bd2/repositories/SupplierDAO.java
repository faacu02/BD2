package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Supplier;

import java.util.Optional;

@Repository
public class SupplierDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Supplier save(Supplier supplier) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(supplier); // <-- persist en lugar de merge
        return supplier;
    }

    public Optional<Supplier> findByAuthorizationNumber(String authorizationNumber) {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query<Supplier> query = session.createQuery(
                "FROM Supplier s WHERE s.authorizationNumber = :auth", Supplier.class);
        query.setParameter("auth", authorizationNumber);
        Optional<Supplier> result = query.uniqueResultOptional();
        return result;
    }

    public boolean existsByAuthorizationNumber(String authorizationNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(s.id) FROM Supplier s WHERE s.authorizationNumber = :auth", Long.class);
        query.setParameter("auth", authorizationNumber);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }

    public Optional<Supplier> findById(Long id) {
        Session session = sessionFactory.getCurrentSession(); // No cerrar manualmente
        Supplier supplier = session.get(Supplier.class, id);
        if (supplier != null) {
            session.refresh(supplier); // Esto está bien si querés forzar actualización desde DB
        }
        return Optional.ofNullable(supplier);
    }

}

