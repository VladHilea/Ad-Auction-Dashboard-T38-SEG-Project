package View;

import Models.MetricCalculator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class AdAuctionGUI extends JFrame{
    private JFrame gui;
    private JLayeredPane menu;
    private JPanel insightsGrid;
    private JPanel chartsGrid;
    private JButton chartsButton;
    private JSlider chartSlider;

    private final ArrayList<String> arrayOfChoicesChart = new ArrayList<>();

    private final MetricCalculator metricCalculator;
    private final Chart chart;

    private final Color primaryColor = new Color(14,139,229);
    private final Color secondaryColor = new Color(220,120,27);
    private final Color tertiaryColor = new Color(242,236,236);

    private final Font mainFont = new Font("Impact", Font.PLAIN, 15);

    public AdAuctionGUI(MetricCalculator metricCalculator, Chart chart) {
        this.metricCalculator = metricCalculator;
        this.chart = chart;
    }

    public void prepareGui() {
        gui = new JFrame("Ad Auction Monitor");
        gui.setVisible(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu();
        gui.add(menu);
    }

    public void createMenu(){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(Color.WHITE);

        createTopMenu();
        createVerticalMenu();
        createInsightsGrid();
        createChartsGrid(chart.getDaysChart("impressions over time", "impressions"));
    }

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

    public void createInsightsGrid(){
        Font fontOfText = new Font("Impact", Font.PLAIN, 25);
        Font fontOfValue = new Font("Impact", Font.BOLD, 30);
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

        JLabel impressionsValue = new JLabel(toString(metricCalculator.getImpressionsNo()));
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(fontOfValue);

        Box insightsVBox = Box.createVerticalBox();
        insightsVBox.add(impressionsValue);
        insightsVBox.add(impressions);
        impressionsPanel.add(insightsVBox);
        //end panel

        //start panel
        JPanel clicksPanel = new JPanel(new GridBagLayout());
        clicksPanel.setBorder(blackBorder);
        clicksPanel.setOpaque(false);

        JLabel clicks = new JLabel("Clicks");
        clicks.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicks.setForeground(secondaryColor);
        clicks.setFont(fontOfText);

        JLabel clicksValue = new JLabel(toString(metricCalculator.getClicksNo()));
        clicksValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicksValue.setFont(fontOfValue);

        Box clicksVbox = Box.createVerticalBox();
        clicksVbox.add(clicksValue);
        clicksVbox.add(clicks);
        clicksPanel.add(clicksVbox);
        //end panel

        //start panel
        JPanel uniquesPanel = new JPanel(new GridBagLayout());
        uniquesPanel.setBorder(blackBorder);
        uniquesPanel.setOpaque(false);

        JLabel uniques = new JLabel("Uniques");
        uniques.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniques.setForeground(secondaryColor);
        uniques.setFont(fontOfText);

        JLabel uniquesValue = new JLabel(toString(metricCalculator.getUniquesNo()));
        uniquesValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniquesValue.setFont(fontOfValue);

        Box uniquesBox = Box.createVerticalBox();
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

        JLabel ctrValues = new JLabel(toString(metricCalculator.getCtr()));
        ctrValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctrValues.setFont(fontOfValue);

        Box ctrBox = Box.createVerticalBox();
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

        JLabel cpaValues = new JLabel(toString(metricCalculator.getCpa()));
        cpaValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpaValues.setFont(fontOfValue);

        Box cpaBox = Box.createVerticalBox();
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

        JLabel cpcValues = new JLabel(toString(metricCalculator.getCpc()));
        cpcValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpcValues.setFont(fontOfValue);

        Box cpcBox = Box.createVerticalBox();
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

        JLabel cpmValues = new JLabel(toString(metricCalculator.getCpm()));
        cpmValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpmValues.setFont(fontOfValue);

        Box cpmBox = Box.createVerticalBox();
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

        JLabel conversionsValues = new JLabel(toString(metricCalculator.getConversionsNo()));
        conversionsValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversionsValues.setFont(fontOfValue);

        Box conversionsBox = Box.createVerticalBox();
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

        JLabel totalCostValues = new JLabel(toString(metricCalculator.getTotalImpressionCost()));
        totalCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCostValues.setFont(fontOfValue);

        Box totalCostBox = Box.createVerticalBox();
        totalCostBox.add(totalCostValues);
        totalCostBox.add(totalCost);
        totalCostPanel.add(totalCostBox);
        //end panel

        //start panel
        JPanel bouncePanel = new JPanel(new GridBagLayout());
        bouncePanel.setBorder(blackBorder);
        bouncePanel.setOpaque(false);

        JLabel bounce = new JLabel("Bounces");
        bounce.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounce.setForeground(secondaryColor);
        bounce.setFont(fontOfText);

        JLabel bounceValues = new JLabel(toString(metricCalculator.getBouncesNo()));
        bounceValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceValues.setFont(fontOfValue);

        Box bounceBox = Box.createVerticalBox();
        bounceBox.add(bounceValues);
        bounceBox.add(bounce);
        bouncePanel.add(bounceBox);
        //end panel

        //start panel
        JPanel bounceRatePanel = new JPanel(new GridBagLayout());
        bounceRatePanel.setBorder(blackBorder);
        bounceRatePanel.setOpaque(false);

        JLabel bounceRate = new JLabel("Bounce Rate");
        bounceRate.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRate.setForeground(secondaryColor);
        bounceRate.setFont(fontOfText);

        JLabel bounceRateValues = new JLabel(toString(metricCalculator.getBr()));
        bounceRateValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRateValues.setFont(fontOfValue);

        Box bounceRateBox = Box.createVerticalBox();
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

        JLabel bounceTimeValues = new JLabel("Bounce Type");
        bounceTimeValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTimeValues.setForeground(secondaryColor);
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

    public void createChartNorthBox(){
        Font comboBoxFont = new Font(chartsButton.getFont().getName(), Font.PLAIN, 14);

        //start Box
        String[] metricsChoices = new String[]{"Impressions", "CPA", "CPC", "CPM", "CTR", "Uniques", "Bounce", "Bounce Rate", "Clicks", "Conversions", "Total Cost"};
        JComboBox<String> metricsBox = new JComboBox<>(metricsChoices);
        metricsBox.setVisible(true);
        metricsBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        metricsBox.setFont(comboBoxFont);

        metricsBox.addActionListener(e -> {
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(0, itemName);
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
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(1, itemName);
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
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(2, itemName);
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
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(3, itemName);
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
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(4, itemName);
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(incomeBox);
        //end box

        //start Box
        String[] timeChoices = new String[]{"Days", "Weeks", "Months"};
        JComboBox<String> timeBox = new JComboBox<>(timeChoices);
        timeBox.setVisible(true);
        timeBox.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        timeBox.setFont(comboBoxFont);

        timeBox.addActionListener(e -> {
            JComboBox<String> cb = new JComboBox<>((String[]) e.getSource());
            String itemName = (String) cb.getSelectedItem();
            arrayOfChoicesChart.set(5, itemName);
        });

        JLabel timeLabel = new JLabel("TIME");
        timeLabel.setFont(mainFont);
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box timeVerticalBox = Box.createVerticalBox();
        timeVerticalBox.add(timeLabel);
        timeVerticalBox.add(timeBox);
        //end box

        JButton createChartButton = new JButton("Create Chart");
        createChartButton.setFont(mainFont);
        createChartButton.setBackground(primaryColor);
        createChartButton.setForeground(Color.WHITE);
        createChartButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        createChartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createChartButton.setPreferredSize(new Dimension(200,40));
        createChartButton.setAlignmentY(CENTER_ALIGNMENT);

        createChartButton.addActionListener(e -> {
            if(! (arrayOfChoicesChart.get(0).equals("Metrics"))){
                JOptionPane.showMessageDialog(null, "No Filter ability has been implemented yet");
                for (String i: arrayOfChoicesChart
                ) {
                    System.out.println(i);
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Metrics choice can't be empty");
                JOptionPane.showMessageDialog(null, "No Filter ability has been implemented yet");
                System.out.println(arrayOfChoicesChart.get(0));
            }
            System.out.println("---------------------------");
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
            int sliderValue = chartSlider.getValue();
            arrayOfChoicesChart.set(1, String.valueOf(sliderValue));

            recalculateChart(position.get(sliderValue).getText(), "impressions");
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

    public void createChartsGrid(JFreeChart chart){
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

    // recalculates metrics on main page, no time range
    public void recalculateMetrics() {
        metricCalculator.calculateMetrics();
    }

    // recalculates metrics on main page, with time range
    public void recalculateMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        metricCalculator.calculateMetrics(startDate, endDate);
    }

    // recalculates chart, no time range
    public void recalculateChart(String granularity, String metric) {
        updateChart(granularity, metric);
    }

    // recalculates chart, with time range
    public void recalculateChart(LocalDateTime startDate, LocalDateTime endDate, String granularity, String metric) {
        chart.recalculateChart(startDate, endDate);
        updateChart(granularity, metric);
    }

    // updates the chart in the gui
    public void updateChart(String granularity, String metric) {
        chartsGrid.remove(2);

        switch (granularity) {
            case "Hours": {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                ChartPanel chartPanel = new ChartPanel(chart.getHoursChart(metric + " over time", metric));
                chartJPanel.add(chartPanel, BorderLayout.CENTER);
                chartJPanel.validate();
                panel.add(chartJPanel);
                chartsGrid.add(panel, BorderLayout.CENTER);

                menu.remove(3);
                menu.add(chartsGrid);
                break;
            }
            case "Days": {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                ChartPanel chartPanel = new ChartPanel(chart.getDaysChart(metric + " over time", metric));
                chartJPanel.add(chartPanel, BorderLayout.CENTER);
                chartJPanel.validate();
                panel.add(chartJPanel);
                chartsGrid.add(panel, BorderLayout.CENTER);

                menu.remove(3);
                menu.add(chartsGrid);
                break;
            }
            case "Weeks": {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                ChartPanel chartPanel = new ChartPanel(chart.getWeeksChart(metric + " over time", metric));
                chartJPanel.add(chartPanel, BorderLayout.CENTER);
                chartJPanel.validate();
                panel.add(chartJPanel);
                chartsGrid.add(panel, BorderLayout.CENTER);

                menu.remove(3);
                menu.add(chartsGrid);
                break;
            }
            case "Months": {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                ChartPanel chartPanel = new ChartPanel(chart.getMonthsChart(metric + " over time", metric));
                chartJPanel.add(chartPanel, BorderLayout.CENTER);
                chartJPanel.validate();
                panel.add(chartJPanel);
                chartsGrid.add(panel, BorderLayout.CENTER);

                menu.remove(3);
                menu.add(chartsGrid);
                break;
            }
            case "Years": {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                ChartPanel chartPanel = new ChartPanel(chart.getYearsChart(metric + " over time", metric));
                chartJPanel.add(chartPanel, BorderLayout.CENTER);
                chartJPanel.validate();
                panel.add(chartJPanel);
                chartsGrid.add(panel, BorderLayout.CENTER);

                menu.remove(3);
                menu.add(chartsGrid);
                break;
            }
        }
    }
}