package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClickLog extends Log {
    private ArrayList<Click> clicksList = new ArrayList<>(); // list of logs
    public boolean isSet = false;

    // constructor for the first time opening a campaign
    public ClickLog(String clickFile) {
        Reader clickReader = new Reader(clickFile); // file reader
        if (clickReader.fileIsReady()) {
            isSet = true;
            clickReader.getLine(); // ignore first line

            // reading the file
            while (clickReader.fileIsReady()) {
                String[] log = clickReader.getLine().split(",");

                // extracting a click log's data
                Click click = new Click(parseDate(log[0]), // date
                        Long.parseLong(log[1]), // id
                        Double.parseDouble(log[2])); // click cost

                clicksList.add(click);
            }
            setDates();
        }
    }

    // constructor for an exisiting list of clicks
    public ClickLog(ArrayList<Click> clicksList) {
        this.clicksList = clicksList; // list of impressions
    }

    // sets the first and last dates of the log
    public void setDates() {
        setFirstDate(clicksList.get(0).date); // start date
        setLastDate(clicksList.get(clicksList.size() - 1).date); // end date
    }

    // converts string to date,
    public LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    public ArrayList<Click> getClicksList() {
        return clicksList;
    }

    public boolean isSet() {
        return isSet;
    }
}
