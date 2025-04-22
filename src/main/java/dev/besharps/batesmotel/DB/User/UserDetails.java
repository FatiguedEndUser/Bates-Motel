package dev.besharps.batesmotel.DB.User;

import io.micrometer.common.lang.Nullable;

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
