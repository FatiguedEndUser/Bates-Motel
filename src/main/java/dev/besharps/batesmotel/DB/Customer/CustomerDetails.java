package dev.besharps.batesmotel.DB.Customer;

import io.micrometer.common.lang.Nullable;

public record CustomerDetails(
        @Nullable
        String car_information,
        @Nullable
        String firstName,
        @Nullable
        String lastName,
        @Nullable
        Integer phoneNumber
) {}
