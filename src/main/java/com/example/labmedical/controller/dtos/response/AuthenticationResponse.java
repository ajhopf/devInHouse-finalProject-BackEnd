package com.example.labmedical.controller.dtos.response;

import com.example.labmedical.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String name;
    private Role role;
    private String photoUrl;
    @JsonProperty("access_token")
    private String accessToken;

}
