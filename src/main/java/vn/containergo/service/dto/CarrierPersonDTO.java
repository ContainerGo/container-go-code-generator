package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.CarrierPerson} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarrierPersonDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String email;

    private String address;

    @NotNull
    private CarrierPersonGroupDTO group;

    private CarrierDTO carrier;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CarrierPersonGroupDTO getGroup() {
        return group;
    }

    public void setGroup(CarrierPersonGroupDTO group) {
        this.group = group;
    }

    public CarrierDTO getCarrier() {
        return carrier;
    }

    public void setCarrier(CarrierDTO carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierPersonDTO)) {
            return false;
        }

        CarrierPersonDTO carrierPersonDTO = (CarrierPersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carrierPersonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierPersonDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", group=" + getGroup() +
            ", carrier=" + getCarrier() +
            "}";
    }
}
