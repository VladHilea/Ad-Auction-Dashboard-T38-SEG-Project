import Models.*;
import View.Chart;
import org.jfree.ui.RefineryUtilities;

public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        MetricCalculator calculator1 = campaign.newMetricCalculator();

        calculator1.calculateMetrics();
        print(calculator1);

        /*
        System.out.println(" ");

        ChartCalculator calculator2 = campaign.newChartCalculator();
        System.out.println(calculator2.createDates(LocalDateTime.now(), LocalDateTime.now().plusYears(1)));
         */

        Chart chart = new Chart(
                "Metrics vs Time" ,
                "Metrics vs Time",
                calculator1);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }

    // temporary function to display metrics in terminal
    public static void print(MetricCalculator calculator) {
        System.out.println("Number of impressions: " + calculator.getImpressionsNo());
        System.out.println("Number of uniques: " + calculator.getUniquesNo());
        System.out.println("Number of clicks: " + calculator.getClicksNo());
        System.out.println("Number of bounces: " + calculator.getBouncesNo());
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
