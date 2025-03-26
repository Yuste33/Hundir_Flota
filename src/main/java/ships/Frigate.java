package ships;

public class Frigate extends Ship {
    public Frigate() {
        super(3);
    }

    @Override
    public boolean hit(int position) {
        if (position >= 0 && position < size) {
            positions[position] = true;
            return true;
        }
        return false;
    }
}
