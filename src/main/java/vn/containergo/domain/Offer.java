package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
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
    private Long id;

    @Field("message")
    private String message;

    @NotNull
    @Field("pickup_from_date")
    private Instant pickupFromDate;

    @NotNull
    @Field("pickup_until_date")
    private Instant pickupUntilDate;

    @NotNull
    @Field("dropoff_from_date")
    private Instant dropoffFromDate;

    @NotNull
    @Field("dropoff_until_date")
    private Instant dropoffUntilDate;

    @NotNull
    @Field("state")
    private OfferState state;

    @NotNull
    @Field("price")
    private Double price;

    @NotNull
    @Field("carrier_id")
    private Long carrierId;

    @Field("carrier_person_id")
    private Long carrierPersonId;

    @Field("truck_id")
    private Long truckId;

    @DBRef
    @Field("container")
    @JsonIgnoreProperties(
        value = {
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

    public Long getId() {
        return this.id;
    }

    public Offer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
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

    public Instant getPickupFromDate() {
        return this.pickupFromDate;
    }

    public Offer pickupFromDate(Instant pickupFromDate) {
        this.setPickupFromDate(pickupFromDate);
        return this;
    }

    public void setPickupFromDate(Instant pickupFromDate) {
        this.pickupFromDate = pickupFromDate;
    }

    public Instant getPickupUntilDate() {
        return this.pickupUntilDate;
    }

    public Offer pickupUntilDate(Instant pickupUntilDate) {
        this.setPickupUntilDate(pickupUntilDate);
        return this;
    }

    public void setPickupUntilDate(Instant pickupUntilDate) {
        this.pickupUntilDate = pickupUntilDate;
    }

    public Instant getDropoffFromDate() {
        return this.dropoffFromDate;
    }

    public Offer dropoffFromDate(Instant dropoffFromDate) {
        this.setDropoffFromDate(dropoffFromDate);
        return this;
    }

    public void setDropoffFromDate(Instant dropoffFromDate) {
        this.dropoffFromDate = dropoffFromDate;
    }

    public Instant getDropoffUntilDate() {
        return this.dropoffUntilDate;
    }

    public Offer dropoffUntilDate(Instant dropoffUntilDate) {
        this.setDropoffUntilDate(dropoffUntilDate);
        return this;
    }

    public void setDropoffUntilDate(Instant dropoffUntilDate) {
        this.dropoffUntilDate = dropoffUntilDate;
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

    public Long getCarrierId() {
        return this.carrierId;
    }

    public Offer carrierId(Long carrierId) {
        this.setCarrierId(carrierId);
        return this;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Long getCarrierPersonId() {
        return this.carrierPersonId;
    }

    public Offer carrierPersonId(Long carrierPersonId) {
        this.setCarrierPersonId(carrierPersonId);
        return this;
    }

    public void setCarrierPersonId(Long carrierPersonId) {
        this.carrierPersonId = carrierPersonId;
    }

    public Long getTruckId() {
        return this.truckId;
    }

    public Offer truckId(Long truckId) {
        this.setTruckId(truckId);
        return this;
    }

    public void setTruckId(Long truckId) {
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
            ", pickupFromDate='" + getPickupFromDate() + "'" +
            ", pickupUntilDate='" + getPickupUntilDate() + "'" +
            ", dropoffFromDate='" + getDropoffFromDate() + "'" +
            ", dropoffUntilDate='" + getDropoffUntilDate() + "'" +
            ", state='" + getState() + "'" +
            ", price=" + getPrice() +
            ", carrierId=" + getCarrierId() +
            ", carrierPersonId=" + getCarrierPersonId() +
            ", truckId=" + getTruckId() +
            "}";
    }
}
