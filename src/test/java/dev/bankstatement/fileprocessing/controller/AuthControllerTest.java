package dev.bankstatement.fileprocessing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import dev.bankstatement.fileprocessing.config.JwtTokenProvider;
import dev.bankstatement.fileprocessing.model.Role;
import dev.bankstatement.fileprocessing.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Set;


class AuthControllerTest {
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("User should login successfully with valid credentials")
    void userShouldLoginSuccessfullyWithValidCredentials() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("user");
        user.setPassword("encoded_pass");
        Role role = new Role();
        user.setRoles(Set.of(role));

        var authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        when(authenticationManager.authenticate(authToken)).thenReturn(authToken);
        when(jwtTokenProvider.createToken(user.getUsername(), List.of("ROLE_USER"))).thenReturn("mockToken");

        // Act
        ResponseEntity<Map<String, String>> response = authController.login(user);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("token", "mockToken");
    }

}