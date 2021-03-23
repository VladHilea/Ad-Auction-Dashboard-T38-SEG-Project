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
     * significant speed improvements (now easier to measure with load button)
     * check every metric is being calculated/displayed properly
     * filter dropdowns for metrics page
     * improve file reading with anomalous data
     * merge histogram and compare features
     */
}
