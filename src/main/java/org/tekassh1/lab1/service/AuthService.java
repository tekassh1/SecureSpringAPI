package org.tekassh1.lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tekassh1.lab1.dto.JwtAuthResponse;
import org.tekassh1.lab1.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtAuthResponse signUp(String username, String password) {
        if (usersService.exists(username)) {
            return JwtAuthResponse.builder()
                    .token(null)
                    .errorMsg("This username already exists!")
                    .build();
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        usersService.create(user);

        String token = jwtService.generateToken(user);
        return JwtAuthResponse.builder()
                .token(token)
                .errorMsg(null)
                .build();
    }

    @Transactional
    public JwtAuthResponse signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username,
                    password
            ));
        } catch (AuthenticationException e) {
            return JwtAuthResponse.builder()
                    .token(null)
                    .errorMsg("Wrong username or password!")
                    .build();
        }

        var user = usersService
                .userDetailsService()
                .loadUserByUsername(username);
        var jwt = jwtService.generateToken(user);

        return new JwtAuthResponse(jwt, null);
    }
}
