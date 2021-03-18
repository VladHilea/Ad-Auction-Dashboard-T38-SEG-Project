package Models;

import java.time.LocalDateTime;

public class ClickEntry {
    private final long userId; // ~19 digit unique user id
    private final LocalDateTime date; // date and time
    private final double clickCost; // 6 d.p. value (>0)

    public ClickEntry(long userId, LocalDateTime date, double clickCost) {
        this.userId = userId;
        this.date = date;
        this.clickCost = clickCost;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getClickCost() {
        return clickCost;
    }
}
