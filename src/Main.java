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

        // a new calculator should be created for each chart, graph or histogram we need
        // the print functions are to be replaced with far more variety
        // atm you can print all, or get each metric individually (OOP getters)
        // atm we can only show them in a terminal but in future this will be changed to produce outputs that can be used in charts and histograms
    }
}
