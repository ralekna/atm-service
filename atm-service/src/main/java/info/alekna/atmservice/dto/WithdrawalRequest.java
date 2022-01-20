package info.alekna.atmservice.dto;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record WithdrawalRequest(
        @NotNull(message = "Card number is required")
        @CreditCardNumber(message = "Invalid card number", ignoreNonDigitCharacters = true)
        String cardNumber,

        @NotNull
        @Positive
        @Max(Integer.MAX_VALUE)
        int amount
) {}
