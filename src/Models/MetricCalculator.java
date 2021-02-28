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

    public MetricCalculator(Impressions impressions, Clicks clicks, Servers servers) {
        this.impressions = impressions;
        this.clicks = clicks;
        this.servers = servers;
    }

    // calculates metrics
    public void calculateMetrics(int pageLimit, int bounceTime, String start, String end) {
        try {
            // converts string to date - temporary till inputs
            Date startDate = parseDate(start);
            Date endDate = parseDate(end);

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

    // converts string to date, catches n/a end dates
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (date.equals("n/a")) {
            return null;
        } else {
            return (sdf.parse(date));
        }
    }

    public boolean inTime(Date logDate, Date filterStart, Date filterEnd) {
        if (filterStart == null && filterEnd == null) {
            return true;
        } else if (filterStart != null && filterEnd == null){
            return logDate.after(filterStart);
        } else if (filterStart == null) {
            return logDate.before(filterEnd);
        } else {
            return logDate.after(filterStart) && logDate.before(filterEnd);
        }
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

        while (inTime && count < impressionsList.size()) {
            Impression impression = impressionsList.get(count);

            // checks if the impression log fits within the given time scale
            if (inTime(impression.date, startDate, endDate)) {
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

        while (inTime && count < clicksList.size()) {
            Click click = clicksList.get(count);

            // checks if the click log fits within the given time scale
            if (inTime(click.date, startDate, endDate)) {
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

        while (inTime && count < serversList.size()) {
            Server server = serversList.get(count);

            // checks if the server log fits within the given time scale
            if (inTime(server.entryDate, startDate, endDate)) {
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