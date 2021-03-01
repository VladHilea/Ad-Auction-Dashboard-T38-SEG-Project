package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Impressions {
    private final ArrayList<Impression> impressions = new ArrayList<>(); // list of logs

    public Impressions(String impressionLog) {
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

    // converts string to date
    public LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
    }
}