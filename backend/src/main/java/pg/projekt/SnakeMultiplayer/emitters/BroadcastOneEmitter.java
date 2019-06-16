package pg.projekt.SnakeMultiplayer.emitters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pg.projekt.SnakeMultiplayer.configuration.OutputEndpoint;
import pg.projekt.SnakeMultiplayer.models.Position;

import java.util.List;

@Component
public class BroadcastOneEmitter {
    @Autowired
    private SimpMessagingTemplate template;

    public void sendPosition(String id, Position position) {
        template.convertAndSend( '/' + id + OutputEndpoint.Paths.BROADCAST_POSITIONS, position);
    }
}
