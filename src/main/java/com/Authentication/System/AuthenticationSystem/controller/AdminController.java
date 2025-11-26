package com.Authentication.System.AuthenticationSystem.controller;


import com.Authentication.System.AuthenticationSystem.dto.ApiResponse;
import com.Authentication.System.AuthenticationSystem.dto.RegisterRequest;
import com.Authentication.System.AuthenticationSystem.dto.UserDto;
import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import com.Authentication.System.AuthenticationSystem.services.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAdminUser(){
        List<UserDto> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .message("Users fetched")
                .data(users)
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id){
        UserDto userDto = adminUserService.getUserById(id);
        return  ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .message("User fetched")
                .data(userDto)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody RegisterRequest req) {
        UserDto dto = adminUserService.createUser(req);
        return ResponseEntity.status(201).body(ApiResponse.builder().status(201).message("User created").data(dto).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequest req) {
        UserDto dto = adminUserService.updateUser(id, req);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("User updated").data(dto).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("User deleted").build());
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse> updateRole(
            @PathVariable Long id,
            @RequestParam Role value
    ) {
        UserDto updated = adminUserService.updateRole(id, value);

        ApiResponse<Object> response = ApiResponse.builder()
                .status(200)
                .message("Role updated successfully")
                .data(updated)
                .build();

        return ResponseEntity.ok(response);
    }



}
