import View.AdAuctionGUI;

import javax.swing.*;

public class AdAuctionMain {
    public static void main(String[] args) {
        // gui launch
        AdAuctionGUI gui = new AdAuctionGUI();
        SwingUtilities.invokeLater(gui::prepareGui);
    }

    /*
     * increment 3:
     * additional chart functionality
     * printing functionality / saving charts
     * campaign loading and comparing
     */
}