package dev.bankstatement.fileprocessing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bankstatement.fileprocessing.model.User;


/**
 * Repository interface for managing users.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations for users.
 *
 * @author Obaid
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or an empty Optional if not found
     */

    Optional<User> findByUsername(String username);
}
