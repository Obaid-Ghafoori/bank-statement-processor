package dev.bankstatement.fileprocessing.controller;

import dev.bankstatement.fileprocessing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides user related endpoint.
 *
 * @author Obaid
 */

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String role) {
        try {
            userService.registerUser(username, password, role);
            return ResponseEntity.ok("User registered successfully ");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcomeMessage() {
        userService.welcomeMessage();
        return ResponseEntity.ok("successful");
    }
}
