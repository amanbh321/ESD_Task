package com.aman.esd_task.dto;

import jakarta.validation.constraints.NotNull;

public record LoginResponse(
        @NotNull
        Boolean authenticated,
        String token
) {
}
