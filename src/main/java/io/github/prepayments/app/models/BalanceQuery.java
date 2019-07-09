package io.github.prepayments.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BalanceQuery {

    @NotNull
    private LocalDate balanceDate;

    private String accountName;

    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutlet;
}
