package View;

import Controllers.*;
import Models.*;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class AdAuctionGUI extends JFrame {
    // primary gui components
    private JFrame gui;
    private JLayeredPane menu;
    private JPanel metricsGrid;
    private JPanel topMenu;

    //components for vertical menu
    private JButton metricsButton;
    private JButton chartsButton;
    private JButton histogramsButton;
    private JButton compareButton;
    private JButton settingsButton;

    // components for campaigns
    private JPanel filesMenu;
    private JFileChooser fileChooser;
    private JLabel impressionFileLabel, clickFileLabel, serverFileLabel;
    private String impressionsFileLocation, clickFileLocation, serverFileLocation;
    private JLabel productName;
    private JButton loadCampaignButton;

    // components for metrics
    private JComboBox<String> metricsGenderBox, metricsAgeBox, metricsContextBox, metricsIncomeBox;
    private JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalImpressionCostValues, totalClickCostValues, bounceValues, bounceRateValues;
    private Box impressionsBox, clicksBox, uniquesBox, ctrBox, cpaBox, cpcBox, cpmBox, conversionsBox, totalImpressionCostBox, totalClickCostBox, bouncesBox, bounceRateBox;
    private Box metricsNorthBox;
    private JDatePicker metricsStartDatePicker, metricsEndDatePicker;

    // components for charts
    private JComboBox<String> chartsMetricsBox, chartsGenderBox, chartsAgeBox, chartsContextBox, chartsIncomeBox;
    private JPanel chartsGrid, chartJPanel;
    private ChartPanel chartPanel;
    private JSlider chartSlider;
    private Hashtable<Integer, JLabel> position;
    private Box chartNorthBox;
    private JDatePicker chartsStartDatePicker, chartsEndDatePicker;

    // components for histograms
    private JPanel histogramGrid, histogramJPanel;
    private ChartPanel histogramPanel;

    // components for compare
    private JPanel compareGrid, compareChartsGrid;
    private JButton resetCompareButton, addChartToCompareButton, saveChartToFileButton;
    private JLabel chartNumber;

    // components for settings page
    private JPanel settingsGrid;
    private Box styleVerticalBox;
    private Box bounceVBox;
    private JComboBox<String> primaryColorComboBox,secondaryColorComboBox,fontComboBox,bounceTimeComboBox, pageLimitComboBox;
    private JLabel styleLabel,primaryColorLabel,secondaryColorLabel,fontLabel,technicalLabel,bounceTimeLabel, pageLimitLabel;

    // filtering
    private final ArrayList<String> arrayOfChoicesMetrics = new ArrayList<>();
    private final ArrayList<String> arrayOfChoicesChart = new ArrayList<>();
    private int countCharts = 0;

    public int pageLimit = 1; // max number of pages to be counted as a bounce
    public int bounceTime = 500; // max amount of time to be counted as a bounce

    // model controllers
    private final CampaignController campaignController;
    private final MetricController metricController;
    private final ChartController chartController;

    // colours
    private final Color blue = new Color(14,139,229);
    private final Color orange = new Color(220,120,27);
    private final Color green = new Color(38 , 226 , 50);
    private final Color grey = new Color(242,236,236);
    private Color primaryColor = blue;
    private Color secondaryColor = orange;
    private final Color tertiaryColor = grey;
    private final Color noColor = Color.WHITE;

    // fonts
    private Font mainFont = new Font("Impact", Font.PLAIN, 15);
    private Font fontOfText = new Font(mainFont.getName(), Font.PLAIN, 25);
    private Font fontOfValue = new Font(mainFont.getName(), Font.BOLD, 30);
    private Font comboBoxFont = new Font(mainFont.getName(), Font.PLAIN, 14);


    //JLabels Insights
    private final JLabel impressions = new JLabel("Impressions");
    private final JLabel uniques = new JLabel("Uniques");
    private final JLabel clicks = new JLabel("Clicks");
    private final JLabel bounces = new JLabel("Bounces");
    private final JLabel conversions = new JLabel("Conversions");
    private final JLabel totalImpressionCost = new JLabel("Total Impression Cost");
    private final JLabel totalClickCost = new JLabel("Total Click Cost");
    private final JLabel ctr = new JLabel("CTR");
    private final JLabel cpa = new JLabel("CPA");
    private final JLabel cpc = new JLabel("CPC");
    private final JLabel cpm = new JLabel("CPM");
    private final JLabel bounceRate = new JLabel("Bounce Rate");

    // initialises the display controllers
    public AdAuctionGUI() {
        this.campaignController = new CampaignController();
        this.metricController = new MetricController(campaignController.getPageLimit(), campaignController.getBounceTime());
        this.chartController = new ChartController(campaignController.getPageLimit(), campaignController.getBounceTime());

        chartPanel = new ChartPanel(new Chart("Blank Chart", "Impressions", "Days", campaignController.getPageLimit(), campaignController.getBounceTime()).getChart());
        histogramPanel = new ChartPanel(new Histogram("Blank Histogram", campaignController.getPageLimit(), campaignController.getBounceTime()).getHistogram());
    }

    // displays the main window
    public void prepareGui() {
        gui = new JFrame("Ad Auction Monitor");
        gui.setResizable(false);
        gui.setUndecorated(true);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gui.setVisible(true);
        createMenu();
        gui.add(menu);
        restartDoubleClick();
    }

    // displays the main structure
    public void createMenu(){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(noColor);

        // create main components
        createTopMenu();
        createVerticalMenu();
        createMetricsGrid();
        createChartsGrid();
        createHistogramsGrid();
        createCompareGrid();
        createSettingsGrid();

        // display main page
        metricsGrid.setVisible(true);
        chartsGrid.setVisible(false);
        histogramGrid.setVisible(false);
        compareGrid.setVisible(false);
        settingsGrid.setVisible(false);

        changeFilters(false);
    }

    // displays the constant vertical menu on the lift hand size
    public void createVerticalMenu(){
        JPanel verticalMenu = new JPanel(new GridLayout(5, 1));
        verticalMenu.setBounds(0,100,200,gui.getHeight()-100);
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(tertiaryColor);

        //start panel
        JPanel metricsButtonPanel = new JPanel(new BorderLayout());
        metricsButtonPanel.setOpaque(false);
        metricsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        //components for vertical menu
        metricsButton = new JButton("Metrics");
        metricsButton.setFont(mainFont);
        metricsButton.setBorderPainted(false);
        metricsButton.setContentAreaFilled(false);
        metricsButton.setFocusPainted(true);
        metricsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        metricsButtonPanel.add(metricsButton);
        //end panel

        //start panel
        JPanel chartsButtonPanel = new JPanel(new BorderLayout());
        chartsButtonPanel.setOpaque(false);
        chartsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

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
        histogramsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        histogramsButton = new JButton("Histograms");
        histogramsButton.setBorderPainted(false);
        histogramsButton.setContentAreaFilled(false);
        histogramsButton.setFont(mainFont);
        histogramsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        histogramsButtonPanel.add(histogramsButton);
        //end panel

        //start panel
        JPanel compareButtonPanel = new JPanel(new BorderLayout());
        compareButtonPanel.setOpaque(false);
        compareButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        compareButton = new JButton("Compare");
        compareButton.setBorderPainted(false);
        compareButton.setContentAreaFilled(false);
        compareButton.setFont(mainFont);
        compareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        compareButtonPanel.add(compareButton);
        //end panel

        //start panel
        JPanel settingsButtonPanel = new JPanel(new BorderLayout());
        settingsButtonPanel.setOpaque(false);
        settingsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        settingsButton = new JButton("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFont(mainFont);
        settingsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        settingsButtonPanel.add(settingsButton);
        //end panel

        verticalMenu.add(metricsButtonPanel);
        verticalMenu.add(chartsButtonPanel);
        verticalMenu.add(histogramsButtonPanel);
        verticalMenu.add(compareButtonPanel);
        verticalMenu.add(settingsButtonPanel);

        menu.add(verticalMenu, BorderLayout.WEST,0);

        // display metrics page
        metricsButton.addActionListener(e -> {
            metricsGrid.setVisible(true);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
            settingsGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        // display charts page
        chartsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(true);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
            settingsGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        // display histogram page
        histogramsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(true);
            compareGrid.setVisible(false);
            settingsGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        // display compare page
        compareButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(true);
            settingsGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        // display settings page
        settingsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
            settingsGrid.setVisible(true);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });
    }

    // displays the constant horizontal menu at the top
    public void createTopMenu(){
        topMenu = new JPanel(new GridLayout(1, 3));
        topMenu.setSize(gui.getWidth(),100);
        topMenu.setOpaque(true);
        topMenu.setBackground(primaryColor);
        topMenu.setBorder(new EmptyBorder(10, 10, 10, 10));

        // product title
        //components for top menu
        productName = new JLabel("Ad Auction Dashboard");
        productName.setBackground(primaryColor);
        productName.setSize(100,100);
        productName.setAlignmentX(20);
        productName.setBounds(20,0,100,100);
        productName.setForeground(noColor);
        productName.setFont(mainFont);

        // load campaign button
        JPanel loadCampaignButtonPanel = new JPanel(new BorderLayout());
        loadCampaignButtonPanel.setOpaque(false);

        loadCampaignButton = new JButton("Load Campaign");
        loadCampaignButton.setFont(mainFont);
        loadCampaignButton.setBorderPainted(false);
        loadCampaignButton.setContentAreaFilled(false);
        loadCampaignButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loadCampaignButton.setForeground(noColor);

        loadCampaignButtonPanel.add(loadCampaignButton, BorderLayout.EAST);

        // load files of campaign
        loadCampaignButton.addActionListener(e -> createFileLoadBox());

        // close the program
        JButton closeButton = new RoundButton("X");

        closeButton.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (dialogResult == 0){
                System.exit(0);
            }
        });

        Box topRight = Box.createHorizontalBox();
        topRight.add(loadCampaignButtonPanel);
        topRight.add(closeButton);
        topRight.setBounds(gui.getWidth()-200,0,200,100);

        topMenu.add(productName, BorderLayout.WEST,0);
        topMenu.add(topRight, BorderLayout.CENTER,1);
        menu.add(topMenu, BorderLayout.NORTH,1);
    }

    // creates a round button
    public class RoundButton extends JButton {

        public RoundButton(String label) {
            super(label);
            setBackground(Color.RED);
            setForeground(noColor);
            setFocusable(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width , size.height);
            setPreferredSize(size);

            setContentAreaFilled(false);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mousePressed(MouseEvent e) {

                }
                @Override
                public void mouseReleased(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(Color.WHITE);
                    setForeground(Color.RED);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                }
            });
        }

        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(Color.gray);
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0 , 0 , getSize().width - 1 , getSize().height - 1);

            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(Color.darkGray);
            g.drawOval(0 , 0 , getSize().width - 1 , getSize().height - 1);
        }
        Shape shape;

        public boolean contains(int x , int y) {
            // If the button has changed size,  make a new shape object.
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0 , 0 , getWidth() , getHeight());
            }
            return shape.contains(x , y);
        }
    }

    public void restartDoubleClick(){
        gui.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gui.getCursor() == Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)) {
                    if (e.getClickCount() == 5) {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "The GUI stopped working unexpectedly. Do you want to force stop the last filter?", "Force StoP", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == 0){
                            changeFilters(true);
                            gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }

                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    // displays the load files page
    public void createFileLoadBox() {
        GridLayout filesMenuLayout = new GridLayout(8, 1);
        filesMenuLayout.setVgap(10);

        filesMenu = new JPanel(filesMenuLayout);
        filesMenu.setSize(gui.getWidth() / 2, gui.getHeight() / 2);
        filesMenu.setLocation(gui.getWidth() / 4, gui.getHeight() / 4);
        filesMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
        filesMenu.setBackground(primaryColor);
        filesMenu.setVisible(true);

        // select impressions file button
        JButton impressionFileButton = new JButton("Select Impression Log");
        impressionFileButton.setFont(mainFont);
        impressionFileButton.setBorderPainted(false);
        impressionFileButton.setBackground(noColor);
        impressionFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // select clicks files button
        JButton clickFileButton = new JButton("Select Click Log");
        clickFileButton.setFont(mainFont);
        clickFileButton.setBorderPainted(false);
        clickFileButton.setBackground(noColor);
        clickFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // select server file button
        JButton serverFileButton = new JButton("Select Server Log");
        serverFileButton.setFont(mainFont);
        serverFileButton.setBorderPainted(false);
        serverFileButton.setBackground(noColor);
        serverFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // select impression log file
        impressionFileButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new JFileChooser().getFileSystemView().getDefaultDirectory());
            fileChooser.setDialogTitle("Select Impressions Log CSV");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel", "csv"));

            int result = fileChooser.showOpenDialog(filesMenu);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                impressionsFileLocation = selectedFile.getAbsolutePath();
            }

            impressionFileLabel = new JLabel(impressionsFileLocation);
            filesMenu.remove(3);
            filesMenu.add(impressionFileLabel, 3);
            filesMenu.validate();
        });

        // select click log file
        clickFileButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new JFileChooser().getFileSystemView().getDefaultDirectory());
            fileChooser.setDialogTitle("Select Click Log CSV");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel", "csv"));

            int result = fileChooser.showOpenDialog(filesMenu);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                clickFileLocation = selectedFile.getAbsolutePath();
            }

            clickFileLabel = new JLabel(clickFileLocation);
            filesMenu.remove(4);
            filesMenu.add(clickFileLabel, 4);
            filesMenu.validate();
        });

        // select server log file
        serverFileButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new JFileChooser().getFileSystemView().getDefaultDirectory());
            fileChooser.setDialogTitle("Select Server Log CSV");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel", "csv"));

            int result = fileChooser.showOpenDialog(filesMenu);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                serverFileLocation = selectedFile.getAbsolutePath();
            }

            serverFileLabel = new JLabel(serverFileLocation);
            filesMenu.remove(5);
            filesMenu.add(serverFileLabel, 5);
            filesMenu.validate();
        });

        // load campaign button
        loadCampaignButton = new JButton("Load Campaign");
        loadCampaignButton.setFont(mainFont);
        loadCampaignButton.setBorderPainted(false);
        loadCampaignButton.setBackground(noColor);
        loadCampaignButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // loads the campaign
        loadCampaignButton.addActionListener(e -> {
            try {
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                createCampaign();

                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();

                loadCampaignButton.setEnabled(true);
            } catch (Exception invalidCampaignE) {
                JOptionPane.showMessageDialog(null, "Invalid campaign files!");
            }
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        });

        JButton cancelLoadCampaign = new JButton("Cancel");
        cancelLoadCampaign.setFont(mainFont);
        cancelLoadCampaign.setBorderPainted(false);
        cancelLoadCampaign.setBackground(noColor);
        cancelLoadCampaign.setForeground(Color.RED);
        cancelLoadCampaign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cancelLoadCampaign.addActionListener(e -> {
            loadCampaignButton.setEnabled(true);
            menu.remove(filesMenu);
            menu.revalidate();
            menu.repaint();
        });

        impressionFileLabel = new JLabel("No File");
        impressionFileLabel.setForeground(noColor);
        clickFileLabel = new JLabel("No File");
        clickFileLabel.setForeground(noColor);
        serverFileLabel = new JLabel("No File");
        serverFileLabel.setForeground(noColor);

        filesMenu.add(impressionFileButton, 0);
        filesMenu.add(clickFileButton, 1);
        filesMenu.add(serverFileButton, 2);
        filesMenu.add(impressionFileLabel, 3);
        filesMenu.add(clickFileLabel, 4);
        filesMenu.add(serverFileLabel, 5);
        filesMenu.add(loadCampaignButton, 6);
        filesMenu.add(cancelLoadCampaign, 7);

        menu.add(filesMenu, 10);
    }

    // displays the metrics filters
    public void createMetricsNorthBox() {
        //start Box
        String[] genderChoices = new String[] {"Any","Male","Female"};
        metricsGenderBox = new JComboBox<>(genderChoices);
        metricsGenderBox.setVisible(true);
        metricsGenderBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsGenderBox.setFont(comboBoxFont);

        metricsGenderBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsGenderBox.getSelectedItem());
            arrayOfChoicesMetrics.set(1, itemName);
            recalculateMetrics();
        });

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(mainFont);
        genderLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box genderVerticalBox = Box.createVerticalBox();
        genderVerticalBox.add(genderLabel);
        genderVerticalBox.add(metricsGenderBox);
        //end box

        //start Box
        String[] ageChoices = new String[]{"Any", "<25", "25-34", "35-44", "45-54", ">54"};
        metricsAgeBox = new JComboBox<>(ageChoices);
        metricsAgeBox.setVisible(true);
        metricsAgeBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsAgeBox.setFont(comboBoxFont);

        metricsAgeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsAgeBox.getSelectedItem());
            arrayOfChoicesMetrics.set(2, itemName);
            recalculateMetrics();
        });

        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(mainFont);
        ageLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box ageVerticalBox = Box.createVerticalBox();
        ageVerticalBox.add(ageLabel);
        ageVerticalBox.add(metricsAgeBox);
        //end box

        //start Box
        String[] contextChoices = new String[]{"Any", "Blog", "Social Media", "Shopping", "News"};
        metricsContextBox = new JComboBox<>(contextChoices);
        metricsContextBox.setVisible(true);
        metricsContextBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsContextBox.setFont(comboBoxFont);

        metricsContextBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsContextBox.getSelectedItem());
            arrayOfChoicesMetrics.set(3, itemName);
            recalculateMetrics();
        });

        JLabel contextLabel = new JLabel("CONTEXT");
        contextLabel.setFont(mainFont);
        contextLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box contextVerticalBox = Box.createVerticalBox();
        contextVerticalBox.add(contextLabel);
        contextVerticalBox.add(metricsContextBox);
        //end box

        //start Box
        String[] incomeChoices = new String[]{"Any", "Low", "Medium", "High"};
        metricsIncomeBox = new JComboBox<>(incomeChoices);
        metricsIncomeBox.setVisible(true);
        metricsIncomeBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsIncomeBox.setFont(comboBoxFont);

        metricsIncomeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsIncomeBox.getSelectedItem());
            arrayOfChoicesMetrics.set(4, itemName);
            recalculateMetrics();
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(metricsIncomeBox);
        //end box

        //start Box
        metricsStartDatePicker = new JDatePicker();
        metricsStartDatePicker.setVisible(true);
        metricsStartDatePicker.setBorder(new EmptyBorder(5,5,5,5));
        metricsStartDatePicker.setFont(comboBoxFont);

        metricsStartDatePicker.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            DateModel<?> model = metricsStartDatePicker.getModel();

            String date = model.getYear() + "-";
            if ((model.getMonth() + 1) < 10) {
                date += "0";
            }
            date += (model.getMonth() + 1) + "-";
            if (model.getDay() < 10) {
                date += "0";
            }
            date += model.getDay() + " 00:00:00";

            arrayOfChoicesMetrics.set(5, date);
            recalculateMetrics();
        });

        JLabel startDateLabel = new JLabel("START DATE");
        startDateLabel.setVisible(true);
        startDateLabel.setFont(mainFont);
        startDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box startDateVerticalBox = Box.createVerticalBox();
        startDateVerticalBox.add(startDateLabel);
        startDateVerticalBox.add(metricsStartDatePicker);
        //end box

        //start Box
        metricsEndDatePicker = new JDatePicker();
        metricsEndDatePicker.setVisible(true);
        metricsEndDatePicker.setBorder(new EmptyBorder(5,5,5,5));
        metricsEndDatePicker.setFont(comboBoxFont);

        metricsEndDatePicker.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            DateModel<?> model = metricsEndDatePicker.getModel();

            String date = model.getYear() + "-";
            if ((model.getMonth() + 1) < 10) {
                date += "0";
            }
            date += (model.getMonth() + 1) + "-";
            if (model.getDay() < 10) {
                date += "0";
            }
            date += model.getDay() + " 00:00:00";

            arrayOfChoicesMetrics.set(6, date);
            recalculateMetrics();
        });

        JLabel endDateLabel = new JLabel("END DATE");
        endDateLabel.setVisible(true);
        endDateLabel.setFont(mainFont);
        endDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box endDateVerticalBox = Box.createVerticalBox();
        endDateVerticalBox.add(endDateLabel);
        endDateVerticalBox.add(metricsEndDatePicker);
        //end box

        metricsNorthBox = Box.createHorizontalBox();
        metricsNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        metricsNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        metricsNorthBox.add(genderVerticalBox);
        metricsNorthBox.add(ageVerticalBox);
        metricsNorthBox.add(contextVerticalBox);
        metricsNorthBox.add(incomeVerticalBox);
        metricsNorthBox.add(startDateVerticalBox);
        metricsNorthBox.add(endDateVerticalBox);

        metricsGrid.add(metricsNorthBox, BorderLayout.NORTH);
    }

    // displays the metrics values
    public void createMetricsBox() {
        Border blackBorder = BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10), BorderFactory.createLineBorder(tertiaryColor, 1));

        //start panel
        JPanel impressionsPanel = new JPanel(new GridBagLayout());
        impressionsPanel.setBorder(blackBorder);
        impressionsPanel.setOpaque(false);

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
        JPanel uniquesPanel = new JPanel(new GridBagLayout());
        uniquesPanel.setBorder(blackBorder);
        uniquesPanel.setOpaque(false);

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
        JPanel clicksPanel = new JPanel(new GridBagLayout());
        clicksPanel.setBorder(blackBorder);
        clicksPanel.setOpaque(false);

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
        JPanel bouncesPanel = new JPanel(new GridBagLayout());
        bouncesPanel.setBorder(blackBorder);
        bouncesPanel.setOpaque(false);

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
        JPanel conversionsPanel = new JPanel(new GridBagLayout());
        conversionsPanel.setBorder(blackBorder);
        conversionsPanel.setOpaque(false);

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
        JPanel totalImpressionCostPanel = new JPanel(new GridBagLayout());
        totalImpressionCostPanel.setBorder(blackBorder);
        totalImpressionCostPanel.setOpaque(false);

        totalImpressionCost.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalImpressionCost.setForeground(secondaryColor);
        totalImpressionCost.setFont(fontOfText);

        totalImpressionCostValues = new JLabel("0");
        totalImpressionCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalImpressionCostValues.setFont(fontOfValue);

        totalImpressionCostBox = Box.createVerticalBox();
        totalImpressionCostBox.add(totalImpressionCostValues);
        totalImpressionCostBox.add(totalImpressionCost);
        totalImpressionCostPanel.add(totalImpressionCostBox);
        //end panel

        //start panel
        JPanel totalClickCostPanel = new JPanel(new GridBagLayout());
        totalClickCostPanel.setBorder(blackBorder);
        totalClickCostPanel.setOpaque(false);

        totalClickCost.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalClickCost.setForeground(secondaryColor);
        totalClickCost.setFont(fontOfText);

        totalClickCostValues = new JLabel("0");
        totalClickCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalClickCostValues.setFont(fontOfValue);

        totalClickCostBox = Box.createVerticalBox();
        totalClickCostBox.add(totalClickCostValues);
        totalClickCostBox.add(totalClickCost);
        totalClickCostPanel.add(totalClickCostBox);
        //end panel

        //start panel
        JPanel ctrPanel = new JPanel(new GridBagLayout());
        ctrPanel.setBorder(blackBorder);
        ctrPanel.setOpaque(false);

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
        JPanel bounceRatePanel = new JPanel(new GridBagLayout());
        bounceRatePanel.setBorder(blackBorder);
        bounceRatePanel.setOpaque(false);

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

        JPanel metricsPanel = new JPanel(new GridLayout(4, 3));

        metricsPanel.add(impressionsPanel);
        metricsPanel.add(uniquesPanel);
        metricsPanel.add(clicksPanel);
        metricsPanel.add(bouncesPanel);
        metricsPanel.add(conversionsPanel);
        metricsPanel.add(totalImpressionCostPanel);
        metricsPanel.add(totalClickCostPanel);
        metricsPanel.add(ctrPanel);
        metricsPanel.add(cpaPanel);
        metricsPanel.add(cpcPanel);
        metricsPanel.add(cpmPanel);
        metricsPanel.add(bounceRatePanel);

        metricsGrid.add(metricsPanel, BorderLayout.CENTER);
    }

    // displays the metrics page
    public void createMetricsGrid(){
        arrayOfChoicesMetrics.add("Impressions"); // metrics
        arrayOfChoicesMetrics.add("Any"); // gender
        arrayOfChoicesMetrics.add("Any"); // age
        arrayOfChoicesMetrics.add("Any"); // context
        arrayOfChoicesMetrics.add("Any"); // income
        arrayOfChoicesMetrics.add("Any"); // start date
        arrayOfChoicesMetrics.add("Any"); // end date
        arrayOfChoicesMetrics.add("Days"); // granularity

        metricsGrid = new JPanel(new BorderLayout());
        metricsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        metricsGrid.setBorder(new EmptyBorder(2,2,2,2));
        metricsGrid.setAlignmentY(100);
        metricsGrid.setVisible(true);
        metricsGrid.setOpaque(true);

        createMetricsNorthBox();
        createMetricsBox();

        menu.add(metricsGrid);
    }

    // displays the chart filters
    public void createChartNorthBox(){
        //start Box
        String[] metricsChoices = new String[]{"Impressions", "Uniques", "Clicks", "Bounces",  "Conversions", "Total Impression Cost", "Total Click Cost", "CTR", "CPA", "CPC", "CPM", "Bounce Rate"};
        chartsMetricsBox = new JComboBox<>(metricsChoices);
        chartsMetricsBox.setVisible(true);
        chartsMetricsBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsMetricsBox.setFont(comboBoxFont);

        chartsMetricsBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsMetricsBox.getSelectedItem());
            arrayOfChoicesChart.set(0, itemName);
            recalculateCharts();
        });

        JLabel metricsLabel = new JLabel("METRICS");
        metricsLabel.setFont(mainFont);
        metricsLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box metricsVerticalBox = Box.createVerticalBox();
        metricsVerticalBox.add(metricsLabel);
        metricsVerticalBox.add(chartsMetricsBox);
        //end box

        //start Box
        String[] genderChoices = new String[]{"Any", "Male", "Female"};
        chartsGenderBox = new JComboBox<>(genderChoices);
        chartsGenderBox.setVisible(true);
        chartsGenderBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsGenderBox.setFont(comboBoxFont);

        chartsGenderBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsGenderBox.getSelectedItem());
            arrayOfChoicesChart.set(1, itemName);
            recalculateCharts();
        });

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(mainFont);
        genderLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box genderVerticalBox = Box.createVerticalBox();
        genderVerticalBox.add(genderLabel);
        genderVerticalBox.add(chartsGenderBox);
        //end box

        //start Box
        String[] ageChoices = new String[]{"Any", "<25", "25-34", "35-44", "45-54", ">54"};
        chartsAgeBox = new JComboBox<>(ageChoices);
        chartsAgeBox.setVisible(true);
        chartsAgeBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsAgeBox.setFont(comboBoxFont);

        chartsAgeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsAgeBox.getSelectedItem());
            arrayOfChoicesChart.set(2, itemName);
            recalculateCharts();
        });

        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(mainFont);
        ageLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box ageVerticalBox = Box.createVerticalBox();
        ageVerticalBox.add(ageLabel);
        ageVerticalBox.add(chartsAgeBox);
        //end box

        //start Box
        String[] contextChoices = new String[]{"Any", "Blog", "Social Media", "Shopping", "News"};
        chartsContextBox = new JComboBox<>(contextChoices);
        chartsContextBox.setVisible(true);
        chartsContextBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsContextBox.setFont(comboBoxFont);

        chartsContextBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsContextBox.getSelectedItem());
            arrayOfChoicesChart.set(3, itemName);
            recalculateCharts();
        });

        JLabel contextLabel = new JLabel("CONTEXT");
        contextLabel.setFont(mainFont);
        contextLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box contextVerticalBox = Box.createVerticalBox();
        contextVerticalBox.add(contextLabel);
        contextVerticalBox.add(chartsContextBox);
        //end box

        //start Box
        String[] incomeChoices = new String[]{"Any", "Low", "Medium", "High"};
        chartsIncomeBox = new JComboBox<>(incomeChoices);
        chartsIncomeBox.setVisible(true);
        chartsIncomeBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsIncomeBox.setFont(comboBoxFont);

        chartsIncomeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsIncomeBox.getSelectedItem());
            arrayOfChoicesChart.set(4, itemName);
            recalculateCharts();
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(chartsIncomeBox);
        //end box

        //start Box
        chartsStartDatePicker = new JDatePicker();
        chartsStartDatePicker.setVisible(true);
        chartsStartDatePicker.setBorder(new EmptyBorder(5,5,5,5));
        chartsStartDatePicker.setFont(comboBoxFont);

        chartsStartDatePicker.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            DateModel<?> model = chartsStartDatePicker.getModel();

            String date = model.getYear() + "-";
            if ((model.getMonth() + 1) < 10) {
                date += "0";
            }
            date += (model.getMonth() + 1) + "-";
            if (model.getDay() < 10) {
                date += "0";
            }
            date += model.getDay() + " 00:00:00";
            arrayOfChoicesChart.set(5, date);
            recalculateCharts();
        });

        JLabel startDateLabel = new JLabel("START DATE");
        startDateLabel.setVisible(true);
        startDateLabel.setFont(mainFont);
        startDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box startDateVerticalBox = Box.createVerticalBox();
        startDateVerticalBox.add(startDateLabel);
        startDateVerticalBox.add(chartsStartDatePicker);
        //end box

        //start Box
        chartsEndDatePicker = new JDatePicker();
        chartsEndDatePicker.setVisible(true);
        chartsEndDatePicker.setBorder(new EmptyBorder(5,5,5,5));
        chartsEndDatePicker.setFont(comboBoxFont);

        chartsEndDatePicker.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            DateModel<?> model = chartsEndDatePicker.getModel();

            String date = model.getYear() + "-";
            if ((model.getMonth() + 1) < 10) {
                date += "0";
            }
            date += (model.getMonth() + 1) + "-";
            if (model.getDay() < 10) {
                date += "0";
            }
            date += model.getDay() + " 00:00:00";

            arrayOfChoicesChart.set(6, date);
            recalculateCharts();
        });

        JLabel endDateLabel = new JLabel("END DATE");
        endDateLabel.setVisible(true);
        endDateLabel.setFont(mainFont);
        endDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box endDateVerticalBox = Box.createVerticalBox();
        endDateVerticalBox.add(endDateLabel);
        endDateVerticalBox.add(chartsEndDatePicker);
        //end box

        chartNorthBox = Box.createHorizontalBox();
        chartNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        chartNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        chartNorthBox.add(metricsVerticalBox);
        chartNorthBox.add(genderVerticalBox);
        chartNorthBox.add(ageVerticalBox);
        chartNorthBox.add(contextVerticalBox);
        chartNorthBox.add(incomeVerticalBox);
        chartNorthBox.add(startDateVerticalBox);
        chartNorthBox.add(endDateVerticalBox);

        chartsGrid.add(chartNorthBox, BorderLayout.NORTH);
    }

    // displays the chart slider (time granularity)
    public void createChartSouthGrid(){
        chartSlider = new JSlider(JSlider.HORIZONTAL,0,4,1);
        chartSlider.setVisible(true);
        chartSlider.setMajorTickSpacing(1);
        chartSlider.setPaintTicks(true);
        chartSlider.setPaintLabels(true);

        position = new Hashtable<>();
        position.put(0, new JLabel("Hours"));
        position.put(1, new JLabel("Days"));
        position.put(2, new JLabel("Weeks"));
        position.put(3, new JLabel("Months"));
        position.put(4, new JLabel("Years"));

        // slider styling
        for(JLabel j : position.values()){
            j.setFont(comboBoxFont);
        }

        chartSlider.setLabelTable(position);

        chartSlider.addChangeListener(e -> {
            String sliderValue = position.get(chartSlider.getValue()).getText();

            if (!arrayOfChoicesChart.get(7).equals(sliderValue)) {
                arrayOfChoicesChart.set(7, sliderValue);
                updateCharts();
            }
        });

        // components for compare
        JPanel addChartToComparePanel = new JPanel(new GridBagLayout());
        addChartToComparePanel.setOpaque(false);
        addChartToComparePanel.setBorder(new EmptyBorder(10,10,10,10));

        addChartToCompareButton = new JButton("Add to Compare");
        addChartToCompareButton.setFont(mainFont);
        addChartToCompareButton.setBackground(primaryColor);
        addChartToCompareButton.setForeground(noColor);
        addChartToCompareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addChartToCompareButton.setPreferredSize(new Dimension(400,40));

        addChartToComparePanel.add(addChartToCompareButton);

        addChartToCompareButton.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            if (countCharts < 4) {
                JPanel panel = new JPanel(new GridBagLayout());
                JPanel chartJPanel = new JPanel(new BorderLayout());
                chartNumber = new JLabel("Chart " + (countCharts + 1));
                chartNumber.setFont(mainFont);
                panel.add(chartNumber);
                countCharts++;

                switch (arrayOfChoicesChart.get(7)) {
                    case "Hours": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getHoursChart());
                        chartPanel.setPreferredSize(new Dimension(500, 280));
                        chartPanel.setBorder(new EmptyBorder(5,5,5,5));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Days": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getDaysChart());
                        chartPanel.setPreferredSize(new Dimension(500, 280));
                        chartPanel.setBorder(new EmptyBorder(5,5,5,5));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Weeks": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getWeeksChart());
                        chartPanel.setPreferredSize(new Dimension(500, 280));
                        chartPanel.setBorder(new EmptyBorder(5,5,5,5));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Months": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getMonthsChart());
                        chartPanel.setPreferredSize(new Dimension(500, 280));
                        chartPanel.setBorder(new EmptyBorder(5,5,5,5));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Years": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getYearsChart());
                        chartPanel.setPreferredSize(new Dimension(500, 280));
                        chartPanel.setBorder(new EmptyBorder(5,5,5,5));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Can't add more than 4 charts to compare");
            }
        });

        JPanel saveChartToFilePanel = new JPanel(new GridBagLayout());
        saveChartToFilePanel.setOpaque(false);
        saveChartToFilePanel.setBorder(new EmptyBorder(10,10,10,10));

        saveChartToFileButton = new JButton("Save Chart");
        saveChartToFileButton.setFont(mainFont);
        saveChartToFileButton.setBackground(primaryColor);
        saveChartToFileButton.setForeground(noColor);
        saveChartToFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveChartToFileButton.setPreferredSize(new Dimension(400,40));

        saveChartToFileButton.addActionListener(e -> {
            try {
                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save a chart");

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    ChartUtilities.saveChartAsJPEG(new File(fileToSave.getAbsolutePath() + ".jpg"), chartPanel.getChart(), 1920, 1080);
                }
            } catch (Exception ignored) { }
        });

        saveChartToFilePanel.add(saveChartToFileButton);

        JPanel chartSouthGrid = new JPanel(new GridLayout(1, 2));
        chartSouthGrid.setPreferredSize(new Dimension(chartsGrid.getWidth(),200));
        chartSouthGrid.setBorder(new EmptyBorder(10,10,10,10));

        chartSouthGrid.add(chartSlider);
        chartSouthGrid.add(addChartToComparePanel);
        chartSouthGrid.add(saveChartToFilePanel);

        chartsGrid.add(chartSouthGrid,BorderLayout.SOUTH);
    }

    // displays the chart page
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
        chartsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        chartsGrid.setBackground(metricsGrid.getBackground());
        chartsGrid.setAlignmentY(100);
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

    // displays the histogram page
    public void createHistogramsGrid(){
        histogramGrid = new JPanel(new BorderLayout());
        histogramGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        histogramGrid.setBackground(metricsGrid.getBackground());
        histogramGrid.setAlignmentY(100);
        histogramGrid.setVisible(false);

        // create panel for chart
        JPanel panel = new JPanel(new GridBagLayout());
        histogramJPanel = new JPanel(new BorderLayout());

        histogramJPanel.add(histogramPanel, BorderLayout.CENTER);
        histogramJPanel.validate();
        panel.add(histogramJPanel);

        histogramGrid.add(panel, BorderLayout.CENTER);
        menu.add(histogramGrid);
    }

    // displays the compare page
    public void createCompareGrid(){
        compareGrid = new JPanel(new BorderLayout());
        compareGrid.setBounds(200 , 100 , gui.getWidth()-200 , gui.getHeight()-100);
        compareGrid.setBorder(new EmptyBorder(2 , 2 , 2 , 2));
        compareGrid.setBackground(metricsGrid.getBackground());
        compareGrid.setAlignmentY(100);
        compareGrid.setOpaque(true);

        compareChartsGrid = new JPanel(new GridLayout(2, 2));

        JPanel resetComparePanel = new JPanel(new GridBagLayout());
        resetComparePanel.setBorder(new EmptyBorder(10,10,10,10));
        resetComparePanel.setPreferredSize(new Dimension(metricsGrid.getWidth(),200));
        resetComparePanel.setOpaque(false);

        resetCompareButton = new JButton("Reset Compare");
        resetCompareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetCompareButton.setPreferredSize(new Dimension(400,40));
        resetCompareButton.setBackground(primaryColor);
        resetCompareButton.setForeground(noColor);
        resetCompareButton.setFont(mainFont);

        resetComparePanel.add(resetCompareButton);

        compareGrid.add(compareChartsGrid, BorderLayout.CENTER);
        compareGrid.add(resetComparePanel, BorderLayout.SOUTH);

        menu.add(compareGrid);

        resetCompareButton.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
            countCharts = 0;

            menu.remove(compareGrid);
            createCompareGrid();
        });
    }

    public void createSettingsStyleBox(){
        styleLabel = new JLabel("Style");
        styleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        styleLabel.setForeground(primaryColor);
        styleLabel.setFont(fontOfText);

        fontLabel = new JLabel("Font Size");
        fontLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        fontLabel.setForeground(secondaryColor);
        fontLabel.setFont(mainFont);

        String[] fontChoices = new String[] {"Small" , "Medium" , "Large"};
        fontComboBox = new JComboBox<>(fontChoices);
        fontComboBox.setVisible(true);
        fontComboBox.setOpaque(false);
        fontComboBox.setPreferredSize(new Dimension(100,35));
        fontComboBox.setMaximumSize(new Dimension(100,35));
        fontComboBox.setBorder(new EmptyBorder(5,5,5,5));
        fontComboBox.setFont(comboBoxFont);
        fontComboBox.setSelectedIndex(1);

        fontComboBox.addActionListener(e -> {
            if(fontComboBox.getSelectedIndex()==0){
                mainFont = new Font("Impact", Font.PLAIN, 10);
                fontOfText = new Font(mainFont.getName(), Font.PLAIN, 15);
                fontOfValue = new Font(mainFont.getName(), Font.BOLD, 20);
                comboBoxFont = new Font(mainFont.getName(), Font.PLAIN, 8);
                metricsNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
                chartNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
                updateFont();
            } else if(fontComboBox.getSelectedIndex()==1){
                mainFont = new Font("Impact", Font.PLAIN, 15);
                fontOfText = new Font(mainFont.getName(), Font.PLAIN, 25);
                fontOfValue = new Font(mainFont.getName(), Font.BOLD, 30);
                comboBoxFont = new Font(mainFont.getName(), Font.PLAIN, 14);
                metricsNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
                chartNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
                updateFont();

            } else if(fontComboBox.getSelectedIndex()==2){
                mainFont = new Font("Impact", Font.PLAIN, 20);
                fontOfText = new Font(mainFont.getName(), Font.PLAIN, 30);
                fontOfValue = new Font(mainFont.getName(), Font.BOLD, 40);
                comboBoxFont = new Font(mainFont.getName(), Font.PLAIN, 16);
                metricsNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),90));
                chartNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),90));
                updateFont();
            }
        });

        Box fontHBox = Box.createHorizontalBox();
        fontHBox.add(fontLabel);
        fontHBox.add(Box.createHorizontalStrut(320));
        fontHBox.add(fontComboBox);

        String[] colorChoices = new String[] {"Blue" , "Orange" , "Green"};
        primaryColorLabel = new JLabel("Font Primary Color");
        primaryColorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        primaryColorLabel.setForeground(secondaryColor);
        primaryColorLabel.setFont(mainFont);

        primaryColorComboBox = new JComboBox<>(colorChoices);
        primaryColorComboBox.setVisible(true);
        primaryColorComboBox.setOpaque(false);
        primaryColorComboBox.setPreferredSize(new Dimension(100,35));
        primaryColorComboBox.setMaximumSize(new Dimension(100,35));
        primaryColorComboBox.setBorder(new EmptyBorder(5,5,5,5));
        primaryColorComboBox.setFont(comboBoxFont);

        primaryColorComboBox.addActionListener(e -> {
            if(primaryColorComboBox.getSelectedIndex()==0){
                primaryColor = blue;
                updateColors();
                gui.repaint();
                gui.revalidate();
            } else if (primaryColorComboBox.getSelectedIndex()==1){
                primaryColor = orange;
                updateColors();
                gui.repaint();
                gui.revalidate();

            } else if(primaryColorComboBox.getSelectedIndex()==2){
                primaryColor = green;
                updateColors();
                gui.repaint();
                gui.revalidate();
            }
        });


        secondaryColorLabel = new JLabel("Font Secondary Color");
        secondaryColorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        secondaryColorLabel.setForeground(secondaryColor);
        secondaryColorLabel.setFont(mainFont);

        secondaryColorComboBox = new JComboBox<>(colorChoices);
        secondaryColorComboBox.setVisible(true);
        secondaryColorComboBox.setOpaque(false);
        secondaryColorComboBox.setPreferredSize(new Dimension(100,35));
        secondaryColorComboBox.setMaximumSize(new Dimension(100,35));
        secondaryColorComboBox.setBorder(new EmptyBorder(5,5,5,5));
        secondaryColorComboBox.setFont(comboBoxFont);
        secondaryColorComboBox.setSelectedIndex(1);

        secondaryColorComboBox.addActionListener(e -> {
            if (secondaryColorComboBox.getSelectedIndex() == 0) {
                secondaryColor = blue;
                updateColors();
                gui.repaint();
                gui.revalidate();
            } else if (secondaryColorComboBox.getSelectedIndex()==1){
                secondaryColor = orange;
                updateColors();
                gui.repaint();
                gui.revalidate();

            } else if(secondaryColorComboBox.getSelectedIndex()==2){
                secondaryColor = green;
                updateColors();
                gui.repaint();
                gui.revalidate();
            }
        });

        Box primaryColorHBox = Box.createHorizontalBox();
        primaryColorHBox.add(primaryColorLabel);
        primaryColorHBox.add(Box.createHorizontalStrut(260));
        primaryColorHBox.add(primaryColorComboBox);

        Box secondaryColorHBox = Box.createHorizontalBox();
        secondaryColorHBox.add(secondaryColorLabel);
        secondaryColorHBox.add(Box.createHorizontalStrut(240));
        secondaryColorHBox.add(secondaryColorComboBox);

        styleVerticalBox = Box.createVerticalBox();
        styleVerticalBox.setPreferredSize(new Dimension(settingsGrid.getWidth(),200));
        styleVerticalBox.setMaximumSize(new Dimension(settingsGrid.getWidth(),200));
        styleVerticalBox.add(styleLabel);
        styleVerticalBox.add(primaryColorHBox);
        styleVerticalBox.add(secondaryColorHBox);
        styleVerticalBox.add(fontHBox);
    }

    public void createSettingsTechnicalBox(){
        technicalLabel = new JLabel("Technical");
        technicalLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        technicalLabel.setForeground(primaryColor);
        technicalLabel.setFont(fontOfText);

        // page limit
        pageLimitLabel = new JLabel("Page Limit");
        pageLimitLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        pageLimitLabel.setForeground(secondaryColor);
        pageLimitLabel.setFont(mainFont);

        String[] pageLimitChoices = new String[] {"None", "1", "2", "3", "4", "5"};
        pageLimitComboBox = new JComboBox<>(pageLimitChoices);
        pageLimitComboBox.setVisible(true);
        pageLimitComboBox.setOpaque(false);
        pageLimitComboBox.setPreferredSize(new Dimension(100,35));
        pageLimitComboBox.setMaximumSize(new Dimension(100,35));
        pageLimitComboBox.setBorder(new EmptyBorder(5,5,5,5));
        pageLimitComboBox.setFont(comboBoxFont);
        pageLimitComboBox.setSelectedIndex(2);

        pageLimitComboBox.addActionListener(e -> {
            if (String.valueOf(pageLimitComboBox.getSelectedItem()).equals("None")) {
                pageLimit = 0;
            } else {
                pageLimit = Integer.parseInt(String.valueOf(pageLimitComboBox.getSelectedItem()));
            }
            metricController.updateBounce(pageLimit, bounceTime);
            chartController.updateBounce(pageLimit, bounceTime);
            recalculateMetrics();
            recalculateCharts();
        });

        // bounce time
        bounceTimeLabel = new JLabel("Bounce Time");
        bounceTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceTimeLabel.setForeground(secondaryColor);
        bounceTimeLabel.setFont(mainFont);

        String[] bounceTimeChoices = new String[] {"None", "100", "500", "1000", "2500"};
        bounceTimeComboBox = new JComboBox<>(bounceTimeChoices);
        bounceTimeComboBox.setVisible(true);
        bounceTimeComboBox.setOpaque(false);
        bounceTimeComboBox.setPreferredSize(new Dimension(100,35));
        bounceTimeComboBox.setMaximumSize(new Dimension(100,35));
        bounceTimeComboBox.setBorder(new EmptyBorder(5,5,5,5));
        bounceTimeComboBox.setFont(comboBoxFont);
        bounceTimeComboBox.setSelectedIndex(2);

        bounceTimeComboBox.addActionListener(e -> {
            if (String.valueOf(bounceTimeComboBox.getSelectedItem()).equals("None")) {
                bounceTime = 0;
            } else {
                bounceTime = Integer.parseInt(String.valueOf(bounceTimeComboBox.getSelectedItem()));
            }
            metricController.updateBounce(pageLimit, bounceTime);
            chartController.updateBounce(pageLimit, bounceTime);
            recalculateMetrics();
            recalculateCharts();
        });

        Box pageLimitHBox = Box.createHorizontalBox();
        pageLimitHBox.add(pageLimitLabel);
        pageLimitHBox.add(Box.createHorizontalStrut(300));
        pageLimitHBox.add(pageLimitComboBox);

        Box bounceTimeHBox = Box.createHorizontalBox();
        bounceTimeHBox.add(bounceTimeLabel);
        bounceTimeHBox.add(Box.createHorizontalStrut(300));
        bounceTimeHBox.add(bounceTimeComboBox);

        bounceVBox = Box.createVerticalBox();
        bounceVBox.setPreferredSize(new Dimension(settingsGrid.getWidth(),150));
        bounceVBox.setMaximumSize(new Dimension(settingsGrid.getWidth(),150));
        bounceVBox.add(technicalLabel);
        bounceVBox.add(pageLimitHBox);
        bounceVBox.add(bounceTimeHBox);
    }

    public void createSettingsGrid(){
        settingsGrid = new JPanel(new BorderLayout());
        settingsGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        settingsGrid.setBorder(new EmptyBorder(2,2,2,2));
        settingsGrid.setBackground(metricsGrid.getBackground());
        settingsGrid.setAlignmentY(100);
        settingsGrid.setVisible(false);
        settingsGrid.setOpaque(true);

        createSettingsStyleBox();
        createSettingsTechnicalBox();

        Box settingsVBox = Box.createVerticalBox();
        settingsVBox.setOpaque(true);
        settingsVBox.add(styleVerticalBox);
        settingsVBox.add(bounceVBox);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(settingsVBox);

        settingsGrid.add(wrapper,BorderLayout.CENTER);

        menu.add(settingsGrid);
    }

    public void updateColors(){
        if (filesMenu!=null) {
            filesMenu.setBackground(primaryColor);
        }
        styleLabel.setForeground(primaryColor);
        technicalLabel.setForeground(primaryColor);
        impressions.setForeground(secondaryColor);
        uniques.setForeground(secondaryColor);
        clicks.setForeground(secondaryColor);
        bounces.setForeground(secondaryColor);
        conversions.setForeground(secondaryColor);
        totalImpressionCost.setForeground(secondaryColor);
        totalClickCost.setForeground(secondaryColor);
        ctr.setForeground(secondaryColor);
        cpa.setForeground(secondaryColor);
        cpc.setForeground(secondaryColor);
        cpm.setForeground(secondaryColor);
        bounceRate.setForeground(secondaryColor);
        fontLabel.setForeground(secondaryColor);
        primaryColorLabel.setForeground(secondaryColor);
        secondaryColorLabel.setForeground(secondaryColor);
        pageLimitLabel.setForeground(secondaryColor);
        bounceTimeLabel.setForeground(secondaryColor);
        topMenu.setBackground(primaryColor);
        addChartToCompareButton.setBackground(primaryColor);
        resetCompareButton.setBackground(primaryColor);
        saveChartToFileButton.setBackground(primaryColor);
    }

    public void updateFont(){
        for(Component c: metricsNorthBox.getComponents()){
            if(c instanceof Box){
                for(Component c1 : ((Box) c).getComponents()){
                    if(c1 instanceof JLabel){
                        c1.setFont(mainFont);
                    }
                    if(c1 instanceof JComboBox){
                        c1.setFont(comboBoxFont);
                    }
                }
            }
        }

        for(Component c: chartNorthBox.getComponents()){
            if(c instanceof Box){
                for(Component c1 : ((Box) c).getComponents()){
                    if(c1 instanceof JLabel){
                        c1.setFont(mainFont);
                    }
                    if(c1 instanceof JComboBox){
                        c1.setFont(comboBoxFont);
                    }
                }
            }
        }

        metricsButton.setFont(mainFont);
        chartsButton.setFont(mainFont);
        histogramsButton.setFont(mainFont);
        compareButton.setFont(mainFont);
        settingsButton.setFont(mainFont);
        productName.setFont(mainFont);
        loadCampaignButton.setFont(mainFont);
        addChartToCompareButton.setFont(mainFont);
        resetCompareButton.setFont(mainFont);
        fontLabel.setFont(mainFont);
        primaryColorLabel.setFont(mainFont);
        secondaryColorLabel.setFont(mainFont);
        bounceTimeLabel.setFont(mainFont);
        pageLimitLabel.setFont(mainFont);

        impressionsValue.setFont(fontOfValue);
        uniquesValue.setFont(fontOfValue);
        clicksValue.setFont(fontOfValue);
        bounceValues.setFont(fontOfValue);
        conversionsValues.setFont(fontOfValue);
        totalImpressionCostValues.setFont(fontOfValue);
        totalClickCostValues.setFont(fontOfValue);
        ctrValues.setFont(fontOfValue);
        cpaValues.setFont(fontOfValue);
        cpcValues.setFont(fontOfValue);
        cpmValues.setFont(fontOfValue);
        bounceRateValues.setFont(fontOfValue);

        for(JLabel l : position.values()){
            l.setFont(comboBoxFont);
        }
    }

    // converts a metric to a readable string
    public String toString(float metric) {
        String dpMetric = String.valueOf(Math.round(metric * 10000.0) / 10000.0);

        StringBuilder sb = new StringBuilder(dpMetric);
        while (sb.length() > 0 && (sb.charAt(sb.length() - 1) == '0' || sb.charAt(sb.length() - 1) == '.')) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    // loads the files
    public void createCampaign() {
        new Thread(() -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
            changeFilters(false);
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            campaignController.createCampaign(impressionsFileLocation, clickFileLocation, serverFileLocation);
            createMetrics(campaignController.createMetrics());
            createCharts(campaignController.createCharts());
            createHistogram(campaignController.createCharts());

            gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            changeFilters(true);

            if(compareGrid != null) {
                menu.remove(compareGrid);
                menu.revalidate();
                menu.repaint();
            }

            createCompareGrid();
            metricsGrid.setVisible(true);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
            settingsGrid.setVisible(false);

            countCharts = 0;
        }).start();
    }

    // changes the filters accessibility
    public void changeFilters(boolean enable) {
        metricsGenderBox.setEnabled(enable);
        metricsAgeBox.setEnabled(enable);
        metricsContextBox.setEnabled(enable);
        metricsIncomeBox.setEnabled(enable);
        metricsStartDatePicker.setEnabled(enable);
        metricsEndDatePicker.setEnabled(enable);

        chartsMetricsBox.setEnabled(enable);
        chartsGenderBox.setEnabled(enable);
        chartsAgeBox.setEnabled(enable);
        chartsContextBox.setEnabled(enable);
        chartsIncomeBox.setEnabled(enable);
        chartsStartDatePicker.setEnabled(enable);
        chartsEndDatePicker.setEnabled(enable);

        pageLimitComboBox.setEnabled(enable);
        bounceTimeComboBox.setEnabled(enable);
    }

    // displays the metrics when loaded
    public void createMetrics(MetricCalculator metricCalculator) {
        metricController.createMetrics(metricCalculator);
        updateMetrics();
    }

    // calls the controller to recalculate the metrics
    public void recalculateMetrics() {
        try {
            new Thread(() -> {
                if (filesMenu!=null){
                    loadCampaignButton.setEnabled(true);
                    menu.remove(filesMenu);
                    menu.revalidate();
                    menu.repaint();
                }
                changeFilters(false);
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                metricController.updateMetrics(arrayOfChoicesMetrics.get(1), // gender
                        arrayOfChoicesMetrics.get(2), // age
                        arrayOfChoicesMetrics.get(3), // context
                        arrayOfChoicesMetrics.get(4), // income
                        arrayOfChoicesMetrics.get(5), // start date
                        arrayOfChoicesMetrics.get(6)); // end date
                updateMetrics();
                changeFilters(true);
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No campaign loaded!");
            changeFilters(true);
        }
    }

    // displays the updated metrics
    public void updateMetrics() {
        impressionsValue = new JLabel(toString(metricController.getImpressionsNo()));
        impressionsValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        impressionsValue.setFont(fontOfValue);
        impressionsBox.remove(0);
        impressionsBox.add(impressionsValue, 0);
        impressionsBox.repaint();
        impressionsBox.revalidate();

        clicksValue = new JLabel(toString(metricController.getClicksNo()));
        clicksValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        clicksValue.setFont(fontOfValue);
        clicksBox.remove(0);
        clicksBox.add(clicksValue, 0);
        clicksBox.repaint();
        clicksBox.revalidate();

        uniquesValue = new JLabel(toString(metricController.getUniquesNo()));
        uniquesValue.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        uniquesValue.setFont(fontOfValue);
        uniquesBox.remove(0);
        uniquesBox.add(uniquesValue, 0);
        uniquesBox.repaint();
        uniquesBox.revalidate();

        ctrValues = new JLabel(toString(metricController.getCtr()));
        ctrValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        ctrValues.setFont(fontOfValue);
        ctrBox.remove(0);
        ctrBox.add(ctrValues, 0);
        ctrBox.repaint();
        ctrBox.revalidate();

        cpaValues = new JLabel(toString(metricController.getCpa()));
        cpaValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpaValues.setFont(fontOfValue);
        cpaBox.remove(0);
        cpaBox.add(cpaValues, 0);
        cpaBox.repaint();
        cpaBox.revalidate();

        cpcValues = new JLabel(toString(metricController.getCpc()));
        cpcValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpcValues.setFont(fontOfValue);
        cpcBox.remove(0);
        cpcBox.add(cpcValues, 0);
        cpcBox.repaint();
        cpcBox.revalidate();

        cpmValues = new JLabel(toString(metricController.getCpm()));
        cpmValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpmValues.setFont(fontOfValue);
        cpmBox.remove(0);
        cpmBox.add(cpmValues, 0);
        cpmBox.repaint();
        cpmBox.revalidate();

        conversionsValues = new JLabel(toString(metricController.getConversionsNo()));
        conversionsValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        conversionsValues.setFont(fontOfValue);
        conversionsBox.remove(0);
        conversionsBox.add(conversionsValues, 0);
        conversionsBox.repaint();
        conversionsBox.revalidate();

        totalImpressionCostValues = new JLabel(toString(metricController.getTotalImpressionCost()));
        totalImpressionCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalImpressionCostValues.setFont(fontOfValue);
        totalImpressionCostBox.remove(0);
        totalImpressionCostBox.add(totalImpressionCostValues, 0);
        totalImpressionCostBox.repaint();
        totalImpressionCostBox.revalidate();

        totalClickCostValues = new JLabel(toString(metricController.getTotalClickCost()));
        totalClickCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalClickCostValues.setFont(fontOfValue);
        totalClickCostBox.remove(0);
        totalClickCostBox.add(totalClickCostValues, 0);
        totalClickCostBox.repaint();
        totalClickCostBox.revalidate();

        bounceValues = new JLabel(toString(metricController.getBouncesNo()));
        bounceValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceValues.setFont(fontOfValue);
        bouncesBox.remove(0);
        bouncesBox.add(bounceValues, 0);
        bouncesBox.repaint();
        bouncesBox.revalidate();

        bounceRateValues = new JLabel(toString(metricController.getBr()));
        bounceRateValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bounceRateValues.setFont(fontOfValue);
        bounceRateBox.remove(0);
        bounceRateBox.add(bounceRateValues, 0);
        bounceRateBox.repaint();
        bounceRateBox.revalidate();
    }

    // displays the initial chart when loaded
    public void createCharts(ChartCalculator calculator) {
        chartController.createCharts(calculator);
        updateCharts();
    }

    // calls the controller to recalculate the charts
    public void recalculateCharts() {
        try {
            new Thread(() -> {
                if (filesMenu!=null){
                    loadCampaignButton.setEnabled(true);
                    menu.remove(filesMenu);
                    menu.revalidate();
                    menu.repaint();
                }
                changeFilters(false);
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                chartController.updateCharts(arrayOfChoicesChart.get(0), // metric
                        arrayOfChoicesChart.get(1), // gender
                        arrayOfChoicesChart.get(2), // age
                        arrayOfChoicesChart.get(3), // context
                        arrayOfChoicesChart.get(4), // income
                        arrayOfChoicesChart.get(5), // start date
                        arrayOfChoicesChart.get(6)); // end date
                updateCharts();
                changeFilters(true);
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No campaign loaded!");
            changeFilters(true);
        }
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

    // displays the initial chart when loaded
    public void createHistogram(ChartCalculator calculator) {
        chartController.createHistogram(calculator);

        histogramPanel = new ChartPanel(chartController.getHistogram());
        histogramJPanel.remove(0);
        histogramJPanel.add(histogramPanel, BorderLayout.CENTER);
        histogramJPanel.revalidate();
    }
}