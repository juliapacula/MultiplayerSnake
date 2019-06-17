package pg.projekt.SnakeMultiplayer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Player {
    private String id;
    private String color;
    private List<Position> positions = new ArrayList<>();

    public Player(String id) {
        this.id = id;
        color = String.format("#%06x", new Random().nextInt(0xffffff + 1));
        positions.add(new PositionGenerator().getRandom());
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public synchronized void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void randNewPosition() {
        positions.set(0, new PositionGenerator().getRandom());
    }

    public boolean isColliding(Position positionToCheck) {
        ListIterator<Position> positionIterator = positions.listIterator();

        while (positionIterator.hasNext()) {
            Position position = positionIterator.next();
            Position nextPosition = null;
            if (positionIterator.hasNext()) {
                nextPosition = positionIterator.next();
            }

            if (position.equals(positionToCheck)) {
                return true;
            }

            if (nextPosition != null) {
                if (!positionToCheck.isBetween(position, nextPosition)) {
                    positionIterator.previous();
                } else {
                    return true;
                }
            }
        }

        return false;
    }
}
