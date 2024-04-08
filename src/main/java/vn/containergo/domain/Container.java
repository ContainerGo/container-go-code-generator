package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.containergo.domain.enumeration.ContainerState;

/**
 * A Container.
 */
@Document(collection = "container")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Container implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("cont_no")
    private String contNo;

    @NotNull
    @Field("estimated_price")
    private Double estimatedPrice;

    @NotNull
    @Field("distance")
    private Double distance;

    @NotNull
    @Field("desired_price")
    private Double desiredPrice;

    @Field("additional_requirements")
    private String additionalRequirements;

    @NotNull
    @Field("pickup_contact")
    private String pickupContact;

    @NotNull
    @Field("pickup_contact_phone")
    private String pickupContactPhone;

    @NotNull
    @Field("pickup_address")
    private String pickupAddress;

    @NotNull
    @Field("pickup_lat")
    private Double pickupLat;

    @NotNull
    @Field("pickup_lng")
    private Double pickupLng;

    @NotNull
    @Field("pickup_from_date")
    private Instant pickupFromDate;

    @Field("dropoff_contact")
    private String dropoffContact;

    @Field("dropoff_contact_phone")
    private String dropoffContactPhone;

    @NotNull
    @Field("dropoff_address")
    private String dropoffAddress;

    @Field("dropoff_lat")
    private Double dropoffLat;

    @Field("dropoff_lng")
    private Double dropoffLng;

    @Field("points")
    private String points;

    @Field("dropoff_until_date")
    private Instant dropoffUntilDate;

    @NotNull
    @Field("state")
    private ContainerState state;

    @NotNull
    @Field("shipper_id")
    private UUID shipperId;

    @Field("carrier_id")
    private UUID carrierId;

    @NotNull
    @Field("total_weight")
    private Double totalWeight;

    @Field("bidding_from_date")
    private Instant biddingFromDate;

    @Field("bidding_until_date")
    private Instant biddingUntilDate;

    @DBRef
    @Field("shipmentPlan")
    @JsonIgnoreProperties(value = { "container" }, allowSetters = true)
    private Set<ShipmentPlan> shipmentPlans = new HashSet<>();

    @DBRef
    @Field("pickupProvice")
    private Provice pickupProvice;

    @DBRef
    @Field("pickupDistrict")
    private District pickupDistrict;

    @DBRef
    @Field("pickupWard")
    private Ward pickupWard;

    @DBRef
    @Field("dropoffProvice")
    private Provice dropoffProvice;

    @DBRef
    @Field("dropoffDistrict")
    private District dropoffDistrict;

    @DBRef
    @Field("dropoffWard")
    private Ward dropoffWard;

    @DBRef
    @Field("type")
    private ContainerType type;

    @DBRef
    @Field("status")
    @JsonIgnoreProperties(value = { "group" }, allowSetters = true)
    private ContainerStatus status;

    @DBRef
    @Field("truckType")
    private TruckType truckType;

    @DBRef
    @Field("truck")
    @JsonIgnoreProperties(value = { "type", "carrier" }, allowSetters = true)
    private Truck truck;

    @DBRef
    @Field("owner")
    private ContainerOwner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Container id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContNo() {
        return this.contNo;
    }

    public Container contNo(String contNo) {
        this.setContNo(contNo);
        return this;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public Double getEstimatedPrice() {
        return this.estimatedPrice;
    }

    public Container estimatedPrice(Double estimatedPrice) {
        this.setEstimatedPrice(estimatedPrice);
        return this;
    }

    public void setEstimatedPrice(Double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Double getDistance() {
        return this.distance;
    }

    public Container distance(Double distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDesiredPrice() {
        return this.desiredPrice;
    }

    public Container desiredPrice(Double desiredPrice) {
        this.setDesiredPrice(desiredPrice);
        return this;
    }

    public void setDesiredPrice(Double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public String getAdditionalRequirements() {
        return this.additionalRequirements;
    }

    public Container additionalRequirements(String additionalRequirements) {
        this.setAdditionalRequirements(additionalRequirements);
        return this;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }

    public String getPickupContact() {
        return this.pickupContact;
    }

    public Container pickupContact(String pickupContact) {
        this.setPickupContact(pickupContact);
        return this;
    }

    public void setPickupContact(String pickupContact) {
        this.pickupContact = pickupContact;
    }

    public String getPickupContactPhone() {
        return this.pickupContactPhone;
    }

    public Container pickupContactPhone(String pickupContactPhone) {
        this.setPickupContactPhone(pickupContactPhone);
        return this;
    }

    public void setPickupContactPhone(String pickupContactPhone) {
        this.pickupContactPhone = pickupContactPhone;
    }

    public String getPickupAddress() {
        return this.pickupAddress;
    }

    public Container pickupAddress(String pickupAddress) {
        this.setPickupAddress(pickupAddress);
        return this;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Double getPickupLat() {
        return this.pickupLat;
    }

    public Container pickupLat(Double pickupLat) {
        this.setPickupLat(pickupLat);
        return this;
    }

    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public Double getPickupLng() {
        return this.pickupLng;
    }

    public Container pickupLng(Double pickupLng) {
        this.setPickupLng(pickupLng);
        return this;
    }

    public void setPickupLng(Double pickupLng) {
        this.pickupLng = pickupLng;
    }

    public Instant getPickupFromDate() {
        return this.pickupFromDate;
    }

    public Container pickupFromDate(Instant pickupFromDate) {
        this.setPickupFromDate(pickupFromDate);
        return this;
    }

    public void setPickupFromDate(Instant pickupFromDate) {
        this.pickupFromDate = pickupFromDate;
    }

    public String getDropoffContact() {
        return this.dropoffContact;
    }

    public Container dropoffContact(String dropoffContact) {
        this.setDropoffContact(dropoffContact);
        return this;
    }

    public void setDropoffContact(String dropoffContact) {
        this.dropoffContact = dropoffContact;
    }

    public String getDropoffContactPhone() {
        return this.dropoffContactPhone;
    }

    public Container dropoffContactPhone(String dropoffContactPhone) {
        this.setDropoffContactPhone(dropoffContactPhone);
        return this;
    }

    public void setDropoffContactPhone(String dropoffContactPhone) {
        this.dropoffContactPhone = dropoffContactPhone;
    }

    public String getDropoffAddress() {
        return this.dropoffAddress;
    }

    public Container dropoffAddress(String dropoffAddress) {
        this.setDropoffAddress(dropoffAddress);
        return this;
    }

    public void setDropoffAddress(String dropoffAddress) {
        this.dropoffAddress = dropoffAddress;
    }

    public Double getDropoffLat() {
        return this.dropoffLat;
    }

    public Container dropoffLat(Double dropoffLat) {
        this.setDropoffLat(dropoffLat);
        return this;
    }

    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public Double getDropoffLng() {
        return this.dropoffLng;
    }

    public Container dropoffLng(Double dropoffLng) {
        this.setDropoffLng(dropoffLng);
        return this;
    }

    public void setDropoffLng(Double dropoffLng) {
        this.dropoffLng = dropoffLng;
    }

    public String getPoints() {
        return this.points;
    }

    public Container points(String points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Instant getDropoffUntilDate() {
        return this.dropoffUntilDate;
    }

    public Container dropoffUntilDate(Instant dropoffUntilDate) {
        this.setDropoffUntilDate(dropoffUntilDate);
        return this;
    }

    public void setDropoffUntilDate(Instant dropoffUntilDate) {
        this.dropoffUntilDate = dropoffUntilDate;
    }

    public ContainerState getState() {
        return this.state;
    }

    public Container state(ContainerState state) {
        this.setState(state);
        return this;
    }

    public void setState(ContainerState state) {
        this.state = state;
    }

    public UUID getShipperId() {
        return this.shipperId;
    }

    public Container shipperId(UUID shipperId) {
        this.setShipperId(shipperId);
        return this;
    }

    public void setShipperId(UUID shipperId) {
        this.shipperId = shipperId;
    }

    public UUID getCarrierId() {
        return this.carrierId;
    }

    public Container carrierId(UUID carrierId) {
        this.setCarrierId(carrierId);
        return this;
    }

    public void setCarrierId(UUID carrierId) {
        this.carrierId = carrierId;
    }

    public Double getTotalWeight() {
        return this.totalWeight;
    }

    public Container totalWeight(Double totalWeight) {
        this.setTotalWeight(totalWeight);
        return this;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Instant getBiddingFromDate() {
        return this.biddingFromDate;
    }

    public Container biddingFromDate(Instant biddingFromDate) {
        this.setBiddingFromDate(biddingFromDate);
        return this;
    }

    public void setBiddingFromDate(Instant biddingFromDate) {
        this.biddingFromDate = biddingFromDate;
    }

    public Instant getBiddingUntilDate() {
        return this.biddingUntilDate;
    }

    public Container biddingUntilDate(Instant biddingUntilDate) {
        this.setBiddingUntilDate(biddingUntilDate);
        return this;
    }

    public void setBiddingUntilDate(Instant biddingUntilDate) {
        this.biddingUntilDate = biddingUntilDate;
    }

    public Set<ShipmentPlan> getShipmentPlans() {
        return this.shipmentPlans;
    }

    public void setShipmentPlans(Set<ShipmentPlan> shipmentPlans) {
        if (this.shipmentPlans != null) {
            this.shipmentPlans.forEach(i -> i.setContainer(null));
        }
        if (shipmentPlans != null) {
            shipmentPlans.forEach(i -> i.setContainer(this));
        }
        this.shipmentPlans = shipmentPlans;
    }

    public Container shipmentPlans(Set<ShipmentPlan> shipmentPlans) {
        this.setShipmentPlans(shipmentPlans);
        return this;
    }

    public Container addShipmentPlan(ShipmentPlan shipmentPlan) {
        this.shipmentPlans.add(shipmentPlan);
        shipmentPlan.setContainer(this);
        return this;
    }

    public Container removeShipmentPlan(ShipmentPlan shipmentPlan) {
        this.shipmentPlans.remove(shipmentPlan);
        shipmentPlan.setContainer(null);
        return this;
    }

    public Provice getPickupProvice() {
        return this.pickupProvice;
    }

    public void setPickupProvice(Provice provice) {
        this.pickupProvice = provice;
    }

    public Container pickupProvice(Provice provice) {
        this.setPickupProvice(provice);
        return this;
    }

    public District getPickupDistrict() {
        return this.pickupDistrict;
    }

    public void setPickupDistrict(District district) {
        this.pickupDistrict = district;
    }

    public Container pickupDistrict(District district) {
        this.setPickupDistrict(district);
        return this;
    }

    public Ward getPickupWard() {
        return this.pickupWard;
    }

    public void setPickupWard(Ward ward) {
        this.pickupWard = ward;
    }

    public Container pickupWard(Ward ward) {
        this.setPickupWard(ward);
        return this;
    }

    public Provice getDropoffProvice() {
        return this.dropoffProvice;
    }

    public void setDropoffProvice(Provice provice) {
        this.dropoffProvice = provice;
    }

    public Container dropoffProvice(Provice provice) {
        this.setDropoffProvice(provice);
        return this;
    }

    public District getDropoffDistrict() {
        return this.dropoffDistrict;
    }

    public void setDropoffDistrict(District district) {
        this.dropoffDistrict = district;
    }

    public Container dropoffDistrict(District district) {
        this.setDropoffDistrict(district);
        return this;
    }

    public Ward getDropoffWard() {
        return this.dropoffWard;
    }

    public void setDropoffWard(Ward ward) {
        this.dropoffWard = ward;
    }

    public Container dropoffWard(Ward ward) {
        this.setDropoffWard(ward);
        return this;
    }

    public ContainerType getType() {
        return this.type;
    }

    public void setType(ContainerType containerType) {
        this.type = containerType;
    }

    public Container type(ContainerType containerType) {
        this.setType(containerType);
        return this;
    }

    public ContainerStatus getStatus() {
        return this.status;
    }

    public void setStatus(ContainerStatus containerStatus) {
        this.status = containerStatus;
    }

    public Container status(ContainerStatus containerStatus) {
        this.setStatus(containerStatus);
        return this;
    }

    public TruckType getTruckType() {
        return this.truckType;
    }

    public void setTruckType(TruckType truckType) {
        this.truckType = truckType;
    }

    public Container truckType(TruckType truckType) {
        this.setTruckType(truckType);
        return this;
    }

    public Truck getTruck() {
        return this.truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Container truck(Truck truck) {
        this.setTruck(truck);
        return this;
    }

    public ContainerOwner getOwner() {
        return this.owner;
    }

    public void setOwner(ContainerOwner containerOwner) {
        this.owner = containerOwner;
    }

    public Container owner(ContainerOwner containerOwner) {
        this.setOwner(containerOwner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Container)) {
            return false;
        }
        return getId() != null && getId().equals(((Container) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Container{" +
            "id=" + getId() +
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
            "}";
    }
}
