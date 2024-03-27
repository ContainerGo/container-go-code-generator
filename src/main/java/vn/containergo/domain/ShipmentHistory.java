package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ShipmentHistory.
 */
@Document(collection = "shipment_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipmentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Field("event")
    private String event;

    @NotNull
    @Field("timestamp")
    private Instant timestamp;

    @NotNull
    @Field("executed_by")
    private String executedBy;

    @Field("location")
    private String location;

    @Field("lat")
    private Double lat;

    @Field("lng")
    private Double lng;

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

    public ShipmentHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return this.event;
    }

    public ShipmentHistory event(String event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public ShipmentHistory timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getExecutedBy() {
        return this.executedBy;
    }

    public ShipmentHistory executedBy(String executedBy) {
        this.setExecutedBy(executedBy);
        return this;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getLocation() {
        return this.location;
    }

    public ShipmentHistory location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        return this.lat;
    }

    public ShipmentHistory lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public ShipmentHistory lng(Double lng) {
        this.setLng(lng);
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public ShipmentHistory container(Container container) {
        this.setContainer(container);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipmentHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentHistory{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            ", location='" + getLocation() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
