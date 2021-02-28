package Models;

import java.util.Date;

public class Click {
    Date date; // date and time
    long id; // ~19 digit unique user id
    double clickCost; // 6 d.p. value (>0)

    public Click(Date date, long id, double clickCost) {
        this.date = date;
        this.id = id;
        this.clickCost = clickCost;
    }
}
