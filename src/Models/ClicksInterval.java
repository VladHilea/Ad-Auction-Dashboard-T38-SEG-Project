package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClicksInterval extends Interval {
    private ArrayList<Click> clicks;

    public ClicksInterval(LocalDateTime date, ArrayList<Click> clicks) {
        super(date);

        this.clicks = clicks;
    }

    public ArrayList<Click> getClicks() {
        return clicks;
    }
}
