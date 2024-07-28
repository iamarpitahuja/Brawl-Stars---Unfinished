import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.net.URL;

public class Windows extends JFrame {

    private JPanel screens;
    private CardLayout cardLayout;
    private Timer timer;
    private int currentIndex;
    private Animation animation;
    private GamePanel game;
    private Sound supDrc;
    private Sound loaderSound;
    public Sound backGroundSound;

    public Windows(String name){
        super(name);
    }

    public Windows(Animation animation) {
        this.animation = animation;
        this.setTitle("CSA Stars");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1008, 436);
        cardLayout = new CardLayout();
        screens = new JPanel(cardLayout);

        

        JPanel logoScreen = new JPanel();
        logoScreen.setLayout(new BorderLayout()); // Using BorderLayout to center the logo
        JLabel logo = new JLabel(new ImageIcon("./images/loadingScreen/background1.png"));
        logoScreen.add(logo, BorderLayout.CENTER);
        logoScreen.setBackground(Color.BLACK);
        screens.add(logoScreen, "logoScreen");

        



        JPanel loadingScreen = new JPanel(null);
        JLayeredPane layeredPane1 = new JLayeredPane();
        layeredPane1.setBounds(0, 0, 1008, 436);

        ImageIcon loadBack = new ImageIcon("./images/loadingScreen/background3.png");
        JLabel loadLabel = new JLabel(loadBack);
        loadLabel.setBounds(0, 0, 1008, 436);
        layeredPane1.add(loadLabel, JLayeredPane.DEFAULT_LAYER);

        JProgressBar load = new JProgressBar();
        load.setStringPainted(false);
        load.setBounds(425, 340, 150, 25);
        load.setBackground(new Color(95, 1, 53));
        load.setForeground(new Color(238, 118, 32));
        load.setBorder(BorderFactory.createLineBorder(new Color(95, 1, 53)));
        layeredPane1.add(load, JLayeredPane.POPUP_LAYER);

        screens.add(layeredPane1, "loadingScreen");


        Timer loadingTimer1 = new Timer(100, new ActionListener() {
            int counter1 = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex == 3) {
                    counter1+=5;
                    load.setValue(counter1);
                    if (counter1 >= 100) {
                        currentIndex = 4;
                        cardLayout.show(screens, "gamePanel");
                        game.requestFocusInWindow(); 
                        animation.displayGamePanel(); 
                        ((Timer) e.getSource()).stop(); 
                    }
        
                }
        
            }
        });
        loadingTimer1.start();




        
        JPanel menuScreen = new JPanel(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1008, 436); // Set bounds for the layeredPane

        ImageIcon menuBack = new ImageIcon("./images/loadingScreen/background2.png");
        JLabel menuLabel = new JLabel(menuBack);
        menuLabel.setBounds(0, 0, 1008, 436);
        layeredPane.add(menuLabel, JLayeredPane.DEFAULT_LAYER);

        ImageIcon play = new ImageIcon("./images/loadingScreen/background21.png");
        JLabel button = new JLabel(play);
        button.setBounds(765, 300, 178, 92);
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        JButton start = new JButton("PLAY");
        start.setOpaque(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.setForeground(new Color(0, 0, 0, 0)); 
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex == 2) {
                    loaderSound = new Sound("./sound/start.wav");
                    loaderSound.setFile();
                    loaderSound.play();
                    currentIndex = 3;
                    cardLayout.show(screens, "loadingScreen");
                    start.setVisible(false);
                }
            }
        });
        start.setBounds(765, 300, 164, 74);
        layeredPane.add(start, JLayeredPane.POPUP_LAYER);

        ImageIcon mode = new ImageIcon("./images/loadingScreen/background22.png");
        JLabel gameMode = new JLabel(mode);
        gameMode.setBounds(397, 291, 200, 100);
        layeredPane.add(gameMode, JLayeredPane.PALETTE_LAYER);


        ImageIcon archetype = new ImageIcon("./images/loadingScreen/background23.png");
        JLabel charType = new JLabel(archetype);
        charType.setBounds(420, 80, 131, 196);
        layeredPane.add(charType, JLayeredPane.PALETTE_LAYER);

        menuScreen.add(layeredPane);
        screens.add(menuScreen, "menuScreen");



        timer = new Timer(1725, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex == 0) {

                    System.out.println("1");
                    currentIndex = 1;
                    cardLayout.show(screens, "loadingScreen");
                }
            }
        });
        timer.start();
        
        try {
            Thread.sleep(500);
            supDrc = new Sound("./sound/supercell.wav");
            supDrc.setFile();
            supDrc.play();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }




        Timer loadingTimer = new Timer(100, new ActionListener() {
            int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex == 1) {
                    counter+=5;
                    load.setValue(counter);
                    if (counter >= 100) {
                        currentIndex = 2;
                        cardLayout.show(screens, "menuScreen");
                        ((Timer) e.getSource()).stop(); 
                    }

                }
            }
        });
        loadingTimer.start();

        game = new GamePanel(animation, this);
        screens.add(game, "gamePanel");

        this.add(screens);
        this.setVisible(true);

        try {
            Thread.sleep(1000);
            loaderSound = new Sound("./sound/loading.wav");
            loaderSound.setFile();
            loaderSound.play();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        
    }

    
    public void showGamePanel() {
        currentIndex = 3;
        cardLayout.show(screens, "gamePanel");
        game.startGame();
        backGroundSound = new Sound("./sound/playing.wav");
        backGroundSound.setFile();
        backGroundSound.play();
        backGroundSound.loop();
    }
}
