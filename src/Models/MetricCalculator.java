package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
    private Servers servers;

    private final String impressionLog;
    private final String clickLog;
    private final String serverLog;

    public MetricCalculator() {
        // File names
        this.impressionLog = "src/Logs/impression_log.csv";
        this.clickLog = "src/Logs/click_log.csv";
        this.serverLog = "src/Logs/server_log.csv";
    }

    // calculates metrics
    public void calculateMetrics(int pageLimit, int bounceTime, String start, String end) {
        // cate filtering
        try {
            Date endDate = parseDate(end);
            Date startDate = parseDate(start);

            // Reads the log files
            impressions = new Impressions(impressionLog);
            calculateImpressionsMetrics(startDate, endDate);

            clicks = new Clicks(clickLog);
            calculateClicksMetrics(startDate, endDate);

            servers = new Servers(serverLog);
            calculateServersMetrics(pageLimit, bounceTime, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    // calculates metrics from impressions
    public void calculateImpressionsMetrics(Date startDate, Date endDate /*filtering to be added*/) {
        ArrayList<Impression> impressionsList = impressions.getImpressions(); // list of impressions
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        // resets the impression metrics
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
    public void calculateClicksMetrics(Date startDate, Date endDate /*filtering to be added*/) {
        ArrayList<Click> clicksList = clicks.getClicks(); // list of clicks
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        while (inTime) {
            Click click = clicksList.get(count);

            // Checks if the click log fits within the given time scale
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

    public void calculateServersMetrics(int pageLimit, int bounceTime, Date startDate, Date endDate /*filtering to be added*/) {
        ArrayList<Server> serversList = servers.getServers(); // list of clicks
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        while (inTime) {
            Server server = serversList.get(count);

            // Checks if the server log fits within the given time scale
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

    // Calculates difference between two dates given as strings
    public long splitDates(int bounceTime, Date entryDate, Date exitDate) {
        if (exitDate == null) {
            return bounceTime - 1; // where the exit date is invalid, it's counted as a bounce
        } else {
            return exitDate.getTime() - (entryDate.getTime() / 1000);
        }
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

    // main
    public static void main(String[] args){
        MetricCalculator calculator = new MetricCalculator();

        // starts taking start date as string
        calculator.calculateMetrics(2, 200, "2015-01-01 12:01:21", "2015-01-01 13:51:59");
        calculator.print();
    }
}