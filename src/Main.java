import Models.*;
import View.Chart;
import org.jfree.ui.RefineryUtilities;

public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        MetricCalculator calculator1 = campaign.newMetricCalculator();
        printMetrics(calculator1);

        System.out.println(" ");

        ChartCalculator calculator2 = campaign.newChartCalculator();
        printCharts(calculator2);

        Chart chart = new Chart(
                "Metrics vs Time" ,
                "Metrics vs Time",
                calculator1);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }

    // temporary function to display metrics in terminal
    public static void printMetrics(MetricCalculator calculator) {
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

    // temporary function to display charts in terminal
    public static void printCharts(ChartCalculator calculator) {
        System.out.println("List of the number of impressions: " + calculator.getImpressionsNoList());
        System.out.println("List of the number of uniques: " + calculator.getUniquesNoList());
        System.out.println("List of the number of clicks: " + calculator.getClicksNoList());
        System.out.println("List of the number of bounces: " + calculator.getBouncesNoList());
        System.out.println("List of the number of conversions: " + calculator.getConversionsNoList());
        System.out.println("List of the total impression cost: " + calculator.getTotalImpressionCostList());
        System.out.println("List of the total click cost: " + calculator.getTotalClickCostList()); // not a required metric, but one that can be calculated

        System.out.println("List of the CTRs: " + calculator.getCtrList());
        System.out.println("List of the CPAs: " + calculator.getCpaList());
        System.out.println("List of the CPCs: " + calculator.getCpcList());
        System.out.println("List of the CPMs: " + calculator.getCpmList());
        System.out.println("List of the Bounce Rates: " + calculator.getBrList());
    }
}
