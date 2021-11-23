package app.routeplanner.repositories;

import app.routeplanner.models.ERole;
import app.routeplanner.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
