package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class AdAuctionGUI extends JFrame{
    private static JFrame gui;
    private static JLayeredPane menu,topMenu;
    private static JPanel verticalMenu,insightsGrid;

    private static JButton insightsButton,chartsButton,histogramsButton,compareButton,settingsButton;

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
        verticalMenu.setBackground(grey);


        //La butoanele astea pastreaza ce am scris eu aici gen design. Cand creezi gridul ala nou sa pastrezi astea 4 linii pt fiecare
        //buton ca sa arate la fel
        insightsButton = new JButton("Insights");
        insightsButton.setBackground(null);
        insightsButton.setFont(getMainFont());
        insightsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        
        insightsButton.setPreferredSize(new Dimension(40,40));
        insightsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,75,70,74)));

        chartsButton = new JButton("Charts");
        chartsButton.setBackground(null);
        chartsButton.setFont(getMainFont());
        chartsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        
        chartsButton.setPreferredSize(new Dimension(40,40));
        chartsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,80,70,82)));

        histogramsButton = new JButton("Histograms");
        histogramsButton.setBackground(null);
        histogramsButton.setFont(getMainFont());
        histogramsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        
        histogramsButton.setPreferredSize(new Dimension(40,40));
        histogramsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,70,70,54)));

        compareButton = new JButton("Compare");
        compareButton.setBackground(null);
        compareButton.setFont(getMainFont());
        compareButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        
        compareButton.setPreferredSize(new Dimension(40,40));
        compareButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.WHITE),BorderFactory.createEmptyBorder(54,75,70,68)));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(null);
        settingsButton.setFont(getMainFont());
        settingsButton.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        
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

    public static void createInsightsGrid(){


        Font fontOfText = new Font("Impact", Font.PLAIN, 25);
        Font fontofValue = new Font("Impact", Font.BOLD, 30);
        Border blackBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10), BorderFactory.createLineBorder(grey, 1));

        ArrayList<Integer> insightsArray = new ArrayList<Integer>();
        for(int i = 0; i < 12; i++){
            insightsArray.add((i+5) *100);
        }

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


        impressionsValue = new JLabel(String.valueOf(insightsArray.get(0)));
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


        clicksValue = new JLabel(String.valueOf(insightsArray.get(1)));
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


        uniquesValue = new JLabel(String.valueOf(insightsArray.get(2)));
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


        ctrValues = new JLabel(String.valueOf(insightsArray.get(3)));
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


        cpaValues = new JLabel(String.valueOf(insightsArray.get(4)));
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


        cpcValues = new JLabel(String.valueOf(insightsArray.get(5)));
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


        cpmValues = new JLabel(String.valueOf(insightsArray.get(6)));
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


        conversionsValues = new JLabel(String.valueOf(insightsArray.get(7)));
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


        totalCostValues = new JLabel(String.valueOf(insightsArray.get(8)));
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


        bounceValues = new JLabel(String.valueOf(insightsArray.get(9)));
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


        bounceRateValues = new JLabel(String.valueOf(insightsArray.get(10)));
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


    public static void main(String[] args) {
        prepareGui();
    }
}
