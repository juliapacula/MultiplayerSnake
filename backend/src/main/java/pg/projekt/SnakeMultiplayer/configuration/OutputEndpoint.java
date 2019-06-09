package pg.projekt.SnakeMultiplayer.configuration;

public enum OutputEndpoint implements Endpoint {
    WELCOME_GAME(Paths.WELCOME_GAME);

    private final String url;

    OutputEndpoint(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public static class Paths {
        public static final String WELCOME_GAME = "/welcome";
    }
}
