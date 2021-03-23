package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    private Map<Long, User> users = new HashMap<>(); // list of unique users

    public ChartCalculator() {
        super(null, null, null);
    }

    public ChartCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users) {
        super(impressionLog, clickLog, serverLog);
        this.users = users;
    }

    // recalculates intervals from whole time range and no filters
    public void calculateCharts(String granularity) {
        calculate(granularity, "Any", "Any", "Any", "Any", getImpressionLog().get(0).getDate(), getImpressionLog().get(getImpressionLog().size() - 1).getDate());
    }

    // recalculates intervals from given time range
    public void calculateCharts(String granularity, LocalDateTime startDate, LocalDateTime endDate) {
        calculate(granularity, "Any", "Any", "Any", "Any", startDate, endDate);
    }

    // recalculates intervals from given time range and filters
    public void calculateCharts(String granularity, String gender, String age, String context, String income) {
        calculate(granularity, gender, age, context, income, getImpressionLog().get(0).getDate(), getImpressionLog().get(getImpressionLog().size() - 1).getDate());
    }

    // recalculates intervals from given time range and filters
    public void calculateCharts(String granularity, String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate) {
        calculate(granularity, gender, age, context, income, startDate, endDate);
    }


    // produces a list of metric calculators that stores all the logs split into a set interval
    public void calculate(String granularity, String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate) {
        // resets the array lists
        this.impressionsNoList = new ArrayList<>();
        this.uniquesNoList = new ArrayList<>();
        this.clicksNoList = new ArrayList<>();
        this.bouncesNoList = new ArrayList<>();
        this.conversionsNoList = new ArrayList<>();
        this.totalImpressionCostList = new ArrayList<>();
        this.totalClickCostList = new ArrayList<>();

        this.ctrList = new ArrayList<>();
        this.cpaList = new ArrayList<>();
        this.cpcList = new ArrayList<>();
        this.cpmList = new ArrayList<>();
        this.brList = new ArrayList<>();

        // creates a list of dates separated by a constant interval
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference and gets dates at every interval
        switch (granularity) {
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

                // adds to the next log
                if (filterEntry(impression, gender, age, income) && filterContext(impression, context)) {
                    currentImpressionLog.add(impression);
                }

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
            // adds the log entry to the current interval log
            if (filterEntry(impression, gender, age, income) && filterContext(impression, context)) {
                currentImpressionLog.add(impression);
            }

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

                // adds to the next log
                if (filterEntry(click, gender, age, income)) {
                    currentClickLog.add(click);
                }

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
            // adds the log entry to the current interval log
            if (filterEntry(click, gender, age, income)) {
                currentClickLog.add(click);
            }

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

                // adds to the next log
                if (filterEntry(server, gender, age, income)) {
                    currentServerLog.add(server);
                }

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
            // adds the log entry to the current interval log
            if (filterEntry(server, gender, age, income)) {
                currentServerLog.add(server);
            }

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
            calculator.calculateMetrics();

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

    // allows the entry to be counted if it matches the filters
    public boolean filterEntry(Entry entry, String gender, String age, String income) {
        User user = users.get(entry.getUserId());

        if (user.getGender().equals(gender) || gender.equals("Any")) {
            if (user.getAge().equals(age) || age.equals("Any")) {
                return user.getIncome().equals(income) || income.equals("Any");
            }
        }
        return false;
    }

    // filters the context for impressions only
    public boolean filterContext(ImpressionEntry impressionEntry, String context) {
        return impressionEntry.getContext().equals(context) || context.equals("Any");
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
