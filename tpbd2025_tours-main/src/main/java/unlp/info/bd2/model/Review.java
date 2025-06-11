package unlp.info.bd2.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "review")
public class Review {

    @Id
    @Field
    private ObjectId id;

    @Field
    private int rating;

    @Field
    private String comment;

    @DBRef
    private Purchase purchase;


    public Review() {

    }

    public Review(int rating, String comment, Purchase purchase) {
        this.rating = rating;
        this.comment = comment;
        this.purchase = purchase;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
