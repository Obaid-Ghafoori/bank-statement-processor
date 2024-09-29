package dev.bankstatement.fileprocessing.controller;

import org.springframework.security.authentication.AuthenticationManager;

import dev.bankstatement.fileprocessing.config.JwtTokenProvider;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
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
     * @param jwtTokenProvider      JWT token provider instance
     * @param authenticationManager authentication manager instance
     */
    public PasswordResetController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(jwtTokenProvider, authenticationManager);
    }


}
