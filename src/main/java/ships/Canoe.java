package ships;

public class Canoe extends Ship {
    public Canoe() {
        super(1);
    }

    @Override
    public boolean hit(int position) {
        positions[0] = true;
        return true;
    }
}
