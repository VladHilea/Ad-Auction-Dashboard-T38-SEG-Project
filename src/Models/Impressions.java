package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Impressions {
    private final ArrayList<Impression> impressions = new ArrayList<>(); // list of logs

    public Impressions(String impressionLog) throws ParseException {
        Reader impressionReader = new Reader(impressionLog); // file reader
        impressionReader.getLine(); // ignore first line

        // reading the file
        while (impressionReader.fileIsReady()) {
            String[] log = impressionReader.getLine().split(",");

            // extracting an impression log's data
            Impression impression = new Impression(parseDate(log[0]), // date
                    Long.parseLong(log[1]), // id
                    log[2], // gender
                    log[3], // age
                    log[4], // income
                    log[5], // context
                    Double.parseDouble(log[6])); // impression cost

            impressions.add(impression);
        }
    }

    // converts string to date, catches n/a end dates
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return (sdf.parse(date));
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
    }
}