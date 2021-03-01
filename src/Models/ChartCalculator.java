package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ChartCalculator extends Calculator {
    public ChartCalculator(Impressions impressions, Clicks clicks, Servers servers) {
        super(impressions, clicks, servers);
    }

    /**
    my next approach will be to try results in multiple groups depending on time granularity
    the first step will be to produce of start and end date pairs where each item follows on from the last
    eg. (Jan-March, April-June, July-Sep, etc.)
    **/

    // creates a list of dates separated by a constant interval
    public ArrayList<LocalDateTime> createDates(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference
        long hours = ChronoUnit.HOURS.between(startDate, endDate);

        // gets dates at every interval
        for (long i=1; i<=hours+1; i++) {
            dates.add(startDate.plusHours(i));
        }

        return dates;
    }

    // gets all the impressions data points sorted into the intervals produced
    public ArrayList<ImpressionsInterval> createImpressionsIntervals() {
        ArrayList<LocalDateTime> dates = createDates(getImpressions().getStartDate(), getImpressions().getEndDate());

        ArrayList<ImpressionsInterval> intervals = new ArrayList<>();
        ArrayList<Impression> interval = new ArrayList<>();

        LocalDateTime currentDate = dates.get(0);
        LocalDateTime nextDate = dates.get(1);
        int count = 1;

        for (Impression impression : getImpressions().getImpressions()) {
            if (!impression.date.isBefore(nextDate)) {
                intervals.add(new ImpressionsInterval(currentDate, interval));
                interval.clear();

                count++;
                currentDate = dates.get(count - 1);
                nextDate = dates.get(count);
            }
            interval.add(impression);
        }

        return intervals;
    }

    // gets all the clicks data points sorted into the intervals produced
    public ArrayList<ClicksInterval> createClicksIntervals() {
        ArrayList<LocalDateTime> dates = createDates(getClicks().getStartDate(), getClicks().getEndDate());

        ArrayList<ClicksInterval> intervals = new ArrayList<>();
        ArrayList<Click> interval = new ArrayList<>();

        LocalDateTime currentDate = dates.get(0);
        LocalDateTime nextDate = dates.get(1);
        int count = 1;

        for (Click click : getClicks().getClicks()) {
            if (!click.date.isBefore(nextDate)) {
                intervals.add(new ClicksInterval(currentDate, interval));
                interval.clear();

                count++;
                currentDate = dates.get(count - 1);
                nextDate = dates.get(count);
            }
            interval.add(click);
        }

        return intervals;
    }

    // gets all the servers data points sorted into the intervals produced
    public ArrayList<ServersInterval> createServersIntervals() {
        ArrayList<LocalDateTime> dates = createDates(getServers().getStartDate(), getServers().getEndDate());

        ArrayList<ServersInterval> intervals = new ArrayList<>();
        ArrayList<Server> interval = new ArrayList<>();

        LocalDateTime currentDate = dates.get(0);
        LocalDateTime nextDate = dates.get(1);
        int count = 1;

        for (Server server : getServers().getServers()) {
            if (!server.entryDate.isBefore(nextDate)) {
                intervals.add(new ServersInterval(currentDate, interval));
                interval.clear();

                count++;
                currentDate = dates.get(count - 1);
                nextDate = dates.get(count);
            }
            interval.add(server);
        }

        return intervals;
    }
}
