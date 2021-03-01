import Models.Campaign;
import Models.ChartCalculator;
import Models.MetricCalculator;

public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        MetricCalculator calculator1 = campaign.newMetricCalculator();

        calculator1.calculateMetrics();
        print(calculator1);

        System.out.println(" ");

        /**
        a new calculator should be created for each chart, graph or histogram we need
        the print functions are to be replaced with far more variety
        atm you can print all, or get each metric individually (OOP getters)
        atm we can only show them in a terminal but in future this will be changed to produce outputs that can be used in charts and histograms

        i also want to go back to storing the records as a map of the id to the details
        i changed this so it was easier to make classes and i think it could now improve the performance a bit
        **/

        ChartCalculator calculator2 = campaign.newChartCalculator();
        calculator2.createImpressionsDates();
        calculator2.createClicksDates();
        calculator2.createServersDates();
    }

    // temporary function to display metrics in terminal
    public static void print(MetricCalculator calculator) {
        System.out.println("Number of impressions: " + calculator.getImpressionsNo());
        System.out.println("Number of uniques: " + calculator.getUniquesNo());
        System.out.println("Number of clicks: " + calculator.getClicksNo());
        System.out.println("Number of bounces: " + calculator.getBounceNo());
        System.out.println("Number of conversions: " + calculator.getConversionsNo());
        System.out.println("Total impression cost: " + calculator.getTotalImpressionCost());
        System.out.println("Total click cost: " + calculator.getTotalClickCost()); // not a required metric, but one that can be calculated

        System.out.println("CTR: " + calculator.getCtr());
        System.out.println("CPA: " + calculator.getCpa());
        System.out.println("CPC: " + calculator.getCpc());
        System.out.println("CPM: " + calculator.getCpm());
        System.out.println("Bounce Rate: " + calculator.getBr());
    }
}
