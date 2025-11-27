package com.Authentication.System.AuthenticationSystem.services;

import com.Authentication.System.AuthenticationSystem.dto.UserDto;
import com.Authentication.System.AuthenticationSystem.enitity.User;
import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import com.Authentication.System.AuthenticationSystem.repository.UserRepository;
import com.Authentication.System.AuthenticationSystem.security.JwtHelper;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public UserDto register(String username, String email, String password, Role role) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .name(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public String login(String username, String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(auth);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return jwtHelper.generateToken(userDetails);
    }

}
