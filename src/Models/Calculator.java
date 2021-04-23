package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Calculator {
    private final ArrayList<ImpressionEntry> impressionLog; // list of impressions
    private final ArrayList<ClickEntry> clickLog; // list of clicks
    private final ArrayList<ServerEntry> serverLog; // list of server entries
    private final Map<Long, User> users; // list of unique users

    public int pageLimit = 1; // max number of pages to be counted as a bounce
    public int bounceTime = 500; // max amount of time to be counted as a bounce
    // set either value to 0 when not being used

    public Calculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users) {
        this.impressionLog = impressionLog;
        this.clickLog = clickLog;
        this.serverLog = serverLog;
        this.users = users;
    }

    // gets a list of all the impression entries
    public ArrayList<ImpressionEntry> getImpressionLog() {
        return impressionLog;
    }

    // gets a list of all the impression entries within a given date range
    public ArrayList<ImpressionEntry> getImpressionLog(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<ImpressionEntry> rangedImpressionsLog = new ArrayList<>();

        for (ImpressionEntry impression : impressionLog) {
            if (!impression.getDate().isBefore(startDate) && !impression.getDate().isAfter(endDate)) {
                rangedImpressionsLog.add(impression);
            } else if (impression.getDate().isAfter(endDate)) {
                break;
            }
        }
        return rangedImpressionsLog;
    }

    // gets a list of all the click entries
    public ArrayList<ClickEntry> getClickLog() {
        return clickLog;
    }

    // gets a list of all the click entries within a given date range
    public ArrayList<ClickEntry> getClickLog(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<ClickEntry> rangedClicksLog = new ArrayList<>();

        for (ClickEntry click : clickLog) {
            if (!click.getDate().isBefore(startDate) && !click.getDate().isAfter(endDate)) {
                rangedClicksLog.add(click);
            }
        }

        return rangedClicksLog;
    }

    // gets a list of all the server entries
    public ArrayList<ServerEntry> getServerLog() {
        return serverLog;
    }

    // gets a list of all the server entries within a given date range
    public ArrayList<ServerEntry> getServerLog(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<ServerEntry> rangedClicksLog = new ArrayList<>();

        for (ServerEntry server : serverLog) {
            if (!server.getEntryDate().isBefore(startDate) && !server.getEntryDate().isAfter(endDate)) {
                rangedClicksLog.add(server);
            }
        }

        return rangedClicksLog;
    }

    // gets a list of all the users
    public Map<Long, User> getUsers() {
        return users;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public int getBounceTime() {
        return bounceTime;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public void setBounceTime(int bounceTime) {
        this.bounceTime = bounceTime;
    }
}
