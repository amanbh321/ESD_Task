package com.aman.esd_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CustomerResponse(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email,
        @JsonProperty("address")
        String address,
        @JsonProperty("city")
        String city,
        @JsonProperty("pincode")
        String pincode
) {
}
