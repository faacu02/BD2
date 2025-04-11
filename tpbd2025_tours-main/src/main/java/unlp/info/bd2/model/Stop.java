package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stop")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToMany(mappedBy = "stops",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //mappedBy va?
    private List<Route> routes = new ArrayList<>();
    // Constructores
    public Stop() {
        // Constructor vacío requerido por JPA
    }

    public Stop(String name, String description, List<Route> routes) {
        this.name = name;
        this.description = description;
        this.routes = routes;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
