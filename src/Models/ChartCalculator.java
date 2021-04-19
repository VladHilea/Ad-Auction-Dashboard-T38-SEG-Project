package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ChartCalculator extends Calculator {
<<<<<<< Updated upstream
    private final ArrayList<Integer> impressionsNoList = new ArrayList<>(); // number of impressions - people who saw the ad
    private final ArrayList<Integer> uniquesNoList = new ArrayList<>(); // number of unique impressions - unique people who saw the ad
    private final ArrayList<Integer> clicksNoList = new ArrayList<>(); // number of clicks - people who clicked the ad
    private final ArrayList<Integer> bouncesNoList = new ArrayList<>(); // number of bounces - people who clicked away after a while
    private final ArrayList<Integer> conversionsNoList = new ArrayList<>(); // number of conversions - people who click then acts on ad
    private final ArrayList<Float> totalImpressionCostList = new ArrayList<>(); // total impression cost - cost of impressions
    private final ArrayList<Float> totalClickCostList = new ArrayList<>(); // total click cost - cost of clicks

    private final ArrayList<Float> ctrList = new ArrayList<>(); // click-through-rate - clicks per impression
    private final ArrayList<Float> cpaList = new ArrayList<>(); // cost-per-acquisition
    private final ArrayList<Float> cpcList = new ArrayList<>(); // cost-per-click
    private final ArrayList<Float> cpmList = new ArrayList<>(); // cost-per-thousand impressions
    private final ArrayList<Float> brList = new ArrayList<>(); // bounce rate - number of bounces per click

    public ChartCalculator(ImpressionLog impressionLog, ClickLog clickLog, ServerLog serverLog) {
        super(impressionLog, clickLog, serverLog);
    }

    // produces a list of metric calculators that stores all the logs split into a set interval
    public void calculateCharts(String interval, LocalDateTime startDate, LocalDateTime endDate /*in future filtering for time granularity to be added*/) {
=======
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

    public ChartCalculator() {
        super(null, null, null, null);
    }

    public ChartCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users) {
        super(impressionLog, clickLog, serverLog, users);
    }

    // recalculates intervals from whole time range and no filters
    public void calculateCharts(String granularity) {
        calculate(granularity, "Any", "Any", "Any", "Any", getImpressionLog().get(0).getDate(), getImpressionLog().get(getImpressionLog().size() - 1).getDate());
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

>>>>>>> Stashed changes
        // creates a list of dates separated by a constant interval
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference and gets dates at every interval
<<<<<<< Updated upstream
        switch (interval) {
            case "hours": {
                for (long i = 1; i <= ChronoUnit.HOURS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusHours(i));
                }
                System.out.println(interval);
            }
            case "days": {
                for (long i = 1; i <= ChronoUnit.DAYS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusDays(i));
                }
            }
            case "weeks": {
                for (long i = 1; i <= ChronoUnit.WEEKS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusWeeks(i));
                }
            }
            case "months": {
                for (long i = 1; i <= ChronoUnit.MONTHS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusMonths(i));
                }
            }
            case "years": {
                for (long i = 1; i <= ChronoUnit.YEARS.between(startDate, endDate) + 1; i++) {
                    dates.add(startDate.plusYears(i));
                }
            }
        }

=======
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

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        Iterator<Impression> impressionIterator = getImpressionLog().getImpressionsList().iterator();
=======
        Iterator<ImpressionEntry> impressionIterator = impressionList.iterator();

>>>>>>> Stashed changes
        while (impressionIterator.hasNext()) {
            Impression impression = impressionIterator.next();
            if (!impression.date.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentImpressionLog.setDates();
                intervalImpressionLogs.add(currentImpressionLog);

                // resets the current interval log
<<<<<<< Updated upstream
                currentImpressionLog = new ImpressionLog(new ArrayList<>());
                currentImpressionLog.getImpressionsList().add(impression);
=======
                currentImpressionLog = new ArrayList<>();

                // adds to the next log
                if (filterEntry(impression, gender, age, income) && filterContext(impression, context)) {
                    currentImpressionLog.add(impression);
                }
>>>>>>> Stashed changes

                count++;
                lastDate = dates.get(count);
            }
<<<<<<< Updated upstream
            currentImpressionLog.getImpressionsList().add(impression); // adds the log entry to the current interval log
=======
            // adds the log entry to the current interval log
            if (filterEntry(impression, gender, age, income) && filterContext(impression, context)) {
                currentImpressionLog.add(impression);
            }
>>>>>>> Stashed changes

            // completes the last interval log
            if (!impressionIterator.hasNext()) {
                currentImpressionLog.setDates();
                intervalImpressionLogs.add(currentImpressionLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of click logs for each interval
<<<<<<< Updated upstream
        Iterator<Click> clickIterator = getClickLog().getClicksList().iterator();
        while (clickIterator.hasNext()) {
            Click click = clickIterator.next();
            if (!click.date.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentClickLog.setDates();
                intervalClickLogs.add(currentClickLog);

                // resets the current interval log
                currentClickLog = new ClickLog(new ArrayList<>());
                currentClickLog.getClicksList().add(click);
=======
        Iterator<ClickEntry> clickIterator = clickList.iterator();

        while (clickIterator.hasNext()) {
            ClickEntry click = clickIterator.next();

            if (click.getDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalClickLogs.add(currentClickLog);

                // resets the current interval log
                currentClickLog = new ArrayList<>();
>>>>>>> Stashed changes

                // adds to the next log
                if (filterEntry(click, gender, age, income)) {
                    currentClickLog.add(click);
                }

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
<<<<<<< Updated upstream
            currentClickLog.getClicksList().add(click); // adds the log entry to the current interval log
=======
            // adds the log entry to the current interval log
            if (filterEntry(click, gender, age, income)) {
                currentClickLog.add(click);
            }
>>>>>>> Stashed changes

            // completes the last interval log
            if (!clickIterator.hasNext()) {
                currentClickLog.setDates();
                intervalClickLogs.add(currentClickLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // Creates a list of server logs for each interval
<<<<<<< Updated upstream
        Iterator<Server> serverIterator = getServerLog().getServerList().iterator();
        while (serverIterator.hasNext()) {
            Server server = serverIterator.next();
            if (!server.entryDate.isBefore(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                currentServerLog.setDates();
                intervalServerLogs.add(currentServerLog);

                // resets the current interval log
                currentServerLog = new ServerLog(new ArrayList<>());
                currentServerLog.getServerList().add(server);
=======
        Iterator<ServerEntry> serverIterator = serverList.iterator();

        while (serverIterator.hasNext()) {
            ServerEntry server = serverIterator.next();

            if (server.getEntryDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalServerLogs.add(currentServerLog);

                // resets the current interval log
                currentServerLog = new ArrayList<>();
>>>>>>> Stashed changes

                // adds to the next log
                if (filterEntry(server, gender, age, income)) {
                    currentServerLog.add(server);
                }

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
<<<<<<< Updated upstream
            currentServerLog.getServerList().add(server); // adds the log entry to the current interval log
=======
            // adds the log entry to the current interval log
            if (filterEntry(server, gender, age, income)) {
                currentServerLog.add(server);
            }
>>>>>>> Stashed changes

            // completes the last interval log
            if (!serverIterator.hasNext()) {
                currentServerLog.setDates();
                intervalServerLogs.add(currentServerLog);
            }
        }

        // creates all the calculators for the intervals (probably doesn't need the if from now on)
        if (intervalImpressionLogs.size() == intervalClickLogs.size() && intervalImpressionLogs.size() == intervalServerLogs.size()) {
<<<<<<< Updated upstream
            for (int i=0; i < intervalImpressionLogs.size(); i++) {
                intervalCalculators.add(new MetricCalculator(intervalImpressionLogs.get(i), intervalClickLogs.get(i), intervalServerLogs.get(i)));
=======
            for (int i = 0; i < intervalImpressionLogs.size(); i++) {
                intervalCalculators.add(new MetricCalculator(intervalImpressionLogs.get(i), intervalClickLogs.get(i), intervalServerLogs.get(i), getUsers()));
>>>>>>> Stashed changes
            }
        } else {
            System.out.println("Error!");
        }

        // calculating metrics for every interval
        for (MetricCalculator calculator : intervalCalculators) {
            calculator.calculateMetrics();
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
    // allows the entry to be counted if it matches the filters
    public boolean filterEntry(Entry entry, String gender, String age, String income) {
        User user = getUsers().get(entry.getUserId());

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

>>>>>>> Stashed changes
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
