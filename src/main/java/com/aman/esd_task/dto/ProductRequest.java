package com.aman.esd_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotNull
        @NotEmpty
        @NotBlank
        @JsonProperty("product_name")
        String productName,

        @NotNull
        @Min(1)
        Double price
) {
}
