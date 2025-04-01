package dev.besharps.batesmotel.DB.User;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Null;

public record UserDetails(
        @Nullable
        String email,

        @Nullable
        String password,

        @Nullable
        Integer points,

        @Nullable
        String username
){}
