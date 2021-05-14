package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final ArrayList<Double> histogramList = new ArrayList<>(); // list of every click cost - used for histograms

    private ArrayList<Float> ctrList = new ArrayList<>(); // click-through-rate - clicks per impression
    private ArrayList<Float> cpaList = new ArrayList<>(); // cost-per-acquisition
    private ArrayList<Float> cpcList = new ArrayList<>(); // cost-per-click
    private ArrayList<Float> cpmList = new ArrayList<>(); // cost-per-thousand impressions
    private ArrayList<Float> brList = new ArrayList<>(); // bounce rate - number of bounces per click

    private ArrayList<MetricCalculator> hourCalculators = new ArrayList<>();
    private ArrayList<MetricCalculator> dayCalculators = new ArrayList<>();
    private ArrayList<MetricCalculator> weekCalculators = new ArrayList<>();
    private ArrayList<MetricCalculator> monthCalculators = new ArrayList<>();
    private ArrayList<MetricCalculator> yearCalculators = new ArrayList<>();

    public ChartCalculator(int pageLimit, int bounceTime) {
        super(null, null, null, null, pageLimit, bounceTime);
    }

    public ChartCalculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users, int pageLimit, int bounceTime) {
        super(impressionLog, clickLog, serverLog, users, pageLimit, bounceTime);

        for (ClickEntry entry : clickLog) {
            if (entry.getClickCost() > 0) {
                histogramList.add(entry.getClickCost());
            }
        }
    }

    // splits the data into its intervals for all time granularities
    public void calculateIntervals(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            startDate = getImpressionLog().get(0).getDate();
        }
        if (endDate == null) {
            endDate = getImpressionLog().get(getImpressionLog().size() - 1).getDate();
        }
        resetChart();

        // creates a list of dates separated by a constant interval
        ArrayList<LocalDateTime> hourDates = new ArrayList<>();
        ArrayList<LocalDateTime> dayDates = new ArrayList<>();
        ArrayList<LocalDateTime> weekDates = new ArrayList<>();
        ArrayList<LocalDateTime> monthDates = new ArrayList<>();
        ArrayList<LocalDateTime> yearDates = new ArrayList<>();

        // calculates time difference and gets dates at every interval
        for (long i = 0; i <= ChronoUnit.HOURS.between(startDate, endDate) + 1; i++) {
            hourDates.add(startDate.plusHours(i));
        }
        for (long i = 0; i <= ChronoUnit.DAYS.between(startDate, endDate) + 1; i++) {
            dayDates.add(startDate.plusDays(i));
        }
        for (long i = 0; i <= ChronoUnit.WEEKS.between(startDate, endDate) + 1; i++) {
            weekDates.add(startDate.plusWeeks(i));
        }
        for (long i = 0; i <= ChronoUnit.MONTHS.between(startDate, endDate) + 1; i++) {
            monthDates.add(startDate.plusMonths(i));
        }
        for (long i = 0; i <= ChronoUnit.YEARS.between(startDate, endDate) + 1; i++) {
            yearDates.add(startDate.plusYears(i));
        }

        // stores the lists of calculators to reduce unnecessary computation later on
        this.hourCalculators = createIntervals(hourDates, startDate, endDate);
        this.dayCalculators = createIntervals(dayDates, startDate, endDate);
        this.weekCalculators = createIntervals(weekDates, startDate, endDate);
        this.monthCalculators = createIntervals(monthDates, startDate, endDate);
        this.yearCalculators = createIntervals(yearDates, startDate, endDate);
    }

    // splits the data into intervals that represent the points on the graph - each interval is turned into a new calculator
    public ArrayList<MetricCalculator> createIntervals(ArrayList<LocalDateTime> dates, LocalDateTime startDate, LocalDateTime endDate) {
        // gets the list of all logs in the time range
        ArrayList<ImpressionEntry> impressionList = getImpressionLog(startDate, endDate); // list of impressions
        ArrayList<ClickEntry> clickList = getClickLog(startDate, endDate); // list of clicks
        ArrayList<ServerEntry> serverList = getServerLog(startDate, endDate); // list of server entries

        // used to store the intervals of data
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

        // creates a list of impression logs for each interval
        Iterator<ImpressionEntry> impressionIterator = impressionList.iterator();

        while (impressionIterator.hasNext()) {
            ImpressionEntry impression = impressionIterator.next();

            if (impression.getDate().isAfter(lastDate)) { // if its the first log in a new interval
                // completes the current interval log
                intervalImpressionLogs.add(currentImpressionLog);

                // resets the current interval log
                currentImpressionLog = new ArrayList<>();

                // adds to the next log
                currentImpressionLog.add(impression);

                // changes the interval
                count++;
                lastDate = dates.get(count);
            }
            // adds the log entry to the current interval log
            currentImpressionLog.add(impression);

            // completes the last interval log
            if (!impressionIterator.hasNext()) {
                intervalImpressionLogs.add(currentImpressionLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // creates a list of click logs for each interval
        Iterator<ClickEntry> clickIterator = clickList.iterator();

        while (clickIterator.hasNext()) {
            ClickEntry click = clickIterator.next();

            if (click.getDate().isAfter(lastDate)) {
                intervalClickLogs.add(currentClickLog);
                currentClickLog = new ArrayList<>();
                currentClickLog.add(click);

                count++;
                lastDate = dates.get(count);
            }
            currentClickLog.add(click);

            // completes the last interval log
            if (!clickIterator.hasNext()) {
                intervalClickLogs.add(currentClickLog);
            }
        }

        // resets the date
        lastDate = dates.get(1); // last date of interval
        count = 1;

        // creates a list of server logs for each interval
        Iterator<ServerEntry> serverIterator = serverList.iterator();

        while (serverIterator.hasNext()) {
            ServerEntry server = serverIterator.next();

            if (server.getEntryDate().isAfter(lastDate)) {
                intervalServerLogs.add(currentServerLog);
                currentServerLog = new ArrayList<>();
                currentServerLog.add(server);

                count++;
                lastDate = dates.get(count);
            }
            currentServerLog.add(server);

            // completes the last interval log
            if (!serverIterator.hasNext()) {
                intervalServerLogs.add(currentServerLog);
            }
        }

        // combines the intervals of logs to create calculators for each of the intervals
        if (intervalImpressionLogs.size() == intervalClickLogs.size() && intervalImpressionLogs.size() == intervalServerLogs.size()) {
            for (int i=0; i < intervalImpressionLogs.size(); i++) {
                intervalCalculators.add(new MetricCalculator(intervalImpressionLogs.get(i), intervalClickLogs.get(i), intervalServerLogs.get(i), getUsers(), getPageLimit(), getBounceTime()));
            }
            return intervalCalculators;
        } else {
            System.out.println("Error!");
            return new ArrayList<>();
        }
    }

    // filters the appropriate list of calculators
    public void calculateFilters(String granularity, String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null) {
            startDate = getImpressionLog().get(0).getDate();
        }
        if (endDate == null) {
            endDate = getImpressionLog().get(getImpressionLog().size() - 1).getDate();
        }
        resetChart();

        // calculating metrics for every interval
        switch (granularity) {
            case "hours":
                calculateMetrics(gender, age, context, income, startDate, endDate, hourCalculators);
                break;
            case "days":
                calculateMetrics(gender, age, context, income, startDate, endDate, dayCalculators);
                break;
            case "weeks":
                calculateMetrics(gender, age, context, income, startDate, endDate, weekCalculators);
                break;
            case "months":
                calculateMetrics(gender, age, context, income, startDate, endDate, monthCalculators);
                break;
            case "years":
                calculateMetrics(gender, age, context, income, startDate, endDate, yearCalculators);
                break;
        }
    }

    private void calculateMetrics(String gender, String age, String context, String income, LocalDateTime startDate, LocalDateTime endDate, ArrayList<MetricCalculator> calculators) {
        for (MetricCalculator calculator : calculators) {
            calculator.calculateMetrics(gender, age, context, income, startDate, endDate);

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

    // resets the array lists
    private void resetChart() {
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

    public ArrayList<Double> getHistogramList() {
        return histogramList;
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
