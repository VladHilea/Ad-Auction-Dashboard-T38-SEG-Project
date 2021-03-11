package Models;

public class Calculator {
    private final ImpressionLog impressionLog;
    private final ClickLog clickLog;
    private final ServerLog serverLog;

    public Calculator(ImpressionLog impressionLog, ClickLog clickLog, ServerLog serverLog) {
        this.impressionLog = impressionLog;
        this.clickLog = clickLog;
        this.serverLog = serverLog;
    }

    public ImpressionLog getImpressionLog() {
        return impressionLog;
    }

    public ClickLog getClickLog() {
        return clickLog;
    }

    public ServerLog getServerLog() {
        return serverLog;
    }
}
