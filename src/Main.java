import Models.*;
import View.Chart;
import org.jfree.ui.RefineryUtilities;

public class Main {
    public static void main(String[] args) {
        // reads the files and stores the logs - only create one campaign otherwise it will be slow
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        // used to display metrics on the main page
        MetricCalculator calculator1 = campaign.newMetricCalculator();
        calculator1.calculateMetrics();
        printMetrics(calculator1);

        System.out.println(" ");

        // used to display metrics as charts
        ChartCalculator calculator2 = campaign.newChartCalculator();
        // hardcoded interval, start and end dates - some server and click log entries are cut off atm because of the dates
        calculator2.calculateCharts("days", calculator2.getImpressionLog().getFirstDate(), calculator2.getImpressionLog().getLastDate());
        printCharts(calculator2);

        // displaying the chart
        Chart chart = new Chart( "Metrics vs Time" , "Metrics vs Time","impressions", calculator2);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        /**
         * notes:
         * interval, start and end dates are partially hardcoded - not much to really to really do till GUI
         * print functions are there to see what is going on easily
         * all the backend is in Models, all the GUI stuff is in View - MVC
         * file reading is only done once - unavoidably slow
         * master branch is using Date, this branch is using LocalDateTime - very important change
         * jfreechart needs to be installed to run - downloads in whatsapp
         *
         * to do:
         * find any possible performance improvements in the backend
         * create class diagrams for 1st deliverable
         * improve & update commenting
         * merge any GUI stuff
         * find out from Yvonne how large the actual data set is - response pending
         * change how the interval is handled - make subclasses of chart calculator for intervals
         *
         * for later:
         * filtering was removed due to my bad implementation - leave till 2nd deliverable
         * bounce factors are hardcoded - leave till 2nd & 3rd deliverables
         * will later add a class HistogramCalculator - leave till 2nd & 3rd deliverables
        **/
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
