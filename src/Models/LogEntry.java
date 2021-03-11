package Models;

public class LogEntry {
    long id; // ~19 digit unique user id

    public LogEntry(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
