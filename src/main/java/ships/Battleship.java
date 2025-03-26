package ships;

public class Battleship extends Ship {
    private boolean[] containers;

    public Battleship() {
        super(5);
        this.containers = new boolean[5];
    }

    @Override
    public boolean hit(int position) {
        if (position >= 0 && position < size) {
            positions[position] = true;
            containers[position] = true;
            return true;
        }
        return false;
    }
}
