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

    @Query("SELECT r FROM Route r LEFT JOIN Purchase p ON r = p.route WHERE p.id IS NULL")
    List<Route> findRoutsNotSells();
    @Query("""
    SELECT r
    FROM Review rev
    JOIN rev.purchase p
    JOIN p.route r
    GROUP BY r.id
    HAVING AVG(rev.rating) = (
        SELECT MIN(avgRating)
        FROM (
            SELECT AVG(rev2.rating) as avgRating
            FROM Review rev2
            JOIN rev2.purchase p2
            JOIN p2.route r2
            GROUP BY r2.id
        )
    )
""")
    List<Route> getRouteWithMinRating();

    @Query("""
    SELECT r, COUNT(s) AS stopCount
    FROM Route r
    JOIN r.stops s
    GROUP BY r
    ORDER BY stopCount DESC
    LIMIT 3
""")
    List<Route> getTop3RoutesWithMoreStops();

}
