package pg.projekt.SnakeMultiplayer.messages;

import pg.projekt.SnakeMultiplayer.models.Position;

public class PositionMessage {
    private String id;
    private Position[] positions;

    public PositionMessage(String id, Position[] positions) {
        this.id = id;
        this.positions = positions;
    }

    public String getId() {
        return id;
    }

    public Position[] getPositions() {
        return positions;
    }
}
