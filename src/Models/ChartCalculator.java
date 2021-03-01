package Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChartCalculator {
    private int[] impressionsNo; // number of impressions - people who saw the ad
    private int[] uniquesNo; // number of unique impressions - unique people who saw the ad
    private int[] clicksNo; // number of clicks - people who clicked the ad
    private int[] bounceNo; // number of bounces - people who clicked away after a while
    private int[] conversionsNo; // number of conversions - people who click then acts on ad
    private double[] totalImpressionCost; // total impression cost - cost of impressions
    private double[] totalClickCost; // total click cost - cost of clicks

    private double[] ctr; // click-through-rate - clicks per impression
    private double[] cpa; // cost-per-acquisition
    private double[] cpc; // cost-per-click
    private double[] cpm; // cost-per-thousand impressions
    private double[] br; // bounce rate - number of bounces per click

    private Impressions impressions;
    private Clicks clicks;
    private Servers servers;

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

    public void createDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date startDate = new Date();
        System.out.println(sdf.format(startDate));
    }
}
