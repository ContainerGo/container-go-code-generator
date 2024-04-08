package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ShipperPerson.
 */
@Document(collection = "shipper_person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("phone")
    private String phone;

    @Field("email")
    private String email;

    @Field("address")
    private String address;

    @DBRef
    @Field("enabledNotifications")
    @JsonIgnoreProperties(value = { "person" }, allowSetters = true)
    private Set<ShipperNotification> enabledNotifications = new HashSet<>();

    @DBRef
    @Field("group")
    private ShipperPersonGroup group;

    @DBRef
    @Field("shipper")
    @JsonIgnoreProperties(value = { "shipperPeople" }, allowSetters = true)
    private Shipper shipper;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ShipperPerson id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ShipperPerson name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public ShipperPerson phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public ShipperPerson email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public ShipperPerson address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<ShipperNotification> getEnabledNotifications() {
        return this.enabledNotifications;
    }

    public void setEnabledNotifications(Set<ShipperNotification> shipperNotifications) {
        if (this.enabledNotifications != null) {
            this.enabledNotifications.forEach(i -> i.setPerson(null));
        }
        if (shipperNotifications != null) {
            shipperNotifications.forEach(i -> i.setPerson(this));
        }
        this.enabledNotifications = shipperNotifications;
    }

    public ShipperPerson enabledNotifications(Set<ShipperNotification> shipperNotifications) {
        this.setEnabledNotifications(shipperNotifications);
        return this;
    }

    public ShipperPerson addEnabledNotifications(ShipperNotification shipperNotification) {
        this.enabledNotifications.add(shipperNotification);
        shipperNotification.setPerson(this);
        return this;
    }

    public ShipperPerson removeEnabledNotifications(ShipperNotification shipperNotification) {
        this.enabledNotifications.remove(shipperNotification);
        shipperNotification.setPerson(null);
        return this;
    }

    public ShipperPersonGroup getGroup() {
        return this.group;
    }

    public void setGroup(ShipperPersonGroup shipperPersonGroup) {
        this.group = shipperPersonGroup;
    }

    public ShipperPerson group(ShipperPersonGroup shipperPersonGroup) {
        this.setGroup(shipperPersonGroup);
        return this;
    }

    public Shipper getShipper() {
        return this.shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public ShipperPerson shipper(Shipper shipper) {
        this.setShipper(shipper);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperPerson)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipperPerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperPerson{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
