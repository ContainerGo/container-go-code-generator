package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import vn.containergo.domain.enumeration.TruckStatus;

/**
 * A DTO for the {@link vn.containergo.domain.Truck} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TruckDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String model;

    private String manufacturer;

    private Integer year;

    private Double capacity;

    @NotNull
    private TruckStatus status;

    private Double mileage;

    @NotNull
    private String numberPlate;

    private Double lat;

    private Double lng;

    @NotNull
    private TruckTypeDTO type;

    private CarrierDTO carrier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public TruckTypeDTO getType() {
        return type;
    }

    public void setType(TruckTypeDTO type) {
        this.type = type;
    }

    public CarrierDTO getCarrier() {
        return carrier;
    }

    public void setCarrier(CarrierDTO carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TruckDTO)) {
            return false;
        }

        TruckDTO truckDTO = (TruckDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, truckDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TruckDTO{" +
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
            ", type=" + getType() +
            ", carrier=" + getCarrier() +
            "}";
    }
}
