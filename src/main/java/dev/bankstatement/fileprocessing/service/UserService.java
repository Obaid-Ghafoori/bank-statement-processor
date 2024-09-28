package dev.bankstatement.fileprocessing.service;

import dev.bankstatement.fileprocessing.model.User;
import dev.bankstatement.fileprocessing.repository.RoleRepository;
import dev.bankstatement.fileprocessing.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public User registerUser(@NonNull String username, @NonNull  String password, @NonNull String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException(String.format("User already exists with username: %s", username));
        }

        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        var role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException(String.format("Role does not exist: %s", roleName)));
        user.setRoles(new HashSet<>(Set.of(role)));
        return userRepository.save(user);
    }


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String welcomeMessage() {
        return "Welcome to the Bank Statement Processing Application!";
    }
}
