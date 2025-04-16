package unlp.info.bd2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "driver_user")
@PrimaryKeyJoinColumn(name = "user_id")

public class DriverUser extends User {

    @Column(length = 50)
    private String expedient;
;
    @ManyToMany(mappedBy = "driverList", cascade =  {CascadeType.MERGE,CascadeType.PERSIST}) //Justificar
    private List<Route> routes = new ArrayList<>();

    // Constructores
    public DriverUser() {
        // Constructor vacío requerido por JPA
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
}
