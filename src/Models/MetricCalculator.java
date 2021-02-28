package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class MetricCalculator {
    private int impressionsNo; // number of impressions - people who saw the ad
    private int uniquesNo; // number of unique impressions - unique people who saw the ad
    private int clicksNo; // number of clicks - people who clicked the ad
    private int bounceNo; // number of bounces - people who clicked away after a while
    private int conversionsNo; // number of conversions - people who click then acts on ad
    private double totalImpressionCost; // total impression cost - cost of impressions
    private double totalClickCost; // total click cost - cost of clicks

    private double ctr; // click-through-rate - clicks per impression
    private double cpa; // cost-per-acquisition
    private double cpc; // cost-per-click
    private double cpm; // cost-per-thousand impressions
    private double br; // bounce rate - number of bounces per click

    private Impressions impressions;
    private Clicks clicks;
    private Servers servers;

    public MetricCalculator() {
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

    // calculates metrics
    public void calculateMetrics(int pageLimit, int bounceTime, String start, String end) {
        try {
            // converts string to date - temporary till inputs
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            // calculating metrics from the three separate logs
            calculateImpressionsMetrics(startDate, endDate);
            calculateClicksMetrics(startDate, endDate);
            calculateServersMetrics(pageLimit, bounceTime, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // additional metrics calculated from previous metrics
        ctr = (double) clicksNo / (double) impressionsNo;
        cpa = totalImpressionCost / conversionsNo;
        cpc = totalImpressionCost / clicksNo;
        cpm = (totalImpressionCost * 1000) / impressionsNo;
        br = (double) bounceNo / (double) clicksNo;
    }

    // calculates metrics from impressions
    public void calculateImpressionsMetrics(Date startDate, Date endDate /*more filtering to be added*/) {
        ArrayList<Impression> impressionsList = impressions.getImpressions(); // list of impressions
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        // resets the impressions metrics
        impressionsNo = 0;
        totalImpressionCost = 0;
        uniquesNo = 0;

        while (inTime) {
            Impression impression = impressionsList.get(count);

            // checks if the impression log fits within the given time scale
            if (impression.date.after(startDate) && impression.date.before(endDate)) {
                // calculating total impressions, total cost and unique impressions
                impressionsNo++;
                totalImpressionCost += impression.impressionCost;

                if (!uniqueIds.contains(impression.id)) {
                    uniquesNo++;
                    uniqueIds.add(impression.id);
                }
            } else if (impression.date.after(endDate)) {
                inTime = false;
            }

            count++;
        }
    }

    // calculates metrics from clicks
    public void calculateClicksMetrics(Date startDate, Date endDate /*more filtering to be added*/) {
        ArrayList<Click> clicksList = clicks.getClicks(); // list of clicks
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        // resets the clicks metrics
        clicksNo = 0;
        totalClickCost = 0;

        while (inTime) {
            Click click = clicksList.get(count);

            // checks if the click log fits within the given time scale
            if (click.date.after(startDate) && click.date.before(endDate)) {
                // calculating total clicks and total cost
                clicksNo++;
                totalClickCost += click.clickCost;
            } else if (click.date.after(endDate)) {
                inTime = false;
            }

            count++;
        }
    }

    public void calculateServersMetrics(int pageLimit, int bounceTime, Date startDate, Date endDate /*more filtering to be added*/) {
        ArrayList<Server> serversList = servers.getServers(); // list of clicks
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        // resets the servers metrics
        bounceNo = 0;
        conversionsNo = 0;

        while (inTime) {
            Server server = serversList.get(count);

            // checks if the server log fits within the given time scale
            if (server.entryDate.after(startDate) && server.entryDate.before(endDate)) {
                // calculating bounce number and conversion number
                if (server.pages <= pageLimit || splitDates(bounceTime, server.entryDate, server.exitDate) <= bounceTime) {
                    bounceNo++;
                }
                if (server.conversion) {
                    conversionsNo++;
                }
            } else if (server.entryDate.after(endDate)) {
                inTime = false;
            }

            count++;
        }
    }

    // calculates difference between two dates given as strings
    public long splitDates(int bounceTime, Date entryDate, Date exitDate) {
        if (exitDate == null) {
            return bounceTime - 1; // where the exit date is invalid, it's counted as a bounce
        } else {
            return exitDate.getTime() - (entryDate.getTime() / 1000);
        }
    }

    // temporary function to display metrics in terminal
    public void print() {
        System.out.println("Number of impressions: " + impressionsNo);
        System.out.println("Number of uniques: " + uniquesNo);
        System.out.println("Number of clicks: " + clicksNo);
        System.out.println("Number of bounces: " + bounceNo);
        System.out.println("Number of conversions: " + conversionsNo);
        System.out.println("Total impression cost: " + totalImpressionCost);
        System.out.println("Total click cost: " + totalClickCost);

        System.out.println("CTR: " + ctr);
        System.out.println("CPA: " + cpa);
        System.out.println("CPC: " + cpc);
        System.out.println("CPM: " + cpm);
        System.out.println("Bounce Rate: " + br);
    }

    // main
    public static void main(String[] args){
        MetricCalculator calculator = new MetricCalculator();

        // filtering variables - temporary till input
        calculator.calculateMetrics(2, 200, "2015-01-01 12:01:21", "2015-01-01 13:51:59");
        calculator.print();
    }
}