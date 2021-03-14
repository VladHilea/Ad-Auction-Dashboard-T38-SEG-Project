import Models.Campaign;
import View.AdAuctionGUI;

import javax.swing.*;

public class Controller {
    public static void main(String[] args) {
        // gui
        AdAuctionGUI adAuctionGUI = new AdAuctionGUI();

        // campaign
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");

        // main metrics page
        adAuctionGUI.setMetricCalculator(campaign.newMetrics());

        // charts
        adAuctionGUI.setChart(campaign.newChart());

        // load gui
        SwingUtilities.invokeLater(AdAuctionGUI::prepareGui);

        /*
         * to do:
         * blank gui
         * update commenting for GUI
         * improve file reading with anomalous data
         * 2nd deliverable sprint plan
         *
         * for later:
         * filtering was removed due to my bad implementation - leave till 2nd deliverable
         * bounce factors are hardcoded - leave till 2nd & 3rd deliverables
         * will later add a class HistogramCalculator - leave till 2nd & 3rd deliverables
         */
    }
}
