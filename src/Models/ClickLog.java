package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClickLog extends Log {
    private ArrayList<Click> clicksList = new ArrayList<>(); // list of logs

    // constructor for the first time opening a campaign
    public ClickLog(String clickFile) {
        Reader clickReader = new Reader(clickFile); // file reader
        clickReader.getLine(); // ignore first line

        // reading the file
        while (clickReader.fileIsReady()){
            String[] log = clickReader.getLine().split(",");

            // extracting a click log's data
            Click click = new Click(parseDate(log[0]), // date
                    Long.parseLong(log[1]), // id
                    Double.parseDouble(log[2])); // click cost

            clicksList.add(click);
        }

        // determines the dates of the logs
        setFirstDate(clicksList.get(0).date);
        setLastDate(clicksList.get(clicksList.size() - 1).date);
    }

    // constructor for an exisiting list of clicks
    public ClickLog(ArrayList<Click> clicksList) {
        this.clicksList = clicksList; // list of impressions
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
}