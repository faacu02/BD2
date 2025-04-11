package unlp.info.bd2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false, name = "total_km")
    private float totalKm;

    @Column(nullable = false, name = "max_number_users")
    private int maxNumberUsers;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}) //MERGE????
    @JoinTable(
            name = "route_stop",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id")
    )
    private List<Stop> stops = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "route_driver",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private List<DriverUser> driverList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "route_tour_guide",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_guide_id")
    )
    private List<TourGuideUser> tourGuideList = new ArrayList<>();

    // Constructores
    public Route() {
    }

    public Route(String name, float price, float totalKm, int maxNumberUsers) {
        this.name = name;
        this.price = price;
        this.totalKm = totalKm;
        this.maxNumberUsers = maxNumberUsers;
    }

    public Route(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) {
        this.name = name;
        this.price = price;
        this.totalKm = totalKm;
        this.maxNumberUsers = maxNumberOfUsers;
        this.stops = stops;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(float totalKm) {
        this.totalKm = totalKm;
    }

    public int getMaxNumberUsers() {
        return maxNumberUsers;
    }

    public void setMaxNumberUsers(int maxNumberUsers) {
        this.maxNumberUsers = maxNumberUsers;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<DriverUser> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverUser> driverList) {
        this.driverList = driverList;
    }

    public List<TourGuideUser> getTourGuideList() {
        return tourGuideList;
    }

    public void setTourGuideList(List<TourGuideUser> tourGuideList) {
        this.tourGuideList = tourGuideList;
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }
    public void addDriver(DriverUser driver) {
        this.driverList.add(driver);
    }
    public void addTourGuideUser(TourGuideUser tourGuideUser) {
        this.tourGuideList.add(tourGuideUser);
    }
}
