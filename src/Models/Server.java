package Models;

import java.util.Date;

public class Server {
    Date entryDate; // entry date and time, stored as date
    long id; // ~19 digit unique user id
    Date exitDate; // exit date and time
    int pages; // num of pages viewed
    boolean conversion; // has the user acted after clicking?

    public Server(Date entryDate, long id, Date exitDate, int pages, boolean conversion) {
        this.entryDate = entryDate;
        this.id = id;
        this.exitDate = exitDate;
        this.pages = pages;
        this.conversion = conversion;
    }
}
