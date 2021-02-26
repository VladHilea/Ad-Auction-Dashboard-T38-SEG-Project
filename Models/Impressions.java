package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Impressions {
    private int impressionNo; // total impressions
    private int uniquesNo; // total uniques
    private double totalCost; // total impression cost

    private final String impressionFile; // file name for logs

    private Date startDate; // date filtering
    private Date endDate;

    // Initial metric calculation
    public Impressions(String impressionLog, Date startDate, Date endDate) throws ParseException {
        this.impressionFile = impressionLog;

        this.startDate = startDate;
        this.endDate = endDate;

        readImpressionLog();
    }

    public void readImpressionLog(/*filtering to be added*/) throws ParseException {
        Reader impressionReader = new Reader(impressionFile);
        impressionReader.getLine(); // Ignores the first line

        // Date format to match strings in csv
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // List of unique users who gave an impression
        HashSet<Long> uniqueIds = new HashSet<>();

        // Reading the file
        while (impressionReader.fileIsReady()){
            String[] log = impressionReader.getLine().split(",");

            // Extracting an impression log's data
            Date date = parseDate(log[0]); // date and time

            // Checks if the impression log fits within the given time scale
            if (date.after(startDate) && date.before(endDate)) {
                long id = Long.parseLong(log[1]); // ~19 digit unique user id
                String gender = log[2]; // male or female
                String age = log[3]; // <25, 25-34, 35-44, 45-54, >54
                String income = log[4]; // high, medium or low
                String context = log[5]; // blog, new, shopping, social media
                double impressionCost = Double.parseDouble(log[6]); // 6 d.p. value (>0)

                // calculating total impressions, total cost and unique impressions
                impressionNo++;
                totalCost += impressionCost;

                if (!uniqueIds.contains(id)) {
                    uniquesNo++;
                    uniqueIds.add(id);
                }
            }
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