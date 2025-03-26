package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import players.Fleet;

@Repository
public interface FleetRepository extends JpaRepository<Fleet, Long> {
}