package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.CarrierPersonGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarrierPersonGroupDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierPersonGroupDTO)) {
            return false;
        }

        CarrierPersonGroupDTO carrierPersonGroupDTO = (CarrierPersonGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carrierPersonGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierPersonGroupDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
