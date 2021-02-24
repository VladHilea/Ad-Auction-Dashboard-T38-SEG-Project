import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MetricCalculator {

    /**
     Number of impressions: length of impression log
     Number of clicks: length of click log
     Number of uniques: length of impression log after removing duplicates
     Number of Bounces: filter by bounce time(server log's exit date - entry date)
     Number of Conversions: server log's conversion
     Total Cost: add all of impression cost


     CTR: number of clicks / number of impressions
     CPA: total cost / number of conversions
     CPC: total cost / number of clicks
     CPM: total cost*1000 / number of impressions
     Bounce rate: number of bounces / number of clicks
     */

    public static void main(String[] args){


        MetricCalculator myCalculator = new MetricCalculator();
        myCalculator.clickCounter();
        myCalculator.readServerLogs();
        myCalculator.uniqueNo = myCalculator.readImpressionLogs().size();

        float ctr = myCalculator.clickNo / myCalculator.impressionNo;
        double cpa = myCalculator.totalCost / myCalculator.conversionNo;
        double cpc = myCalculator.totalCost / myCalculator.clickNo;
        double cpm = myCalculator.totalCost*1000 / myCalculator.impressionNo;
        double br = myCalculator.bounceNo / myCalculator.clickNo;



        System.out.println("Number of impressions: "+Integer.toString(myCalculator.impressionNo));
        System.out.println("Number of clicks: "+Integer.toString(myCalculator.clickNo));
        System.out.println("Number of uniques: "+Integer.toString(myCalculator.uniqueNo));
        System.out.println("Number of bounces: "+Integer.toString(myCalculator.bounceNo));
        System.out.println("Number of conversions: "+Integer.toString(myCalculator.conversionNo));
        System.out.println("Total cost: "+Double.toString(myCalculator.totalCost));
        System.out.println("CTR: "+ ctr); // Don't know why its giving 0?
        System.out.println("CPA: "+ Double.toString(cpa));
        System.out.println("CPC: " + Double.toString(cpc));
        System.out.println("CPM: " + Double.toString(cpm));
        System.out.println("Bounce Rate: "+ Double.toString(br));

    }


    String clickLogs = "click_log.csv";
    String serverLogs = "server_log.csv";
    String impressionLogs = "impression_log.csv";


    Reader reader1 = new Reader(clickLogs);
    Reader reader2 = new Reader(serverLogs);
    Reader reader3 = new Reader(impressionLogs);

    int bounceTime = 10; // bounce time, input by user

    int impressionNo = 0;
    int clickNo= 0;
    int uniqueNo= 0;
    int bounceNo= 0;
    int conversionNo = 0;
    double totalCost= 0;



    public void clickCounter() {
        reader1.getLine();
        while (reader1.fileIsReady()){
            clickNo++;
            reader1.getLine();
        }
    }

    public void readServerLogs() {
        reader2.getLine();
        while (reader2.fileIsReady()){
            String[] log = reader2.getLine().split(",");
            String exitDate = log[2];
            String entryDate = log[0];
            String conversion = log[4];
            if (conversion.equals("Yes")) {
                conversionNo++;
            }
        }
    }
    //Calculates difference between time (in form of string)
    public int timeDifference(String entryDate, String exitDate){

        String entryTime = exitDate.split(" ")[1];


        String exitTime = exitDate.split(" ")[1];
        return 0;
    }




    //Reads the impression log, and creates a map of lists, using ID as the key, and putting the rest in the list
    public Map<String, ArrayList<String>> readImpressionLogs(){
        Map<String,ArrayList<String>> impressions = new HashMap<>();
        ArrayList items = new ArrayList();

        reader3.getLine();//removing first line

        while (reader3.fileIsReady()){
            impressionNo++; //used to count total impressions
            String[] log = reader3.getLine().split(",");
            String date = log[0];
            String id = log[1];
            String gender = log[2];
            String age = log[3];
            String income = log[4];
            String context = log[5];
            String impressionCost = log[6];

            items.add(date);
            items.add(gender);
            items.add(age);
            items.add(income);
            items.add(context);
            items.add(impressionCost);

            //Adding cost of impression to totalCost
            double intCost = Double.parseDouble(impressionCost);
            totalCost = totalCost + intCost;


            impressions.put(id,items);
            items.clear();
        }

        return impressions;
    }

}
