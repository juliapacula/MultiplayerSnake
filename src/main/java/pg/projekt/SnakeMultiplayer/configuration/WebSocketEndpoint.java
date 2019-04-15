package pg.projekt.SnakeMultiplayer.configuration;

public enum WebSocketEndpoint implements Endpoint {
    GAME(WebSockets.GAME);

    private String url;

    WebSocketEndpoint(String endpoint) {
        this.url = endpoint;
    }

    public String getUrl() {
        return url;
    }

    public static class WebSockets {
        public static final String GAME = "/websocket/game";
    }
}
