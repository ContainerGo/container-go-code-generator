package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import vn.containergo.domain.enumeration.OfferState;

/**
 * A DTO for the {@link vn.containergo.domain.Offer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfferDTO implements Serializable {

    private UUID id;

    private String message;

    @NotNull
    private Instant estimatedPickupFromDate;

    @NotNull
    private Instant estimatedPickupUntilDate;

    @NotNull
    private Instant estimatedDropoffFromDate;

    @NotNull
    private Instant estimatedDropoffUntilDate;

    @NotNull
    private OfferState state;

    @NotNull
    private Double price;

    @NotNull
    private UUID carrierId;

    private UUID truckId;

    @NotNull
    private ContainerDTO container;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UUID getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(UUID carrierId) {
        this.carrierId = carrierId;
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
        if (!(o instanceof OfferDTO)) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, offerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferDTO{" +
            "id='" + getId() + "'" +
            ", message='" + getMessage() + "'" +
            ", estimatedPickupFromDate='" + getEstimatedPickupFromDate() + "'" +
            ", estimatedPickupUntilDate='" + getEstimatedPickupUntilDate() + "'" +
            ", estimatedDropoffFromDate='" + getEstimatedDropoffFromDate() + "'" +
            ", estimatedDropoffUntilDate='" + getEstimatedDropoffUntilDate() + "'" +
            ", state='" + getState() + "'" +
            ", price=" + getPrice() +
            ", carrierId='" + getCarrierId() + "'" +
            ", truckId='" + getTruckId() + "'" +
            ", container=" + getContainer() +
            "}";
    }
}
