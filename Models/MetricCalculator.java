package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MetricCalculator {
    // Already calculated
    private int impressionsNo; // number of impressions
    private int uniquesNo; // number of unique impressions
    private int clicksNo; // number of clicks
    private int bounceNo; // number of bounces
    private int conversionsNo; // number of conversions
    private double totalImpressionCost; // total impression cost
    private double totalClickCost; // total click cost

    // To be calculated
    private double ctr; // number of clicks / number of impressions
    private double cpa; // total impressions cost / number of conversions
    private double cpc; // total impression cost / number of clicks
    private double cpm; // (total impressions cost * 1000) / number of impressions
    private double br; // number of bounces / number of clicks

    // to be set by the user
    // private int pageLimit = 1; // max number of pages to count as a bounce
    // private int bounceTime = 100; // max amount of time spent by user to count as a bounce

    /**
     Number of impressions: people who saw the ad
     Number of clicks: people who clicked the ad
     Number of uniques: unique people who saw the ad
     Number of Bounces: number of people who clicked away after a while
     Number of Conversions: clicks then acts on ad
     Total Impressions Cost: cost of impressions
     Total Click Cost: cost of clicks
     CTR: Click through rate, clicks per impression
     CPA: Cost per acquisition
     CPC: Cost per click
     CPM: Cost per thousand impressions
     Bounce rate
     */

    private Impressions impressions;
    private Clicks clicks;
    private Server server;

    private String impressionLog;
    private String clickLog;
    private String serverLog;

    public MetricCalculator() {
        // File names
        this.impressionLog = "Logs/impression_log.csv";
        this.clickLog = "Logs/click_log.csv";
        this.serverLog = "Logs/server_log.csv";
    }

    // Calculates metrics
    public void calculateMetrics(int pageLimit, int bounceTime, String start, String end) {
        // Date filtering
        try {
            Date endDate = parseDate(end);
            Date startDate = parseDate(start);

            // Reads the log files
            impressions = new Impressions(impressionLog, startDate, endDate);
            clicks = new Clicks(clickLog, startDate, endDate);
            server = new Server(serverLog, pageLimit, bounceTime, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Metrics gathered directly from logs
        impressionsNo = impressions.getImpressionNo();
        uniquesNo = impressions.getUniquesNo();
        clicksNo = clicks.getClickNo();
        bounceNo = server.getBounceNo();
        conversionsNo = server.getConversionNo();
        totalImpressionCost = impressions.getTotalCost();
        totalClickCost = clicks.getTotalCost();

        // Additional metrics calculated from previous metrics
        ctr = (double) clicksNo / (double) impressionsNo;
        cpa = totalImpressionCost / conversionsNo;
        cpc = totalImpressionCost / clicksNo;
        cpm = (totalImpressionCost * 1000) / impressionsNo;
        br = (double) bounceNo / (double) clicksNo;
    }

    // Converts string to date
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return (sdf.parse(date));
    }

    // Temporary function to display metrics in terminal
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

    // Main
    public static void main(String[] args){
        MetricCalculator calculator = new MetricCalculator();

        // starts taking start date as string
        calculator.calculateMetrics(2, 200, "2015-01-01 12:01:21", "2015-01-01 13:51:59");
        calculator.print();
    }
}