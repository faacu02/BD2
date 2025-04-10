package unlp.info.bd2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver_users")
@PrimaryKeyJoinColumn(name = "user_id")

public class DriverUser extends User {

    @Column(nullable = false, unique = true, length = 50)
    private String expedient;

    @ManyToMany(mappedBy = "driverList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Route> routes = new ArrayList<>();

    // Constructores
    public DriverUser() {
        // Constructor vacío requerido por JPA
    }

    public DriverUser(String username, String password, String name, String email, String expedient) {
        super(username, password, name, email); // Llama al constructor de User
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
}
