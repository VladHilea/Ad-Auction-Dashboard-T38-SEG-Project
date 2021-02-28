package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Clicks {
    private int clickNo; // number of clicks
    private double totalCost; // total cost of clicks

    private final String clickLog;

    private Date startDate; // date filtering
    private Date endDate;

    // Initial metric calculation
    public Clicks(String clickLog, Date startDate, Date endDate) throws ParseException {
        this.clickLog = clickLog;

        this.startDate = startDate;
        this.endDate = endDate;

        readClickLog();
    }

    public void readClickLog(/*filtering to be added*/) throws ParseException {
        Reader clickReader = new Reader(clickLog);
        clickReader.getLine(); // Ignores the first line

        // Reading the file
        while (clickReader.fileIsReady()){
            String[] log = clickReader.getLine().split(",");

            // Extracting a click log's data
            Date date = parseDate(log[0]); // date and time

            // Checks if the click log fits within the given time scale
            if (date.after(startDate) && date.before(endDate)) {
                long id = Long.parseLong(log[1]); // ~19 digit unique user id
                double clickCost = Double.parseDouble(log[2]); // 6 d.p. value (>0)

                // calculating total clicks and total cost
                clickNo++;
                totalCost += clickCost;
            }
        }
    }

    // Converts string to date
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return (sdf.parse(date));
    }

    public int getClickNo() {
        return clickNo;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
