package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.CenterPersonGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterPersonGroupDTO implements Serializable {

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
        if (!(o instanceof CenterPersonGroupDTO)) {
            return false;
        }

        CenterPersonGroupDTO centerPersonGroupDTO = (CenterPersonGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centerPersonGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterPersonGroupDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
