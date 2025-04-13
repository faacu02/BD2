package unlp.info.bd2.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private float totalPrice;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToOne(mappedBy = "purchase", cascade = {CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST}, orphanRemoval = true) //MERGE VA???
    private Review review;

    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemService> itemServiceList = new ArrayList<>();
    public Purchase() {}
    public Purchase(String code, Route route, User user) {
        this.code = code;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
    }

    public Purchase(String code, Date date, Route route, User user) {
        this.code = code;
        this.date = date;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalPrice() {
        float total = (float) this.itemServiceList.stream()
                .mapToDouble(item -> item.getPrice())
                .sum();
        this.totalPrice = total + this.route.getPrice();
        return this.totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<ItemService> getItemServiceList() {
        return itemServiceList;
    }

    public void setItemServiceList(List<ItemService> itemServiceList) {
        this.itemServiceList = itemServiceList;
    }

}
