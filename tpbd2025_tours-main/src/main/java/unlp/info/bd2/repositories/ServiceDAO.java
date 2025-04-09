package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.Service;

import java.util.Optional;

@Repository
@Transactional
public class ServiceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public Service save(Service service) {
        return getSession().merge(service); // Hibernate 6+
    }


    public Optional<Service> findById(Long id) {
        Service service = getSession().get(Service.class, id);
        return Optional.ofNullable(service);
    }


    public Optional<Service> findByNameAndSupplierId(String name, Long supplierId) {
        Query<Service> query = getSession().createQuery(
                "FROM Service s WHERE s.name = :name AND s.supplier.id = :supplierId", Service.class);
        query.setParameter("name", name);
        query.setParameter("supplierId", supplierId);
        return query.uniqueResultOptional();
    }
}


