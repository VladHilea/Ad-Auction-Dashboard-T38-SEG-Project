package Models;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ChartCalculator {
    private ArrayList<Integer> impressionsNos; // number of impressions - people who saw the ad
    private ArrayList<Integer> uniquesNos; // number of unique impressions - unique people who saw the ad
    private ArrayList<Integer> clicksNos; // number of clicks - people who clicked the ad
    private ArrayList<Integer> bounceNos; // number of bounces - people who clicked away after a while
    private ArrayList<Integer> conversionsNos; // number of conversions - people who click then acts on ad
    private ArrayList<Double> totalImpressionCosts; // total impression cost - cost of impressions
    private ArrayList<Double> totalClickCosts; // total click cost - cost of clicks

    private ArrayList<Double> ctrs; // click-through-rate - clicks per impression
    private ArrayList<Double> cpas; // cost-per-acquisition
    private ArrayList<Double> cpcs; // cost-per-click
    private ArrayList<Double> cpms; // cost-per-thousand impressions
    private ArrayList<Double> brs; // bounce rate - number of bounces per click

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

    // creates a list of dates separated by a constant interval
    public void createDates() {
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        dates.add(LocalDateTime.now());

        // calculates time difference
        long weeks = ChronoUnit.WEEKS.between(LocalDateTime.now(), LocalDateTime.now().plusYears(1));

        // gets dates at every interval
        for (long i=1; i<weeks; i++) {
            dates.add(LocalDateTime.now().plusWeeks(i));
        }

        System.out.println(dates);
        System.out.println(dates.size());
    }
}
