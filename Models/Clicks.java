package Models;

import java.util.ArrayList;

public class Clicks {
    private int clickNo; // number of clicks
    private double totalCost; // total cost of clicks

    private final String clickFile;

    // Initial metric calculation
    public Clicks(String clickLog) {
        clickFile = clickLog;
        readClickLog();
    }

    public void readClickLog(/*filtering to be added*/) {
        Reader clickReader = new Reader(clickFile);
        clickReader.getLine(); // Ignores the first line

        // Reading the file
        while (clickReader.fileIsReady()){
            String[] log = clickReader.getLine().split(",");

            // Extracting a click log's data
            String date = log[0]; // date and time
            long id = Long.parseLong(log[1]); // ~19 digit unique user id
            double clickCost = Double.parseDouble(log[2]); // 6 d.p. value (>0)

            // calculating total clicks and total cost
            clickNo++;
            totalCost += clickCost;
        }
    }

    public int getClickNo() {
        return clickNo;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
