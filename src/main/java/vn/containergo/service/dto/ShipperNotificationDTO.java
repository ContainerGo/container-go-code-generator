package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.ShipperNotification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperNotificationDTO implements Serializable {

    private UUID id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private Boolean isEmailNotificationEnabled;

    private Boolean isSmsNotificationEnabled;

    private Boolean isAppNotificationEnabled;

    private ShipperPersonDTO person;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsEmailNotificationEnabled() {
        return isEmailNotificationEnabled;
    }

    public void setIsEmailNotificationEnabled(Boolean isEmailNotificationEnabled) {
        this.isEmailNotificationEnabled = isEmailNotificationEnabled;
    }

    public Boolean getIsSmsNotificationEnabled() {
        return isSmsNotificationEnabled;
    }

    public void setIsSmsNotificationEnabled(Boolean isSmsNotificationEnabled) {
        this.isSmsNotificationEnabled = isSmsNotificationEnabled;
    }

    public Boolean getIsAppNotificationEnabled() {
        return isAppNotificationEnabled;
    }

    public void setIsAppNotificationEnabled(Boolean isAppNotificationEnabled) {
        this.isAppNotificationEnabled = isAppNotificationEnabled;
    }

    public ShipperPersonDTO getPerson() {
        return person;
    }

    public void setPerson(ShipperPersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperNotificationDTO)) {
            return false;
        }

        ShipperNotificationDTO shipperNotificationDTO = (ShipperNotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipperNotificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperNotificationDTO{" +
            "id='" + getId() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isEmailNotificationEnabled='" + getIsEmailNotificationEnabled() + "'" +
            ", isSmsNotificationEnabled='" + getIsSmsNotificationEnabled() + "'" +
            ", isAppNotificationEnabled='" + getIsAppNotificationEnabled() + "'" +
            ", person=" + getPerson() +
            "}";
    }
}
