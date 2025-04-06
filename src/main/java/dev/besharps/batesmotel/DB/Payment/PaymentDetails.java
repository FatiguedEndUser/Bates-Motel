package dev.besharps.batesmotel.DB.Payment;

import io.micrometer.common.lang.Nullable;

import java.time.LocalDate;

public record PaymentDetails(
        @Nullable
        Integer cvv,

        @Nullable
        LocalDate expiration,

        @Nullable
        String cardholder,

        @Nullable
        Integer zip
){}
