package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.IsTokenized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.RegisteredSupplier} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisteredSupplierDTO implements Serializable,IsTokenized<String> {

    private Long id;

    @NotNull
    private String supplierName;

    private String supplierAddress;

    private String phoneNumber;

    private String supplierEmail;

    private String bankAccountName;

    private String bankAccountNumber;

    private String supplierBankName;

    private String supplierBankBranch;

    private String bankSwiftCode;

    private String bankPhysicalAddress;

    private String domicile;

    private String taxAuthorityPIN;

    private String originatingFileToken;

    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
