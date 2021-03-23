package View;

import Controllers.ChartController;
import Controllers.MetricController;
import Models.*;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class AdAuctionGUI extends JFrame{
    private JFrame gui;
    private JLayeredPane menu;
    private JPanel insightsGrid;

    // components for metrics
    private JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalCostValues, bounceValues, bounceRateValues;
    private Box impressionsBox, clicksBox, uniquesBox, ctrBox, cpaBox, cpcBox, cpmBox, conversionsBox, totalCostBox, bouncesBox, bounceRateBox;

    // components for charts
    private JPanel chartJPanel;
    private ChartPanel chartPanel;
    private JButton chartsButton;
    private JSlider chartSlider;
    private JPanel chartsGrid;

    private final ArrayList<String> arrayOfChoicesChart = new ArrayList<>();

    private final ChartController chartController;
    private final MetricController metricController;

    private final Color primaryColor = new Color(14,139,229);
    private final Color secondaryColor = new Color(220,120,27);
    private final Color tertiaryColor = new Color(242,236,236);

    private final Font mainFont = new Font("Impact", Font.PLAIN, 15);
    private final Font fontOfText = new Font("Impact", Font.PLAIN, 25);
    private final Font fontOfValue = new Font("Impact", Font.BOLD, 30);

    // initialises the display controllers
    public AdAuctionGUI() {
        this.chartController = new ChartController();
        this.metricController = new MetricController();

        chartPanel = new ChartPanel(new Chart("Blank Chart", "Impressions", "Days").getChart());
    }

    // displays the main window
    public void prepareGui() {
        gui = new JFrame("Ad Auction Monitor");
        gui.setVisible(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenu();
        gui.add(menu);
    }

    // displays the main structure
    public void createMenu(){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);

        createTopMenu();
        createVerticalMenu();
        createInsightsGrid();
        createChartsGrid();
    }

    // displays the constant vertical menu on the lift hand size
    public void createVerticalMenu(){
        JPanel verticalMenu = new JPanel(new GridLayout(5, 1));
        verticalMenu.setBounds(0,100,200,gui.getHeight()-100);
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(tertiaryColor);

        //start panel
        JPanel insightsButtonPanel = new JPanel(new BorderLayout());
        insightsButtonPanel.setOpaque(false);
        insightsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        JButton insightsButton = new JButton("Insights");
        insightsButton.setFont(mainFont);
        insightsButton.setBorderPainted(false);
        insightsButton.setContentAreaFilled(false);
        insightsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        insightsButtonPanel.add(insightsButton);
        //end panel

        //start panel
        JPanel chartsButtonPanel = new JPanel(new BorderLayout());
        chartsButtonPanel.setOpaque(false);
        chartsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        chartsButton = new JButton("Charts");
        chartsButton.setBorderPainted(false);
        chartsButton.setContentAreaFilled(false);
        chartsButton.setFont(mainFont);
        chartsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        chartsButtonPanel.add(chartsButton);
        //end panel

        //start panel
        JPanel histogramsButtonPanel = new JPanel(new BorderLayout());
        histogramsButtonPanel.setOpaque(false);
        histogramsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        JButton histogramsButton = new JButton("Histograms");
        histogramsButton.setBorderPainted(false);
        histogramsButton.setContentAreaFilled(false);
        histogramsButton.setFont(mainFont);
        histogramsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        histogramsButtonPanel.add(histogramsButton);
        //end panel

        //start panel
        JPanel compareButtonPanel = new JPanel(new BorderLayout());
        compareButtonPanel.setOpaque(false);
        compareButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        JButton compareButton = new JButton("Compare");
        compareButton.setBorderPainted(false);
        compareButton.setContentAreaFilled(false);
        compareButton.setFont(mainFont);
        compareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        compareButtonPanel.add(compareButton);
        //end panel

        //start panel
        JPanel settingsButtonPanel = new JPanel(new BorderLayout());
        settingsButtonPanel.setOpaque(false);
        settingsButtonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFont(mainFont);
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        settingsButtonPanel.add(settingsButton);
        //end panel

        verticalMenu.add(insightsButtonPanel);
        verticalMenu.add(chartsButtonPanel);
        verticalMenu.add(histogramsButtonPanel);
        verticalMenu.add(compareButtonPanel);
        verticalMenu.add(settingsButtonPanel);

        menu.add(verticalMenu, BorderLayout.WEST,0);

        insightsButton.addActionListener(e -> {
            insightsGrid.setVisible(true);
            chartsGrid.setVisible(false);
        });

        chartsButton.addActionListener(e -> {
            insightsGrid.setVisible(false);
            chartsGrid.setVisible(true);
        });

        histogramsButton.addActionListener(e -> {
            insightsGrid.setVisible(false);
            chartsGrid.setVisible(false);
        });

        compareButton.addActionListener(e -> {
            insightsGrid.setVisible(false);
            chartsGrid.setVisible(false);
        });

        settingsButton.addActionListener(e -> {
            insightsGrid.setVisible(false);
            chartsGrid.setVisible(false);
        });
    }

    // displays the constant horizontal menu at the top
    public void createTopMenu(){
        JLayeredPane topMenu = new JLayeredPane();
        topMenu.setSize(gui.getWidth(),100);
        topMenu.setOpaque(true);
        topMenu.setBackground(new Color(14,139,229));

        JLabel productName = new JLabel("Ad Monitor");
        productName.setBackground(primaryColor);
        productName.setSize(100,100);
        productName.setAlignmentX(20);
        productName.setBounds(20,0,100,100);
        productName.setForeground(Color.WHITE);
        productName.setFont(mainFont);

        JLabel customerName = new JLabel("Customer Name");
        productName.setSize(100,100);
        customerName.setBounds(gui.getWidth()-120,0,100,100);
        customerName.setForeground(Color.WHITE);

        topMenu.add(productName,BorderLayout.WEST,0);
        topMenu.add(customerName,BorderLayout.EAST,1);
        menu.add(topMenu,BorderLayout.NORTH,1);
    }

    // displays the metrics page
    public void createInsightsGrid(){
        Border blackBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10,10,10,10), BorderFactory.createLineBorder(tertiaryColor, 1));

        insightsGrid = new JPanel(new GridLayout(4,3));
        insightsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        insightsGrid.setAlignmentY(100);
        insightsGrid.setOpaque(true);
        insightsGrid.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        insightsGrid.setVisible(true);

        //start panel
        JPanel impressionsPanel = new JPanel(new GridBagLayout());
        impressionsPanel.setBorder(blackBorder);
        impressionsPanel.setOpaque(false);

        JLabel impressions = new JLabel("Impressions");
        impressions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressions.setForeground(secondaryColor);
        impressions.setFont(fontOfText);

        impressionsValue = new JLabel("0");
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(fontOfValue);

        impressionsBox = Box.createVerticalBox();
        impressionsBox.add(impressionsValue);
        impressionsBox.add(impressions);
        impressionsPanel.add(impressionsBox);
        //end panel

        //start panel
        JPanel clicksPanel = new JPanel(new GridBagLayout());
        clicksPanel.setBorder(blackBorder);
        clicksPanel.setOpaque(false);

        JLabel clicks = new JLabel("Clicks");
        clicks.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicks.setForeground(secondaryColor);
        clicks.setFont(fontOfText);

        clicksValue = new JLabel("0");
        clicksValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicksValue.setFont(fontOfValue);

        clicksBox = Box.createVerticalBox();
        clicksBox.add(clicksValue);
        clicksBox.add(clicks);
        clicksPanel.add(clicksBox);
        //end panel

        //start panel
        JPanel uniquesPanel = new JPanel(new GridBagLayout());
        uniquesPanel.setBorder(blackBorder);
        uniquesPanel.setOpaque(false);

        JLabel uniques = new JLabel("Uniques");
        uniques.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniques.setForeground(secondaryColor);
        uniques.setFont(fontOfText);

        uniquesValue = new JLabel("0");
        uniquesValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniquesValue.setFont(fontOfValue);

        uniquesBox = Box.createVerticalBox();
        uniquesBox.add(uniquesValue);
        uniquesBox.add(uniques);
        uniquesPanel.add(uniquesBox);
        //end panel

        //start panel
        JPanel ctrPanel = new JPanel(new GridBagLayout());
        ctrPanel.setBorder(blackBorder);
        ctrPanel.setOpaque(false);

        JLabel ctr = new JLabel("CTR");
        ctr.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctr.setForeground(secondaryColor);
        ctr.setFont(fontOfText);

        ctrValues = new JLabel("0");
        ctrValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctrValues.setFont(fontOfValue);

        ctrBox = Box.createVerticalBox();
        ctrBox.add(ctrValues);
        ctrBox.add(ctr);
        ctrPanel.add(ctrBox);
        //end panel

        //start panel
        JPanel cpaPanel = new JPanel(new GridBagLayout());
        cpaPanel.setBorder(blackBorder);
        cpaPanel.setOpaque(false);

        JLabel cpa = new JLabel("CPA");
        cpa.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpa.setForeground(secondaryColor);
        cpa.setFont(fontOfText);

        cpaValues = new JLabel("0");
        cpaValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpaValues.setFont(fontOfValue);

        cpaBox = Box.createVerticalBox();
        cpaBox.add(cpaValues);
        cpaBox.add(cpa);
        cpaPanel.add(cpaBox);
        //end panel

        //start panel
        JPanel cpcPanel = new JPanel(new GridBagLayout());
        cpcPanel.setBorder(blackBorder);
        cpcPanel.setOpaque(false);

        JLabel cpc = new JLabel("CPC");
        cpc.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpc.setForeground(secondaryColor);
        cpc.setFont(fontOfText);

        cpcValues = new JLabel("0");
        cpcValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpcValues.setFont(fontOfValue);

        cpcBox = Box.createVerticalBox();
        cpcBox.add(cpcValues);
        cpcBox.add(cpc);
        cpcPanel.add(cpcBox);
        //end panel

        //start panel
        JPanel cpmPanel = new JPanel(new GridBagLayout());
        cpmPanel.setBorder(blackBorder);
        cpmPanel.setOpaque(false);

        JLabel cpm = new JLabel("CPM");
        cpm.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpm.setForeground(secondaryColor);
        cpm.setFont(fontOfText);

        cpmValues = new JLabel("0");
        cpmValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpmValues.setFont(fontOfValue);

        cpmBox = Box.createVerticalBox();
        cpmBox.add(cpmValues);
        cpmBox.add(cpm);
        cpmPanel.add(cpmBox);
        //end panel

        //start panel
        JPanel conversionsPanel = new JPanel(new GridBagLayout());
        conversionsPanel.setBorder(blackBorder);
        conversionsPanel.setOpaque(false);

        JLabel conversions = new JLabel("Conversions");
        conversions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversions.setForeground(secondaryColor);
        conversions.setFont(fontOfText);

        conversionsValues = new JLabel("0");
        conversionsValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversionsValues.setFont(fontOfValue);

        conversionsBox = Box.createVerticalBox();
        conversionsBox.add(conversionsValues);
        conversionsBox.add(conversions);
        conversionsPanel.add(conversionsBox);
        //end panel

        //start panel
        JPanel totalCostPanel = new JPanel(new GridBagLayout());
        totalCostPanel.setBorder(blackBorder);
        totalCostPanel.setOpaque(false);

        JLabel totalCost = new JLabel("Total Cost");
        totalCost.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCost.setForeground(secondaryColor);
        totalCost.setFont(fontOfText);

        totalCostValues = new JLabel("0");
        totalCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCostValues.setFont(fontOfValue);

        totalCostBox = Box.createVerticalBox();
        totalCostBox.add(totalCostValues);
        totalCostBox.add(totalCost);
        totalCostPanel.add(totalCostBox);
        //end panel

        //start panel
        JPanel bouncesPanel = new JPanel(new GridBagLayout());
        bouncesPanel.setBorder(blackBorder);
        bouncesPanel.setOpaque(false);

        JLabel bounces = new JLabel("Bounces");
        bounces.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounces.setForeground(secondaryColor);
        bounces.setFont(fontOfText);

        bounceValues = new JLabel("0");
        bounceValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceValues.setFont(fontOfValue);

        bouncesBox = Box.createVerticalBox();
        bouncesBox.add(bounceValues);
        bouncesBox.add(bounces);
        bouncesPanel.add(bouncesBox);
        //end panel

        //start panel
        JPanel bounceRatePanel = new JPanel(new GridBagLayout());
        bounceRatePanel.setBorder(blackBorder);
        bounceRatePanel.setOpaque(false);

        JLabel bounceRate = new JLabel("Bounce Rate");
        bounceRate.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRate.setForeground(secondaryColor);
        bounceRate.setFont(fontOfText);

        bounceRateValues = new JLabel("0");
        bounceRateValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRateValues.setFont(fontOfValue);

        bounceRateBox = Box.createVerticalBox();
        bounceRateBox.add(bounceRateValues);
        bounceRateBox.add(bounceRate);
        bounceRatePanel.add(bounceRateBox);
        //end panel

        //start panel
        JPanel bounceTimePanel = new JPanel(new GridBagLayout());
        bounceTimePanel.setBorder(blackBorder);
        bounceTimePanel.setOpaque(false);

        JLabel bounceTime = new JLabel("Time");
        bounceTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTime.setFont(fontOfValue);

        JLabel bouncesTimeValues = new JLabel("Bounce Type");
        bouncesTimeValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bouncesTimeValues.setForeground(secondaryColor);
        bouncesTimeValues.setFont(fontOfText);

        Box bounceTimeBox = Box.createVerticalBox();
        bounceTimeBox.add(bounceTime);
        bounceTimeBox.add(bouncesTimeValues);

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
        insightsGrid.add(bouncesPanel);
        insightsGrid.add(bounceRatePanel);
        insightsGrid.add(bounceTimePanel);

        menu.add(insightsGrid);
    }

    // displays the chart filters
    public void createChartNorthBox(){
        Font comboBoxFont = new Font(chartsButton.getFont().getName(), Font.PLAIN, 14);

        //start Box
        String[] metricsChoices = new String[]{"Impressions", "CPA", "CPC", "CPM", "CTR", "Uniques", "Bounce", "Bounce Rate", "Clicks", "Conversions", "Total Cost"};
        JComboBox<String> metricsBox = new JComboBox<>(metricsChoices);
        metricsBox.setVisible(true);
        metricsBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        metricsBox.setFont(comboBoxFont);

        metricsBox.addActionListener(e -> {
            String itemName = String.valueOf(metricsBox.getSelectedItem());
            arrayOfChoicesChart.set(0, itemName);
            recalculateCharts();
        });

        JLabel metricsLabel = new JLabel("METRICS");
        metricsLabel.setFont(mainFont);
        metricsLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box metricsVerticalBox = Box.createVerticalBox();
        metricsVerticalBox.add(metricsLabel);
        metricsVerticalBox.add(metricsBox);
        //end box

        //start Box
        String[] genderChoices = new String[]{"Any", "Male", "Female"};
        JComboBox<String> genderBox = new JComboBox<>(genderChoices);
        genderBox.setVisible(true);
        genderBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        genderBox.setFont(comboBoxFont);

        genderBox.addActionListener(e -> {
            String itemName = String.valueOf(genderBox.getSelectedItem());
            arrayOfChoicesChart.set(1, itemName);
            recalculateCharts();
        });

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(mainFont);
        genderLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box genderVerticalBox = Box.createVerticalBox();
        genderVerticalBox.add(genderLabel);
        genderVerticalBox.add(genderBox);
        //end box

        //start Box
        String[] ageChoices = new String[]{"Any", "<25", "25-34", "35-44", "45-54", ">54"};
        JComboBox<String> ageBox = new JComboBox<>(ageChoices);
        ageBox.setVisible(true);
        ageBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        ageBox.setFont(comboBoxFont);

        ageBox.addActionListener(e -> {
            String itemName = String.valueOf(ageBox.getSelectedItem());
            arrayOfChoicesChart.set(2, itemName);
            recalculateCharts();
        });

        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(mainFont);
        ageLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box ageVerticalBox = Box.createVerticalBox();
        ageVerticalBox.add(ageLabel);
        ageVerticalBox.add(ageBox);
        //end box

        //start Box
        String[] contextChoices = new String[]{"Any", "Blog", "Social Media", "Shopping", "News"};
        JComboBox<String> contextBox = new JComboBox<>(contextChoices);
        contextBox.setVisible(true);
        contextBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        contextBox.setFont(comboBoxFont);

        contextBox.addActionListener(e -> {
            String itemName = String.valueOf(contextBox.getSelectedItem());
            arrayOfChoicesChart.set(3, itemName);
            recalculateCharts();
        });

        JLabel contextLabel = new JLabel("CONTEXT");
        contextLabel.setFont(mainFont);
        contextLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box contextVerticalBox = Box.createVerticalBox();
        contextVerticalBox.add(contextLabel);
        contextVerticalBox.add(contextBox);
        //end box

        //start Box
        String[] incomeChoices = new String[]{"Any", "Low", "Medium", "High"};
        JComboBox<String> incomeBox = new JComboBox<>(incomeChoices);
        incomeBox.setVisible(true);
        incomeBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        incomeBox.setFont(comboBoxFont);

        incomeBox.addActionListener(e -> {
            String itemName = String.valueOf(incomeBox.getSelectedItem());
            arrayOfChoicesChart.set(4, itemName);
            recalculateCharts();
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(incomeBox);
        //end box

        Box chartNorthBox = Box.createHorizontalBox();
        chartNorthBox.setPreferredSize(new Dimension(chartsGrid.getWidth(),72));
        chartNorthBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        chartNorthBox.add(metricsVerticalBox);
        chartNorthBox.add(genderVerticalBox);
        chartNorthBox.add(ageVerticalBox);
        chartNorthBox.add(contextVerticalBox);
        chartNorthBox.add(incomeVerticalBox);

        chartsGrid.add(chartNorthBox,BorderLayout.NORTH);
    }

    // displays the chart slider (time granularity)
    public void createChartSouthGrid(){
        chartSlider = new JSlider(JSlider.HORIZONTAL,0,4,1);
        chartSlider.setVisible(true);
        chartSlider.setMajorTickSpacing(1);
        chartSlider.setPaintTicks(true);
        chartSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> position = new Hashtable<>();
        position.put(0, new JLabel("Hours"));
        position.put(1, new JLabel("Days"));
        position.put(2, new JLabel("Weeks"));
        position.put(3, new JLabel("Months"));
        position.put(4, new JLabel("Years"));

        chartSlider.setLabelTable(position);

        chartSlider.addChangeListener(e -> {
            String sliderValue = position.get(chartSlider.getValue()).getText();

            if (!arrayOfChoicesChart.get(7).equals(sliderValue)) {
                arrayOfChoicesChart.set(7, sliderValue);
                updateCharts();
            }
        });

        JPanel addToComparePanel = new JPanel(new GridBagLayout());
        addToComparePanel.setOpaque(false);
        addToComparePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton addToCompareButton = new JButton("Add to Compare");
        addToCompareButton.setFont(mainFont);
        addToCompareButton.setBackground(primaryColor);
        addToCompareButton.setForeground(Color.WHITE);
        addToCompareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addToCompareButton.setPreferredSize(new Dimension(400,40));

        addToComparePanel.add(addToCompareButton);

        JPanel chartSouthGrid = new JPanel(new GridLayout(1, 2));
        chartSouthGrid.setPreferredSize(new Dimension(chartsGrid.getWidth(),200));
        chartSouthGrid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        chartSouthGrid.add(chartSlider);
        chartSouthGrid.add(addToComparePanel);

        chartsGrid.add(chartSouthGrid,BorderLayout.SOUTH);
    }

    // displays the chart
    public void createChartsGrid(){
        arrayOfChoicesChart.add("Impressions"); // metrics
        arrayOfChoicesChart.add("Any"); // gender
        arrayOfChoicesChart.add("Any"); // age
        arrayOfChoicesChart.add("Any"); // context
        arrayOfChoicesChart.add("Any"); // income
        arrayOfChoicesChart.add("Any"); // start date
        arrayOfChoicesChart.add("Any"); // end date
        arrayOfChoicesChart.add("Days"); // granularity

        chartsGrid = new JPanel(new BorderLayout());
        chartsGrid.setBounds(200,100,gui.getWidth()-210,gui.getHeight()-100);
        chartsGrid.setAlignmentY(100);
        chartsGrid.setBackground(insightsGrid.getBackground());
        chartsGrid.setVisible(false);

        createChartNorthBox();
        createChartSouthGrid();

        JPanel panel = new JPanel(new GridBagLayout());
        chartJPanel = new JPanel(new BorderLayout());

        chartJPanel.add(chartPanel, BorderLayout.CENTER);
        chartJPanel.validate();
        panel.add(chartJPanel);

        chartsGrid.add(panel, BorderLayout.CENTER);

        menu.add(chartsGrid);
    }

    // converts a metric to a readable string
    public String toString(float metric)
    {
        if (metric == (int) metric)
            return String.format("%d", (int) metric);
        else
            return String.format("%.4g%n", metric); // change the 4 to change the dp
    }

    // displays the metrics when loaded
    public void createMetrics(MetricCalculator metricCalculator) {
        metricController.createMetrics(metricCalculator);
        updateMetrics();
    }

    // displays the updated metrics
    public void updateMetrics() {
        impressionsValue = new JLabel(String.valueOf(metricController.getImpressionsNo()));
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(fontOfValue);
        impressionsBox.remove(0);
        impressionsBox.add(impressionsValue, 0);
        impressionsBox.revalidate();

        clicksValue = new JLabel(toString(metricController.getClicksNo()));
        clicksValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicksValue.setFont(fontOfValue);
        clicksBox.remove(0);
        clicksBox.add(clicksValue, 0);
        clicksBox.revalidate();

        uniquesValue = new JLabel(toString(metricController.getUniquesNo()));
        uniquesValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniquesValue.setFont(fontOfValue);
        uniquesBox.remove(0);
        uniquesBox.add(uniquesValue, 0);
        uniquesBox.revalidate();

        ctrValues = new JLabel(toString(metricController.getCtr()));
        ctrValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctrValues.setFont(fontOfValue);
        ctrBox.remove(0);
        ctrBox.add(ctrValues, 0);
        ctrBox.revalidate();

        cpaValues = new JLabel(toString(metricController.getCpa()));
        cpaValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpaValues.setFont(fontOfValue);
        cpaBox.remove(0);
        cpaBox.add(cpaValues, 0);
        cpaBox.revalidate();

        cpcValues = new JLabel(toString(metricController.getCpc()));
        cpcValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpcValues.setFont(fontOfValue);
        cpcBox.remove(0);
        cpcBox.add(cpcValues, 0);
        cpcBox.revalidate();

        cpmValues = new JLabel(toString(metricController.getCpm()));
        cpmValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpmValues.setFont(fontOfValue);
        cpmBox.remove(0);
        cpmBox.add(cpmValues, 0);
        cpmBox.revalidate();

        conversionsValues = new JLabel(toString(metricController.getConversionsNo()));
        conversionsValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversionsValues.setFont(fontOfValue);
        conversionsBox.remove(0);
        conversionsBox.add(conversionsValues, 0);
        conversionsBox.revalidate();

        totalCostValues = new JLabel(toString(metricController.getTotalImpressionCost()));
        totalCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCostValues.setFont(fontOfValue);
        totalCostBox.remove(0);
        totalCostBox.add(totalCostValues, 0);
        totalCostBox.revalidate();

        bounceValues = new JLabel(toString(metricController.getBouncesNo()));
        bounceValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceValues.setFont(fontOfValue);
        bouncesBox.remove(0);
        bouncesBox.add(bounceValues, 0);
        bouncesBox.revalidate();

        bounceRateValues = new JLabel(toString(metricController.getBr()));
        bounceRateValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRateValues.setFont(fontOfValue);
        bounceRateBox.remove(0);
        bounceRateBox.add(bounceRateValues, 0);
        bounceRateBox.revalidate();
    }

    // displays the initial chart when loaded
    public void createCharts(ChartCalculator calculator) {
        chartController.createCharts(calculator);
        updateCharts();
    }

    // calls the controller to recalculate the charts
    public void recalculateCharts() {
        chartController.updateCharts(arrayOfChoicesChart.get(0), // metric
                arrayOfChoicesChart.get(1), // gender
                arrayOfChoicesChart.get(2), // age
                arrayOfChoicesChart.get(3), // context
                arrayOfChoicesChart.get(4), // income
                arrayOfChoicesChart.get(5), // start date
                arrayOfChoicesChart.get(6)); // start date
        updateCharts();
    }

    // displays the updated charts
    public void updateCharts() {
        switch (arrayOfChoicesChart.get(7)) {
            case "Hours": {
                chartPanel = new ChartPanel(chartController.getHoursChart());
                break;
            }
            case "Days": {
                chartPanel = new ChartPanel(chartController.getDaysChart());
                break;
            }
            case "Weeks": {
                chartPanel = new ChartPanel(chartController.getWeeksChart());
                break;
            }
            case "Months": {
                chartPanel = new ChartPanel(chartController.getMonthsChart());
                break;
            }
            case "Years": {
                chartPanel = new ChartPanel(chartController.getYearsChart());
                break;
            }
        }
        chartJPanel.remove(0);
        chartJPanel.add(chartPanel, BorderLayout.CENTER);
        chartJPanel.revalidate();
    }
}