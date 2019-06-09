package pg.projekt.SnakeMultiplayer.configuration;

// Here are endpoints that client can send messages to
// They have defined PREFIX (WebSocketConfiguration) so
// to send eg. /postPosition you have to actually send /snake/postPosition
public enum InputEndpoint implements Endpoint {
    COORD(Paths.POST_POSITION),
    LAST(Paths.GET_LAST_POSITION);

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
        public static final String GET_LAST_POSITION = "/position/last";
    }
}
