package pg.projekt.SnakeMultiplayer.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import pg.projekt.SnakeMultiplayer.configuration.InputEndpoint;
import org.springframework.stereotype.Controller;
import pg.projekt.SnakeMultiplayer.messages.PositionMessage;

@Controller
public class PositionsController {
    @MessageMapping(InputEndpoint.Paths.POST_POSITION)
    public void receivePotition(@Payload PositionMessage message) {
//        TODO: Save Positions
    }
}
