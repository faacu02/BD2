package unlp.info.bd2.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "route")
public class Route {

    @MongoId
    private ObjectId id;

    @Field
    private String name;

    @Field
    private float price;

    @Field
    private float totalKm;

    @Field
    private int maxNumberUsers;


    @DBRef(lazy = false)
    private List<Stop> stops = new ArrayList<>();


    @DBRef(lazy = true)
    private List<DriverUser> driverList = new ArrayList<>();


    @DBRef(lazy = true)
    private List<TourGuideUser> tourGuideList = new ArrayList<>();


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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
    public void addTourGuide(TourGuideUser tourGuideUser) {
        this.tourGuideList.add(tourGuideUser);
    }
}
