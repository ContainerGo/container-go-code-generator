package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.containergo.domain.enumeration.OfferState;

/**
 * A Offer.
 */
@Document(collection = "offer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Field("message")
    private String message;

    @NotNull
    @Field("estimated_pickup_from_date")
    private Instant estimatedPickupFromDate;

    @NotNull
    @Field("estimated_pickup_until_date")
    private Instant estimatedPickupUntilDate;

    @NotNull
    @Field("estimated_dropoff_from_date")
    private Instant estimatedDropoffFromDate;

    @NotNull
    @Field("estimated_dropoff_until_date")
    private Instant estimatedDropoffUntilDate;

    @NotNull
    @Field("state")
    private OfferState state;

    @NotNull
    @Field("price")
    private Double price;

    @NotNull
    @Field("carrier_id")
    private UUID carrierId;

    @Field("truck_id")
    private UUID truckId;

    @DBRef
    @Field("container")
    @JsonIgnoreProperties(
        value = {
            "shipmentPlans",
            "pickupProvice",
            "pickupDistrict",
            "pickupWard",
            "dropoffProvice",
            "dropoffDistrict",
            "dropoffWard",
            "type",
            "status",
            "truckType",
            "truck",
            "owner",
        },
        allowSetters = true
    )
    private Container container;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Offer id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public Offer message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getEstimatedPickupFromDate() {
        return this.estimatedPickupFromDate;
    }

    public Offer estimatedPickupFromDate(Instant estimatedPickupFromDate) {
        this.setEstimatedPickupFromDate(estimatedPickupFromDate);
        return this;
    }

    public void setEstimatedPickupFromDate(Instant estimatedPickupFromDate) {
        this.estimatedPickupFromDate = estimatedPickupFromDate;
    }

    public Instant getEstimatedPickupUntilDate() {
        return this.estimatedPickupUntilDate;
    }

    public Offer estimatedPickupUntilDate(Instant estimatedPickupUntilDate) {
        this.setEstimatedPickupUntilDate(estimatedPickupUntilDate);
        return this;
    }

    public void setEstimatedPickupUntilDate(Instant estimatedPickupUntilDate) {
        this.estimatedPickupUntilDate = estimatedPickupUntilDate;
    }

    public Instant getEstimatedDropoffFromDate() {
        return this.estimatedDropoffFromDate;
    }

    public Offer estimatedDropoffFromDate(Instant estimatedDropoffFromDate) {
        this.setEstimatedDropoffFromDate(estimatedDropoffFromDate);
        return this;
    }

    public void setEstimatedDropoffFromDate(Instant estimatedDropoffFromDate) {
        this.estimatedDropoffFromDate = estimatedDropoffFromDate;
    }

    public Instant getEstimatedDropoffUntilDate() {
        return this.estimatedDropoffUntilDate;
    }

    public Offer estimatedDropoffUntilDate(Instant estimatedDropoffUntilDate) {
        this.setEstimatedDropoffUntilDate(estimatedDropoffUntilDate);
        return this;
    }

    public void setEstimatedDropoffUntilDate(Instant estimatedDropoffUntilDate) {
        this.estimatedDropoffUntilDate = estimatedDropoffUntilDate;
    }

    public OfferState getState() {
        return this.state;
    }

    public Offer state(OfferState state) {
        this.setState(state);
        return this;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public Double getPrice() {
        return this.price;
    }

    public Offer price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UUID getCarrierId() {
        return this.carrierId;
    }

    public Offer carrierId(UUID carrierId) {
        this.setCarrierId(carrierId);
        return this;
    }

    public void setCarrierId(UUID carrierId) {
        this.carrierId = carrierId;
    }

    public UUID getTruckId() {
        return this.truckId;
    }

    public Offer truckId(UUID truckId) {
        this.setTruckId(truckId);
        return this;
    }

    public void setTruckId(UUID truckId) {
        this.truckId = truckId;
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Offer container(Container container) {
        this.setContainer(container);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return getId() != null && getId().equals(((Offer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", estimatedPickupFromDate='" + getEstimatedPickupFromDate() + "'" +
            ", estimatedPickupUntilDate='" + getEstimatedPickupUntilDate() + "'" +
            ", estimatedDropoffFromDate='" + getEstimatedDropoffFromDate() + "'" +
            ", estimatedDropoffUntilDate='" + getEstimatedDropoffUntilDate() + "'" +
            ", state='" + getState() + "'" +
            ", price=" + getPrice() +
            ", carrierId='" + getCarrierId() + "'" +
            ", truckId='" + getTruckId() + "'" +
            "}";
    }
}
