package pg.projekt.SnakeMultiplayer.messages;

import pg.projekt.SnakeMultiplayer.models.Position;

import java.util.ArrayList;
import java.util.List;

public class WelcomeClientMessage {
    private String id;
    private List<Position> positions;

    public WelcomeClientMessage(String id) {
        this.id = id;
//        TODO: Rand new available position
        this.positions = new ArrayList<Position>();
        this.positions.add(new Position(10, 5));
        this.positions.add(new Position(10, 9));
    }

    public String getId() {
        return id;
    }

    public Position[] getPositions() {
        return positions.toArray(new Position[positions.size()]);
    }
}
