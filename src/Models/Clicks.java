package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Clicks {
    private int clickNo; // number of clicks
    private double totalCost; // total cost of clicks

    private final ArrayList<Click> clicks = new ArrayList<>(); // list of logs

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
    }

    // converts string to date
    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return (sdf.parse(date));
    }

    public ArrayList<Click> getClicks() {
        return clicks;
    }
}
