package Controllers;

import Models.Campaign;
import View.AdAuctionGUI;

import javax.swing.*;

public class CampaignController {
    public static void main(String[] args) {
        // gui
        AdAuctionGUI adAuctionGUI = new AdAuctionGUI();
        SwingUtilities.invokeLater(adAuctionGUI::prepareGui);

        // campaign
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");
        adAuctionGUI.createMetrics(campaign.createMetrics());
        adAuctionGUI.createCharts(campaign.createCharts());

        /*
         * to do:
         * significant speed improvements
         * not every metric is being calculated/displayed properly
         * load campaign from files button
         * update commenting for GUI
         * improve file reading with anomalous data
         * merge histogram and compare features
         */
    }
}
