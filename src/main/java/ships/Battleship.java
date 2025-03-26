package ships;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("BATTLESHIP")
public class Battleship extends Ship {
    int number;
    String name;
    int level;
    public Battleship(int number, String name, int level) {
        this.name=name;
        this.level=level;
        this.number=number;

    }

    public Battleship() {}

    public Battleship(String name) {
        super(name, 5);
    }


    @Override
    public String getType() {
        return "BATTLESHIP";
    }
}