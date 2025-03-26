package builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ships.Battleship;
import repositories.ShipRepository;

@Component
public class BattleshipBuilder implements ShipBuilder {
    @Autowired
    private ShipRepository shipRepository;

    private Battleship battleship;

    @Override
    public void buildName(String name) {
        this.battleship = new Battleship(name);
    }

    @Override
    public Battleship getShip() {
        return shipRepository.save(battleship);
    }
}