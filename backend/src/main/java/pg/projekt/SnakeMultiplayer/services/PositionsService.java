package pg.projekt.SnakeMultiplayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastAllEmitter;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastOneEmitter;
import pg.projekt.SnakeMultiplayer.exceptions.CollisionException;
import pg.projekt.SnakeMultiplayer.models.Position;

import java.util.List;

@Service
public class PositionsService {
    @Autowired
    MainService mainService;
    @Autowired
    BroadcastAllEmitter broadcastAll;
    @Autowired
    BroadcastOneEmitter broadcastOne;

    @Async
    public void sendAllPositions() {
        broadcastAll.sendPositions(mainService.getPlayers());
    }

    @Async
    public void changePositions(String sessionId, List<Position> positions) {
        try {
            mainService.setNewPositions(sessionId, positions);
        } catch (CollisionException e) {
            broadcastAll.broadcastDeath(mainService.getPlayer(sessionId));
            mainService.disconnectPlayer(sessionId);
        }
    }

}
