package unlp.info.bd2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("Driver")
public class DriverUser extends User {

    @Column(length = 50)
    private String expedient;
;
    @ManyToMany(mappedBy = "driverList",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Route> routes = new ArrayList<>();

    public DriverUser() {
    }
    public DriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) {
        super(username, password, fullName, email, birthdate, phoneNumber);
        this.expedient = expedient;
    }


    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRouts(List<Route> routs) {
        this.routes = routs;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }
    @Override
    public boolean canBeDeleted() {
        return this.routes.isEmpty();
    }
}
