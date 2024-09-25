package dev.bankstatement.fileprocessing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 * <p>
 * This class provides security-related configurations for the application, including:
 * <ul>
 *     <li>Security filter chain configuration</li>
 *     <li>Password encryption using BCryptPasswordEncoder</li>
 *     <li>User details service configuration</li>
 * </ul>
 *
 * @author Obaid
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method creates a security filter chain that disables CSRF protection,
     * configures authorization for HTTP requests, and sets up form-based login and logout.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/welcome", "/api/auth/register", "/login").permitAll() // Public routes
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only for ADMIN role
                        .anyRequest().authenticated() // All other routes require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );
        return http.build();
    }

    /**
     * Configures a bean to secure user passwords using BCryptPasswordEncoder.
     *
     * @return a secured password encrypted with BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the user details service for the application.
     * <p>
     * This method returns an InMemoryUserDetailsManager instance, which provides user details for authentication.
     *
     * @return the user details service
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory user details, can be replaced with JDBC, LDAP, etc.
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}