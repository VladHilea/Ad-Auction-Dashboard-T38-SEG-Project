package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Impressions {
    private int impressionNo; // total impressions
    private int uniquesNo; // total uniques
    private double totalCost; // total impression cost

    private final String impressionFile; // file name for logs

    private ArrayList<Impression> impressions; // list of logs

    // initial metric calculation
    public Impressions(String impressionLog) throws ParseException {
        this.impressionFile = impressionLog;

        Reader impressionReader = new Reader(impressionFile);  // file reader
        impressionReader.getLine(); // ignore first line

        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users

        // reading the file
        while (impressionReader.fileIsReady()) {
            String[] log = impressionReader.getLine().split(",");

            // Extracting an impression log's data
            Impression impression = new Impression(parseDate(log[0]), // date
                    Long.parseLong(log[1]), // id
                    log[2], // gender
                    log[3], // age
                    log[4], // income
                    log[5], // context
                    Double.parseDouble(log[6])); // impression cost

            impressions.add(impression);

            // calculating total impressions, total cost and unique impressions
            impressionNo++;
            totalCost += impression.impressionCost;

            if (!uniqueIds.contains(impression.id)) {
                uniquesNo++;
                uniqueIds.add(impression.id);
            }
        }
    }

    public void filterMetrics(Date startDate, Date endDate) {
        HashSet<Long> uniqueIds = new HashSet<>(); // list of unique users
        boolean inTime = true; // date filtering
        int count = 0; // indexing

        // Reading the file
        while (inTime) {
            Impression impression = impressions.get(count);

            // Checks if the impression log fits within the given time scale
            if (impression.date.after(startDate) && impression.date.before(endDate)) {
                // calculating total impressions, total cost and unique impressions
                impressionNo++;
                totalCost += impression.impressionCost;

                if (!uniqueIds.contains(impression.id)) {
                    uniquesNo++;
                    uniqueIds.add(impression.id);
                }
            } else {
                inTime = false;
            }

            count++;
        }
    }

    // Converts string to date, catches n/a end dates
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return (sdf.parse(date));
    }

    public int getImpressionNo() {
        return impressionNo;
    }

    public int getUniquesNo() {
        return uniquesNo;
    }

    public double getTotalCost() {
        return totalCost;
    }
}