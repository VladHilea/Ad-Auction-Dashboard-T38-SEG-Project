package View;

import Controllers.CampaignController;
import Controllers.ChartController;
import Controllers.MetricController;
import Models.ChartCalculator;
import Models.MetricCalculator;
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
    private JPanel metricsGrid;

    JButton loadCampaignButton;

    // components for campaigns
    private JPanel filesMenu;
    private JFileChooser fileChooser;
    private JLabel impressionFileLabel, clickFileLabel, serverFileLabel;
    private String impressionsFileLocation, clickFileLocation, serverFileLocation;

    // components for metrics
    private JLabel impressionsValue, clicksValue, uniquesValue, ctrValues, cpaValues, cpcValues, cpmValues, conversionsValues, totalCostValues, bounceValues, bounceRateValues;
    private Box impressionsBox, clicksBox, uniquesBox, ctrBox, cpaBox, cpcBox, cpmBox, conversionsBox, totalCostBox, bouncesBox, bounceRateBox;

    // components for charts
    private JPanel chartsGrid;
    private JPanel chartJPanel;
    private ChartPanel chartPanel;
    private JSlider chartSlider;

    // components for histograms
    private JPanel histogramGrid;
    private JSlider histogramSlider;

    private JPanel compareGrid;
    private JPanel compareChartsGrid;


    private final ArrayList<String> arrayOfChoicesMetrics = new ArrayList<>();
    private final ArrayList<String> arrayOfChoicesChart = new ArrayList<>();
    private final ArrayList<String> arrayOfChoicesHistogram = new ArrayList<>();
    //private final ArrayList<JFreeChart> chartsToCompare = new ArrayList<>();
    private int countCharts = 0;

    // model controllers
    private final CampaignController campaignController;
    private final MetricController metricController;
    private final ChartController chartController;

    // gui styling
    private final Color primaryColor = new Color(14,139,229);
    private final Color secondaryColor = new Color(220,120,27);
    private final Color tertiaryColor = new Color(242,236,236);
    private final Color noColor = Color.WHITE;
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
        gui.setUndecorated(true);
        gui.setResizable(false);
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
        });

        chartsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(true);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
        });

        histogramsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(true);
            compareGrid.setVisible(false);
        });

        compareButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(true);
        });

        settingsButton.addActionListener(e -> {
            metricsGrid.setVisible(false);
            chartsGrid.setVisible(false);
            histogramGrid.setVisible(false);
            compareGrid.setVisible(false);
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
        loadCampaignButton.addActionListener(e -> {
                createFileLoadBox();

        });

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

    // displays a close button
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

        loadCampaignButton.setEnabled(false);
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
        JButton loadCampaignButtonLocalLocal = new JButton("LoadCampaign");
        loadCampaignButtonLocalLocal.setFont(mainFont);
        loadCampaignButtonLocalLocal.setBorderPainted(false);
        loadCampaignButtonLocalLocal.setBackground(noColor);
        loadCampaignButtonLocalLocal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // loads the campaign
        loadCampaignButtonLocalLocal.addActionListener(e -> {
            loadCampaignButton.setEnabled(false);
            try {
                createCampaign();
                menu.remove(filesMenu);
                menu.revalidate();
                menu.repaint();
                loadCampaignButtonLocalLocal.setEnabled(true);

            } catch (Exception invalidCampaignE) {
                JOptionPane.showMessageDialog(null, "Invalid campaign files!");
            }
        });


        JButton cancelLoadCampaign = new JButton("Cancel");
        cancelLoadCampaign.setFont(mainFont);
        cancelLoadCampaign.setBorderPainted(false);
        cancelLoadCampaign.setBackground(noColor);
        cancelLoadCampaign.setForeground(Color.RED);
        cancelLoadCampaign.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        filesMenu.add(loadCampaignButtonLocalLocal, 6);
        filesMenu.add(cancelLoadCampaign);

        menu.add(filesMenu, 0);

        cancelLoadCampaign.addActionListener(actionEvent -> {
            loadCampaignButton.setEnabled(true);
            menu.remove(filesMenu);
            menu.revalidate();
            menu.repaint();


        });
    }

    // displays the metrics filters
    public void createMetricsNorthBox() {
        //start Box
        String[] genderChoices = new String[] {"Any","Male","Female"};
        JComboBox<String> genderBox = new JComboBox<>(genderChoices);
        genderBox.setVisible(true);
        genderBox.setBorder(new EmptyBorder(5,5,5,5));
        genderBox.setFont(comboBoxFont);

        genderBox.addActionListener(e -> {
            String itemName = String.valueOf(genderBox.getSelectedItem());
            arrayOfChoicesMetrics.set(1, itemName);
            recalculateMetrics();
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
        ageBox.setBorder(new EmptyBorder(5,5,5,5));
        ageBox.setFont(comboBoxFont);

        ageBox.addActionListener(e -> {
            String itemName = String.valueOf(ageBox.getSelectedItem());
            arrayOfChoicesMetrics.set(2, itemName);
            recalculateMetrics();
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
        contextBox.setBorder(new EmptyBorder(5,5,5,5));
        contextBox.setFont(comboBoxFont);

        contextBox.addActionListener(e -> {
            String itemName = String.valueOf(contextBox.getSelectedItem());
            arrayOfChoicesMetrics.set(3, itemName);
            recalculateMetrics();
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
        incomeBox.setBorder(new EmptyBorder(5,5,5,5));
        incomeBox.setFont(comboBoxFont);

        incomeBox.addActionListener(e -> {
            String itemName = String.valueOf(incomeBox.getSelectedItem());
            arrayOfChoicesMetrics.set(4, itemName);
            recalculateMetrics();
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(incomeBox);
        //end box

        Box metricsNorthBox = Box.createHorizontalBox();
        metricsNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        metricsNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        metricsNorthBox.add(genderVerticalBox);
        metricsNorthBox.add(ageVerticalBox);
        metricsNorthBox.add(contextVerticalBox);
        metricsNorthBox.add(incomeVerticalBox);

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
        JComboBox<String> metricsBox = new JComboBox<>(metricsChoices);
        metricsBox.setVisible(true);
        metricsBox.setBorder(new EmptyBorder(5,5,5,5));
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
        genderBox.setBorder(new EmptyBorder(5,5,5,5));
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
        ageBox.setBorder(new EmptyBorder(5,5,5,5));
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
        contextBox.setBorder(new EmptyBorder(5,5,5,5));
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
        incomeBox.setBorder(new EmptyBorder(5,5,5,5));
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
        chartNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        chartNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        chartNorthBox.add(metricsVerticalBox);
        chartNorthBox.add(genderVerticalBox);
        chartNorthBox.add(ageVerticalBox);
        chartNorthBox.add(contextVerticalBox);
        chartNorthBox.add(incomeVerticalBox);

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
            if (countCharts < 4){
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
        }
            else {
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
        JComboBox<String> metricsBox = new JComboBox<>(metricChoices);
        metricsBox.setVisible(true);
        metricsBox.setBorder(new EmptyBorder(5,5,5,5));
        metricsBox.setFont(comboBoxFont);

        metricsBox.addActionListener(e -> {
            String itemName = String.valueOf(metricsBox.getSelectedItem());
            arrayOfChoicesHistogram.set(0, itemName);
        });

        JLabel metricsLabel = new JLabel("METRICS");
        metricsLabel.setFont(mainFont);
        metricsLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box metricsVerticalBox = Box.createVerticalBox();
        metricsVerticalBox.add(metricsLabel);
        metricsVerticalBox.add(metricsBox);

        //end box

        //start Box
        String[] genderChoices = new String[] {"Any","Male","Female"};
        JComboBox<String> genderBox = new JComboBox<>(genderChoices);
        genderBox.setVisible(true);
        genderBox.setBorder(new EmptyBorder(5,5,5,5));
        genderBox.setFont(comboBoxFont);

        genderBox.addActionListener(e -> {
            String itemName = String.valueOf(genderBox.getSelectedItem());
            arrayOfChoicesHistogram.set(1, itemName);
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
        ageBox.setBorder(new EmptyBorder(5,5,5,5));
        ageBox.setFont(comboBoxFont);

        ageBox.addActionListener(e -> {
            String itemName = String.valueOf(ageBox.getSelectedItem());
            arrayOfChoicesHistogram.set(2, itemName);
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
        contextBox.setBorder(new EmptyBorder(5,5,5,5));
        contextBox.setFont(comboBoxFont);

        contextBox.addActionListener(e -> {
            String itemName = String.valueOf(contextBox.getSelectedItem());
            arrayOfChoicesHistogram.set(3, itemName);
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
        incomeBox.setBorder(new EmptyBorder(5,5,5,5));
        incomeBox.setFont(comboBoxFont);

        incomeBox.addActionListener(e -> {
            String itemName = String.valueOf(incomeBox.getSelectedItem());
            arrayOfChoicesHistogram.set(4, itemName);
        });

        JLabel incomeLabel = new JLabel("INCOME");
        incomeLabel.setFont(mainFont);
        incomeLabel.setAlignmentX(CENTER_ALIGNMENT);

        Box incomeVerticalBox = Box.createVerticalBox();
        incomeVerticalBox.add(incomeLabel);
        incomeVerticalBox.add(incomeBox);
        //end box

        Box histogramNorthBox = Box.createHorizontalBox();
        histogramNorthBox.setPreferredSize(new Dimension(metricsGrid.getWidth(),72));
        histogramNorthBox.setBorder(new EmptyBorder(10,10,10,10));
        histogramNorthBox.add(metricsVerticalBox);
        histogramNorthBox.add(genderVerticalBox);
        histogramNorthBox.add(ageVerticalBox);
        histogramNorthBox.add(contextVerticalBox);
        histogramNorthBox.add(incomeVerticalBox);

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
        campaignController.createCampaign(impressionsFileLocation, clickFileLocation, serverFileLocation);
        createMetrics(campaignController.createMetrics());
        createCharts(campaignController.createCharts());
    }

    // fast load the campaign for testing only
    public void fastCreateCampaign() {
        campaignController.createCampaign("src/Logs/impression_log.csv", "src/Logs/click_log.csv", "src/Logs/server_log.csv");
        createMetrics(campaignController.createMetrics());
        createCharts(campaignController.createCharts());
    }

    // displays the metrics when loaded
    public void createMetrics(MetricCalculator metricCalculator) {
        metricController.createMetrics(metricCalculator);
        updateMetrics();
    }

    // calls the controller to recalculate the metrics
    public void recalculateMetrics() {
        metricController.updateMetrics(arrayOfChoicesMetrics.get(1), // gender
                arrayOfChoicesMetrics.get(2), // age
                arrayOfChoicesMetrics.get(3), // context
                arrayOfChoicesMetrics.get(4), // income
                arrayOfChoicesMetrics.get(5), // start date
                arrayOfChoicesMetrics.get(6)); // start date
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