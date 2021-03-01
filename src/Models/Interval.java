package Models;

import java.time.LocalDateTime;

public class Interval {
    private final LocalDateTime date;

    public Interval(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
