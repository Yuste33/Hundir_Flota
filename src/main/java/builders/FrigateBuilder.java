package builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ships.Frigate;
import repositories.ShipRepository;

@Component
public class FrigateBuilder implements ShipBuilder {

    @Autowired
    private ShipRepository shipRepository;

    private Frigate frigate;

    @Override
    public void buildName(String name) {
        this.frigate = new Frigate(name);
    }

    // Método adicional para configurar cañones
    public void buildCannons(int cannonCount) {
        frigate.setCannonCount(cannonCount);
    }

    @Override
    public Frigate getShip() {
        return shipRepository.save(frigate); // Persiste la Frigate en la BD
    }
}