package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImpressionLog extends Log {
    private ArrayList<Impression> impressionsList = new ArrayList<>(); // list of logs
<<<<<<< Updated upstream
=======
    public boolean isSet = false;
>>>>>>> Stashed changes

    // constructor for the first time opening a campaign
    public ImpressionLog(String impressionFile) {
        Reader impressionReader = new Reader(impressionFile); // file reader
<<<<<<< Updated upstream
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

            impressionsList.add(impression);
        }
        setDates();
=======
        if (impressionReader.fileIsReady()) {
            isSet = true;
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

                impressionsList.add(impression);
            }
            setDates();
        }
>>>>>>> Stashed changes
    }

    // constructor for an exisiting list of impressions
    public ImpressionLog(ArrayList<Impression> impressionsList) {
        this.impressionsList = impressionsList; // list of impressions
    }

    // sets the first and last dates of the log
    public void setDates() {
        setFirstDate(impressionsList.get(0).date); // start date
        setLastDate(impressionsList.get(impressionsList.size() - 1).date); // end date
    }

    // converts string to date
    public LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    public ArrayList<Impression> getImpressionsList() {
        return impressionsList;
    }
<<<<<<< Updated upstream
=======

    public boolean isSet() {
        return isSet;
    }


>>>>>>> Stashed changes
}