package dev.bankstatement.fileprocessing.repository;

import dev.bankstatement.fileprocessing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing roles.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations for roles.
 *
 * @author Obaid
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by their name.
     *
     * @param name the name to search for
     * @return an Optional containing the role if found, or an empty Optional if not found
     */
    Optional<Role> findByName(String name);
}
