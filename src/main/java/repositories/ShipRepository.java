package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ships.Ship;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
}