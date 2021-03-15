package Models;

import View.Chart;

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
    public MetricCalculator newMetrics() {
        return new MetricCalculator(impressionLog, clicksLog, serversLog);
    }

    public Chart newChart() {
        ChartCalculator hoursCalculator = new ChartCalculator(impressionLog, clicksLog, serversLog, "Hours");
        ChartCalculator daysCalculator = new ChartCalculator(impressionLog, clicksLog, serversLog, "Days");
        ChartCalculator weeksCalculator = new ChartCalculator(impressionLog, clicksLog, serversLog, "Weeks");
        ChartCalculator monthsCalculator = new ChartCalculator(impressionLog, clicksLog, serversLog, "Months");
        ChartCalculator yearsCalculator = new ChartCalculator(impressionLog, clicksLog, serversLog, "Years");

        return new Chart("Metrics vs Time", hoursCalculator, daysCalculator, weeksCalculator, monthsCalculator, yearsCalculator);
    }
}
