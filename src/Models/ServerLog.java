package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServerLog extends Log {
    private ArrayList<Server> serverList = new ArrayList<>(); // list of logs

    public ServerLog(String serverFile) {
        Reader serverReader = new Reader(serverFile); // file reader
        serverReader.getLine(); // ignore first line

        // Reading the file
        while (serverReader.fileIsReady()) {
            String[] log = serverReader.getLine().split(",");

            // Extracting a server log's data
            Server server = new Server(parseDate(log[0]), // entry date
                    Long.parseLong(log[1]), // id
                    parseDate(log[2]), // exit date
                    Integer.parseInt(log[3]), // pages viewed
                    log[4].equalsIgnoreCase("Yes")); // conversion

            serverList.add(server);
        }
        setDates();
    }

    // constructor for an existing list of server logs
    public ServerLog(ArrayList<Server> serversList) {
        this.serverList = serversList; // list of server logs
    }

    // sets the first and last dates of the log
    public void setDates() {
        setFirstDate(serverList.get(0).entryDate); // start date
        setLastDate(serverList.get(serverList.size() - 1).entryDate); // end date
    }

    // converts string to date, catches n/a end dates
    public LocalDateTime parseDate(String date) {
        if (date.equals("n/a")) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        }
    }

    public ArrayList<Server> getServerList() {
        return serverList;
    }

    public ArrayList<Server> getServerList(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Server> rangedClicksList = new ArrayList<>();

        for (Server server : serverList) {
            if (!server.getEntryDate().isBefore(startDate) && !server.getEntryDate().isAfter(endDate)) {
                rangedClicksList.add(server);
            }
        }

        return rangedClicksList;
    }
}
