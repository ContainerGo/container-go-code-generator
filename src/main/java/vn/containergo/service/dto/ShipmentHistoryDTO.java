package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.ShipmentHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipmentHistoryDTO implements Serializable {

    private UUID id;

    @NotNull
    private String event;

    @NotNull
    private Instant timestamp;

    @NotNull
    private String executedBy;

    private String location;

    private Double lat;

    private Double lng;

    @NotNull
    private ContainerDTO container;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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
        if (!(o instanceof ShipmentHistoryDTO)) {
            return false;
        }

        ShipmentHistoryDTO shipmentHistoryDTO = (ShipmentHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipmentHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentHistoryDTO{" +
            "id='" + getId() + "'" +
            ", event='" + getEvent() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", executedBy='" + getExecutedBy() + "'" +
            ", location='" + getLocation() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", container=" + getContainer() +
            "}";
    }
}
