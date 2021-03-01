package Models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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

    public void createImpressionsDates() {
         createDates(impressions.getStartDate(), impressions.getEndDate());
    }

    public void createClicksDates() {
        createDates(clicks.getStartDate(), clicks.getEndDate());
    }

    public void createServersDates() {
        createDates(servers.getStartDate(), clicks.getEndDate());
    }

    // creates a list of dates separated by a constant interval
    public void createDates(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(startDate);

        // calculates time difference
        long hours = ChronoUnit.HOURS.between(startDate, endDate);

        // gets dates at every interval
        for (long i=1; i<=hours+1; i++) {
            dates.add(startDate.plusHours(i));
        }

        System.out.println(dates); // list of intervals
        System.out.println(dates.size()); // number of intervals
    }
}
