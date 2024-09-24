package dev.bankstatement.fileprocessing.service;

import dev.bankstatement.fileprocessing.model.Role;
import dev.bankstatement.fileprocessing.model.User;
import dev.bankstatement.fileprocessing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User registerUser(String username, String password, Set<Role> roles){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("User already exists");
        }

        var user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        return userRepository.save(user);
    }


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
