package Models;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartCalculator {
    private final Impressions impressions;
    private final Clicks clicks;
    private final Servers servers;

    /**
    my next approach will be to try results in multiple groups depending on time granularity
    the first step will be to produce of start and end date pairs where each item follows on from the last
    eg. (Jan-March, April-June, July-Sep, etc.)
    **/

    public ChartCalculator(Impressions impressions, Clicks clicks, Servers servers) {
        this.impressions = impressions;
        this.clicks = clicks;
        this.servers = servers;
    }

    public ArrayList<LocalDateTime> createClicksDates() {
        return createDates(clicks.getStartDate(), clicks.getEndDate());
    }

    public ArrayList<LocalDateTime> createServersDates() {
        return createDates(servers.getStartDate(), clicks.getEndDate());
    }

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
    public Map<LocalDateTime, ArrayList<Impression>> createImpressionsIntervals() {
        ArrayList<LocalDateTime> dates = createDates(impressions.getStartDate(), impressions.getEndDate());

        Map<LocalDateTime, ArrayList<Impression>> intervals = new HashMap<>();
        ArrayList<Impression> interval = new ArrayList<>();

        LocalDateTime currentDate = dates.get(0);
        LocalDateTime nextDate = dates.get(1);
        int count = 1;

        for (Impression impression : impressions.getImpressions()) {
            if (!impression.date.isBefore(nextDate)) {
                intervals.put(currentDate, interval);
                interval.clear();

                count++;
                currentDate = dates.get(count - 1);
                nextDate = dates.get(count);
            }
            interval.add(impression);
        }

        return intervals;
    }
}
