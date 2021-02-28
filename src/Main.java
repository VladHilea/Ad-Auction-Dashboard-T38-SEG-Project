import Models.Campaign;
import Models.MetricCalculator;

public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        MetricCalculator calculator1 = campaign.newCalculator();
        MetricCalculator calculator2 = campaign.newCalculator();

        // all metrics
        calculator1.calculateMetrics(1, 500, "n/a", "n/a");
        calculator1.print();

        System.out.println(" ");

        // metrics within a time frame, and different filtering
        calculator2.calculateMetrics(2, 250, "2015-01-01 12:01:21", "2015-01-01 13:51:59");
        calculator2.print();
    }
}
