package Models;

public class Campaign {
    private final ImpressionLog impressionLog;
    private final ClickLog clicksLog;
    private final ServerLog serversLog;

    public Campaign(String impressionFile, String clickFile, String serverFile) {
        // reads the log files
        this.impressionLog = new ImpressionLog(impressionFile);
        this.clicksLog = new ClickLog(clickFile);
        this.serversLog = new ServerLog(serverFile);
    }

    // modular functions for later
    public MetricCalculator newMetricCalculator() {
        return new MetricCalculator(impressionLog, clicksLog, serversLog);
    }

    public ChartCalculator newChartCalculator() {
        return new ChartCalculator(impressionLog, clicksLog, serversLog);
    }
}
