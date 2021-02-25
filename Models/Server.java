package Models;

public class Server {
    private int bounceNo; // number of bounces
    private int conversionNo; // number of conversions

    private final String serverFile;
    private int pageLimit = 1;

    public Server(String serverLog) {
        serverFile = serverLog;
        readServerLog();
    }

    public void readServerLog(/*filtering to be added*/) {
        Reader serverReader = new Reader(serverFile);
        serverReader.getLine(); // Ignores the first line

        // Reading the file
        while (serverReader.fileIsReady()){
            String[] log = serverReader.getLine().split(",");

            // Extracting a server log's data
            String entryDate = log[0]; // entry date and time
            long id = Long.parseLong(log[1]); // ~19 digit unique user id
            String exitDate = log[2]; // exit date and time
            int pages = Integer.parseInt(log[3]); // num of pages viewed
            boolean conversion = log[4].equalsIgnoreCase("Yes"); // has the user acted after clicking?

            // calculating bounce number and conversion number
            if (pages <= pageLimit && !conversion) {
                bounceNo++;
            }
            if (conversion) {
                conversionNo++;
            }
        }
    }

    // Calculates difference between time (in form of string)
    public int timeDifference(String entryDate, String exitDate){

        String entryTime = exitDate.split(" ")[1];


        String exitTime = exitDate.split(" ")[1];
        return 0;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getBounceNo() {
        return bounceNo;
    }

    public int getConversionNo() {
        return conversionNo;
    }
}
