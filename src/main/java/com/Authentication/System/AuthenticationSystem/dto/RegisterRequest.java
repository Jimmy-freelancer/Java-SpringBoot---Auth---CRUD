package com.Authentication.System.AuthenticationSystem.dto;

import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
