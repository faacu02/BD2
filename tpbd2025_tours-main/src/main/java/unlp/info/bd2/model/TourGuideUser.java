package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("Guide")
public class TourGuideUser extends User {

    @Column(length = 100)
    private String education;

    @ManyToMany(mappedBy = "tourGuideList",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Route> routes = new ArrayList<>();

    public TourGuideUser() {
    }

    public TourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) {
        super(username, password, fullName, email, birthdate, phoneNumber);
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }
    @Override
    public boolean canBeDeleted() {
        return this.routes.isEmpty();
    }
}
