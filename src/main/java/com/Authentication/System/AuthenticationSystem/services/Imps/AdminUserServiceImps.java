package com.Authentication.System.AuthenticationSystem.services.Imps;

import com.Authentication.System.AuthenticationSystem.dto.RegisterRequest;
import com.Authentication.System.AuthenticationSystem.dto.UserDto;
import com.Authentication.System.AuthenticationSystem.enitity.User;
import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import com.Authentication.System.AuthenticationSystem.repository.UserRepository;
import com.Authentication.System.AuthenticationSystem.security.ResourceNotFoundException;
import com.Authentication.System.AuthenticationSystem.services.AdminUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserServiceImps implements AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() == null ? Role.USER : request.getRole())
                .build();

        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (request.getUsername() != null) user.setName(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) user.setRole(request.getRole());

        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    @Override
    public UserDto updateRole(Long id, Role role){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        user.setRole(role);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

}
