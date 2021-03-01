package Models;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Servers {
    private final ArrayList<Server> servers = new ArrayList<>(); // list of logs

    private final LocalDateTime startDate; // entry date of first log
    private final LocalDateTime endDate; // entry date of last log

    public Servers(String serverLog) throws ParseException {
        Reader serverReader = new Reader(serverLog); // file reader
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

            servers.add(server);
        }

        this.startDate = servers.get(0).entryDate; // start date
        this.endDate = servers.get(servers.size() - 1).entryDate; // end date
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

    public ArrayList<Server> getServers() {
        return servers;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
