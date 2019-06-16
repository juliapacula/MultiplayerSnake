package pg.projekt.SnakeMultiplayer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pg.projekt.SnakeMultiplayer.services.MainService;

@Component
public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    @Autowired
    MainService mainService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        mainService.disconnectPlayer(event.getSessionId());
    }
}