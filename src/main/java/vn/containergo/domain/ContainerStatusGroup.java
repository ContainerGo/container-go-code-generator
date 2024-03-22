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
 * A ContainerStatusGroup.
 */
@Document(collection = "container_status_group")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContainerStatusGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @DBRef
    @Field("containerStatus")
    @JsonIgnoreProperties(value = { "group" }, allowSetters = true)
    private Set<ContainerStatus> containerStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContainerStatusGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ContainerStatusGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public ContainerStatusGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ContainerStatusGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ContainerStatus> getContainerStatuses() {
        return this.containerStatuses;
    }

    public void setContainerStatuses(Set<ContainerStatus> containerStatuses) {
        if (this.containerStatuses != null) {
            this.containerStatuses.forEach(i -> i.setGroup(null));
        }
        if (containerStatuses != null) {
            containerStatuses.forEach(i -> i.setGroup(this));
        }
        this.containerStatuses = containerStatuses;
    }

    public ContainerStatusGroup containerStatuses(Set<ContainerStatus> containerStatuses) {
        this.setContainerStatuses(containerStatuses);
        return this;
    }

    public ContainerStatusGroup addContainerStatus(ContainerStatus containerStatus) {
        this.containerStatuses.add(containerStatus);
        containerStatus.setGroup(this);
        return this;
    }

    public ContainerStatusGroup removeContainerStatus(ContainerStatus containerStatus) {
        this.containerStatuses.remove(containerStatus);
        containerStatus.setGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContainerStatusGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((ContainerStatusGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContainerStatusGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
