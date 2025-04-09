package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Service;

import java.util.Optional;

@Repository
public class ServiceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Service save(Service service) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(service);  // usamos persist en lugar de merge
        return service;
    }

    public Optional<Service> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Service service = session.get(Service.class, id);
        return Optional.ofNullable(service);
    }

    public Optional<Service> findByNameAndSupplierId(String name, Long supplierId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery(
                "FROM Service s WHERE s.name = :name AND s.supplier.id = :supplierId", Service.class);
        query.setParameter("name", name);
        query.setParameter("supplierId", supplierId);
        Optional<Service> result = query.uniqueResultOptional();
        return result;
    }
}



