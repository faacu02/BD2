package unlp.info.bd2.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "purchase")
public class Purchase {

    @Id
    @Field
    private ObjectId id;

    @Field
    private String code;

    @Field
    private float totalPrice;

    @Field
    private Date date;

    @Field
    private User user;

    @Field
    private Route route;

    @Field
    private Review review;

    @Field
    private List<ItemService> itemServiceList = new ArrayList<>();

    public Purchase() {}
    public Purchase(String code, Route route, User user) {
        this.code = code;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
        this.user.addPurchase(this);
    }

    public Purchase(String code, Date date, Route route, User user) {
        this.code = code;
        this.date = date;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
        this.user.addPurchase(this);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalPrice() {
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

    public void addItemService(ItemService itemService) {
        this.itemServiceList.add(itemService);
        this.totalPrice += itemService.getPrice();
    }
}
