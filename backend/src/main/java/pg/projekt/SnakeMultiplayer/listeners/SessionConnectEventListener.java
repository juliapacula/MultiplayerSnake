package pg.projekt.SnakeMultiplayer.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import pg.projekt.SnakeMultiplayer.services.MainService;

@Component
public class SessionConnectEventListener implements ApplicationListener<SessionConnectEvent> {
    @Autowired
    MainService mainService;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
//        mainService.connectPlayer((String)event.getMessage().getHeaders().get("simpSessionId"));
    }
}