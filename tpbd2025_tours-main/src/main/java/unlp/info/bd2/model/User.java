package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50,updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "phone_number", length = 20) //unique?
    private String phoneNumber;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Purchase> purchaseList = new ArrayList<>();

    public User() {
    }

     public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.active = true;
    }
    public User(String username, String password, String name, String email, Date birthdate, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void addPurchase(Purchase purchase) {
        this.purchaseList.add(purchase);
    }
    public boolean canBeDeleted() {
        return true;
    }
    public void removePurchase(Purchase purchase) {
        this.purchaseList.remove(purchase);
        purchase.setUser(null);
    }

}