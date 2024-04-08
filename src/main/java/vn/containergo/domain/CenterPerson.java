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
 * A CenterPerson.
 */
@Document(collection = "center_person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterPerson implements Serializable {

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
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private CenterPersonGroup group;

    @DBRef
    @Field("groups")
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private Set<CenterPersonGroup> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public CenterPerson id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CenterPerson name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public CenterPerson phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public CenterPerson email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public CenterPerson address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CenterPersonGroup getGroup() {
        return this.group;
    }

    public void setGroup(CenterPersonGroup centerPersonGroup) {
        this.group = centerPersonGroup;
    }

    public CenterPerson group(CenterPersonGroup centerPersonGroup) {
        this.setGroup(centerPersonGroup);
        return this;
    }

    public Set<CenterPersonGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<CenterPersonGroup> centerPersonGroups) {
        this.groups = centerPersonGroups;
    }

    public CenterPerson groups(Set<CenterPersonGroup> centerPersonGroups) {
        this.setGroups(centerPersonGroups);
        return this;
    }

    public CenterPerson addGroups(CenterPersonGroup centerPersonGroup) {
        this.groups.add(centerPersonGroup);
        return this;
    }

    public CenterPerson removeGroups(CenterPersonGroup centerPersonGroup) {
        this.groups.remove(centerPersonGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterPerson)) {
            return false;
        }
        return getId() != null && getId().equals(((CenterPerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterPerson{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
