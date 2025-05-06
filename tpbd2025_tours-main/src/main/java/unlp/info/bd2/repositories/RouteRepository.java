package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
    List<Route> findByPriceLessThan(float price);


    @Query("""
        SELECT r, AVG(rev.rating) as avgRating
        FROM Route r
        JOIN Purchase p ON p.route = r
        JOIN Review rev ON rev.purchase = p
        GROUP BY r
        ORDER BY avgRating DESC
        LIMIT 3
    """)
    List<Object[]> findTop3RoutesByAverageRating();

    @Query("""
        SELECT r
        FROM Route r
        JOIN r.stops s
        WHERE s.id = :stopId
    """)
    //"SELECT r FROM Route r JOIN r.stops s WHERE s.id = :stopId"
    List<Route> findRoutesWithStop(@Param("stopId") Long stopId);

    @Query("SELECT MAX(size(r.stops)) FROM Route r")
    Long findMaxStopOfRoutes();

}
