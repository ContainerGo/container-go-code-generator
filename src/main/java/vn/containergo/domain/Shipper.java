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
import vn.containergo.domain.enumeration.ContractType;
import vn.containergo.domain.enumeration.PaymentType;

/**
 * A Shipper.
 */
@Document(collection = "shipper")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Shipper implements Serializable {

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

    @Field("company_size")
    private Integer companySize;

    @NotNull
    @Field("payment_type")
    private PaymentType paymentType;

    @NotNull
    @Field("contract_type")
    private ContractType contractType;

    @Field("contract_valid_until")
    private Instant contractValidUntil;

    @Field("is_approved")
    private Boolean isApproved;

    @Field("is_billing_information_complete")
    private Boolean isBillingInformationComplete;

    @Field("is_profile_complete")
    private Boolean isProfileComplete;

    @DBRef
    @Field("shipperPerson")
    @JsonIgnoreProperties(value = { "enabledNotifications", "group", "shipper" }, allowSetters = true)
    private Set<ShipperPerson> shipperPeople = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Shipper id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Shipper code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Shipper name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Shipper address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxCode() {
        return this.taxCode;
    }

    public Shipper taxCode(String taxCode) {
        this.setTaxCode(taxCode);
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Integer getCompanySize() {
        return this.companySize;
    }

    public Shipper companySize(Integer companySize) {
        this.setCompanySize(companySize);
        return this;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public Shipper paymentType(PaymentType paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ContractType getContractType() {
        return this.contractType;
    }

    public Shipper contractType(ContractType contractType) {
        this.setContractType(contractType);
        return this;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Instant getContractValidUntil() {
        return this.contractValidUntil;
    }

    public Shipper contractValidUntil(Instant contractValidUntil) {
        this.setContractValidUntil(contractValidUntil);
        return this;
    }

    public void setContractValidUntil(Instant contractValidUntil) {
        this.contractValidUntil = contractValidUntil;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public Shipper isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsBillingInformationComplete() {
        return this.isBillingInformationComplete;
    }

    public Shipper isBillingInformationComplete(Boolean isBillingInformationComplete) {
        this.setIsBillingInformationComplete(isBillingInformationComplete);
        return this;
    }

    public void setIsBillingInformationComplete(Boolean isBillingInformationComplete) {
        this.isBillingInformationComplete = isBillingInformationComplete;
    }

    public Boolean getIsProfileComplete() {
        return this.isProfileComplete;
    }

    public Shipper isProfileComplete(Boolean isProfileComplete) {
        this.setIsProfileComplete(isProfileComplete);
        return this;
    }

    public void setIsProfileComplete(Boolean isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    public Set<ShipperPerson> getShipperPeople() {
        return this.shipperPeople;
    }

    public void setShipperPeople(Set<ShipperPerson> shipperPeople) {
        if (this.shipperPeople != null) {
            this.shipperPeople.forEach(i -> i.setShipper(null));
        }
        if (shipperPeople != null) {
            shipperPeople.forEach(i -> i.setShipper(this));
        }
        this.shipperPeople = shipperPeople;
    }

    public Shipper shipperPeople(Set<ShipperPerson> shipperPeople) {
        this.setShipperPeople(shipperPeople);
        return this;
    }

    public Shipper addShipperPerson(ShipperPerson shipperPerson) {
        this.shipperPeople.add(shipperPerson);
        shipperPerson.setShipper(this);
        return this;
    }

    public Shipper removeShipperPerson(ShipperPerson shipperPerson) {
        this.shipperPeople.remove(shipperPerson);
        shipperPerson.setShipper(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipper)) {
            return false;
        }
        return getId() != null && getId().equals(((Shipper) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shipper{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", companySize=" + getCompanySize() +
            ", paymentType='" + getPaymentType() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", contractValidUntil='" + getContractValidUntil() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isBillingInformationComplete='" + getIsBillingInformationComplete() + "'" +
            ", isProfileComplete='" + getIsProfileComplete() + "'" +
            "}";
    }
}
