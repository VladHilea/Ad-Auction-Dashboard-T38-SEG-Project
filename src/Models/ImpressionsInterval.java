package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ImpressionsInterval extends Interval {
    private ArrayList<Impression> impressions;

    public ImpressionsInterval(LocalDateTime date, ArrayList<Impression> impressions) {
        super(date);

        this.impressions = impressions;
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
    }
}
