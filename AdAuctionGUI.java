package View;

import Models.Campaign;
import Models.MetricCalculator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdAuctionGUI extends JFrame{
    private static JFrame gui;
    private static JLayeredPane menu,topMenu;
    private static JPanel verticalMenu,insightsGrid;

    private static JButton insightsButton,chartsButton,histogramsButton,compareButton,settingsButton;
    private static JPanel insightsButtonPanel,chartsButtonPanel,histogramsButtonPanel,compareButtonPanel,settingsButtonPanel;


    private static JPanel impressionsPanel,clicksPanel,uniquesPanel,ctrPanel,cpmPanel,cpaPanel,cpcPanel,conversionsPanel,totalCostPanel,bouncePanel,bounceRatePanel,bounceTimePanel;
    private static JLabel impressions,clicks,uniques,ctr,cpm,cpa,cpc,conversions,totalCost,bounce,bounceRate,bounceTime;
    private static JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalCostValues,bounceValues, bounceRateValues, bounceTimeValues;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();



     //Color
     static Color orange= new Color(220,120,27);
     static Color blue= new Color(220,120,27);
     static Color grey = new Color(242,236,236);

     public static Color getPrimaryColor(){
         return blue;
     }
     public static Color getSeondaryColor(){
        return orange;
     }


     //Font
    static Font mainFont = new Font("Impact", Font.PLAIN, 15);

     public static Font getMainFont(){
         return mainFont;
     }


    public static void prepareGui(MetricCalculator calculator){
        gui = new JFrame("Ad Auction Monitor");
        gui.setVisible(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu(calculator);
        gui.add(menu);

    }
    public static void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    public static void createMenu(MetricCalculator calculator){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);

        createTopMenu();
        createVerticalMenu();
        createInsightsGrid(calculator);
    }


    public static void createVerticalMenu(){
        verticalMenu = new JPanel(new GridLayout(5,1));
        verticalMenu.setBounds(0,100,200,gui.getHeight()-100);
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(grey);


        //start panel
        insightsButtonPanel = new JPanel(new BorderLayout());
        insightsButtonPanel.setOpaque(false);
        insightsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        insightsButton = new JButton("Insights");
        insightsButton.setFont(getMainFont());
        insightsButton.setBorderPainted(false);
        insightsButton.setContentAreaFilled(false);
        insightsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        insightsButtonPanel.add(insightsButton);
        //end panel


        //start panel
        chartsButtonPanel = new JPanel(new BorderLayout());
        chartsButtonPanel.setOpaque(false);
        chartsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        chartsButton = new JButton("Charts");
        chartsButton.setBorderPainted(false);
        chartsButton.setContentAreaFilled(false);
        chartsButton.setFont(getMainFont());
        chartsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        chartsButtonPanel.add(chartsButton);
        //end panel


        //start panel
        histogramsButtonPanel = new JPanel(new BorderLayout());
        histogramsButtonPanel.setOpaque(false);
        histogramsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        histogramsButton = new JButton("Histograms");
        histogramsButton.setBorderPainted(false);
        histogramsButton.setContentAreaFilled(false);
        histogramsButton.setFont(getMainFont());
        histogramsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        histogramsButtonPanel.add(histogramsButton);
        //end panel


        //start panel
        compareButtonPanel = new JPanel(new BorderLayout());
        compareButtonPanel.setOpaque(false);
        compareButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        compareButton = new JButton("Compare");
        compareButton.setBorderPainted(false);
        compareButton.setContentAreaFilled(false);
        compareButton.setFont(getMainFont());
        compareButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        compareButtonPanel.add(compareButton);
        //end panel


        //start panel
        settingsButtonPanel = new JPanel(new BorderLayout());
        settingsButtonPanel.setOpaque(false);
        settingsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        settingsButton = new JButton("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFont(getMainFont());
        settingsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        settingsButtonPanel.add(settingsButton);
        //end panel


        verticalMenu.add(insightsButtonPanel);
        verticalMenu.add(chartsButtonPanel);
        verticalMenu.add(histogramsButtonPanel);
        verticalMenu.add(compareButtonPanel);
        verticalMenu.add(settingsButtonPanel);

        menu.add(verticalMenu, BorderLayout.WEST,0);




        insightsButton.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(true);
            }
        });

        chartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
            }
        });

        histogramsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
            }
        });

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
            }
        });
    }


    public static void createTopMenu(){
        topMenu = new JLayeredPane();
        topMenu.setSize(gui.getWidth(),100);
        topMenu.setOpaque(true);
        topMenu.setBackground(new Color(14,139,229));



        JLabel productName = new JLabel("Ad Monitor");
        productName.setBackground(getPrimaryColor());
        productName.setSize(100,100);
        productName.setAlignmentX(20);
        productName.setBounds(20,0,100,100);
        productName.setForeground(Color.WHITE);
        productName.setFont(getMainFont());



        JLabel customerName = new JLabel("Customer Name");
        productName.setSize(100,100);
        customerName.setBounds(gui.getWidth()-120,0,100,100);
        customerName.setForeground(Color.WHITE);



        topMenu.add(productName,BorderLayout.WEST,0);
        topMenu.add(customerName,BorderLayout.EAST,1);
        menu.add(topMenu,BorderLayout.NORTH,1);

    }

    public static void createInsightsGrid(MetricCalculator calculator){


        Font fontOfText = new Font("Impact", Font.PLAIN, 25);
        Font fontofValue = new Font("Impact", Font.BOLD, 30);
        Border blackBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10), BorderFactory.createLineBorder(grey, 1));

        insightsGrid = new JPanel(new GridLayout(4,3));
        insightsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        insightsGrid.setAlignmentY(100);
        insightsGrid.setOpaque(true);
        insightsGrid.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));


        //start panel
        impressionsPanel = new JPanel(new GridBagLayout());
        impressionsPanel.setBorder(blackBorder);
        impressionsPanel.setOpaque(false);

        impressions = new JLabel("Impressions");
        impressions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressions.setForeground(getSeondaryColor());
        impressions.setFont(fontOfText);


        impressionsValue = new JLabel(toString(calculator.getImpressionsNo()));
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(fontofValue);

        Box insightsVBox = Box.createVerticalBox();
        insightsVBox.add(impressionsValue);
        insightsVBox.add(impressions);
        impressionsPanel.add(insightsVBox);
        //end panel

        //start panel
        clicksPanel = new JPanel(new GridBagLayout());
        clicksPanel.setBorder(blackBorder);
        clicksPanel.setOpaque(false);

        clicks = new JLabel("Clicks");
        clicks.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicks.setForeground(getSeondaryColor());
        clicks.setFont(fontOfText);


        clicksValue = new JLabel(toString(calculator.getClicksNo()));
        clicksValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicksValue.setFont(fontofValue);

        Box clicksVbox = Box.createVerticalBox();
        clicksVbox.add(clicksValue);
        clicksVbox.add(clicks);
        clicksPanel.add(clicksVbox);
        //end panel

        //start panel
        uniquesPanel = new JPanel(new GridBagLayout());
        uniquesPanel.setBorder(blackBorder);
        uniquesPanel.setOpaque(false);

        uniques = new JLabel("Clicks");
        uniques.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniques.setForeground(getSeondaryColor());
        uniques.setFont(fontOfText);


        uniquesValue = new JLabel(toString(calculator.getUniquesNo()));
        uniquesValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniquesValue.setFont(fontofValue);

        Box uniquesBox = Box.createVerticalBox();
        uniquesBox.add(uniquesValue);
        uniquesBox.add(uniques);
        uniquesPanel.add(uniquesBox);
        //end panel




        //start panel
        ctrPanel = new JPanel(new GridBagLayout());
        ctrPanel.setBorder(blackBorder);
        ctrPanel.setOpaque(false);

        ctr = new JLabel("CTR");
        ctr.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctr.setForeground(getSeondaryColor());
        ctr.setFont(fontOfText);


        ctrValues = new JLabel(toString(calculator.getCtr()));
        ctrValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctrValues.setFont(fontofValue);

        Box ctrBox = Box.createVerticalBox();
        ctrBox.add(ctrValues);
        ctrBox.add(ctr);
        ctrPanel.add(ctrBox);
        //end panel

        //start panel
        cpaPanel = new JPanel(new GridBagLayout());
        cpaPanel.setBorder(blackBorder);
        cpaPanel.setOpaque(false);

        cpa = new JLabel("CPA");
        cpa.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpa.setForeground(getSeondaryColor());
        cpa.setFont(fontOfText);


        cpaValues = new JLabel(toString(calculator.getCpa()));
        cpaValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpaValues.setFont(fontofValue);

        Box cpaBox = Box.createVerticalBox();
        cpaBox.add(cpaValues);
        cpaBox.add(cpa);
        cpaPanel.add(cpaBox);
        //end panel

        //start panel
        cpcPanel = new JPanel(new GridBagLayout());
        cpcPanel.setBorder(blackBorder);
        cpcPanel.setOpaque(false);

        cpc = new JLabel("CPC");
        cpc.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpc.setForeground(getSeondaryColor());
        cpc.setFont(fontOfText);


        cpcValues = new JLabel(toString(calculator.getCpc()));
        cpcValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpcValues.setFont(fontofValue);

        Box cpcBox = Box.createVerticalBox();
        cpcBox.add(cpcValues);
        cpcBox.add(cpc);
        cpcPanel.add(cpcBox);
        //end panel


        //start panel
        cpmPanel = new JPanel(new GridBagLayout());
        cpmPanel.setBorder(blackBorder);
        cpmPanel.setOpaque(false);

        cpm = new JLabel("CPM");
        cpm.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpm.setForeground(getSeondaryColor());
        cpm.setFont(fontOfText);


        cpmValues = new JLabel(toString(calculator.getCpm()));
        cpmValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpmValues.setFont(fontofValue);

        Box cpmBox = Box.createVerticalBox();
        cpmBox.add(cpmValues);
        cpmBox.add(cpm);
        cpmPanel.add(cpmBox);
        //end panel


        //start panel
        conversionsPanel = new JPanel(new GridBagLayout());
        conversionsPanel.setBorder(blackBorder);
        conversionsPanel.setOpaque(false);

        conversions = new JLabel("Conversions");
        conversions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversions.setForeground(getSeondaryColor());
        conversions.setFont(fontOfText);


        conversionsValues = new JLabel(toString(calculator.getConversionsNo()));
        conversionsValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversionsValues.setFont(fontofValue);

        Box conversionsBox = Box.createVerticalBox();
        conversionsBox.add(conversionsValues);
        conversionsBox.add(conversions);
        conversionsPanel.add(conversionsBox);
        //end panel


        //start panel
        totalCostPanel = new JPanel(new GridBagLayout());
        totalCostPanel.setBorder(blackBorder);
        totalCostPanel.setOpaque(false);

        totalCost = new JLabel("Total Cost");
        totalCost.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCost.setForeground(getSeondaryColor());
        totalCost.setFont(fontOfText);


        totalCostValues = new JLabel(toString(calculator.getTotalImpressionCost()));
        totalCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCostValues.setFont(fontofValue);

        Box totalCostBox = Box.createVerticalBox();
        totalCostBox.add(totalCostValues);
        totalCostBox.add(totalCost);
        totalCostPanel.add(totalCostBox);
        //end panel


        //start panel
        bouncePanel = new JPanel(new GridBagLayout());
        bouncePanel.setBorder(blackBorder);
        bouncePanel.setOpaque(false);

        bounce = new JLabel("Bounces");
        bounce.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounce.setForeground(getSeondaryColor());
        bounce.setFont(fontOfText);


        bounceValues = new JLabel(toString(calculator.getBouncesNo()));
        bounceValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceValues.setFont(fontofValue);

        Box bounceBox = Box.createVerticalBox();
        bounceBox.add(bounceValues);
        bounceBox.add(bounce);
        bouncePanel.add(bounceBox);
        //end panel


        //start panel
        bounceRatePanel = new JPanel(new GridBagLayout());
        bounceRatePanel.setBorder(blackBorder);
        bounceRatePanel.setOpaque(false);

        bounceRate = new JLabel("Bounce Rate");
        bounceRate.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRate.setForeground(getSeondaryColor());
        bounceRate.setFont(fontOfText);


        bounceRateValues = new JLabel(toString(calculator.getBr()));
        bounceRateValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRateValues.setFont(fontofValue);

        Box bounceRateBox = Box.createVerticalBox();
        bounceRateBox.add(bounceRateValues);
        bounceRateBox.add(bounceRate);
        bounceRatePanel.add(bounceRateBox);
        //end panel

        //start panel
        bounceTimePanel = new JPanel(new GridBagLayout());
        bounceTimePanel.setBorder(blackBorder);
        bounceTimePanel.setOpaque(false);

        bounceTime = new JLabel("Time");
        bounceTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTime.setFont(fontOfText);


        bounceTimeValues = new JLabel("Bounce Type:");
        bounceTimeValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTimeValues.setForeground(getSeondaryColor());
        bounceTimeValues.setFont(fontofValue);

        Box bounceTimeBox = Box.createVerticalBox();
        bounceTimeBox.add(bounceTimeValues);
        bounceTimeBox.add(bounceTime);
        bounceTimePanel.add(bounceTimeBox);
        //end panel


        insightsGrid.add(impressionsPanel);
        insightsGrid.add(clicksPanel);
        insightsGrid.add(uniquesPanel);
        insightsGrid.add(ctrPanel);
        insightsGrid.add(cpaPanel);
        insightsGrid.add(cpcPanel);
        insightsGrid.add(cpmPanel);
        insightsGrid.add(conversionsPanel);
        insightsGrid.add(totalCostPanel);
        insightsGrid.add(bouncePanel);
        insightsGrid.add(bounceRatePanel);
        insightsGrid.add(bounceTimePanel);



        menu.add(insightsGrid);
    }

    // converts a metric to a readable string
    public static String toString(float metric)
    {
        if (metric == (int) metric)
            return String.format("%d", (int) metric);
        else
            return String.format("%.4g%n", metric); // change the 4 to change the dp
    }

    public static void main(String[] args) {
        // reads the files and stores the logs - only create one campaign otherwise it will be slow
        Campaign campaign = new Campaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv"); // string inputs temporary

        // used to display metrics as values
        MetricCalculator calculator1 = campaign.newMetricCalculator();
        calculator1.calculateMetrics();

        prepareGui(calculator1);


        /**
         * notes:
         * interval, start and end dates are partially hardcoded - not much to really to really do till GUI
         * print functions are there to see what is going on easily
         * all the backend is in Models, all the GUI stuff is in View - MVC
         * file reading is only done once - unavoidably slow
         * master branch is using Date, this branch is using LocalDateTime - very important change
         * jfreechart needs to be installed to run - downloads in whatsapp
         *
         * to do:
         * find any possible performance improvements in the backend
         * create class diagrams for 1st deliverable
         * improve & update commenting
         * merge any GUI stuff
         * find out from Yvonne how large the actual data set is - response pending
         * change how the interval is handled - make subclasses of chart calculator for intervals
         *
         * for later:
         * filtering was removed due to my bad implementation - leave till 2nd deliverable
         * bounce factors are hardcoded - leave till 2nd & 3rd deliverables
         * will later add a class HistogramCalculator - leave till 2nd & 3rd deliverables
         **/
    }
}
