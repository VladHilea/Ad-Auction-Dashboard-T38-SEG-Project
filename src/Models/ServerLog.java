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

        // determines the dates of the logs
        setFirstDate(serverList.get(0).entryDate);
        setLastDate(serverList.get(serverList.size() - 1).entryDate);
    }

    // constructor for an existing list of server logs
    public ServerLog(ArrayList<Server> serversList) {
        this.serverList = serversList; // list of server logs

        setFirstDate(serversList.get(0).entryDate); // start date
        setLastDate(serversList.get(serversList.size() - 1).entryDate); // end date
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
}
