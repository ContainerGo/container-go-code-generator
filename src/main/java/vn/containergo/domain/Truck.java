package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.containergo.domain.enumeration.TruckStatus;

/**
 * A Truck.
 */
@Document(collection = "truck")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @Field("model")
    private String model;

    @Field("manufacturer")
    private String manufacturer;

    @Field("year")
    private Integer year;

    @Field("capacity")
    private Double capacity;

    @NotNull
    @Field("status")
    private TruckStatus status;

    @Field("mileage")
    private Double mileage;

    @NotNull
    @Field("number_plate")
    private String numberPlate;

    @Field("lat")
    private Double lat;

    @Field("lng")
    private Double lng;

    @DBRef
    @Field("type")
    private TruckType type;

    @DBRef
    @Field("carrier")
    @JsonIgnoreProperties(value = { "trucks", "carrierPeople" }, allowSetters = true)
    private Carrier carrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Truck id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Truck code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Truck name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public Truck model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Truck manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getYear() {
        return this.year;
    }

    public Truck year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCapacity() {
        return this.capacity;
    }

    public Truck capacity(Double capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return this.status;
    }

    public Truck status(TruckStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public Double getMileage() {
        return this.mileage;
    }

    public Truck mileage(Double mileage) {
        this.setMileage(mileage);
        return this;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

    public Truck numberPlate(String numberPlate) {
        this.setNumberPlate(numberPlate);
        return this;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getLat() {
        return this.lat;
    }

    public Truck lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public Truck lng(Double lng) {
        this.setLng(lng);
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public TruckType getType() {
        return this.type;
    }

    public void setType(TruckType truckType) {
        this.type = truckType;
    }

    public Truck type(TruckType truckType) {
        this.setType(truckType);
        return this;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Truck carrier(Carrier carrier) {
        this.setCarrier(carrier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truck)) {
            return false;
        }
        return getId() != null && getId().equals(((Truck) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", year=" + getYear() +
            ", capacity=" + getCapacity() +
            ", status='" + getStatus() + "'" +
            ", mileage=" + getMileage() +
            ", numberPlate='" + getNumberPlate() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
