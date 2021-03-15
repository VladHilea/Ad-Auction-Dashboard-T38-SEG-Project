package Controllers;

import Models.Campaign;
import View.AdAuctionGUI;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    public static void main(String[] args) {
        // gui
        AdAuctionGUI adAuctionGUI = new AdAuctionGUI();

        // campaign
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");

        // main metrics page
        adAuctionGUI.setMetricCalculator(campaign.newMetrics());

        // example of date ranging
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.parse("2015-01-01 12:00:18", formatter);
        LocalDateTime date2 = LocalDateTime.parse("2015-01-01 12:05:50", formatter);

        adAuctionGUI.recalculateMetrics(date1, date2);

        // charts
        adAuctionGUI.setChart(campaign.newChart());

        // load gui
        SwingUtilities.invokeLater(adAuctionGUI::prepareGui);

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
