package Models;

import java.time.LocalDateTime;

public class Click {
    LocalDateTime date; // date and time
    long id; // ~19 digit unique user id
    double clickCost; // 6 d.p. value (>0)

    public Click(LocalDateTime date, long id, double clickCost) {
        this.date = date;
        this.id = id;
        this.clickCost = clickCost;
    }
}
