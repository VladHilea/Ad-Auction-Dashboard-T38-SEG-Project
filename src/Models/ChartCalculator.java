package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;

public class ChartCalculator extends Calculator {
    private ArrayList<Integer> impressionsNoList = new ArrayList<>(); // number of impressions - people who saw the ad
    private ArrayList<Integer> uniquesNoList = new ArrayList<>(); // number of unique impressions - unique people who saw the ad
    private ArrayList<Integer> clicksNoList = new ArrayList<>(); // number of clicks - people who clicked the ad
    private ArrayList<Integer> bouncesNoList = new ArrayList<>(); // number of bounces - people who clicked away after a while
    private ArrayList<Integer> conversionsNoList = new ArrayList<>(); // number of conversions - people who click then acts on ad
    private ArrayList<Float> totalImpressionCostList = new ArrayList<>(); // total impression cost - cost of impressions
    private ArrayList<Float> totalClickCostList = new ArrayList<>(); // total click cost - cost of clicks

    private ArrayList<Float> ctrList = new ArrayList<>(); // click-through-rate - clicks per impression
    private ArrayList<Float> cpaList = new ArrayList<>(); // cost-per-acquisition
    private ArrayList<Float> cpcList = new ArrayList<>(); // cost-per-click
    private ArrayList<Float> cpmList = new ArrayList<>(); // cost-per-thousand impressions
    private ArrayList<Float> brList = new ArrayList<>(); // bounce rate - number of bounces per click

    public ChartCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, String intervalLength) {
        super(impressionLog, clickLog, serverLog);

        resetChart();
        calculateCharts(intervalLength);
    }

    // recalculates intervals, no time range
    public void calculateCharts(String interval) {
        resetChart();
        calculate(interval, getImpressionLog().get(0).getDate(), getImpressionLog().get(getImpressionLog().size() - 1).getDate());
    }

    // recalculates intervals, with time range
    public void calculateCharts(String interval, LocalDateTime startDate, LocalDateTime endDate) {
        resetChart();
        calculate(interval, startDate, endDate);
    }

    // resets the array lists
    public void resetChart() {
        impressionsNoList = new ArrayList<>();
        uniquesNoList = new ArrayList<>();
        clicksNoList = new ArrayList<>();
        bouncesNoList = new ArrayList<>();
        conversionsNoList = new ArrayList<>();
        totalImpressionCostList = new ArrayList<>();
        totalClickCostList = new ArrayList<>();

        ctrList = new ArrayList<>();
        cpaList = new ArrayList<>();
        cpcList = new ArrayList<>();
        cpmList = new ArrayList<>();
        brList = new ArrayList<>();
    }

    // produces a list of metric calculators that stores all the logs split into a set interval
    public void calculate(String interval, LocalDateTime startDate, LocalDateTime endDate /*in future filtering for time granularity to be added*/) {
        // creates a list of dates separated by a constant interval
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference and gets dates at every interval
        switch (interval) {
            case "Hours": {
                for (long i = 1; i <= ChronoUnit.HOURS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusHours(i));
                }
                break;
            }
            case "Days": {
                for (long i = 1; i <= ChronoUnit.DAYS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusDays(i));
                }
                break;
            }
            case "Weeks": {
                for (long i = 1; i <= ChronoUnit.WEEKS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusWeeks(i));
                }
                break;
            }
            case "Months": {
                for (long i = 1; i <= ChronoUnit.MONTHS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusMonths(i));
                }
                break;
            }
            case "Years": {
                for (long i = 1; i <= ChronoUnit.YEARS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusYears(i));
                }
                break;
            }
        }

        ArrayList<ImpressionEntry> impressionList = getImpressionLog(startDate, endDate); // list of impressions
        ArrayList<ClickEntry> clickList = getClickLog(startDate, endDate); // list of clicks
        ArrayList<ServerEntry> serverList = getServerLog(startDate, endDate); // list of server entries

        ArrayList<MetricCalculator> intervalCalculators = new ArrayList<>(); // list of calculators for log entries in each interval
        ArrayList<ArrayList<ImpressionEntry>> intervalImpressionLogs = new ArrayList<>(); // list of impression logs in each interval
        ArrayList<ArrayList<ClickEntry>> intervalClickLogs = new ArrayList<>(); // list of click logs in each interval
        ArrayList<ArrayList<ServerEntry>> intervalServerLogs = new ArrayList<>(); // list of server logs in each interval

        // current interval logs
        ArrayList<ImpressionEntry> currentImpressionLog = new ArrayList<>();
        ArrayList<ClickEntry> currentClickLog = new ArrayList<>();
        ArrayList<ServerEntry> currentServerLog = new ArrayList<>();

        LocalDateTime lastDate = dates.get(1); // last date of interval
        int count = 1;

        // Creates a list of impression logs for each interval
        Iterator<ImpressionEntry> impressionIterator = impressionList.iterator();

        while (impressionIterator.hasNext()) {
            ImpressionEntry impression = impressionIterator.next();

            if (impression.getDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalImpressionLogs.add(currentImpressionLog);

                // resets the current interval log
                currentImpressionLog = new ArrayList<>();
                currentImpressionLog.add(impression);

                count++;
                lastDate = dates.get(count);
            }
            currentImpressionLog.add(impression); // adds the log entry to the current interval log

            // completes the last interval log
            if (!impressionIterator.hasNext()) {
                intervalImpressionLogs.add(currentImpressionLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of click logs for each interval
        Iterator<ClickEntry> clickIterator = clickList.iterator();

        while (clickIterator.hasNext()) {
            ClickEntry click = clickIterator.next();

            if (click.getDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalClickLogs.add(currentClickLog);

                // resets the current interval log
                currentClickLog = new ArrayList<>();
                currentClickLog.add(click);

                count++;
                lastDate = dates.get(count);
            }
            currentClickLog.add(click); // adds the log entry to the current interval log

            // completes the last interval log
            if (!clickIterator.hasNext()) {
                intervalClickLogs.add(currentClickLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of server logs for each interval
        Iterator<ServerEntry> serverIterator = serverList.iterator();

        while (serverIterator.hasNext()) {
            ServerEntry server = serverIterator.next();

            if (server.getEntryDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalServerLogs.add(currentServerLog);

                // resets the current interval log
                currentServerLog = new ArrayList<>();
                currentServerLog.add(server);

                count++;
                lastDate = dates.get(count);
            }
            currentServerLog.add(server); // adds the log entry to the current interval log

            // completes the last interval log
            if (!serverIterator.hasNext()) {
                intervalServerLogs.add(currentServerLog);
            }
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
            calculator.calculateMetrics(startDate, endDate);

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

    public ArrayList<Float> getTotalImpressionCostList() {
        return totalImpressionCostList;
    }

    public ArrayList<Float> getTotalClickCostList() {
        return totalClickCostList;
    }

    public ArrayList<Float> getCtrList() {
        return ctrList;
    }

    public ArrayList<Float> getCpaList() {
        return cpaList;
    }

    public ArrayList<Float> getCpcList() {
        return cpcList;
    }

    public ArrayList<Float> getCpmList() {
        return cpmList;
    }

    public ArrayList<Float> getBrList() {
        return brList;
    }
}
