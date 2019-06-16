package pg.projekt.SnakeMultiplayer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import pg.projekt.SnakeMultiplayer.configuration.InputEndpoint;
import pg.projekt.SnakeMultiplayer.emitters.BroadcastOneEmitter;
import pg.projekt.SnakeMultiplayer.models.PositionGenerator;
import pg.projekt.SnakeMultiplayer.services.MainService;
import pg.projekt.SnakeMultiplayer.services.PositionsService;

@Controller
public class GameController {
    @Autowired
    MainService mainService;
    @Autowired
    PositionsService positionsService;

    @MessageMapping(InputEndpoint.Paths.JOIN_GAME)
    public void joinGame(@Header("simpSessionId") String sessionId) {
        mainService.connectPlayer(sessionId);
        positionsService.sendAllPositions();
    }

    @MessageMapping(InputEndpoint.Paths.LEAVE_GAME)
    public void leaveGame(@Header("simpSessionId") String sessionId) {
        mainService.disconnectPlayer(sessionId);
    }
}
