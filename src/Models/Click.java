package Models;

import java.time.LocalDateTime;

public class Click extends LogEntry {
    LocalDateTime date; // date and time
    double clickCost; // 6 d.p. value (>0)

    public Click(LocalDateTime date, long id, double clickCost) {
        super(id);

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
