package Models;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Clicks {
    private final ArrayList<Click> clicks = new ArrayList<>(); // list of logs

    private final LocalDateTime startDate; // date of first log
    private final LocalDateTime endDate; // date of last log

    // Initial metric calculation
    public Clicks(String clickLog) throws ParseException {
        Reader clickReader = new Reader(clickLog); // file reader
        clickReader.getLine(); // ignore first line

        // reading the file
        while (clickReader.fileIsReady()){
            String[] log = clickReader.getLine().split(",");

            // extracting a click log's data
            Click click = new Click(parseDate(log[0]), // date
                    Long.parseLong(log[1]), // id
                    Double.parseDouble(log[2])); // click cost

            clicks.add(click);
        }

        this.startDate = clicks.get(0).date; // start date
        this.endDate = clicks.get(clicks.size() - 1).date; // end date
    }

    // converts string to date,
    public LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    public ArrayList<Click> getClicks() {
        return clicks;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
