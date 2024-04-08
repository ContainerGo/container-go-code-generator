package vn.containergo.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A TruckType.
 */
@Document(collection = "truck_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TruckType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @Field("category")
    private String category;

    @Field("height")
    private Integer height;

    @Field("length")
    private Integer length;

    @Field("max_speed")
    private Double maxSpeed;

    @Field("type")
    private String type;

    @Field("weight")
    private Integer weight;

    @Field("width")
    private Integer width;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public TruckType id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TruckType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public TruckType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public TruckType category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getHeight() {
        return this.height;
    }

    public TruckType height(Integer height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return this.length;
    }

    public TruckType length(Integer length) {
        this.setLength(length);
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Double getMaxSpeed() {
        return this.maxSpeed;
    }

    public TruckType maxSpeed(Double maxSpeed) {
        this.setMaxSpeed(maxSpeed);
        return this;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getType() {
        return this.type;
    }

    public TruckType type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public TruckType weight(Integer weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWidth() {
        return this.width;
    }

    public TruckType width(Integer width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TruckType)) {
            return false;
        }
        return getId() != null && getId().equals(((TruckType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TruckType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", maxSpeed=" + getMaxSpeed() +
            ", type='" + getType() + "'" +
            ", weight=" + getWeight() +
            ", width=" + getWidth() +
            "}";
    }
}
