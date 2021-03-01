package Models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;

public class MetricCalculator extends Calculator {
    private int impressionsNo; // number of impressions - people who saw the ad
    private int uniquesNo; // number of unique impressions - unique people who saw the ad
    private int clicksNo; // number of clicks - people who clicked the ad
    private int bouncesNo; // number of bounces - people who clicked away after a while
    private int conversionsNo; // number of conversions - people who click then acts on ad
    private double totalImpressionsCost; // total impression cost - cost of impressions
    private double totalClicksCost; // total click cost - cost of clicks

    private double ctr; // click-through-rate - clicks per impression
    private double cpa; // cost-per-acquisition
    private double cpc; // cost-per-click
    private double cpm; // cost-per-thousand impressions
    private double br; // bounce rate - number of bounces per click

    private final int pageLimit; // max number of pages to be counted as a bounce
    private final int bounceTime; // max amount of time to be counted as a bounce

    public MetricCalculator(ImpressionLog impressions, ClickLog clickLog, ServerLog serverLog) {
        super(impressions, clickLog, serverLog);

        this.pageLimit = 1;
        this.bounceTime = 500;
    }

    // calculates metrics
    public void calculateMetrics() {
        // calculating metrics from the three log classes
        calculateImpressionsNo();
        calculateUniquesNo();
        calculateImpressionsCost();
        calculateClicksNo();
        calculateClicksCost();
        calculateBouncesNo(pageLimit, bounceTime);
        calculateConversionsNo();

        // additional metrics calculated from previous metrics
        ctr = (double) clicksNo / (double) impressionsNo;
        cpa = totalImpressionsCost / conversionsNo;
        cpc = totalImpressionsCost / clicksNo;
        cpm = (totalImpressionsCost * 1000) / impressionsNo;
        br = (double) bouncesNo / (double) clicksNo;
    }

    // calculates the number of impressions
    public void calculateImpressionsNo() {
        this.impressionsNo = getImpressionLog().getImpressionsList().size();
    }

    // calculates the number of impressions from unique users
    public void calculateUniquesNo() {
        ArrayList<Impression> impressionsList = getImpressionLog().getImpressionsList(); // list of impressions
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users

        for (Impression impression : impressionsList) {
            if (!uniqueIds.contains(impression.getId())) {
                this.uniquesNo++;
                uniqueIds.add(impression.getId());
            }
        }
    }

    // calculates the total cost of impressions
    public void calculateImpressionsCost() {
        ArrayList<Impression> impressionsList = getImpressionLog().getImpressionsList(); // list of impressions

        for (Impression impression : impressionsList) {
            this.totalImpressionsCost += impression.getImpressionCost();
        }
    }

    // calculates the number of clicks
    public void calculateClicksNo() {
        this.clicksNo = getClickLog().getClicksList().size();
    }

    // calculates the total cost of clicks
    public void calculateClicksCost() {
        ArrayList<Click> clickList = getClickLog().getClicksList(); // list of clicks

        for (Click click : clickList) {
            this.totalClicksCost += click.getClickCost();
        }
    }

    // calculates the number of bounces
    public void calculateBouncesNo(int pageLimit, int bounceTime) {
        ArrayList<Server> serverList = getServerLog().getServerList(); // list of server entries

        for (Server server : serverList) {
            if (server.getPages() <= pageLimit || timeDifference(bounceTime, server.getEntryDate(), server.getExitDate()) <= bounceTime) {
                this.bouncesNo++;
            }
        }
    }

    // calculates difference between two dates given as strings
    public long timeDifference(int bounceTime, LocalDateTime entryDate, LocalDateTime exitDate) {
        if (exitDate == null) {
            return bounceTime - 1; // where the exit date is invalid, it's counted as a bounce
        } else {
            return (exitDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli() - entryDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()) / 1000;
        }
    }

    // calculates the number of conversions
    public void calculateConversionsNo() {
        ArrayList<Server> serverList = getServerLog().getServerList(); // list of server entries

        for (Server server : serverList) {
            if (server.isConversion()) {
                this.conversionsNo++;
            }
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

    public int getBouncesNo() {
        return bouncesNo;
    }

    public int getConversionsNo() {
        return conversionsNo;
    }

    public double getTotalImpressionCost() {
        return totalImpressionsCost;
    }

    public double getTotalClickCost() {
        return totalClicksCost;
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