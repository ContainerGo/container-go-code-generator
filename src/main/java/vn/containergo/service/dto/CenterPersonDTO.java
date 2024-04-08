package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.CenterPerson} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterPersonDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String email;

    private String address;

    @NotNull
    private CenterPersonGroupDTO group;

    private Set<CenterPersonGroupDTO> groups = new HashSet<>();

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

    public CenterPersonGroupDTO getGroup() {
        return group;
    }

    public void setGroup(CenterPersonGroupDTO group) {
        this.group = group;
    }

    public Set<CenterPersonGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<CenterPersonGroupDTO> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterPersonDTO)) {
            return false;
        }

        CenterPersonDTO centerPersonDTO = (CenterPersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centerPersonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterPersonDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", group=" + getGroup() +
            ", groups=" + getGroups() +
            "}";
    }
}
