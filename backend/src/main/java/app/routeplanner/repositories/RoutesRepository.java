package app.routeplanner.repositories;

import app.routeplanner.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoutesRepository extends JpaRepository<Route, Long> {
}
