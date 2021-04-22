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
     * histograms
     * speed improvements wherever possible - multithreading + reworking chart calculator class
     * finalise compare and gui updates
     * ctr, cpa etc. w/ lots of filters during days not displaying + start and end date selection - calendars?
     * functionality testing
     *
     * increment 3:
     * additional chart functionality - explore JFreeChart features
     * printing functionality and saving charts as files
     * user bounce definition - settings page
     * file reading with anomalous data
     * campaign loading and comparing
     *
     * testing:
     * unit testing
     * manual testing w/ user stories
     * calculation testing
     */
}