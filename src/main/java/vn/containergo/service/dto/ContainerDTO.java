package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import vn.containergo.domain.enumeration.ContainerState;

/**
 * A DTO for the {@link vn.containergo.domain.Container} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContainerDTO implements Serializable {

    private Long id;

    @NotNull
    private String contNo;

    @NotNull
    private Double estimatedPrice;

    @NotNull
    private Double distance;

    @NotNull
    private Double desiredPrice;

    private String additionalRequirements;

    private String dropoffContact;

    private String dropoffContactPhone;

    private String dropoffAddress;

    private Double dropoffLat;

    private Double dropoffLng;

    private Instant dropoffUntilDate;

    @NotNull
    private ContainerState state;

    @NotNull
    private Long shipperId;

    private Long carrierId;

    @NotNull
    private Double totalWeight;

    private Instant pickupFromDate;

    private Instant biddingFromDate;

    private Instant biddingUntilDate;

    private ProviceDTO dropoffProvice;

    private DistrictDTO dropoffDistrict;

    private WardDTO dropoffWard;

    private ContainerTypeDTO type;

    private ContainerStatusDTO status;

    private TruckTypeDTO truckType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(Double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public String getAdditionalRequirements() {
        return additionalRequirements;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }

    public String getDropoffContact() {
        return dropoffContact;
    }

    public void setDropoffContact(String dropoffContact) {
        this.dropoffContact = dropoffContact;
    }

    public String getDropoffContactPhone() {
        return dropoffContactPhone;
    }

    public void setDropoffContactPhone(String dropoffContactPhone) {
        this.dropoffContactPhone = dropoffContactPhone;
    }

    public String getDropoffAddress() {
        return dropoffAddress;
    }

    public void setDropoffAddress(String dropoffAddress) {
        this.dropoffAddress = dropoffAddress;
    }

    public Double getDropoffLat() {
        return dropoffLat;
    }

    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public Double getDropoffLng() {
        return dropoffLng;
    }

    public void setDropoffLng(Double dropoffLng) {
        this.dropoffLng = dropoffLng;
    }

    public Instant getDropoffUntilDate() {
        return dropoffUntilDate;
    }

    public void setDropoffUntilDate(Instant dropoffUntilDate) {
        this.dropoffUntilDate = dropoffUntilDate;
    }

    public ContainerState getState() {
        return state;
    }

    public void setState(ContainerState state) {
        this.state = state;
    }

    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipperId) {
        this.shipperId = shipperId;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Instant getPickupFromDate() {
        return pickupFromDate;
    }

    public void setPickupFromDate(Instant pickupFromDate) {
        this.pickupFromDate = pickupFromDate;
    }

    public Instant getBiddingFromDate() {
        return biddingFromDate;
    }

    public void setBiddingFromDate(Instant biddingFromDate) {
        this.biddingFromDate = biddingFromDate;
    }

    public Instant getBiddingUntilDate() {
        return biddingUntilDate;
    }

    public void setBiddingUntilDate(Instant biddingUntilDate) {
        this.biddingUntilDate = biddingUntilDate;
    }

    public ProviceDTO getDropoffProvice() {
        return dropoffProvice;
    }

    public void setDropoffProvice(ProviceDTO dropoffProvice) {
        this.dropoffProvice = dropoffProvice;
    }

    public DistrictDTO getDropoffDistrict() {
        return dropoffDistrict;
    }

    public void setDropoffDistrict(DistrictDTO dropoffDistrict) {
        this.dropoffDistrict = dropoffDistrict;
    }

    public WardDTO getDropoffWard() {
        return dropoffWard;
    }

    public void setDropoffWard(WardDTO dropoffWard) {
        this.dropoffWard = dropoffWard;
    }

    public ContainerTypeDTO getType() {
        return type;
    }

    public void setType(ContainerTypeDTO type) {
        this.type = type;
    }

    public ContainerStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ContainerStatusDTO status) {
        this.status = status;
    }

    public TruckTypeDTO getTruckType() {
        return truckType;
    }

    public void setTruckType(TruckTypeDTO truckType) {
        this.truckType = truckType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContainerDTO)) {
            return false;
        }

        ContainerDTO containerDTO = (ContainerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, containerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContainerDTO{" +
            "id=" + getId() +
            ", contNo='" + getContNo() + "'" +
            ", estimatedPrice=" + getEstimatedPrice() +
            ", distance=" + getDistance() +
            ", desiredPrice=" + getDesiredPrice() +
            ", additionalRequirements='" + getAdditionalRequirements() + "'" +
            ", dropoffContact='" + getDropoffContact() + "'" +
            ", dropoffContactPhone='" + getDropoffContactPhone() + "'" +
            ", dropoffAddress='" + getDropoffAddress() + "'" +
            ", dropoffLat=" + getDropoffLat() +
            ", dropoffLng=" + getDropoffLng() +
            ", dropoffUntilDate='" + getDropoffUntilDate() + "'" +
            ", state='" + getState() + "'" +
            ", shipperId=" + getShipperId() +
            ", carrierId=" + getCarrierId() +
            ", totalWeight=" + getTotalWeight() +
            ", pickupFromDate='" + getPickupFromDate() + "'" +
            ", biddingFromDate='" + getBiddingFromDate() + "'" +
            ", biddingUntilDate='" + getBiddingUntilDate() + "'" +
            ", dropoffProvice=" + getDropoffProvice() +
            ", dropoffDistrict=" + getDropoffDistrict() +
            ", dropoffWard=" + getDropoffWard() +
            ", type=" + getType() +
            ", status=" + getStatus() +
            ", truckType=" + getTruckType() +
            "}";
    }
}
