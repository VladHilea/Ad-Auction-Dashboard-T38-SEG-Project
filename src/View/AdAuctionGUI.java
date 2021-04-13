package View;

import Controllers.CampaignController;
import Controllers.ChartController;
import Controllers.MetricController;
import Models.*;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class AdAuctionGUI extends JFrame {
    // primary gui components
    private JFrame gui;
    private JLayeredPane menu;
    private JPanel metricsGrid, verticalMenu;

    // components for campaigns
    private JPanel filesMenu;
    private JFileChooser fileChooser;
    private JLabel impressionFileLabel, clickFileLabel, serverFileLabel;
    private String impressionsFileLocation, clickFileLocation, serverFileLocation;
    private JButton loadCampaignButton;
    private JLabel loadingImage = new JLabel();

    // components for metrics
    private JComboBox<String> metricsGenderBox, metricsAgeBox, metricsContextBox, metricsIncomeBox, metricsStartDateBox, metricsEndDateBox;
    private JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalCostValues, bounceValues, bounceRateValues;
    private Box impressionsBox, clicksBox, uniquesBox, ctrBox, cpaBox, cpcBox, cpmBox, conversionsBox, totalCostBox, bouncesBox, bounceRateBox;

    // components for charts
    private JComboBox<String> chartsMetricsBox, chartsGenderBox, chartsAgeBox, chartsContextBox, chartsIncomeBox, chartsStartDateBox, chartsEndDateBox;
    private JPanel chartsGrid, chartJPanel;
    private ChartPanel chartPanel;
    private JSlider chartSlider;

    // components for histograms
    private JComboBox<String> histogramMetricsBox, histogramGenderBox, histogramAgeBox, histogramContextBox, histogramIncomeBox, histogramStartDateBox, histogramEndDateBox;
    private JPanel histogramGrid;
    private JSlider histogramSlider;

    private JPanel compareGrid, compareChartsGrid;

    private final ArrayList<String> arrayOfChoicesMetrics = new ArrayList<>();
    private final ArrayList<String> arrayOfChoicesChart = new ArrayList<>();
    private final ArrayList<String> arrayOfChoicesHistogram = new ArrayList<>();
    private int countCharts = 0;

    // model controllers
    private final CampaignController campaignController;
    private final MetricController metricController;
    private final ChartController chartController;

    // colours
    private final Color primaryColor = new Color(14,139,229);
    private final Color secondaryColor = new Color(220,120,27);
    private final Color tertiaryColor = new Color(242,236,236);
    private final Color noColor = Color.WHITE;

    // fonts
    private final Font mainFont = new Font("Impact", Font.PLAIN, 15);
    private final Font fontOfText = new Font("Impact", Font.PLAIN, 25);
    private final Font fontOfValue = new Font("Impact", Font.BOLD, 30);
    private final Font comboBoxFont = new Font(fontOfText.getName(), Font.PLAIN, 14);

    // initialises the display controllers
    public AdAuctionGUI() {
        this.campaignController = new CampaignController();
        this.metricController = new MetricController();
        this.chartController = new ChartController();

        chartPanel = new ChartPanel(new Chart("Blank Chart", "Impressions", "Days").getChart());
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
    }

    // displays the main structure
    public void createMenu(){
        menu = new JLayeredPane();
        menu.setSize(gui.getWidth(),gui.getHeight());
        menu.setOpaque(true);
        menu.setBackground(noColor);

        createTopMenu();
        createVerticalMenu();
        createMetricsGrid();
        createChartsGrid();
        createHistogramsGrid();
        createCompareGrid();

        metricsGrid.setVisible(true);
        chartsGrid.setVisible(false);
        histogramGrid.setVisible(false);
        compareGrid.setVisible(false);

        disableFilters();
    }

    // displays the constant vertical menu on the lift hand size
    public void createVerticalMenu(){
        verticalMenu = new JPanel(new GridLayout(5, 1));
        verticalMenu.setBounds(0,100,200,gui.getHeight()-100);
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(tertiaryColor);

        //start panel
        JPanel metricsButtonPanel = new JPanel(new BorderLayout());
        metricsButtonPanel.setOpaque(false);
        metricsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        JButton metricsButton = new JButton("Metrics");
        metricsButton.setFont(mainFont);
        metricsButton.setBorderPainted(false);
        metricsButton.setContentAreaFilled(false);
        metricsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        metricsButtonPanel.add(metricsButton);
        //end panel

        //start panel
        JPanel chartsButtonPanel = new JPanel(new BorderLayout());
        chartsButtonPanel.setOpaque(false);
        chartsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        JButton chartsButton = new JButton("Charts");
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
        compareButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

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
        settingsButtonPanel.setBorder(BorderFactory.createLineBorder(noColor,2));

        JButton settingsButton = new JButton("Settings");
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

        metricsButton.addActionListener(e -> {
            metricsGrid.setVisible(true);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        chartsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(true);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        histogramsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(true);
            compareGrid.setVisible(false);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        compareButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(true);

            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }
        });

        settingsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);

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
        JPanel topMenu = new JPanel(new GridLayout(1, 3));
        topMenu.setSize(gui.getWidth(),100);
        topMenu.setOpaque(true);
        topMenu.setBackground(primaryColor);
        topMenu.setBorder(new EmptyBorder(10, 10, 10, 10));

        // product title
        JLabel productName = new JLabel("Ad Monitor");
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

        // TESTING ONLY
        // fast load campaign button
        JPanel fastCampaignButtonPanel = new JPanel(new BorderLayout());
        fastCampaignButtonPanel.setOpaque(false);

        JButton fastCampaignButton = new JButton("Fast Load Campaign (TESTING/DEMONSTRATION ONLY!)");
        fastCampaignButton.setFont(mainFont);
        fastCampaignButton.setBorderPainted(false);
        fastCampaignButton.setContentAreaFilled(false);
        fastCampaignButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fastCampaignButton.setForeground(noColor);

        fastCampaignButtonPanel.add(fastCampaignButton);

        // fast load files of campaign
        fastCampaignButton.addActionListener(e -> {
            fastCreateCampaign();
        });
        // TESTING ONLY

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
        topRight.add(fastCampaignButtonPanel); // TEMPORARY FOR TESTING ONLY
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

    /*
     These statements enlarge the button so that it
     becomes a circle rather than an oval.
    */
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width , size.height);
            setPreferredSize(size);

    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
            setContentAreaFilled(false);
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

        // Hit detection.
        Shape shape;

        public boolean contains(int x , int y) {
            // If the button has changed size,  make a new shape object.
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0 , 0 , getWidth() , getHeight());
            }
            return shape.contains(x , y);
        }
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

        // files selection button functions
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
        String[] startDateChoices = new String[]{"Any"};
        metricsStartDateBox = new JComboBox<>(startDateChoices);
        metricsStartDateBox.setVisible(true);
        metricsStartDateBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsStartDateBox.setFont(comboBoxFont);

        metricsStartDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsStartDateBox.getSelectedItem());
            arrayOfChoicesMetrics.set(5, itemName);
            recalculateMetrics();
        });

        JLabel startDateLabel = new JLabel("START DATE");
        startDateLabel.setFont(mainFont);
        startDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box startDateVerticalBox = Box.createVerticalBox();
        startDateVerticalBox.add(startDateLabel);
        startDateVerticalBox.add(metricsStartDateBox);
        //end box

        //start Box
        String[] endDateChoices = new String[]{"Any"};
        metricsEndDateBox = new JComboBox<>(endDateChoices);
        metricsEndDateBox.setVisible(true);
        metricsEndDateBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsEndDateBox.setFont(comboBoxFont);

        metricsEndDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(metricsEndDateBox.getSelectedItem());
            arrayOfChoicesMetrics.set(6, itemName);
            recalculateMetrics();
        });

        JLabel endDateLabel = new JLabel("END DATE");
        endDateLabel.setFont(mainFont);
        endDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box endDateVerticalBox = Box.createVerticalBox();
        endDateVerticalBox.add(endDateLabel);
        endDateVerticalBox.add(metricsEndDateBox);
        //end box

        Box metricsNorthBox = Box.createHorizontalBox();
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

        JPanel metricsPanel = new JPanel(new GridLayout(4, 3));

        metricsPanel.add(impressionsPanel);
        metricsPanel.add(clicksPanel);
        metricsPanel.add(uniquesPanel);
        metricsPanel.add(ctrPanel);
        metricsPanel.add(cpaPanel);
        metricsPanel.add(cpcPanel);
        metricsPanel.add(cpmPanel);
        metricsPanel.add(conversionsPanel);
        metricsPanel.add(totalCostPanel);
        metricsPanel.add(bouncesPanel);
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
        String[] metricsChoices = new String[]{"Impressions", "CPA", "CPC", "CPM", "CTR", "Uniques", "Bounces", "Bounce Rate", "Clicks", "Conversions", "Total Impression Cost"};
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
        String[] startDateChoices = new String[]{"Any"};
        chartsStartDateBox = new JComboBox<>(startDateChoices);
        chartsStartDateBox.setVisible(true);
        chartsStartDateBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsStartDateBox.setFont(comboBoxFont);

        chartsStartDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsStartDateBox.getSelectedItem());
            arrayOfChoicesHistogram.set(5, itemName);
        });

        JLabel startDateLabel = new JLabel("START DATE");
        startDateLabel.setFont(mainFont);
        startDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box startDateVerticalBox = Box.createVerticalBox();
        startDateVerticalBox.add(startDateLabel);
        startDateVerticalBox.add(chartsStartDateBox);
        //end box

        //start Box
        String[] endDateChoices = new String[]{"Any"};
        chartsEndDateBox = new JComboBox<>(endDateChoices);
        chartsEndDateBox.setVisible(true);
        chartsEndDateBox.setBorder(new EmptyBorder(5,5,5,5));
        chartsEndDateBox.setFont(comboBoxFont);

        chartsEndDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(chartsEndDateBox.getSelectedItem());
            arrayOfChoicesHistogram.set(6, itemName);
        });

        JLabel endDateLabel = new JLabel("END DATE");
        endDateLabel.setFont(mainFont);
        endDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box endDateVerticalBox = Box.createVerticalBox();
        endDateVerticalBox.add(endDateLabel);
        endDateVerticalBox.add(chartsEndDateBox);
        //end box

        Box chartNorthBox = Box.createHorizontalBox();
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

        // components for compare
        JPanel addChartToComparePanel = new JPanel(new GridBagLayout());
        addChartToComparePanel.setOpaque(false);
        addChartToComparePanel.setBorder(new EmptyBorder(10,10,10,10));

        JButton addChartToCompareButton = new JButton("Add to Compare");
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
                countCharts++;

                switch (arrayOfChoicesChart.get(7)) {
                    case "Hours": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getHoursChart());
                        chartPanel.setPreferredSize(new Dimension(500, 300));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Days": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getDaysChart());
                        chartPanel.setPreferredSize(new Dimension(500, 300));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Weeks": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getWeeksChart());
                        chartPanel.setPreferredSize(new Dimension(500, 300));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Months": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getMonthsChart());
                        chartPanel.setPreferredSize(new Dimension(500, 300));
                        chartJPanel.add(chartPanel);
                        chartJPanel.validate();
                        panel.add(chartJPanel);
                        compareChartsGrid.add(panel);
                        break;
                    }
                    case "Years": {
                        ChartPanel chartPanel = new ChartPanel(chartController.getYearsChart());
                        chartPanel.setPreferredSize(new Dimension(500, 300));
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

        JPanel chartSouthGrid = new JPanel(new GridLayout(1, 2));
        chartSouthGrid.setPreferredSize(new Dimension(chartsGrid.getWidth(),200));
        chartSouthGrid.setBorder(new EmptyBorder(10,10,10,10));

        chartSouthGrid.add(chartSlider);
        chartSouthGrid.add(addChartToComparePanel);

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

    public void createHistogramNorthBox() {
        // start Box
        String[] metricChoices = new String[] {"Metrics","Impressions","CPA","CPC","CPM","CTR","Uniques","Bounce","Bounce Rate","Clicks","Conversions","Total Cost"};
        histogramMetricsBox = new JComboBox<>(metricChoices);
        histogramMetricsBox.setVisible(true);
        histogramMetricsBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramMetricsBox.setFont(comboBoxFont);

        histogramMetricsBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramMetricsBox.getSelectedItem());
            arrayOfChoicesHistogram.set(0, itemName);
        });

        JLabel metricsLabel = new JLabel("METRICS");
        metricsLabel.setFont(mainFont);
        metricsLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box metricsVerticalBox = Box.createVerticalBox();
        metricsVerticalBox.add(metricsLabel);
        metricsVerticalBox.add(histogramMetricsBox);

        //end box

        //start Box
        String[] genderChoices = new String[] {"Any","Male","Female"};
        histogramGenderBox = new JComboBox<>(genderChoices);
        histogramGenderBox.setVisible(true);
        histogramGenderBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramGenderBox.setFont(comboBoxFont);

        histogramGenderBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramGenderBox.getSelectedItem());
            arrayOfChoicesHistogram.set(1, itemName);
        });

        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(mainFont);
        genderLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box genderVerticalBox = Box.createVerticalBox();
        genderVerticalBox.add(genderLabel);
        genderVerticalBox.add(histogramGenderBox);
        //end box

        //start Box
        String[] ageChoices = new String[]{"Any", "<25", "25-34", "35-44", "45-54", ">54"};
        histogramAgeBox = new JComboBox<>(ageChoices);
        histogramAgeBox.setVisible(true);
        histogramAgeBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramAgeBox.setFont(comboBoxFont);

        histogramAgeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramAgeBox.getSelectedItem());
            arrayOfChoicesHistogram.set(2, itemName);
        });

        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(mainFont);
        ageLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box ageVerticalBox = Box.createVerticalBox();
        ageVerticalBox.add(ageLabel);
        ageVerticalBox.add(histogramAgeBox);
        //end box

        //start Box
        String[] contextChoices = new String[]{"Any", "Blog", "Social Media", "Shopping", "News"};
        histogramContextBox = new JComboBox<>(contextChoices);
        histogramContextBox.setVisible(true);
        histogramContextBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramContextBox.setFont(comboBoxFont);

        histogramContextBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramContextBox.getSelectedItem());
            arrayOfChoicesHistogram.set(3, itemName);
        });

        JLabel contextLabel = new JLabel("CONTEXT");
        contextLabel.setFont(mainFont);
        contextLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box contextVerticalBox = Box.createVerticalBox();
        contextVerticalBox.add(contextLabel);
        contextVerticalBox.add(histogramContextBox);
        //end box

        //start Box
        String[] incomeChoices = new String[]{"Any", "Low", "Medium", "High"};
        histogramIncomeBox = new JComboBox<>(incomeChoices);
        histogramIncomeBox.setVisible(true);
        histogramIncomeBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramIncomeBox.setFont(comboBoxFont);

        histogramIncomeBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramIncomeBox.getSelectedItem());
            arrayOfChoicesHistogram.set(4, itemName);
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(histogramIncomeBox);
        //end box

        //start Box
        String[] startDateChoices = new String[]{"Any"};
        histogramStartDateBox = new JComboBox<>(startDateChoices);
        histogramStartDateBox.setVisible(true);
        histogramStartDateBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramStartDateBox.setFont(comboBoxFont);

        histogramStartDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramStartDateBox.getSelectedItem());
            arrayOfChoicesHistogram.set(5, itemName);
        });

        JLabel startDateLabel = new JLabel("START DATE");
        startDateLabel.setFont(mainFont);
        startDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box startDateVerticalBox = Box.createVerticalBox();
        startDateVerticalBox.add(startDateLabel);
        startDateVerticalBox.add(histogramStartDateBox);
        //end box

        //start Box
        String[] endDateChoices = new String[]{"Any"};
        histogramEndDateBox = new JComboBox<>(endDateChoices);
        histogramEndDateBox.setVisible(true);
        histogramEndDateBox.setBorder(new EmptyBorder(5,5,5,5));
        histogramEndDateBox.setFont(comboBoxFont);

        histogramEndDateBox.addActionListener(e -> {
            if (filesMenu!=null){
                loadCampaignButton.setEnabled(true);
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
            }

            String itemName = String.valueOf(histogramEndDateBox.getSelectedItem());
            arrayOfChoicesHistogram.set(6, itemName);
        });

        JLabel endDateLabel = new JLabel("END DATE");
        endDateLabel.setFont(mainFont);
        endDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box endDateVerticalBox = Box.createVerticalBox();
        endDateVerticalBox.add(endDateLabel);
        endDateVerticalBox.add(histogramEndDateBox);
        //end box

        Box histogramNorthBox = Box.createHorizontalBox();
        histogramNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        histogramNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        histogramNorthBox.add(metricsVerticalBox);
        histogramNorthBox.add(genderVerticalBox);
        histogramNorthBox.add(ageVerticalBox);
        histogramNorthBox.add(contextVerticalBox);
        histogramNorthBox.add(incomeVerticalBox);
        histogramNorthBox.add(startDateVerticalBox);
        histogramNorthBox.add(endDateVerticalBox);

        histogramGrid.add(histogramNorthBox,BorderLayout.NORTH);
    }

    public void createHistogramSouthGrid(){
        histogramSlider = new JSlider(JSlider.HORIZONTAL,0,4,1);
        histogramSlider.setVisible(true);
        histogramSlider.setMajorTickSpacing(1);
        histogramSlider.setPaintTicks(true);
        histogramSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> position = new Hashtable<>();
        position.put(0, new JLabel("Hours"));
        position.put(1, new JLabel("Days"));
        position.put(2, new JLabel("Weeks"));
        position.put(3, new JLabel("Months"));
        position.put(4, new JLabel("Years"));

        histogramSlider.setLabelTable(position);

        histogramSlider.addChangeListener(e -> {
            String sliderValue = position.get(histogramSlider.getValue()).getText();

            if (!arrayOfChoicesHistogram.get(7).equals(sliderValue)) {
                arrayOfChoicesHistogram.set(7, sliderValue);
            }
        });

        JPanel addHistogramToComparePanel = new JPanel(new GridBagLayout());
        addHistogramToComparePanel.setOpaque(false);
        addHistogramToComparePanel.setBorder(new EmptyBorder(10,10,10,10));

        JButton addHistogramToCompareButton = new JButton("Add to Compare");
        addHistogramToCompareButton.setFont(mainFont);
        addHistogramToCompareButton.setBackground(primaryColor);
        addHistogramToCompareButton.setForeground(noColor);
        addHistogramToCompareButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addHistogramToCompareButton.setPreferredSize(new Dimension(400,40));

        addHistogramToComparePanel.add(addHistogramToCompareButton);

        addHistogramToCompareButton.addActionListener(e -> {
            // no histograms to add
        });

        JPanel histogramSouthGrid = new JPanel(new GridLayout(1, 2));
        histogramSouthGrid.setPreferredSize(new Dimension(histogramSouthGrid.getWidth(),200));
        histogramSouthGrid.setBorder(new EmptyBorder(10,10,10,10));

        histogramSouthGrid.add(histogramSlider);
        histogramSouthGrid.add(addHistogramToComparePanel);

        histogramGrid.add(histogramSouthGrid,BorderLayout.SOUTH);
    }

    public void createHistogramsGrid(){
        arrayOfChoicesHistogram.add("Metrics");
        arrayOfChoicesHistogram.add("Any");
        arrayOfChoicesHistogram.add("Any");
        arrayOfChoicesHistogram.add("Any");
        arrayOfChoicesHistogram.add("Any");
        arrayOfChoicesHistogram.add("Days");

        histogramGrid = new JPanel(new BorderLayout());
        histogramGrid.setBounds(200,100,gui.getWidth()-200,gui.getHeight()-100);
        histogramGrid.setBackground(metricsGrid.getBackground());
        histogramGrid.setAlignmentY(100);
        histogramGrid.setVisible(false);

        createHistogramNorthBox();
        createHistogramSouthGrid();

        //create panel for chart
        menu.add(histogramGrid);
    }

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

        JButton resetCompareButton = new JButton("Reset Compare");
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

            menu.remove(compareGrid);
            createCompareGrid();
        });
    }

    // converts a metric to a readable string
    public String toString(float metric) {
        if (metric == (int) metric)
            return String.format("%d", (int) metric);
        else
            return String.format("%.4g%n", metric); // change the 4 to change the dp
    }

    // loads the files
    public void createCampaign() {
        new Thread(() -> {
            disableFilters();
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            campaignController.createCampaign(impressionsFileLocation, clickFileLocation, serverFileLocation);
            createMetrics(campaignController.createMetrics());

            enableFilters();
            createCharts(campaignController.createCharts());
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

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

            countCharts = 0;
        }).start();
    }

    // fast load the campaign for testing only
    public void fastCreateCampaign() {
        new Thread(() -> {
            disableFilters();
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            campaignController.createCampaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");
            createMetrics(campaignController.createMetrics());

            enableFilters();
            createCharts(campaignController.createCharts());
            gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

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

            countCharts = 0;
        }).start();
    }

    // enables the filter dropdown menus
    public void enableFilters() {
        metricsGenderBox.setEnabled(true);
        metricsAgeBox.setEnabled(true);
        metricsContextBox.setEnabled(true);
        metricsIncomeBox.setEnabled(true);
        metricsStartDateBox.setEnabled(true);
        metricsEndDateBox.setEnabled(true);

        chartsMetricsBox.setEnabled(true);
        chartsGenderBox.setEnabled(true);
        chartsAgeBox.setEnabled(true);
        chartsContextBox.setEnabled(true);
        chartsIncomeBox.setEnabled(true);
        chartsStartDateBox.setEnabled(true);
        chartsEndDateBox.setEnabled(true);

        histogramMetricsBox.setEnabled(true);
        histogramGenderBox.setEnabled(true);
        histogramAgeBox.setEnabled(true);
        histogramContextBox.setEnabled(true);
        histogramIncomeBox.setEnabled(true);
        histogramStartDateBox.setEnabled(true);
        histogramEndDateBox.setEnabled(true);
    }

    // disables the filter dropdown menus
    public void disableFilters() {
        metricsGenderBox.setEnabled(false);
        metricsAgeBox.setEnabled(false);
        metricsContextBox.setEnabled(false);
        metricsIncomeBox.setEnabled(false);
        metricsStartDateBox.setEnabled(false);
        metricsEndDateBox.setEnabled(false);

        chartsMetricsBox.setEnabled(false);
        chartsGenderBox.setEnabled(false);
        chartsAgeBox.setEnabled(false);
        chartsContextBox.setEnabled(false);
        chartsIncomeBox.setEnabled(false);
        chartsStartDateBox.setEnabled(false);
        chartsEndDateBox.setEnabled(false);

        histogramMetricsBox.setEnabled(false);
        histogramGenderBox.setEnabled(false);
        histogramAgeBox.setEnabled(false);
        histogramContextBox.setEnabled(false);
        histogramIncomeBox.setEnabled(false);
        histogramStartDateBox.setEnabled(false);
        histogramEndDateBox.setEnabled(false);
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
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                disableFilters();
                metricController.updateMetrics(arrayOfChoicesMetrics.get(1), // gender
                        arrayOfChoicesMetrics.get(2), // age
                        arrayOfChoicesMetrics.get(3), // context
                        arrayOfChoicesMetrics.get(4), // income
                        arrayOfChoicesMetrics.get(5), // start date
                        arrayOfChoicesMetrics.get(6)); // start date
                updateMetrics();
                enableFilters();
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No campaign loaded!");
            enableFilters();
        }
    }

    // displays the updated metrics
    public void updateMetrics() {
        impressionsValue = new JLabel(String.valueOf(metricController.getImpressionsNo()));
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

        totalCostValues = new JLabel(toString(metricController.getTotalImpressionCost()));
        totalCostValues.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        totalCostValues.setFont(fontOfValue);
        totalCostBox.remove(0);
        totalCostBox.add(totalCostValues, 0);
        totalCostBox.repaint();
        totalCostBox.revalidate();

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
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                disableFilters();
                chartController.updateCharts(arrayOfChoicesChart.get(0), // metric
                        arrayOfChoicesChart.get(1), // gender
                        arrayOfChoicesChart.get(2), // age
                        arrayOfChoicesChart.get(3), // context
                        arrayOfChoicesChart.get(4), // income
                        arrayOfChoicesChart.get(5), // start date
                        arrayOfChoicesChart.get(6)); // end date
                updateCharts();
                enableFilters();
                gui.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No campaign loaded!");
            enableFilters();
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
}