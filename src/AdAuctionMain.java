import View.AdAuctionGUI;

import javax.swing.*;

public class AdAuctionMain {
    public static void main(String[] args) {
        // gui launch
        AdAuctionGUI gui = new AdAuctionGUI();
        SwingUtilities.invokeLater(gui::prepareGui);
    }

    /*
     * increment 2:
     * histograms - view, controller and model should be fairly similar to charts
     * speed improvements wherever possible - multithreading + reworking chart calculator class
     * finalise compare and gui updates
     * start and end date selection - calendars?
     * functionality testing
     *
     * increment 3:
     * additional chart functionality - explore JFreeChart features
     * printing functionality and saving charts as files
     * user bounce definition - settings page
     * file reading with anomalous data
     * campaign loading and comparing
     */
}
