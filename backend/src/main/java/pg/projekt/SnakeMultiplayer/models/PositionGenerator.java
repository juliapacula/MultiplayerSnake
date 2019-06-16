package pg.projekt.SnakeMultiplayer.models;

import java.util.Random;

public class PositionGenerator {
    private final int size = 40;
    public Position getRandom() {
        return new Position(new Random().nextInt(size), new Random().nextInt(size));
    }
}
