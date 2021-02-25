package Models;

import java.util.ArrayList;
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
    // private int bounceTime = 10; // bounce time, input by user

    /**
     Number of impressions: people who saw the ad
     Number of clicks: people who clicked the ad
     Number of uniques: unique people who saw the ad
     Number of Bounces: number of people who clicked away after a while
     Number of Conversions: clicks then acts on ad
     Total Cost:
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
        impressionLog = "Logs/impression_log.csv";
        clickLog = "Logs/click_log.csv";
        serverLog = "Logs/server_log.csv";

        calculateMetrics();
        print();
    }

    // Calculates metrics
    public void calculateMetrics(/*filtering to be added*/) {
        // Reads the log files
        impressions = new Impressions(impressionLog);
        clicks = new Clicks(clickLog);
        server = new Server(serverLog);

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

    // Temporary function to display metrics in terminal
    public void print() {
        System.out.println("Number of impressions: " + impressionsNo);
        System.out.println("Number of clicks: " + clicksNo);
        System.out.println("Number of uniques: " + uniquesNo);
        System.out.println("Number of bounces: " + bounceNo);
        System.out.println("Number of conversions: " + conversionsNo);
        System.out.println("Total cost: " + totalImpressionCost);
        System.out.println("CTR: " + ctr);
        System.out.println("CPA: " + cpa);
        System.out.println("CPC: " + cpc);
        System.out.println("CPM: " + cpm);
        System.out.println("Bounce Rate: " + br);
    }

    // Main
    public static void main(String[] args){
        MetricCalculator calculator = new MetricCalculator();
    }
}
