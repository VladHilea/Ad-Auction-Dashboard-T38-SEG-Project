package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ChartCalculator extends Calculator {
    private final ArrayList<Integer> impressionsNoList = new ArrayList<>(); // number of impressions - people who saw the ad
    private final ArrayList<Integer> uniquesNoList = new ArrayList<>(); // number of unique impressions - unique people who saw the ad
    private final ArrayList<Integer> clicksNoList = new ArrayList<>(); // number of clicks - people who clicked the ad
    private final ArrayList<Integer> bouncesNoList = new ArrayList<>(); // number of bounces - people who clicked away after a while
    private final ArrayList<Integer> conversionsNoList = new ArrayList<>(); // number of conversions - people who click then acts on ad
    private final ArrayList<Double> totalImpressionCostList = new ArrayList<>(); // total impression cost - cost of impressions
    private final ArrayList<Double> totalClickCostList = new ArrayList<>(); // total click cost - cost of clicks

    private final ArrayList<Double> ctrList = new ArrayList<>(); // click-through-rate - clicks per impression
    private final ArrayList<Double> cpaList = new ArrayList<>(); // cost-per-acquisition
    private final ArrayList<Double> cpcList = new ArrayList<>(); // cost-per-click
    private final ArrayList<Double> cpmList = new ArrayList<>(); // cost-per-thousand impressions
    private final ArrayList<Double> brList = new ArrayList<>(); // bounce rate - number of bounces per click

    public ChartCalculator(ImpressionLog impressionLog, ClickLog clickLog, ServerLog serverLog) {
        super(impressionLog, clickLog, serverLog);

        // hardcoded start and end dates - some server and click log entries are cut off atm
        calculateCharts(getImpressionLog().getFirstDate(), getImpressionLog().getLastDate());
    }

    // produces a list of metric calculators that stores all the logs split into a set interval
    public void calculateCharts(LocalDateTime startDate, LocalDateTime endDate /*in future filtering for time granularity to be added*/) {
        // creates a list of dates separated by a constant interval
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference (temporarily in days)
        long days = ChronoUnit.DAYS.between(startDate, endDate);

        // gets dates at every interval
        for (long i=1; i<=days+1; i++) {
            dates.add(startDate.plusDays(i));
        }

        ArrayList<MetricCalculator> intervalCalculators = new ArrayList<>(); // list of calculators for log entries in each interval
        ArrayList<ImpressionLog> intervalImpressionLogs = new ArrayList<>(); // list of impression logs in each interval
        ArrayList<ClickLog> intervalClickLogs = new ArrayList<>(); // list of click logs in each interval
        ArrayList<ServerLog> intervalServerLogs = new ArrayList<>(); // list of server logs in each interval

        // current interval logs
        ImpressionLog currentImpressionLog = new ImpressionLog(new ArrayList<>());
        ClickLog currentClickLog = new ClickLog(new ArrayList<>());
        ServerLog currentServerLog = new ServerLog(new ArrayList<>());

        LocalDateTime lastDate = dates.get(1); // last date of interval
        int count = 1;

        // Creates a list of impression logs for each interval
        for (Impression impression : getImpressionLog().getImpressionsList()) {
            if (!impression.date.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentImpressionLog.setDates();
                intervalImpressionLogs.add(currentImpressionLog);

                // resets the current interval log
                currentImpressionLog = new ImpressionLog(new ArrayList<>());
                currentImpressionLog.getImpressionsList().add(impression);

                count++;
                lastDate = dates.get(count);
            }
            currentImpressionLog.getImpressionsList().add(impression); // adds the log entry to the current interval log
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of click logs for each interval
        for (Click click : getClickLog().getClicksList()) {
            if (!click.date.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentClickLog.setDates();
                intervalClickLogs.add(currentClickLog);

                // resets the current interval log
                currentClickLog = new ClickLog(new ArrayList<>());
                currentClickLog.getClicksList().add(click);

                count++;
                lastDate = dates.get(count);
            }
            currentClickLog.getClicksList().add(click); // adds the log entry to the current interval log
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of server logs for each interval
        for (Server server : getServerLog().getServerList()) {
            if (!server.entryDate.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentServerLog.setDates();
                intervalServerLogs.add(currentServerLog);

                // resets the current interval log
                currentServerLog = new ServerLog(new ArrayList<>());
                currentServerLog.getServerList().add(server);

                count++;
                lastDate = dates.get(count);
            }
            currentServerLog.getServerList().add(server); // adds the log entry to the current interval log
        }

        // creates all the calculators for the intervals (probably doesn't need the if from now on)
        if (intervalImpressionLogs.size() == intervalClickLogs.size() && intervalImpressionLogs.size() == intervalServerLogs.size()) {
            for (int i=0; i < intervalImpressionLogs.size(); i++) {
                intervalCalculators.add(new MetricCalculator(intervalImpressionLogs.get(i), intervalClickLogs.get(i), intervalServerLogs.get(i)));
            }
        } else {
            System.out.println("Error!");
        }

        // calculating metrics for every interval
        for (MetricCalculator calculator : intervalCalculators) {
            this.impressionsNoList.add(calculator.getImpressionsNo());
            this.uniquesNoList.add(calculator.getUniquesNo());
            this.totalImpressionCostList.add(calculator.getTotalImpressionCost());
            this.clicksNoList.add(calculator.getClicksNo());
            this.totalClickCostList.add(calculator.getTotalClickCost());
            this.bouncesNoList.add(calculator.getBouncesNo());
            this.conversionsNoList.add(calculator.getConversionsNo());
            this.ctrList.add(calculator.getCtr());
            this.cpaList.add(calculator.getCpa());
            this.cpcList.add(calculator.getCpc());
            this.cpmList.add(calculator.getCpm());
            this.brList.add(calculator.getBr());
        }
    }

    public ArrayList<Integer> getImpressionsNoList() {
        return impressionsNoList;
    }

    public ArrayList<Integer> getUniquesNoList() {
        return uniquesNoList;
    }

    public ArrayList<Integer> getClicksNoList() {
        return clicksNoList;
    }

    public ArrayList<Integer> getBouncesNoList() {
        return bouncesNoList;
    }

    public ArrayList<Integer> getConversionsNoList() {
        return conversionsNoList;
    }

    public ArrayList<Double> getTotalImpressionCostList() {
        return totalImpressionCostList;
    }

    public ArrayList<Double> getTotalClickCostList() {
        return totalClickCostList;
    }

    public ArrayList<Double> getCtrList() {
        return ctrList;
    }

    public ArrayList<Double> getCpaList() {
        return cpaList;
    }

    public ArrayList<Double> getCpcList() {
        return cpcList;
    }

    public ArrayList<Double> getCpmList() {
        return cpmList;
    }

    public ArrayList<Double> getBrList() {
        return brList;
    }
}
