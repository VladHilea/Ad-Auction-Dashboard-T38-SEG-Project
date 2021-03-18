package Models;

import java.time.LocalDateTime;

public class ServerEntry {
    private final long userId; // ~19 digit unique user id
    private final LocalDateTime entryDate; // entry date and time, stored as date
    private final LocalDateTime exitDate; // exit date and time
    private final int pages; // num of pages viewed
    private final boolean conversion; // has the user acted after clicking?

    public ServerEntry(Long userId, LocalDateTime entryDate, LocalDateTime exitDate, int pages, boolean conversion) {
        this.userId = userId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.pages = pages;
        this.conversion = conversion;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public int getPages() {
        return pages;
    }

    public boolean isConversion() {
        return conversion;
    }
}
