package pg.projekt.SnakeMultiplayer.configuration;

// Here are endpoints that client can send messages to
// They have defined PREFIX (WebSocketConfiguration) so
// to send eg. /postPosition you have to actually send /snake/postPosition
public enum InputEndpoint implements Endpoint {
    ENTER_GAME(Paths.JOIN_GAME),
    LEAVE_GAME(Paths.LEAVE_GAME),
    COORD(Paths.POST_POSITION);

    private final String url;

    InputEndpoint(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public static class Paths {
        public static final String POST_POSITION = "/position/post";
        public static final String JOIN_GAME = "/join";
        public static final String LEAVE_GAME = "/leave";
    }
}
