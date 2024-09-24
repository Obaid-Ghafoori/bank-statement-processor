package dev.bankstatement.fileprocessing.service;

import dev.bankstatement.fileprocessing.model.User;
import dev.bankstatement.fileprocessing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(String username, String password, List<String> roles){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("User already exists");
        }

        var user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
