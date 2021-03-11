package Models;

import java.time.LocalDateTime;

public class Server extends LogEntry {
    LocalDateTime entryDate; // entry date and time, stored as date
    LocalDateTime exitDate; // exit date and time
    int pages; // num of pages viewed
    boolean conversion; // has the user acted after clicking?

    public Server(LocalDateTime entryDate, long id, LocalDateTime exitDate, int pages, boolean conversion) {
        super(id);

        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.pages = pages;
        this.conversion = conversion;
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
