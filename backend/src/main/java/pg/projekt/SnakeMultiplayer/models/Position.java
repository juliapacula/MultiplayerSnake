package pg.projekt.SnakeMultiplayer.models;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isBetween(Position position1, Position position2) {
        if (position1.getX() == position2.getX() && position1.getX() == x) {
            if (position1.getY() > position2.getY()) {
                return y <= position1.getY() && y >= position2.getY();
            } else {
                return y >= position1.getY() && y <= position2.getY();
            }
        }

        if (position1.getY() == position2.getY() && position1.getY() == y) {
            if (position1.getX() > position2.getX()) {
                return x <= position1.getX() && x >= position2.getX();
            } else {
                return x >= position1.getX() && x <= position2.getX();
            }
        }

        return false;
    }

    public boolean equals(Position position) {
        return x == position.getX() && y == position.getY();
    }
}
