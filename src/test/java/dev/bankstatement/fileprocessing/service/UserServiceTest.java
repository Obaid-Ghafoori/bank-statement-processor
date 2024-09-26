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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("when user already exists, system throws exception")
    public void whenUserAlreadyExists_SystemThrowsException() {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("Tom");
        Role role = new Role();
        role.setName("ROLE_USER");
        existingUser.setRoles(Set.of(role));

        when(userRepository.findByUsername("Tom")).thenReturn(Optional.of(existingUser));  // User already exists

        // Act & Assert using AssertJ
        assertThatThrownBy(() -> userService.registerUser("Tom", "1234ojd", "ROLE_USER"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User already exists with username: Tom");

        // Verify userRepository.save() is never called since the user already exists
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("when role does not exist, system throws exception")
    public void whenRoleDoesNotExist_SystemThrowsException() {
        // Arrange
        when(userRepository.findByUsername("Tom")).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_UNKNOWN")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.registerUser("Tom", "1234ojd", "ROLE_UNKNOWN"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Role does not exist: ROLE_UNKNOWN");

        verify(userRepository, never()).save(any(User.class));
    }


}