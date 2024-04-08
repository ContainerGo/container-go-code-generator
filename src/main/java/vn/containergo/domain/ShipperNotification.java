package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ShipperNotification.
 */
@Document(collection = "shipper_notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @Field("is_email_notification_enabled")
    private Boolean isEmailNotificationEnabled;

    @Field("is_sms_notification_enabled")
    private Boolean isSmsNotificationEnabled;

    @Field("is_app_notification_enabled")
    private Boolean isAppNotificationEnabled;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties(value = { "enabledNotifications", "group", "shipper" }, allowSetters = true)
    private ShipperPerson person;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ShipperNotification id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ShipperNotification code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public ShipperNotification name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsEmailNotificationEnabled() {
        return this.isEmailNotificationEnabled;
    }

    public ShipperNotification isEmailNotificationEnabled(Boolean isEmailNotificationEnabled) {
        this.setIsEmailNotificationEnabled(isEmailNotificationEnabled);
        return this;
    }

    public void setIsEmailNotificationEnabled(Boolean isEmailNotificationEnabled) {
        this.isEmailNotificationEnabled = isEmailNotificationEnabled;
    }

    public Boolean getIsSmsNotificationEnabled() {
        return this.isSmsNotificationEnabled;
    }

    public ShipperNotification isSmsNotificationEnabled(Boolean isSmsNotificationEnabled) {
        this.setIsSmsNotificationEnabled(isSmsNotificationEnabled);
        return this;
    }

    public void setIsSmsNotificationEnabled(Boolean isSmsNotificationEnabled) {
        this.isSmsNotificationEnabled = isSmsNotificationEnabled;
    }

    public Boolean getIsAppNotificationEnabled() {
        return this.isAppNotificationEnabled;
    }

    public ShipperNotification isAppNotificationEnabled(Boolean isAppNotificationEnabled) {
        this.setIsAppNotificationEnabled(isAppNotificationEnabled);
        return this;
    }

    public void setIsAppNotificationEnabled(Boolean isAppNotificationEnabled) {
        this.isAppNotificationEnabled = isAppNotificationEnabled;
    }

    public ShipperPerson getPerson() {
        return this.person;
    }

    public void setPerson(ShipperPerson shipperPerson) {
        this.person = shipperPerson;
    }

    public ShipperNotification person(ShipperPerson shipperPerson) {
        this.setPerson(shipperPerson);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperNotification)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipperNotification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperNotification{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isEmailNotificationEnabled='" + getIsEmailNotificationEnabled() + "'" +
            ", isSmsNotificationEnabled='" + getIsSmsNotificationEnabled() + "'" +
            ", isAppNotificationEnabled='" + getIsAppNotificationEnabled() + "'" +
            "}";
    }
}
