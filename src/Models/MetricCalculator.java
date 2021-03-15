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
    private float totalImpressionsCost; // total impression cost - cost of impressions
    private float totalClicksCost; // total click cost - cost of clicks

    private float ctr; // click-through-rate - clicks per impression
    private float cpa; // cost-per-acquisition
    private float cpc; // cost-per-click
    private float cpm; // cost-per-thousand impressions
    private float br; // bounce rate - number of bounces per click

    private final int pageLimit; // max number of pages to be counted as a bounce
    private final int bounceTime; // max amount of time to be counted as a bounce

    public MetricCalculator(ImpressionLog impressions, ClickLog clickLog, ServerLog serverLog) {
        super(impressions, clickLog, serverLog);

        this.pageLimit = 1;
        this.bounceTime = 500;

        resetMetrics();
        calculateMetrics();
    }

    // calculates metrics
    public void calculateMetrics() {
        ArrayList<Impression> impressionList = getImpressionLog().getImpressionsList(); // list of impressions
        ArrayList<Click> clickList = getClickLog().getClicksList(); // list of clicks
        ArrayList<Server> serverList = getServerLog().getServerList(); // list of server entries

        resetMetrics();
        calculate(impressionList, clickList, serverList);
    }

    // calculates metrics within a given range
    public void calculateMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Impression> impressionList = getImpressionLog().getImpressionsList(startDate, endDate); // list of impressions
        ArrayList<Click> clickList = getClickLog().getClicksList(startDate, endDate); // list of clicks
        ArrayList<Server> serverList = getServerLog().getServerList(startDate, endDate); // list of server entries

        resetMetrics();
        calculate(impressionList, clickList, serverList);
    }

    // resets the metrics
    public void resetMetrics() {
        impressionsNo = 0;
        uniquesNo = 0;
        clicksNo = 0;
        bouncesNo = 0;
        conversionsNo = 0;
        totalImpressionsCost = 0;
        totalClicksCost = 0;

        ctr = 0;
        cpa = 0;
        cpc = 0;
        cpm = 0;
        br = 0;
    }

    // the actual calculations
    public void calculate(ArrayList<Impression> impressionList, ArrayList<Click> clickList, ArrayList<Server> serverList) {
        // calculates the number of impressions
        this.impressionsNo = impressionList.size();

        // calculates the number of impressions from unique users and the total cost of impressions
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users
        for (Impression impression : impressionList) {
            if (!uniqueIds.contains(impression.getId())) {
                uniqueIds.add(impression.getId());
                this.uniquesNo++;
                this.totalImpressionsCost += impression.getImpressionCost();
            }
        }

        // calculates the number of clicks
        this.clicksNo = clickList.size();

        // calculates the total cost of clicks
        for (Click click : clickList) {
            this.totalClicksCost += click.getClickCost();
        }

        // calculates the number of bounces and the number of conversions
        for (Server server : serverList) {
            if (server.getPages() <= pageLimit || timeDifference(bounceTime, server.getEntryDate(), server.getExitDate()) <= bounceTime) {
                this.bouncesNo++;
            }
            if (server.isConversion()) {
                this.conversionsNo++;
            }
        }

        // additional metrics calculated from previous metrics
        ctr = (float) clicksNo / (float) impressionsNo;
        cpa = totalImpressionsCost / conversionsNo;
        cpc = totalImpressionsCost / clicksNo;
        cpm = (totalImpressionsCost * 1000) / impressionsNo;
        br = (float) bouncesNo / (float) clicksNo;
    }

    // calculates difference between two dates given as strings
    public long timeDifference(int bounceTime, LocalDateTime entryDate, LocalDateTime exitDate) {
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

    public int getBouncesNo() {
        return bouncesNo;
    }

    public int getConversionsNo() {
        return conversionsNo;
    }

    public float getTotalImpressionCost() {
        return totalImpressionsCost;
    }

    public float getTotalClickCost() {
        return totalClicksCost;
    }

    public float getCtr() {
        return ctr;
    }

    public float getCpa() {
        return cpa;
    }

    public float getCpc() {
        return cpc;
    }

    public float getCpm() {
        return cpm;
    }

    public float getBr() {
        return br;
    }
}