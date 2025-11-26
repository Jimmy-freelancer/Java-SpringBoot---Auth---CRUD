package com.Authentication.System.AuthenticationSystem.services;

import com.Authentication.System.AuthenticationSystem.dto.RegisterRequest;
import com.Authentication.System.AuthenticationSystem.dto.UpdateRequest;
import com.Authentication.System.AuthenticationSystem.dto.UserDto;
import com.Authentication.System.AuthenticationSystem.enitity.type.Role;

import java.util.List;


public interface AdminUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(RegisterRequest request);
    UserDto updateUser(Long id, RegisterRequest request);
    void deleteUser(Long id);
    UserDto updateRole(Long id, Role role);
}
