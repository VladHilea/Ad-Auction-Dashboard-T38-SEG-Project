package Models;

public class Calculator {
    private final Impressions impressions;
    private final Clicks clicks;
    private final Servers servers;

    public Calculator(Impressions impressions, Clicks clicks, Servers servers) {
        this.impressions = impressions;
        this.clicks = clicks;
        this.servers = servers;
    }

    public Impressions getImpressions() {
        return impressions;
    }

    public Clicks getClicks() {
        return clicks;
    }

    public Servers getServers() {
        return servers;
    }
}
