package pg.projekt.SnakeMultiplayer.configuration;

public enum OutputEndpoint implements Endpoint {
    BROADCAST_DEATHS(Paths.BROADCAST_DEATHS),
    BROADCAST_POSITIONS(Paths.BROADCAST_POSITIONS);

    private final String url;

    OutputEndpoint(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public static class Paths {
        public static final String BROADCAST_DEATHS = "/died";
        public static final String BROADCAST_POSITIONS = "/positions/all";
    }
}
