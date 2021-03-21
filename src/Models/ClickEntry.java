package Models;

import java.time.LocalDateTime;

public class ClickEntry extends Entry {
    private final LocalDateTime date; // date and time
    private final double clickCost; // 6 d.p. value (>0)

    public ClickEntry(long userId, LocalDateTime date, double clickCost) {
        super(userId);
        this.date = date;
        this.clickCost = clickCost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getClickCost() {
        return clickCost;
    }
}
