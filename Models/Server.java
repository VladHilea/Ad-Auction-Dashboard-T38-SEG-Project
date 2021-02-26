package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private int bounceNo; // number of bounces
    private int conversionNo; // number of conversions

    private final String serverFile;

    // Bounce variables
    private int pageLimit; // bounce page limit
    private int bounceTime; // // bounce time

    // Initial metric calculation
    public Server(String serverLog, int pageLimit, int bounceTime) {
        this.serverFile = serverLog;
        this.pageLimit = pageLimit;
        this.bounceTime = bounceTime;

        readServerLog();
    }

    public void readServerLog(/*filtering to be added*/) {
        Reader serverReader = new Reader(serverFile);
        serverReader.getLine(); // Ignores the first line

        // Reading the file
        while (serverReader.fileIsReady()) {
            String[] log = serverReader.getLine().split(",");

            // Extracting a server log's data
            String entryDate = log[0]; // entry date and time
            long id = Long.parseLong(log[1]); // ~19 digit unique user id
            String exitDate = log[2]; // exit date and time
            int pages = Integer.parseInt(log[3]); // num of pages viewed
            boolean conversion = log[4].equalsIgnoreCase("Yes"); // has the user acted after clicking?

            // calculating bounce number and conversion number
            if (pages <= pageLimit || splitDates(entryDate, exitDate) <= bounceTime) {
                bounceNo++;
            }
            if (conversion) {
                conversionNo++;
            }
        }
    }

    // Calculates difference between two dates given as strings
    public long splitDates(String entry, String exit){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            // converts dates as strings to time
            Date entryDate = sdf.parse(entry);
            Date exitDate = sdf.parse(exit);

            // returns the time difference in seconds
            return exitDate.getTime() - (entryDate.getTime() / 1000);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getBounceNo() {
        return bounceNo;
    }

    public int getConversionNo() {
        return conversionNo;
    }
}
