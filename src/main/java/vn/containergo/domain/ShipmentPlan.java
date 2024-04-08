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

/**
 * A ShipmentPlan.
 */
@Document(collection = "shipment_plan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipmentPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

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
    @Field("driver_id")
    private UUID driverId;

    @NotNull
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

    public ShipmentPlan id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getEstimatedPickupFromDate() {
        return this.estimatedPickupFromDate;
    }

    public ShipmentPlan estimatedPickupFromDate(Instant estimatedPickupFromDate) {
        this.setEstimatedPickupFromDate(estimatedPickupFromDate);
        return this;
    }

    public void setEstimatedPickupFromDate(Instant estimatedPickupFromDate) {
        this.estimatedPickupFromDate = estimatedPickupFromDate;
    }

    public Instant getEstimatedPickupUntilDate() {
        return this.estimatedPickupUntilDate;
    }

    public ShipmentPlan estimatedPickupUntilDate(Instant estimatedPickupUntilDate) {
        this.setEstimatedPickupUntilDate(estimatedPickupUntilDate);
        return this;
    }

    public void setEstimatedPickupUntilDate(Instant estimatedPickupUntilDate) {
        this.estimatedPickupUntilDate = estimatedPickupUntilDate;
    }

    public Instant getEstimatedDropoffFromDate() {
        return this.estimatedDropoffFromDate;
    }

    public ShipmentPlan estimatedDropoffFromDate(Instant estimatedDropoffFromDate) {
        this.setEstimatedDropoffFromDate(estimatedDropoffFromDate);
        return this;
    }

    public void setEstimatedDropoffFromDate(Instant estimatedDropoffFromDate) {
        this.estimatedDropoffFromDate = estimatedDropoffFromDate;
    }

    public Instant getEstimatedDropoffUntilDate() {
        return this.estimatedDropoffUntilDate;
    }

    public ShipmentPlan estimatedDropoffUntilDate(Instant estimatedDropoffUntilDate) {
        this.setEstimatedDropoffUntilDate(estimatedDropoffUntilDate);
        return this;
    }

    public void setEstimatedDropoffUntilDate(Instant estimatedDropoffUntilDate) {
        this.estimatedDropoffUntilDate = estimatedDropoffUntilDate;
    }

    public UUID getDriverId() {
        return this.driverId;
    }

    public ShipmentPlan driverId(UUID driverId) {
        this.setDriverId(driverId);
        return this;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public UUID getTruckId() {
        return this.truckId;
    }

    public ShipmentPlan truckId(UUID truckId) {
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

    public ShipmentPlan container(Container container) {
        this.setContainer(container);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentPlan)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipmentPlan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentPlan{" +
            "id=" + getId() +
            ", estimatedPickupFromDate='" + getEstimatedPickupFromDate() + "'" +
            ", estimatedPickupUntilDate='" + getEstimatedPickupUntilDate() + "'" +
            ", estimatedDropoffFromDate='" + getEstimatedDropoffFromDate() + "'" +
            ", estimatedDropoffUntilDate='" + getEstimatedDropoffUntilDate() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", truckId='" + getTruckId() + "'" +
            "}";
    }
}
