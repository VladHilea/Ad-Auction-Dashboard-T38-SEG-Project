import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdAuctionGUI extends JFrame{
    private static JFrame gui;
    private static JLayeredPane menu,topMenu;
    private static JPanel verticalMenu,insightsGrid;

    private static JButton insightsButton,chartsButton,histogramsButton,compareButton,settingsButton;

    private static JPanel impressionsPanel,clicksPanel,uniquePanel,ctrPanel,cpmPanel,cpaPanel,cpcPanel,conversionsPanel,totalCostPanel,bouncePanel,bounceRatePanel,bounceTimePanel;
    private static JLabel impressions,clicks,uniques,ctr,cpm,cpa,cpc,conversions,totalCost,bounce,bounceRate,bounceTime;
    private static JLabel impressionsValue;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public static void prepareGui(){
        gui = new JFrame("Ad Auction Monitor");
        gui.setVisible(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu();
        gui.add(menu);

    }
    public static void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    public static void createMenu(){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);

        createTopMenu();
        createVerticalMenu();
        createInsightsGrid();
    }


    public static void createVerticalMenu(){
        verticalMenu = new JPanel();
        verticalMenu.setBounds(0,100,200,gui.getHeight());
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(new Color(242,236,236));


        insightsButton = new JButton("Insights");
        insightsButton.setBackground(new Color(242,236,236));
        insightsButton.setPreferredSize(new Dimension(40,40));
        insightsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,75,70,74)));

        chartsButton = new JButton("Charts");
        chartsButton.setBackground(new Color(242,236,236));
        chartsButton.setPreferredSize(new Dimension(40,40));
        chartsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,80,70,82)));

        histogramsButton = new JButton("Histograms");
        histogramsButton.setBackground(new Color(242,236,236));
        histogramsButton.setPreferredSize(new Dimension(40,40));
        histogramsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,70,70,54)));

        compareButton = new JButton("Compare");
        compareButton.setBackground(new Color(242,236,236));
        compareButton.setPreferredSize(new Dimension(40,40));
        compareButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,75,70,68)));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(new Color(242,236,236));
        settingsButton.setPreferredSize(new Dimension(40,40));
        settingsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,75,70,73)));


        Box vbox = Box.createVerticalBox();
        vbox.setBounds(0,100,200,verticalMenu.getHeight());
        vbox.setOpaque(true);


        vbox.add(insightsButton);
        vbox.add(chartsButton);
        vbox.add(histogramsButton);
        vbox.add(compareButton);
        vbox.add(settingsButton);

        verticalMenu.setLayout(new BoxLayout(verticalMenu, BoxLayout.Y_AXIS));
        verticalMenu.add(vbox);
        menu.add(verticalMenu, BorderLayout.WEST,0);
    }

    public static void createTopMenu(){
        topMenu = new JLayeredPane();
        topMenu.setSize(gui.getWidth(),100);
        topMenu.setOpaque(true);
        topMenu.setBackground(new Color(14,139,229));



        JLabel productName = new JLabel("Ad Monitor");
        productName.setBackground(new Color(14,139,229));
        productName.setSize(100,100);
        productName.setAlignmentX(20);
        productName.setBounds(20,0,100,100);



        JLabel customerName = new JLabel("Customer Name");
        productName.setSize(100,100);
        customerName.setBounds(gui.getWidth()-120,0,100,100);



        topMenu.add(productName,BorderLayout.WEST,0);
        topMenu.add(customerName,BorderLayout.EAST,1);
        menu.add(topMenu,BorderLayout.NORTH,1);

    }

    public static void createInsightsGrid(){
        insightsGrid = new JPanel(new GridLayout(4,3));
        insightsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        insightsGrid.setAlignmentY(100);
        insightsGrid.setOpaque(true);
        insightsGrid.setBackground(Color.YELLOW);
        insightsGrid.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));


        //start panel
        impressionsPanel = new JPanel(new GridBagLayout());
        impressionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        impressionsPanel.setOpaque(false);

        impressions = new JLabel("Impressions");
        impressions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressions.setForeground(new Color(220,120,27));
        impressions.setFont(new Font(impressions.getFont().getName(),Font.BOLD,25));


        impressionsValue = new JLabel("500");
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(new Font(impressionsValue.getFont().getName(),Font.BOLD,30));

        Box impressionsVBox = Box.createVerticalBox();
        impressionsVBox.add(impressionsValue);
        impressionsVBox.add(impressions);
        impressionsPanel.add(impressionsVBox);
        //end panel



        clicks = new JLabel("Clicks");
        clicks.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        uniques = new JLabel("Uniques");
        uniques.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ctr = new JLabel("CTR");
        ctr.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cpa = new JLabel("CPA");
        cpa.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cpc = new JLabel("CPC");
        cpc.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cpm = new JLabel("CPM");
        cpm.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        conversions = new JLabel("Conversions");
        conversions.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        totalCost = new JLabel("Total Cost");
        totalCost.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        bounce = new JLabel("Bounce");
        bounce.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        bounceRate = new JLabel("Bounce Rate");
        bounceRate.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        bounceTime = new JLabel("Bounce Time");
        bounceTime.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        insightsGrid.add(impressionsPanel);
        insightsGrid.add(clicks);
        insightsGrid.add(uniques);
        insightsGrid.add(ctr);
        insightsGrid.add(cpa);
        insightsGrid.add(cpc);
        insightsGrid.add(cpm);
        insightsGrid.add(conversions);
        insightsGrid.add(totalCost);
        insightsGrid.add(bounce);
        insightsGrid.add(bounceRate);
        insightsGrid.add(bounceTime);



        menu.add(insightsGrid);
    }


    public static void main(String[] args) {
        prepareGui();
    }
}
