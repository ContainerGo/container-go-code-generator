package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Carrier.
 */
@Document(collection = "carrier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Carrier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("address")
    private String address;

    @Field("tax_code")
    private String taxCode;

    @Field("bank_account")
    private String bankAccount;

    @Field("bank_name")
    private String bankName;

    @Field("account_name")
    private String accountName;

    @Field("branch_name")
    private String branchName;

    @Field("company_size")
    private Integer companySize;

    @Field("is_approved")
    private Boolean isApproved;

    @Field("vehicles")
    private Integer vehicles;

    @Field("shipments_left_for_day")
    private Integer shipmentsLeftForDay;

    @Field("verified_since")
    private Instant verifiedSince;

    @DBRef
    @Field("truck")
    @JsonIgnoreProperties(value = { "type", "carrier" }, allowSetters = true)
    private Set<Truck> trucks = new HashSet<>();

    @DBRef
    @Field("carrierPerson")
    @JsonIgnoreProperties(value = { "group", "carrier" }, allowSetters = true)
    private Set<CarrierPerson> carrierPeople = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Carrier id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Carrier code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Carrier name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Carrier address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxCode() {
        return this.taxCode;
    }

    public Carrier taxCode(String taxCode) {
        this.setTaxCode(taxCode);
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public Carrier bankAccount(String bankAccount) {
        this.setBankAccount(bankAccount);
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Carrier bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public Carrier accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public Carrier branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getCompanySize() {
        return this.companySize;
    }

    public Carrier companySize(Integer companySize) {
        this.setCompanySize(companySize);
        return this;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public Carrier isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Integer getVehicles() {
        return this.vehicles;
    }

    public Carrier vehicles(Integer vehicles) {
        this.setVehicles(vehicles);
        return this;
    }

    public void setVehicles(Integer vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getShipmentsLeftForDay() {
        return this.shipmentsLeftForDay;
    }

    public Carrier shipmentsLeftForDay(Integer shipmentsLeftForDay) {
        this.setShipmentsLeftForDay(shipmentsLeftForDay);
        return this;
    }

    public void setShipmentsLeftForDay(Integer shipmentsLeftForDay) {
        this.shipmentsLeftForDay = shipmentsLeftForDay;
    }

    public Instant getVerifiedSince() {
        return this.verifiedSince;
    }

    public Carrier verifiedSince(Instant verifiedSince) {
        this.setVerifiedSince(verifiedSince);
        return this;
    }

    public void setVerifiedSince(Instant verifiedSince) {
        this.verifiedSince = verifiedSince;
    }

    public Set<Truck> getTrucks() {
        return this.trucks;
    }

    public void setTrucks(Set<Truck> trucks) {
        if (this.trucks != null) {
            this.trucks.forEach(i -> i.setCarrier(null));
        }
        if (trucks != null) {
            trucks.forEach(i -> i.setCarrier(this));
        }
        this.trucks = trucks;
    }

    public Carrier trucks(Set<Truck> trucks) {
        this.setTrucks(trucks);
        return this;
    }

    public Carrier addTruck(Truck truck) {
        this.trucks.add(truck);
        truck.setCarrier(this);
        return this;
    }

    public Carrier removeTruck(Truck truck) {
        this.trucks.remove(truck);
        truck.setCarrier(null);
        return this;
    }

    public Set<CarrierPerson> getCarrierPeople() {
        return this.carrierPeople;
    }

    public void setCarrierPeople(Set<CarrierPerson> carrierPeople) {
        if (this.carrierPeople != null) {
            this.carrierPeople.forEach(i -> i.setCarrier(null));
        }
        if (carrierPeople != null) {
            carrierPeople.forEach(i -> i.setCarrier(this));
        }
        this.carrierPeople = carrierPeople;
    }

    public Carrier carrierPeople(Set<CarrierPerson> carrierPeople) {
        this.setCarrierPeople(carrierPeople);
        return this;
    }

    public Carrier addCarrierPerson(CarrierPerson carrierPerson) {
        this.carrierPeople.add(carrierPerson);
        carrierPerson.setCarrier(this);
        return this;
    }

    public Carrier removeCarrierPerson(CarrierPerson carrierPerson) {
        this.carrierPeople.remove(carrierPerson);
        carrierPerson.setCarrier(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrier)) {
            return false;
        }
        return getId() != null && getId().equals(((Carrier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carrier{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", companySize=" + getCompanySize() +
            ", isApproved='" + getIsApproved() + "'" +
            ", vehicles=" + getVehicles() +
            ", shipmentsLeftForDay=" + getShipmentsLeftForDay() +
            ", verifiedSince='" + getVerifiedSince() + "'" +
            "}";
    }
}
