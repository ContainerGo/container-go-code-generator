package vn.containergo.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import vn.containergo.domain.enumeration.ShipperAccountType;

/**
 * A DTO for the {@link vn.containergo.domain.ShipperAccount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperAccountDTO implements Serializable {

    private UUID id;

    @NotNull
    private Double balance;

    @NotNull
    private ShipperAccountType accountType;

    @NotNull
    private ShipperDTO shipper;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ShipperAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(ShipperAccountType accountType) {
        this.accountType = accountType;
    }

    public ShipperDTO getShipper() {
        return shipper;
    }

    public void setShipper(ShipperDTO shipper) {
        this.shipper = shipper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperAccountDTO)) {
            return false;
        }

        ShipperAccountDTO shipperAccountDTO = (ShipperAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipperAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperAccountDTO{" +
            "id='" + getId() + "'" +
            ", balance=" + getBalance() +
            ", accountType='" + getAccountType() + "'" +
            ", shipper=" + getShipper() +
            "}";
    }
}
