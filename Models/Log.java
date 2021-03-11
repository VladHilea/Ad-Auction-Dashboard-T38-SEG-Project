package Models;

import java.time.LocalDateTime;

public class Log {
    private LocalDateTime firstDate; // date of first log
    private LocalDateTime lastDate; // date of last log

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    public void setLastDate(LocalDateTime lastDate) {
        this.lastDate = lastDate;
    }
}
