package ships;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("BATTLESHIP")
public class Battleship extends Ship {
    public Battleship() {}

    public Battleship(String name) {
        super(name, 5);
    }

    @Override
    public String getType() {
        return "BATTLESHIP";
    }
}