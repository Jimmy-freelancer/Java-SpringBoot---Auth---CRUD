package com.Authentication.System.AuthenticationSystem.dto;

import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
