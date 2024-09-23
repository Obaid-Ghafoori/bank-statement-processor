package dev.bankstatement.fileprocessing.repository;

import dev.bankstatement.fileprocessing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing users.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations for users.
 *
 * @author Obaid
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or an empty Optional if not found
     */

    Optional<User> findByUsername(String username);
}
