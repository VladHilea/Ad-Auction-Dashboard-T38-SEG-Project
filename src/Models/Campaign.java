package Models;

import java.text.ParseException;

public class Campaign {
    private Impressions impressions;
    private Clicks clicks;
    private Servers servers;

    public Campaign(String impressionLog, String clickLog, String serverLog) {
        // reads the log files
        try {
            this.impressions = new Impressions(impressionLog);
            this.clicks = new Clicks(clickLog);
            this.servers = new Servers(serverLog);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // modular functions for later
    public MetricCalculator newMetricCalculator() {
        return new MetricCalculator(impressions, clicks, servers);
    }

    public ChartCalculator newChartCalculator() {
        return new ChartCalculator(impressions, clicks, servers);
    }
}
