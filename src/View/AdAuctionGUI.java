package View;

import Models.Campaign;
import Models.ChartCalculator;
import Models.MetricCalculator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class AdAuctionGUI extends JFrame{
    private static JFrame gui;
    private static JLayeredPane menu,topMenu;
    private static JPanel verticalMenu,insightsGrid,chartsGrid;

    private static JButton insightsButton,chartsButton,histogramsButton,compareButton,settingsButton;
    private static JPanel insightsButtonPanel,chartsButtonPanel,histogramsButtonPanel,compareButtonPanel,settingsButtonPanel;


    private static JPanel impressionsPanel,clicksPanel,uniquesPanel,ctrPanel,cpmPanel,cpaPanel,cpcPanel,conversionsPanel,totalCostPanel,bouncePanel,bounceRatePanel,bounceTimePanel;
    private static JLabel impressions,clicks,uniques,ctr,cpm,cpa,cpc,conversions,totalCost,bounce,bounceRate,bounceTime;
    private static JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalCostValues,bounceValues, bounceRateValues, bounceTimeValues;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    private static JComboBox<String> metricsBox,genderBox,ageBox,contextBox,incomeBox,timeBox;
    private static String[] metricsChoices,genderChoices,ageChoices,contextChoices,incomeChoices,timeChoices;

    private static JSlider chartSlider;
    private static JPanel addToComparePanel;
    private static JButton addToCompareButton, createChartButton;
    private static JPanel chartSouthGrid;
    private static Chart chart ;


    public static ArrayList<String> arrayOfChoicesChart = new ArrayList<String>();
    public static ArrayList<String> arrayOfChoicesHistogram = new ArrayList<String>();


    //Color
    static Color orange= new Color(220,120,27);
    static Color blue= new Color(14,139,229);
    static Color grey = new Color(242,236,236);

    public static Color getPrimaryColor(){
        return blue;
    }
    public static Color getSecondaryColor(){
        return orange;
    }


    //Font
    static Font mainFont = new Font("Impact", Font.PLAIN, 15);

    public static Font getMainFont(){
        return mainFont;
    }


    public static void prepareGui(MetricCalculator calculator,Chart chart){
        gui = new JFrame("Ad Auction Monitor");
        gui.setVisible(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu(calculator,chart);
        gui.add(menu);

    }
    public static void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    public static void createMenu(MetricCalculator calculator, Chart chart){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);

        createTopMenu();
        createVerticalMenu();
        createInsightsGrid(calculator);
        createChartsGrid(chart.getChart());

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
        insightsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        chartsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        histogramsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        compareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
                chartsGrid.setVisible(false);

            }
        });

        chartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
                chartsGrid.setVisible(true);
            }
        });

        histogramsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
                chartsGrid.setVisible(false);
            }
        });

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
                chartsGrid.setVisible(false);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insightsGrid.setVisible(false);
                chartsGrid.setVisible(false);
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

        insightsGrid.setVisible(true);

        //start panel
        impressionsPanel = new JPanel(new GridBagLayout());
        impressionsPanel.setBorder(blackBorder);
        impressionsPanel.setOpaque(false);

        impressions = new JLabel("Impressions");
        impressions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressions.setForeground(getSecondaryColor());
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
        clicks.setForeground(getSecondaryColor());
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
        uniques.setForeground(getSecondaryColor());
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
        ctr.setForeground(getSecondaryColor());
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
        cpa.setForeground(getSecondaryColor());
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
        cpc.setForeground(getSecondaryColor());
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
        cpm.setForeground(getSecondaryColor());
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
        conversions.setForeground(getSecondaryColor());
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
        totalCost.setForeground(getSecondaryColor());
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
        bounce.setForeground(getSecondaryColor());
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
        bounceRate.setForeground(getSecondaryColor());
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
        bounceTime.setFont(fontofValue);


        bounceTimeValues = new JLabel("Bounce Type");
        bounceTimeValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTimeValues.setForeground(getSecondaryColor());
        bounceTimeValues.setFont(fontOfText);

        Box bounceTimeBox = Box.createVerticalBox();
        bounceTimeBox.add(bounceTime);
        bounceTimeBox.add(bounceTimeValues);

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


    public static void createChartNorthBox(){
        Font comboBoxFont = new Font(chartsButton.getFont().getName(), Font.PLAIN, 14);

        //start Box
        metricsChoices = new String[] {"Impressions","CPA","CPC","CPM","CTR","Uniques","Bounce","Bounce Rate","Clicks","Conversions","Total Cost"};
        metricsBox = new JComboBox<>(metricsChoices);
        metricsBox.setVisible(true);
        metricsBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        metricsBox.setFont(comboBoxFont);

        metricsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();
                arrayOfChoicesChart.set(0, itemName);
            }
        });

        JLabel metricsLabel = new JLabel("METRICS");
        metricsLabel.setFont(mainFont);
        metricsLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box metricsVerticalBox = Box.createVerticalBox();
        metricsVerticalBox.add(metricsLabel);
        metricsVerticalBox.add(metricsBox);

        //end box

        //start Box
        genderChoices = new String[] {"Any","Male","Female"};
        genderBox = new JComboBox<>(genderChoices);
        genderBox.setVisible(true);
        genderBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        genderBox.setFont(comboBoxFont);

        genderBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();

                arrayOfChoicesChart.set(1, itemName);

            }
        });

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(mainFont);
        genderLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box genderVerticalBox = Box.createVerticalBox();
        genderVerticalBox.add(genderLabel);
        genderVerticalBox.add(genderBox);
        //end box

        //start Box
        ageChoices = new String[] {"Any","<25","25-34","35-44","45-54",">54"};
        ageBox = new JComboBox<>(ageChoices);
        ageBox.setVisible(true);
        ageBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        ageBox.setFont(comboBoxFont);

        ageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();

                arrayOfChoicesChart.set(2, itemName);

            }
        });

        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(mainFont);
        ageLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box ageVerticalBox = Box.createVerticalBox();
        ageVerticalBox.add(ageLabel);
        ageVerticalBox.add(ageBox);

        //end box

        //start Box
        contextChoices = new String[] {"Any","Blog","Social Media","Shopping","News"};
        contextBox = new JComboBox<>(contextChoices);
        contextBox.setVisible(true);
        contextBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        contextBox.setFont(comboBoxFont);

        contextBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();

                arrayOfChoicesChart.set(3, itemName);

            }
        });

        JLabel contextLabel = new JLabel("CONTEXT");
        contextLabel.setFont(mainFont);
        contextLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box contextVerticalBox = Box.createVerticalBox();
        contextVerticalBox.add(contextLabel);
        contextVerticalBox.add(contextBox);


        //end box

        //start Box
        incomeChoices = new String[] {"Any","Low","Medium","High"};
        incomeBox = new JComboBox<>(incomeChoices);
        incomeBox.setVisible(true);
        incomeBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        incomeBox.setFont(comboBoxFont);

        incomeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();

                arrayOfChoicesChart.set(4, itemName);

            }
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(incomeBox);


        //end box

        //start Box
        timeChoices = new String[] {"Days","Weeks","Months"};
        timeBox = new JComboBox<>(timeChoices);
        timeBox.setVisible(true);
        timeBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        timeBox.setFont(comboBoxFont);

        timeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String itemName = (String)cb.getSelectedItem();

                arrayOfChoicesChart.set(5, itemName);

            }
        });

        JLabel timeLabel = new JLabel("TIME");
        timeLabel.setFont(mainFont);
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box timeVerticalBox = Box.createVerticalBox();
        timeVerticalBox.add(timeLabel);
        timeVerticalBox.add(timeBox);
        //end box


        createChartButton = new JButton("Create Chart");
        createChartButton.setFont(getMainFont());
        createChartButton.setBackground(blue);
        createChartButton.setForeground(Color.WHITE);
        createChartButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        createChartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createChartButton.setPreferredSize(new Dimension(200,40));
        createChartButton.setAlignmentY(CENTER_ALIGNMENT);

        createChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! (arrayOfChoicesChart.get(0).equals("Metrics"))){
                    for (String i: arrayOfChoicesChart
                    ) {
                        System.out.println(i);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Metrics choice can't be empty");
                    System.out.println(arrayOfChoicesChart.get(0));
                }
                System.out.println("---------------------------");
            }
        });

        Box chartNorthBox = Box.createHorizontalBox();
        chartNorthBox.setPreferredSize(new Dimension(chartsGrid.getWidth(),72));
        chartNorthBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        chartNorthBox.add(metricsVerticalBox);
        chartNorthBox.add(genderVerticalBox);
        chartNorthBox.add(ageVerticalBox);
        chartNorthBox.add(contextVerticalBox);
        chartNorthBox.add(incomeVerticalBox);
        chartNorthBox.add(timeVerticalBox);
        chartNorthBox.add(createChartButton);






        chartsGrid.add(chartNorthBox,BorderLayout.NORTH);
    }

    public static void createChartSouthGrid(){
        chartSlider = new JSlider(JSlider.HORIZONTAL,0,50,25);
        chartSlider.setVisible(true);
        chartSlider.setMajorTickSpacing(10);
        chartSlider.setMinorTickSpacing(5);
        chartSlider.setPaintTicks(true);
        chartSlider.setPaintLabels(true);
        chartSlider.setValue(13);

        Hashtable position = new Hashtable();
        position.put(0, new JLabel("0"));
        position.put(10, new JLabel("10"));
        position.put(20, new JLabel("20"));
        position.put(30, new JLabel("30"));
        position.put(40, new JLabel("40"));
        position.put(50, new JLabel("50"));

        chartSlider.setLabelTable(position);

        chartSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int sliderValue = chartSlider.getValue();
                arrayOfChoicesChart.set(6, String.valueOf(sliderValue));
            }
        });


        addToComparePanel = new JPanel(new GridBagLayout());
        addToComparePanel.setOpaque(false);
        addToComparePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        addToCompareButton = new JButton("Add to Compare");
        addToCompareButton.setFont(getMainFont());
        addToCompareButton.setBackground(blue);
        addToCompareButton.setForeground(Color.WHITE);
        addToCompareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addToCompareButton.setPreferredSize(new Dimension(400,40));

        addToComparePanel.add(addToCompareButton);


        chartSouthGrid = new JPanel(new GridLayout(1,2));
        chartSouthGrid.setPreferredSize(new Dimension(chartsGrid.getWidth(),200));
        chartSouthGrid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        chartSouthGrid.add(chartSlider);
        chartSouthGrid.add(addToComparePanel);

        chartsGrid.add(chartSouthGrid,BorderLayout.SOUTH);
    }

    public static void createChartsGrid(JFreeChart chart){

        arrayOfChoicesChart.add("Metrics");
        arrayOfChoicesChart.add("Any");
        arrayOfChoicesChart.add("Any");
        arrayOfChoicesChart.add("Any");
        arrayOfChoicesChart.add("Any");
        arrayOfChoicesChart.add("Days");
        arrayOfChoicesChart.add("25");




        chartsGrid = new JPanel(new BorderLayout());
        chartsGrid.setBounds(200,100,gui.getWidth()-210,gui.getHeight()-100);
        chartsGrid.setAlignmentY(100);
        chartsGrid.setBackground(insightsGrid.getBackground());
        chartsGrid.setVisible(false);

        createChartNorthBox();
        createChartSouthGrid();

        //create panel for chart
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel chartJPanel = new JPanel(new BorderLayout());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartJPanel.add(chartPanel,BorderLayout.CENTER);
        chartJPanel.validate();
        panel.add(chartJPanel);
        chartsGrid.add(panel,BorderLayout.CENTER);

        menu.add(chartsGrid);
    }

    public static ArrayList<String> getArrayOfChoicesChart() {
        if(! (arrayOfChoicesChart.get(0).equals("Metrics"))) {
            return arrayOfChoicesChart;
        } else {
            return null;
        }
    }

    public static ArrayList<String> getArrayOfChoicesHistogram(){
        if(! (arrayOfChoicesHistogram.get(0).equals("Metrics"))) {
            return arrayOfChoicesHistogram;
        } else {
            return null;
        }

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

        // used to display chart
        ChartCalculator calculator2 = campaign.newChartCalculator();
        calculator2.calculateCharts("days", calculator2.getImpressionLog().getFirstDate(), calculator2.getImpressionLog().getLastDate());

        Chart chart = new Chart( "Metrics vs Time" , "Metrics vs Time","impressions", calculator2);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                prepareGui(calculator1,chart);
            }
        });


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
