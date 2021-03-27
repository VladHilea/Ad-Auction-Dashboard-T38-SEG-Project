package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Calculator {
    private final ArrayList<ImpressionEntry> impressionLog;
    private final ArrayList<ClickEntry> clickLog;
    private final ArrayList<ServerEntry> serverLog;
    private final Map<Long, User> users; // list of unique users

    public Calculator(ArrayList<ImpressionEntry> impressionLog, ArrayList<ClickEntry> clickLog, ArrayList<ServerEntry> serverLog, Map<Long, User> users) {
        this.impressionLog = impressionLog;
        this.clickLog = clickLog;
        this.serverLog = serverLog;
        this.users = users;
    }

    public ArrayList<ImpressionEntry> getImpressionLog() {
        return impressionLog;
    }

    // gets a list of all the impressions within a given date range
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

    public ArrayList<ClickEntry> getClickLog() {
        return clickLog;
    }

    // gets a list of all the clicks within a given date range
    public ArrayList<ClickEntry> getClickLog(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<ClickEntry> rangedClicksLog = new ArrayList<>();

        for (ClickEntry click : clickLog) {
            if (!click.getDate().isBefore(startDate) && !click.getDate().isAfter(endDate)) {
                rangedClicksLog.add(click);
            }
        }

        return rangedClicksLog;
    }

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

    public Map<Long, User> getUsers() {
        return users;
    }
}
