package dev.bankstatement.fileprocessing.service;

import dev.bankstatement.fileprocessing.model.Role;
import dev.bankstatement.fileprocessing.model.User;
import dev.bankstatement.fileprocessing.repository.RoleRepository;
import dev.bankstatement.fileprocessing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private  RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("when user does not exist, system registers with role successfully")
    public void whenUserDoesNotExist_ThenSystemRegistersUserWithRoleSuccessfully() {
        // Arrange
        var user = new User();
        user.setUsername("Tom");
        user.setPassword("encoded_password");

        var role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        // Mocking the behavior of repositories
        when(userRepository.findByUsername("Tom")).thenReturn(Optional.empty());  // User does not exist
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role)); // Role exists
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        var registeredUser = userService.registerUser("Tom", "1234ojd", "ROLE_USER");

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getRoles()).extracting("name").containsExactly("ROLE_USER");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("when user registers, system encode user password successfully")
    public void whenUserRegisters_ThenSystemEncodesUserPasswordSuccessfully() {
        // Arrange
        var user = new User();
        user.setUsername("Tom");
        user.setPassword("encoded_password");

        var role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(passwordEncoder.encode("1234ojd")).thenReturn("encoded_password");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role)); // Role exists
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        var registeredUser = userService.registerUser("Tom", "1234ojd", "ROLE_USER");

        assertThat(registeredUser.getPassword()).isEqualTo("encoded_password");

        verify(userRepository, times(1)).save(any(User.class));
    }

}