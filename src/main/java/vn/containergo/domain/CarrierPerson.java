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
 * A CarrierPerson.
 */
@Document(collection = "carrier_person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarrierPerson implements Serializable {

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
    @Field("group")
    private CarrierPersonGroup group;

    @DBRef
    @Field("carrier")
    @JsonIgnoreProperties(value = { "trucks", "carrierPeople" }, allowSetters = true)
    private Carrier carrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public CarrierPerson id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CarrierPerson name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public CarrierPerson phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public CarrierPerson email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public CarrierPerson address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CarrierPersonGroup getGroup() {
        return this.group;
    }

    public void setGroup(CarrierPersonGroup carrierPersonGroup) {
        this.group = carrierPersonGroup;
    }

    public CarrierPerson group(CarrierPersonGroup carrierPersonGroup) {
        this.setGroup(carrierPersonGroup);
        return this;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public CarrierPerson carrier(Carrier carrier) {
        this.setCarrier(carrier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierPerson)) {
            return false;
        }
        return getId() != null && getId().equals(((CarrierPerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierPerson{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
