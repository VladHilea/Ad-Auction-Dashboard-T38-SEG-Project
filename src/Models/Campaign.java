package Models;

import java.text.ParseException;

public class Campaign {
    private Impressions impressions;
    private Clicks clicks;
    private Servers servers;

    public Campaign() {
        // file names - temporary till inputs
        String impressionLog = "src/Logs/impression_log.csv";
        String clickLog = "src/Logs/click_log.csv";
        String serverLog = "src/Logs/server_log.csv";

        // reads the log files
        try {
            this.impressions = new Impressions(impressionLog);
            this.clicks = new Clicks(clickLog);
            this.servers = new Servers(serverLog);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Campaign campaign = new Campaign();

        MetricCalculator calculator1 = new MetricCalculator(campaign.impressions, campaign.clicks, campaign.servers);
        MetricCalculator calculator2 = new MetricCalculator(campaign.impressions, campaign.clicks, campaign.servers);

        // all metrics
        calculator1.calculateMetrics(1, 500, "n/a", "n/a");
        calculator1.print();

        System.out.println(" ");

        // metrics within a time frame, and different filtering
        calculator2.calculateMetrics(2, 250, "2015-01-01 12:01:21", "2015-01-01 13:51:59");
        calculator2.print();
    }
}
