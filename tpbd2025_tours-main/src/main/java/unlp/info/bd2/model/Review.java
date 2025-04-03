package unlp.info.bd2.model;

import jakarta.persistence.*;


@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private int rating;

    @Column(length = 1000)
    private String comment;

    @OneToOne
    @JoinColumn(name = "purchase_id", nullable = false, unique = true)
    private Purchase purchase;

    // Constructores
    public Review() {
        // Constructor vacío requerido por JPA
    }

    public Review(int rating, String comment, Purchase purchase) {
        this.rating = rating;
        this.comment = comment;
        this.purchase = purchase;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
