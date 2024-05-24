package com.security.analyzer.v1.Authentication;

import com.security.analyzer.v1.Enum.Roles;
import com.security.analyzer.v1.user.User;
import com.security.analyzer.v1.user.UserRepository;
import com.security.analyzer.v1.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest registerRequest){
        var user =  User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .login(registerRequest.getLogin())
                .roles(Roles.ADMIN)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail()).build();
        var response = userRepository.save(user);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticateRequest.getLogin(),
                                authenticateRequest.getPassword()
                )
        );
        var user  = userRepository.findByLogin(authenticateRequest.getLogin())
                .orElseThrow();
        var jwttoken = jwtService.genarateToken(user);
        return AuthenticationResponse.builder()
                .token(jwttoken).build();
    }
}
