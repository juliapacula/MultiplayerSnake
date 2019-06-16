package pg.projekt.SnakeMultiplayer.emitters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pg.projekt.SnakeMultiplayer.configuration.OutputEndpoint;
import pg.projekt.SnakeMultiplayer.models.Player;

import java.util.List;

@Component
public class BroadcastAllEmitter {
    @Autowired
    private SimpMessagingTemplate template;

    public void sendPositions(List<Player> positions) {
        template.convertAndSend(OutputEndpoint.Paths.BROADCAST_POSITIONS, positions);
    }

    public void broadcastDeath(Player player) {
        template.convertAndSend(OutputEndpoint.Paths.BROADCAST_DEATHS, player);
    }
}
