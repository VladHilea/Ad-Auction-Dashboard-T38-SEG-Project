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

    private final int pageLimit = 1; // max number of pages to be counted as a bounce
    private final int bounceTime = 500; // max amount of time to be counted as a bounce

    public MetricCalculator() {
        super(null, null, null);
    }

    public MetricCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog) {
        super(impressionLog, clickLog, serverLog);
        calculateMetrics();
    }

    // calculates metrics from the entire dataset
    public void calculateMetrics() {
        calculate(getImpressionLog(), getClickLog(), getServerLog());
    }

    // the actual calculations
    public void calculate(ArrayList<ImpressionEntry> impressionList, ArrayList<ClickEntry> clickList, ArrayList<ServerEntry> serverList) {
        // resets the metrics
        this.impressionsNo = 0;
        this.uniquesNo = 0;
        this.clicksNo = 0;
        this.bouncesNo = 0;
        this.conversionsNo = 0;
        this.totalImpressionsCost = 0;
        this.totalClicksCost = 0;

        this.ctr = 0;
        this.cpa = 0;
        this.cpc = 0;
        this.cpm = 0;
        this.br = 0;

        // calculates the number of impressions
        this.impressionsNo = impressionList.size();

        // calculates the number of impressions from unique users and the total cost of impressions
        HashSet<Long> uniqueIds = new HashSet<>();

        for (ImpressionEntry impression : impressionList) {
            if (!uniqueIds.contains(impression.getUserId())) {
                uniqueIds.add(impression.getUserId());
                this.uniquesNo++;
            }
            this.totalImpressionsCost += impression.getImpressionCost();
        }

        // calculates the number of clicks
        this.clicksNo = clickList.size();

        // calculates the total cost of clicks
        for (ClickEntry click : clickList) {
            this.totalClicksCost += click.getClickCost();
        }

        // calculates the number of bounces and the number of conversions
        for (ServerEntry server : serverList) {
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