package Controllers;

import Models.Campaign;
import View.AdAuctionGUI;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CampaignController {
    public static void main(String[] args) {
        // campaign
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");

        // gui
        AdAuctionGUI adAuctionGUI = new AdAuctionGUI(campaign.newMetrics(), campaign.newChart());
        SwingUtilities.invokeLater(adAuctionGUI::prepareGui);

        // example of date ranging - dates hardcoded
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.parse("2015-01-01 12:00:00", formatter); // start date
        LocalDateTime date2 = LocalDateTime.parse("2015-01-07 12:00:00", formatter); // end date

        adAuctionGUI.recalculateMetrics(date1, date2);
        adAuctionGUI.recalculateChart(date1, date2, "Days", "clicks");
        // there are also methods of the same name with no parameters to show the whole range

        /*
         * to do:
         * blank gui
         * load metrics and default chart button
         * update commenting for GUI
         * improve file reading with anomalous data - test on large dataset
         * follow 2nd deliverable sprint plan
         */
    }
}
