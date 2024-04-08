package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.ShipmentPlan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipmentPlanDTO implements Serializable {

    private UUID id;

    @NotNull
    private Instant estimatedPickupFromDate;

    @NotNull
    private Instant estimatedPickupUntilDate;

    @NotNull
    private Instant estimatedDropoffFromDate;

    @NotNull
    private Instant estimatedDropoffUntilDate;

    @NotNull
    private UUID driverId;

    @NotNull
    private UUID truckId;

    private ContainerDTO container;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getEstimatedPickupFromDate() {
        return estimatedPickupFromDate;
    }

    public void setEstimatedPickupFromDate(Instant estimatedPickupFromDate) {
        this.estimatedPickupFromDate = estimatedPickupFromDate;
    }

    public Instant getEstimatedPickupUntilDate() {
        return estimatedPickupUntilDate;
    }

    public void setEstimatedPickupUntilDate(Instant estimatedPickupUntilDate) {
        this.estimatedPickupUntilDate = estimatedPickupUntilDate;
    }

    public Instant getEstimatedDropoffFromDate() {
        return estimatedDropoffFromDate;
    }

    public void setEstimatedDropoffFromDate(Instant estimatedDropoffFromDate) {
        this.estimatedDropoffFromDate = estimatedDropoffFromDate;
    }

    public Instant getEstimatedDropoffUntilDate() {
        return estimatedDropoffUntilDate;
    }

    public void setEstimatedDropoffUntilDate(Instant estimatedDropoffUntilDate) {
        this.estimatedDropoffUntilDate = estimatedDropoffUntilDate;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public UUID getTruckId() {
        return truckId;
    }

    public void setTruckId(UUID truckId) {
        this.truckId = truckId;
    }

    public ContainerDTO getContainer() {
        return container;
    }

    public void setContainer(ContainerDTO container) {
        this.container = container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentPlanDTO)) {
            return false;
        }

        ShipmentPlanDTO shipmentPlanDTO = (ShipmentPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipmentPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentPlanDTO{" +
            "id='" + getId() + "'" +
            ", estimatedPickupFromDate='" + getEstimatedPickupFromDate() + "'" +
            ", estimatedPickupUntilDate='" + getEstimatedPickupUntilDate() + "'" +
            ", estimatedDropoffFromDate='" + getEstimatedDropoffFromDate() + "'" +
            ", estimatedDropoffUntilDate='" + getEstimatedDropoffUntilDate() + "'" +
            ", driverId='" + getDriverId() + "'" +
            ", truckId='" + getTruckId() + "'" +
            ", container=" + getContainer() +
            "}";
    }
}
