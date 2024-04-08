package vn.containergo.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ShipperPersonGroup.
 */
@Document(collection = "shipper_person_group")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperPersonGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ShipperPersonGroup id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ShipperPersonGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperPersonGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipperPersonGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperPersonGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
