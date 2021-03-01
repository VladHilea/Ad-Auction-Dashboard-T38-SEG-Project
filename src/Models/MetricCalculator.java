package Models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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

    private final Impressions impressions;
    private final Clicks clicks;
    private final Servers servers;

    public MetricCalculator(Impressions impressions, Clicks clicks, Servers servers) {
        this.impressions = impressions;
        this.clicks = clicks;
        this.servers = servers;
    }

    // calculates metrics
    public void calculateMetrics(int pageLimit, int bounceTime) {
        // calculating metrics from the three separate logs
        calculateImpressionsMetrics();
        calculateClicksMetrics();
        calculateServersMetrics(pageLimit, bounceTime);

        // additional metrics calculated from previous metrics
        ctr = (double) clicksNo / (double) impressionsNo;
        cpa = totalImpressionCost / conversionsNo;
        cpc = totalImpressionCost / clicksNo;
        cpm = (totalImpressionCost * 1000) / impressionsNo;
        br = (double) bounceNo / (double) clicksNo;
    }

    // calculates metrics from impressions
    public void calculateImpressionsMetrics(/*filtering to be added*/) {
        ArrayList<Impression> impressionsList = impressions.getImpressions(); // list of impressions
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users
        int count = 0; // indexing

        // resets the impressions metrics
        impressionsNo = 0;
        totalImpressionCost = 0;
        uniquesNo = 0;

        while (count < impressionsList.size()) {
            Impression impression = impressionsList.get(count);

            // calculating total impressions, total cost and unique impressions
            impressionsNo++;
            totalImpressionCost += impression.impressionCost;

            if (!uniqueIds.contains(impression.id)) {
                uniquesNo++;
                uniqueIds.add(impression.id);
            }

            count++;
        }
    }

    // calculates metrics from clicks
    public void calculateClicksMetrics(/*filtering to be added*/) {
        ArrayList<Click> clicksList = clicks.getClicks(); // list of clicks
        int count = 0; // indexing

        // resets the clicks metrics
        clicksNo = 0;
        totalClickCost = 0;

        while (count < clicksList.size()) {
            Click click = clicksList.get(count);

            // calculating total clicks and total cost
            clicksNo++;
            totalClickCost += click.clickCost;

            count++;
        }
    }

    public void calculateServersMetrics(int pageLimit, int bounceTime /*filtering to be added*/) {
        ArrayList<Server> serversList = servers.getServers(); // list of clicks
        int count = 0; // indexing

        // resets the servers metrics
        bounceNo = 0;
        conversionsNo = 0;

        while (count < serversList.size()) {
            Server server = serversList.get(count);

            // calculating bounce number and conversion number
            if (server.pages <= pageLimit || splitDates(bounceTime, server.entryDate, server.exitDate) <= bounceTime) {
                bounceNo++;
            }
            if (server.conversion) {
                conversionsNo++;
            }
            count++;
        }
    }

    // calculates difference between two dates given as strings
    public long splitDates(int bounceTime, LocalDateTime entryDate, LocalDateTime exitDate) {
        if (exitDate == null) {
            return bounceTime - 1; // where the exit date is invalid, it's counted as a bounce
        } else {
            return (exitDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli() - entryDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()) / 1000;
        }
    }

    public int getImpressionsNo() {
        return impressionsNo;
    }

    public int getUniquesNo() {
        return uniquesNo;
    }

    public int getClicksNo() {
        return clicksNo;
    }

    public int getBounceNo() {
        return bounceNo;
    }

    public int getConversionsNo() {
        return conversionsNo;
    }

    public double getTotalImpressionCost() {
        return totalImpressionCost;
    }

    public double getTotalClickCost() {
        return totalClickCost;
    }

    public double getCtr() {
        return ctr;
    }

    public double getCpa() {
        return cpa;
    }

    public double getCpc() {
        return cpc;
    }

    public double getCpm() {
        return cpm;
    }

    public double getBr() {
        return br;
    }
}