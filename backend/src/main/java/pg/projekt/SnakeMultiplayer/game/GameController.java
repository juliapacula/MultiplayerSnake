package pg.projekt.SnakeMultiplayer.game;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import pg.projekt.SnakeMultiplayer.configuration.OutputEndpoint;
import pg.projekt.SnakeMultiplayer.messages.WelcomeClientMessage;

import java.util.UUID;

@Controller
public class GameController {
    @SubscribeMapping(OutputEndpoint.Paths.WELCOME_GAME)
    @SendTo(OutputEndpoint.Paths.WELCOME_GAME)
    public WelcomeClientMessage handleClient() {
        UUID uuid = UUID.randomUUID();

        return new WelcomeClientMessage(uuid.toString());
    }
}
