package unlp.info.bd2.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "supplier")
public class Supplier {

    @MongoId
    @Field
    private ObjectId id;

    @Field
    private String businessName;

    @Field
    private String authorizationNumber;

    @DBRef(lazy = true)
    private List<Service> services = new ArrayList<>();


    public Supplier() {
    }

    public Supplier(String businessName, String authorizationNumber) {
        this.businessName = businessName;
        this.authorizationNumber = authorizationNumber;
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }


}
