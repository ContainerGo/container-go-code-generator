package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link vn.containergo.domain.Carrier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarrierDTO implements Serializable {

    private UUID id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String taxCode;

    private String bankAccount;

    private String bankName;

    private String accountName;

    private String branchName;

    private Integer companySize;

    private Boolean isApproved;

    private Integer vehicles;

    private Integer shipmentsLeftForDay;

    private Instant verifiedSince;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Integer getVehicles() {
        return vehicles;
    }

    public void setVehicles(Integer vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getShipmentsLeftForDay() {
        return shipmentsLeftForDay;
    }

    public void setShipmentsLeftForDay(Integer shipmentsLeftForDay) {
        this.shipmentsLeftForDay = shipmentsLeftForDay;
    }

    public Instant getVerifiedSince() {
        return verifiedSince;
    }

    public void setVerifiedSince(Instant verifiedSince) {
        this.verifiedSince = verifiedSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierDTO)) {
            return false;
        }

        CarrierDTO carrierDTO = (CarrierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carrierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarrierDTO{" +
            "id='" + getId() + "'" +
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
