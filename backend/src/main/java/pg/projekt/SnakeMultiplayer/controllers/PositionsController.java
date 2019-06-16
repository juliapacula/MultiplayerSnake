package pg.projekt.SnakeMultiplayer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import pg.projekt.SnakeMultiplayer.configuration.InputEndpoint;
import org.springframework.stereotype.Controller;
import pg.projekt.SnakeMultiplayer.models.Position;
import pg.projekt.SnakeMultiplayer.services.PositionsService;

import java.util.ArrayList;

@Controller
public class PositionsController {
    @Autowired
    private PositionsService positionsService;

    @MessageMapping(InputEndpoint.Paths.POST_POSITION)
    public void receivePosition(@Payload ArrayList<Position> positions, @Header("simpSessionId") String sessionId) {
        positionsService.changePositions(sessionId, positions);
    }

    @Scheduled(fixedRate=1000)
    public void broadcastAll() {
        positionsService.sendAllPositions();
    }
}
