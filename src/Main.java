import View.AdAuctionGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // gui
        AdAuctionGUI adAuctionGUI = new AdAuctionGUI();
        SwingUtilities.invokeLater(adAuctionGUI::prepareGui);
    }

    /*
     * to do:
     * significant speed improvements - multithreading + reworking chart calculator class
     * improve file reading with anomalous data
     * start and end date selection
     * check every metric is being calculated/displayed properly - hours time granularity!
     * histograms - controller and model should be fairly similar
     */
}
