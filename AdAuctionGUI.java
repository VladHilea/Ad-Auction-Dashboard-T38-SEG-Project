import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdAuctionGUI extends JFrame{
    private static JFrame gui;
    private static JLayeredPane menu,topMenu;
    private static JPanel verticalMenu;
    //private static Box verticalMenu;
    private static JButton insightsButton,chartsButton,histogramsButton,compareButton,settingsButton;

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

    }


    public static void createVerticalMenu(){
        verticalMenu = new JPanel();
        verticalMenu.setBounds(0,100,200,gui.getHeight());
        verticalMenu.setAlignmentY(100);
        verticalMenu.setOpaque(true);
        verticalMenu.setBackground(new Color(242,236,236));
        verticalMenu.setLayout(new BoxLayout(verticalMenu,BoxLayout.PAGE_AXIS));



        insightsButton = new JButton("Insights");
        insightsButton.setBackground(new Color(242,236,236));
        //insightsButton.setSize(100,100);
        insightsButton.setPreferredSize(new Dimension(40,40));
        insightsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),BorderFactory.createEmptyBorder(50,80,70,74)));
        //insightsButton.setBorder(BorderFactory.createMatteBorder(50,70,70,20,new Color(242,236,236)));

        chartsButton = new JButton("Charts");
        chartsButton.setBackground(new Color(242,236,236));
        //chartsButton.setSize(100,100);
        chartsButton.setPreferredSize(new Dimension(40,40));
        chartsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),BorderFactory.createEmptyBorder(50,80,70,82)));


        //chartsButton.setBorder(BorderFactory.createMatteBorder(50,70,70,20,new Color(242,236,236)));

        histogramsButton = new JButton("Histograms");
        histogramsButton.setBackground(new Color(242,236,236));
        //histogramsButton.setSize(100,100);
        histogramsButton.setPreferredSize(new Dimension(40,40));
        histogramsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),BorderFactory.createEmptyBorder(50,80,70,54)));


        //histogramsButton.setBorder(BorderFactory.createMatteBorder(50,70,70,20,new Color(242,236,236)));


        compareButton = new JButton("Compare");
        compareButton.setBackground(new Color(242,236,236));
        //compareButton.setSize(100,100);
        compareButton.setPreferredSize(new Dimension(40,40));
        compareButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE),BorderFactory.createEmptyBorder(50,80,70,68)));


        //compareButton.setBorder(BorderFactory.createMatteBorder(50,70,70,20,new Color(242,236,236)));


        settingsButton = new JButton("Settings");
        settingsButton.setBackground(new Color(242,236,236));
        //settingsButton.setSize(100,100);
        settingsButton.setPreferredSize(new Dimension(40,40));
        settingsButton.setBorder(BorderFactory.createEmptyBorder(50,80,70,70));

        //settingsButton.setBorder(BorderFactory.createMatteBorder(50,70,70,70,Color.WHITE));



        Box vbox = Box.createVerticalBox();
        vbox.setBounds(0,100,200,verticalMenu.getHeight());
        vbox.setOpaque(true);

        //vbox.add(new JSeparator(SwingConstants.HORIZONTAL));


        vbox.add(insightsButton);
        vbox.add(chartsButton);
        vbox.add(histogramsButton);
        vbox.add(compareButton);
        vbox.add(settingsButton);

/*
        verticalMenu.add(insightsButton);
        verticalMenu.add(chartsButton);
        verticalMenu.add(histogramsButton);
        verticalMenu.add(compareButton);
        verticalMenu.add(Box.createVerticalGlue());
        verticalMenu.add(settingsButton);

 */

        //verticalMenu.setLayout(new BoxLayout(verticalMenu, Y_AXIS));
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




    public static void main(String[] args) {
        prepareGui();
    }
}
