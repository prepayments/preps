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
 * A DTO for the {@link io.github.prepayments.domain.ServiceOutlet} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServiceOutletDTO implements Serializable,IsTokenized<String> {

    private Long id;

    @NotNull
    private String serviceOutletName;

    @NotNull
    private String serviceOutletCode;

    private String serviceOutletLocation;

    private String serviceOutletManager;

    private Integer numberOfStaff;

    private String building;

    private Integer floor;

    private String postalAddress;

    private String contactPersonName;

    private String contactEmail;

    private String street;

    private String OriginatingFileToken;

    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
