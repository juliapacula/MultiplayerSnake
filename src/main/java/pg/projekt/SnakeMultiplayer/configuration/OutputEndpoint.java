package pg.projekt.SnakeMultiplayer.configuration;

public enum OutputEndpoint implements Endpoint {
    CURRENT_GAME(Paths.GAME);

    private final String url;

    OutputEndpoint(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public static class Paths {
        public static final String GAME = "/gameStatus";
    }
}
