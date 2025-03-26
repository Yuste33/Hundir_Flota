package builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ships.Canoe;
import repositories.ShipRepository;

@Component
public class CanoeBuilder implements ShipBuilder {


    @Autowired
    private ShipRepository shipRepository;

    private Canoe canoe;

    @Override
    public void buildName(String name) {
        this.canoe = new Canoe(name);
    }

    @Override
    public Canoe getShip() {
        return shipRepository.save(canoe); // Persiste la Canoe en la BD
    }
}