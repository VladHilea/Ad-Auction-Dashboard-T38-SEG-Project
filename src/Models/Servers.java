package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Servers {
    private final ArrayList<Server> servers = new ArrayList<>(); // list of logs

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
    }

    // converts string to date, catches n/a end dates
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (date.equals("n/a")) {
            return null;
        } else {
            return (sdf.parse(date));
        }
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
