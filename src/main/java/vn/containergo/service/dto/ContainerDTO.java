package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import vn.containergo.domain.enumeration.ContainerState;

/**
 * A DTO for the {@link vn.containergo.domain.Container} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContainerDTO implements Serializable {

    private UUID id;

    @NotNull
    private String contNo;

    @NotNull
    private Double estimatedPrice;

    @NotNull
    private Double distance;

    @NotNull
    private Double desiredPrice;

    private String additionalRequirements;

    @NotNull
    private String pickupContact;

    @NotNull
    private String pickupContactPhone;

    @NotNull
    private String pickupAddress;

    @NotNull
    private Double pickupLat;

    @NotNull
    private Double pickupLng;

    @NotNull
    private Instant pickupFromDate;

    private String dropoffContact;

    private String dropoffContactPhone;

    @NotNull
    private String dropoffAddress;

    private Double dropoffLat;

    private Double dropoffLng;

    private String points;

    private Instant dropoffUntilDate;

    @NotNull
    private ContainerState state;

    @NotNull
    private UUID shipperId;

    private UUID carrierId;

    @NotNull
    private Double totalWeight;

    private Instant biddingFromDate;

    private Instant biddingUntilDate;

    @NotNull
    private ProviceDTO pickupProvice;

    @NotNull
    private DistrictDTO pickupDistrict;

    @NotNull
    private WardDTO pickupWard;

    @NotNull
    private ProviceDTO dropoffProvice;

    @NotNull
    private DistrictDTO dropoffDistrict;

    private WardDTO dropoffWard;

    @NotNull
    private ContainerTypeDTO type;

    @NotNull
    private ContainerStatusDTO status;

    private TruckTypeDTO truckType;

    private TruckDTO truck;

    private ContainerOwnerDTO owner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getPickupContact() {
        return pickupContact;
    }

    public void setPickupContact(String pickupContact) {
        this.pickupContact = pickupContact;
    }

    public String getPickupContactPhone() {
        return pickupContactPhone;
    }

    public void setPickupContactPhone(String pickupContactPhone) {
        this.pickupContactPhone = pickupContactPhone;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public Double getPickupLng() {
        return pickupLng;
    }

    public void setPickupLng(Double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public Instant getPickupFromDate() {
        return pickupFromDate;
    }

    public void setPickupFromDate(Instant pickupFromDate) {
        this.pickupFromDate = pickupFromDate;
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

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
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

    public UUID getShipperId() {
        return shipperId;
    }

    public void setShipperId(UUID shipperId) {
        this.shipperId = shipperId;
    }

    public UUID getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(UUID carrierId) {
        this.carrierId = carrierId;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
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

    public ProviceDTO getPickupProvice() {
        return pickupProvice;
    }

    public void setPickupProvice(ProviceDTO pickupProvice) {
        this.pickupProvice = pickupProvice;
    }

    public DistrictDTO getPickupDistrict() {
        return pickupDistrict;
    }

    public void setPickupDistrict(DistrictDTO pickupDistrict) {
        this.pickupDistrict = pickupDistrict;
    }

    public WardDTO getPickupWard() {
        return pickupWard;
    }

    public void setPickupWard(WardDTO pickupWard) {
        this.pickupWard = pickupWard;
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

    public TruckDTO getTruck() {
        return truck;
    }

    public void setTruck(TruckDTO truck) {
        this.truck = truck;
    }

    public ContainerOwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(ContainerOwnerDTO owner) {
        this.owner = owner;
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
            "id='" + getId() + "'" +
            ", contNo='" + getContNo() + "'" +
            ", estimatedPrice=" + getEstimatedPrice() +
            ", distance=" + getDistance() +
            ", desiredPrice=" + getDesiredPrice() +
            ", additionalRequirements='" + getAdditionalRequirements() + "'" +
            ", pickupContact='" + getPickupContact() + "'" +
            ", pickupContactPhone='" + getPickupContactPhone() + "'" +
            ", pickupAddress='" + getPickupAddress() + "'" +
            ", pickupLat=" + getPickupLat() +
            ", pickupLng=" + getPickupLng() +
            ", pickupFromDate='" + getPickupFromDate() + "'" +
            ", dropoffContact='" + getDropoffContact() + "'" +
            ", dropoffContactPhone='" + getDropoffContactPhone() + "'" +
            ", dropoffAddress='" + getDropoffAddress() + "'" +
            ", dropoffLat=" + getDropoffLat() +
            ", dropoffLng=" + getDropoffLng() +
            ", points='" + getPoints() + "'" +
            ", dropoffUntilDate='" + getDropoffUntilDate() + "'" +
            ", state='" + getState() + "'" +
            ", shipperId='" + getShipperId() + "'" +
            ", carrierId='" + getCarrierId() + "'" +
            ", totalWeight=" + getTotalWeight() +
            ", biddingFromDate='" + getBiddingFromDate() + "'" +
            ", biddingUntilDate='" + getBiddingUntilDate() + "'" +
            ", pickupProvice=" + getPickupProvice() +
            ", pickupDistrict=" + getPickupDistrict() +
            ", pickupWard=" + getPickupWard() +
            ", dropoffProvice=" + getDropoffProvice() +
            ", dropoffDistrict=" + getDropoffDistrict() +
            ", dropoffWard=" + getDropoffWard() +
            ", type=" + getType() +
            ", status=" + getStatus() +
            ", truckType=" + getTruckType() +
            ", truck=" + getTruck() +
            ", owner=" + getOwner() +
            "}";
    }
}
