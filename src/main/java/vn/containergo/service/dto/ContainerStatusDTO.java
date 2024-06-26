package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.ContainerStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContainerStatusDTO implements Serializable {

    private UUID id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private ContainerStatusGroupDTO group;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContainerStatusGroupDTO getGroup() {
        return group;
    }

    public void setGroup(ContainerStatusGroupDTO group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContainerStatusDTO)) {
            return false;
        }

        ContainerStatusDTO containerStatusDTO = (ContainerStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, containerStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContainerStatusDTO{" +
            "id='" + getId() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", group=" + getGroup() +
            "}";
    }
}
