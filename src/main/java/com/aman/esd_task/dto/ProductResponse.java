package com.aman.esd_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponse(
        @JsonProperty("product_name")
        String productName,
        @JsonProperty("price")
        Double price
) {
}
