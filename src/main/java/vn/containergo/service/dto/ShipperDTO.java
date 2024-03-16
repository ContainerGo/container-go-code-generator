package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link vn.containergo.domain.Shipper} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private String address;

    private String taxCode;

    private Integer companySize;

    @NotNull
    private String paymentType;

    private Boolean isApproved;

    private Boolean isBillingInformationComplete;

    private Boolean isProfileComplete;

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

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsBillingInformationComplete() {
        return isBillingInformationComplete;
    }

    public void setIsBillingInformationComplete(Boolean isBillingInformationComplete) {
        this.isBillingInformationComplete = isBillingInformationComplete;
    }

    public Boolean getIsProfileComplete() {
        return isProfileComplete;
    }

    public void setIsProfileComplete(Boolean isProfileComplete) {
        this.isProfileComplete = isProfileComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperDTO)) {
            return false;
        }

        ShipperDTO shipperDTO = (ShipperDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipperDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", companySize=" + getCompanySize() +
            ", paymentType='" + getPaymentType() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isBillingInformationComplete='" + getIsBillingInformationComplete() + "'" +
            ", isProfileComplete='" + getIsProfileComplete() + "'" +
            "}";
    }
}
