package pg.projekt.SnakeMultiplayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastAllEmitter;
import pg.projekt.SnakeMultiplayer.exceptions.CollisionException;
import pg.projekt.SnakeMultiplayer.models.Player;
import pg.projekt.SnakeMultiplayer.models.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    private final Map<String, Player> players = new HashMap<String, Player>();
    @Autowired
    BroadcastAllEmitter broadcastAll;

    @Async
    public void connectPlayer(String id) {
        if (players.size() < 5) {
            Player playerToAdd = new Player(id);
            while (true) {
                try {
                    checkForPositionColliding(id, playerToAdd.getPositions().get(0));
                    break;
                } catch (CollisionException e) {
                    playerToAdd.randNewPosition();
                }
            }
            synchronized (players) {
                players.put(id, playerToAdd);
            }
        }
    }

    @Async
    public void  disconnectPlayer(String id) {
        if (hasPlayer(id)) {
            synchronized (players) {
                players.remove(id);
            }
        }
    }

    boolean hasPlayer(String id) {
        return players.get(id) != null;
    }

    List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }


    synchronized Player getPlayer(String id) {
        return players.get(id);
    }


    void setNewPositions(String playerId, List<Position> positions) throws CollisionException {
        checkForPositionColliding(playerId, positions.get(0));

        synchronized (players) {
            Player player = players.get(playerId);
            player.setPositions(positions);
        }
    }


    private synchronized void checkForPositionColliding(String id, Position position) throws CollisionException {
        for (Player player: players.values()) {
            if (!player.getId().equals(id) && player.isColliding(position)) {
                throw new CollisionException();
            }
        }
    }
}
