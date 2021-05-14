package Models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

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

    public MetricCalculator(int pageLimit, int bounceTime) {
        super(null, null, null, null, pageLimit, bounceTime);
    }

    public MetricCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users, int pageLimit, int bounceTime) {
        super(impressionLog, clickLog, serverLog, users, pageLimit, bounceTime);
        calculateMetrics();
    }

    // calculates metrics from the entire dataset
    public void calculateMetrics() {
        if (getImpressionLog().size() != 0) {
            calculate(getImpressionLog(), getClickLog(), getServerLog(), "Any", "Any", "Any", "Any", getImpressionLog().get(0).getDate(), getImpressionLog().get(getImpressionLog().size() - 1).getDate());
        }
    }

    // calculates metrics with filters
    public void calculateMetrics(String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate) {
        calculate(getImpressionLog(startDate, endDate), getClickLog(startDate, endDate), getServerLog(startDate, endDate), gender, age, context, income, startDate, endDate);
    }

    // the actual calculations
    public void calculate(ArrayList<ImpressionEntry> impressionList, ArrayList<ClickEntry> clickList, ArrayList<ServerEntry> serverList, String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate) {
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

        ArrayList<ImpressionEntry> filteredImpressionList = new ArrayList<>();
        ArrayList<ClickEntry> filteredClickList = new ArrayList<>();
        ArrayList<ServerEntry> filteredServerList = new ArrayList<>();

        // filtering the lists of impressions, clicks and server logs
        for (ImpressionEntry impressionEntry : impressionList) {
            User impressionUser = getUsers().get(impressionEntry.getUserId());

            if (impressionUser.getGender().equals(gender) || gender.equals("Any")) {
                if (impressionUser.getAge().equals(age) || age.equals("Any")) {
                    if (impressionEntry.getContext().equals(context) || context.equals("Any")) {
                        if (impressionUser.getIncome().equals(income) || income.equals("Any")) {
                            filteredImpressionList.add(impressionEntry);
                        }
                    }
                }
            }
        }
        for (ClickEntry clickEntry : clickList) {
            User clickUser = getUsers().get(clickEntry.getUserId());

            if (clickUser.getGender().equals(gender) || gender.equals("Any")) {
                if (clickUser.getAge().equals(age) || age.equals("Any")) {
                    if (clickUser.getIncome().equals(income) || income.equals("Any")) {
                        filteredClickList.add(clickEntry);
                    }
                }
            }
        }
        for (ServerEntry serverEntry : serverList) {
            User serverUser = getUsers().get(serverEntry.getUserId());

            if (serverUser.getGender().equals(gender) || gender.equals("Any")) {
                if (serverUser.getAge().equals(age) || age.equals("Any")) {
                    if (serverUser.getIncome().equals(income) || income.equals("Any")) {
                        filteredServerList.add(serverEntry);
                    }
                }
            }
        }

        // calculates the number of impressions
        this.impressionsNo = filteredImpressionList.size();

        // calculates the number of impressions from unique users and the total cost of impressions
        HashSet<Long> uniqueIds = new HashSet<>();

        for (ImpressionEntry impression : filteredImpressionList) {
            if (!uniqueIds.contains(impression.getUserId())) {
                uniqueIds.add(impression.getUserId());
                this.uniquesNo++;
            }
            this.totalImpressionsCost += impression.getImpressionCost();
        }

        // calculates the number of clicks
        this.clicksNo = filteredClickList.size();

        // calculates the total cost of clicks
        for (ClickEntry click : filteredClickList) {
            this.totalClicksCost += click.getClickCost();
        }

        // calculates the number of bounces and the number of conversions
        for (ServerEntry server : filteredServerList) {
            if ((server.getPages() <= pageLimit && pageLimit != 0) || (timeDifference(server.getEntryDate(), server.getExitDate()) <= bounceTime && bounceTime != 0)) {
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
    public long timeDifference(LocalDateTime entryDate, LocalDateTime exitDate) {
        if (exitDate == null) {
            return -1; // where the exit date is invalid, it's counted as a bounce
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