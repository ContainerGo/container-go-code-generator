package vn.containergo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.containergo.domain.enumeration.ShipperAccountType;

/**
 * A ShipperAccount.
 */
@Document(collection = "shipper_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipperAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    @Field("balance")
    private Double balance;

    @NotNull
    @Field("account_type")
    private ShipperAccountType accountType;

    @DBRef
    @Field("shipper")
    @JsonIgnoreProperties(value = { "shipperPeople" }, allowSetters = true)
    private Shipper shipper;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ShipperAccount id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getBalance() {
        return this.balance;
    }

    public ShipperAccount balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ShipperAccountType getAccountType() {
        return this.accountType;
    }

    public ShipperAccount accountType(ShipperAccountType accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(ShipperAccountType accountType) {
        this.accountType = accountType;
    }

    public Shipper getShipper() {
        return this.shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public ShipperAccount shipper(Shipper shipper) {
        this.setShipper(shipper);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipperAccount)) {
            return false;
        }
        return getId() != null && getId().equals(((ShipperAccount) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipperAccount{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
