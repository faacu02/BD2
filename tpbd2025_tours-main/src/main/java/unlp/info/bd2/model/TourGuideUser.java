package unlp.info.bd2.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "user")
public class TourGuideUser extends User {

    @Field
    private String education;

    @DBRef(lazy = false)//????
    private List<Route> routes = new ArrayList<>();

    public TourGuideUser() {
        super();
        this.setUserType("Guide");
    }

    public TourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) {
        super(username, password, fullName, email, birthdate, phoneNumber);
        this.education = education;
        this.setUserType("Guide");
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
