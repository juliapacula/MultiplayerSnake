package pg.projekt.SnakeMultiplayer.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import pg.projekt.SnakeMultiplayer.configuration.InputEndpoint;
import org.springframework.stereotype.Controller;
import pg.projekt.SnakeMultiplayer.configuration.OutputEndpoint;

@Controller
public class GameController {
    private Position last;

    @MessageMapping(InputEndpoint.Paths.POST_POSITION)
    public void receivePotition(Position playerPosition) {
        last = playerPosition;
    }

    @MessageMapping(InputEndpoint.Paths.GET_LAST_POSITION)
    @SendTo(OutputEndpoint.Paths.GAME)
    public Position sendLastPosition() {
        return last;
    }
}
