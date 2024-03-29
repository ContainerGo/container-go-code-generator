package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import vn.containergo.domain.enumeration.OfferState;

/**
 * A DTO for the {@link vn.containergo.domain.Offer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfferDTO implements Serializable {

    private Long id;

    private String message;

    @NotNull
    private Instant pickupFromDate;

    @NotNull
    private Instant pickupUntilDate;

    @NotNull
    private Instant dropoffFromDate;

    @NotNull
    private Instant dropoffUntilDate;

    @NotNull
    private OfferState state;

    @NotNull
    private Double price;

    @NotNull
    private Long carrierId;

    private Long carrierPersonId;

    private Long truckId;

    @NotNull
    private ContainerDTO container;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getPickupFromDate() {
        return pickupFromDate;
    }

    public void setPickupFromDate(Instant pickupFromDate) {
        this.pickupFromDate = pickupFromDate;
    }

    public Instant getPickupUntilDate() {
        return pickupUntilDate;
    }

    public void setPickupUntilDate(Instant pickupUntilDate) {
        this.pickupUntilDate = pickupUntilDate;
    }

    public Instant getDropoffFromDate() {
        return dropoffFromDate;
    }

    public void setDropoffFromDate(Instant dropoffFromDate) {
        this.dropoffFromDate = dropoffFromDate;
    }

    public Instant getDropoffUntilDate() {
        return dropoffUntilDate;
    }

    public void setDropoffUntilDate(Instant dropoffUntilDate) {
        this.dropoffUntilDate = dropoffUntilDate;
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

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Long getCarrierPersonId() {
        return carrierPersonId;
    }

    public void setCarrierPersonId(Long carrierPersonId) {
        this.carrierPersonId = carrierPersonId;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
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
            ", container=" + getContainer() +
            "}";
    }
}
