package dev.bankstatement.fileprocessing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bankstatement.fileprocessing.config.JwtTokenProvider;
import dev.bankstatement.fileprocessing.model.User;

/**
 * REST controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/auth")
public sealed class AuthController permits PasswordResetController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs an instance of the AuthController class.
     *
     * @param jwtTokenProvider      the JWT token provider instance
     * @param authenticationManager the authentication manager instance
     */
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Handles user login requests.
     *
     * @param userDTO the user data transfer object containing the user's credentials
     * @return a response entity with a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User userDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());

        try {
            authenticationManager.authenticate(authToken);
            String token = jwtTokenProvider.createToken(userDTO.getUsername(), List.of("ROLE_USER"));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
