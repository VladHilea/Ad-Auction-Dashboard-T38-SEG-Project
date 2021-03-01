import Models.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        MetricCalculator calculator1 = campaign.newMetricCalculator();

        calculator1.calculateMetrics();
        print(calculator1);

        System.out.println(" ");

        /**
        a new chart calculator should be created for each chart, graph or histogram we need
        the print functions are to be replaced with far more variety
        atm you can print all, or get each metric individually (OOP getters)
        atm we can only show them in a terminal but in future this will be changed to produce outputs that can be used in charts and histograms
        **/

        ChartCalculator calculator2 = campaign.newChartCalculator();
        //System.out.println(calculator2.createDates(LocalDateTime.now(), LocalDateTime.now().plusYears(1)));
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
