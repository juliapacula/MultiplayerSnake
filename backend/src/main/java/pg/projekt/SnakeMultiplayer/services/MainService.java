package pg.projekt.SnakeMultiplayer.services;

import org.springframework.stereotype.Service;
import pg.projekt.SnakeMultiplayer.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    private Map<String, Player> players = new HashMap<String, Player>();

    public void connectPlayer(String id) {
        System.out.println(id);
        if (players.size() < 5) {
            players.put(id, new Player(id));
        }
    }

    public void  disconnectPlayer(String id) {
        if (hasPlayer(id)) {
            players.remove(id);
        }
    }

    public boolean hasPlayer(String id) {
        return players.get(id) != null;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }
}
