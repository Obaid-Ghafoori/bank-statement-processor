package dev.bankstatement.fileprocessing.controller;

import dev.bankstatement.fileprocessing.config.JwtTokenProvider;
import dev.bankstatement.fileprocessing.service.UserService;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AuthenticationManager;

/**
 *
 * This class extends the AuthController and provides a password reset endpoint.
 *
 * @author Obaid
 */
@ToString
@EqualsAndHashCode
public non-sealed class PasswordResetController extends AuthController {

    /**
     * Constructor for PasswordResetController.
     *
     * @param userService        user service instance
     * @param jwtTokenProvider  JWT token provider instance
     * @param authenticationManager authentication manager instance
     */
    public PasswordResetController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(userService, jwtTokenProvider, authenticationManager);
    }



}
