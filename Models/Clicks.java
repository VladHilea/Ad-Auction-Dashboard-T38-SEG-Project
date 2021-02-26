package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Clicks {
    private int clickNo; // number of clicks
    private double totalCost; // total cost of clicks

    private final String clickLog;

    // Initial metric calculation
    public Clicks(String clickLog) {
        this.clickLog = clickLog;

        try {
            readClickLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readClickLog(/*filtering to be added*/) throws ParseException {
        Reader clickReader = new Reader(clickLog);
        clickReader.getLine(); // Ignores the first line

        // Date format to match strings in csv
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // Reading the file
        while (clickReader.fileIsReady()){
            String[] log = clickReader.getLine().split(",");

            // Extracting a click log's data
            Date date = parseDate(log[0]); // date and time
            long id = Long.parseLong(log[1]); // ~19 digit unique user id
            double clickCost = Double.parseDouble(log[2]); // 6 d.p. value (>0)

            // calculating total clicks and total cost
            clickNo++;
            totalCost += clickCost;
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
