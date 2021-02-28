package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private int bounceNo; // number of bounces
    private int conversionNo; // number of conversions

    private final String serverFile;

    // Bounce variables
    private int pageLimit; // bounce page limit
    private int bounceTime; // // bounce time

    private Date startDate; // date filtering
    private Date endDate;

    // Initial metric calculation
    public Server(String serverLog, int pageLimit, int bounceTime, Date startDate, Date endDate) throws ParseException {
        this.serverFile = serverLog;
        this.pageLimit = pageLimit;
        this.bounceTime = bounceTime;

        this.startDate = startDate;
        this.endDate = endDate;

        readServerLog();
    }

    public void readServerLog(/*filtering to be added*/) throws ParseException {
        Reader serverReader = new Reader(serverFile);
        serverReader.getLine(); // Ignores the first line

        // Reading the file
        while (serverReader.fileIsReady()) {
            String[] log = serverReader.getLine().split(",");

            // Extracting a server log's data
            Date entryDate = parseDate(log[0]); // entry date and time, stored as date

            // Checks if the server log fits within the given time scale
            if (entryDate.after(startDate) && entryDate.before(endDate)) {
                long id = Long.parseLong(log[1]); // ~19 digit unique user id
                Date exitDate = parseDate(log[2]); // exit date and time
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
    }

    // Converts string to date, catches n/a end dates
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (date.equals("n/a")) {
            return null;
        } else {
            return (sdf.parse(date));
        }
    }

    // Calculates difference between two dates given as strings
    public long splitDates(Date entryDate, Date exitDate) {
        if (exitDate == null) {
            return bounceTime - 1; // where the exit date is invalid, it's counted as a bounce
        } else {
            return exitDate.getTime() - (entryDate.getTime() / 1000);
        }
    }

    public int getBounceNo() {
        return bounceNo;
    }

    public int getConversionNo() {
        return conversionNo;
    }
}
