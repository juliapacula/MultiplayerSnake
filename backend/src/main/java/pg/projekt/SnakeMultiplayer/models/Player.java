package pg.projekt.SnakeMultiplayer.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Player {
    private String id;
//    private Color color;
    private List<Position> positions = new ArrayList<>();

    public Player(String id) {
        this.id = id;
        positions.add(new PositionGenerator().getRandom());
    }

    public String getId() {
        return id;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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
