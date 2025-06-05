package unlp.info.bd2.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "user")
public class DriverUser extends User {

    private String expedient;
;
    private List<Route> routes = new ArrayList<>();


    public DriverUser() {
        super();
        this.setUserType("Driver");
    }

    public DriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) {
        super(username, password, fullName, email, birthdate, phoneNumber);
        this.expedient = expedient;
        this.setUserType("Driver");
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
