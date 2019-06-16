package pg.projekt.SnakeMultiplayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastAllEmitter;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastOneEmitter;
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

    public void sendAllPositions() {
        broadcastAll.sendPositions(mainService.getPlayers());
    }

    public void changePositions(String sessionId, List<Position> positions) {
        if (mainService.getPlayers().stream().filter((player) -> !player.getId().equals(sessionId)).noneMatch((player) -> player.isColliding(positions.get(0)))) {
            mainService.getPlayer(sessionId).setPositions(positions);
        } else {
            broadcastAll.broadcastDeath(mainService.getPlayer(sessionId));
            mainService.disconnectPlayer(sessionId);
        }
    }
}
