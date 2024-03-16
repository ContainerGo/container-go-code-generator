package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A CenterPersonGroup.
 */
@Document(collection = "center_person_group")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterPersonGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @DBRef
    @Field("users")
    @JsonIgnoreProperties(value = { "groups" }, allowSetters = true)
    private Set<CenterPerson> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CenterPersonGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CenterPersonGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CenterPersonGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CenterPerson> getUsers() {
        return this.users;
    }

    public void setUsers(Set<CenterPerson> centerPeople) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeGroups(this));
        }
        if (centerPeople != null) {
            centerPeople.forEach(i -> i.addGroups(this));
        }
        this.users = centerPeople;
    }

    public CenterPersonGroup users(Set<CenterPerson> centerPeople) {
        this.setUsers(centerPeople);
        return this;
    }

    public CenterPersonGroup addUsers(CenterPerson centerPerson) {
        this.users.add(centerPerson);
        centerPerson.getGroups().add(this);
        return this;
    }

    public CenterPersonGroup removeUsers(CenterPerson centerPerson) {
        this.users.remove(centerPerson);
        centerPerson.getGroups().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterPersonGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((CenterPersonGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterPersonGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
