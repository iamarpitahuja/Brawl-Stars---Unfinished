import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private Animation animation;
    private final int tileSize = 16;
    private final int scaling = 3;
    private final int gameTile = tileSize * scaling;
    private final int screenColumns = 15;
    private final int screenRows = 8;
    private final int width = gameTile * screenColumns;
    private final int height = gameTile * screenRows;
    private final int brawlColumns = 26;
    private final int brawlRows = 33;
    private final int brawlWidth = gameTile * brawlColumns;
    private final int brawlHeight = gameTile * brawlRows;
    private int frames = 60;
    PlayerMover listener = new PlayerMover();
    Thread time;
    public objectPlacer placer = new objectPlacer(this);
    public Hero hero;
    private Sound backGroundSound;
    public Sound winSound;
    public Sound loseSound;
    public objectHeader safe[] = new objectHeader[10];
    SafeObject safes;
    public tileMaker makeTile = new tileMaker(this, 30000);
    public CountTimer gameTimer;
    private Windows songPlayer;
    private boolean gameEnded = false;  // Game end flag

    public GamePanel(Animation a, Windows window) {
        this.animation = a;
        hero = new Hero(this, listener);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(listener);
        this.setFocusable(true);
        this.songPlayer = window;
        int squareSize = 50;
        int startX = (width - squareSize) / 2;
        int startY = 0;
        int safeX = 576;
        int safeY = 1104;
        int safeSize = 100;
        safes = new SafeObject(safeX, safeY, safeSize, 100000);
        gameTimer = new CountTimer();
        add(gameTimer);
        
        gameTimer.startTimer();
    }

    public void gameSet() {
        placer.place();
    }

    public void startGame() {
        gameSet();
        time = new Thread(this);
        time.start();
    }

    @Override
    public void run() {
        double drawing = 1000000000 / frames;
        double refreshRate = System.nanoTime() + drawing;
        while (time != null) {
            if (!gameEnded) {  
                update();
                repaint();
            }
            try {
                double timeLeft = refreshRate - System.nanoTime();
                timeLeft = timeLeft / 1000000;
                if (timeLeft < 0) {
                    timeLeft = 0;
                }
                Thread.sleep((long) timeLeft);
                refreshRate = refreshRate + drawing;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int gameTileSize() {
        return gameTile;
    }

    public void update() {
        hero.update();
        makeTile.shootFireball();
        if ((gameTimer.currentTime() == 0 && safes.health > 0) || makeTile.getHealth() <= 0) {
            if (winSound == null || !winSound.playing()) {
                winSound = new Sound("./sound/Voicy_brawl stars win song meme.wav");
                winSound.setFile();
                winSound.play();
                winSound.loop();
            }
            if (songPlayer.backGroundSound != null && songPlayer.backGroundSound.playing()) {
                songPlayer.backGroundSound.stop();
            }
            gameEnded = true;  // Set game end flag
        }
        if (gameTimer.currentTime() >= 0 && safes.health <= 0) {
            if (loseSound == null || !loseSound.playing()) {
                loseSound = new Sound("./sound/Voicy_Brawl Stars Song Lose Meme.wav");
                loseSound.setFile();
                loseSound.play();
                loseSound.loop();
            }
            if (songPlayer.backGroundSound != null && songPlayer.backGroundSound.playing()) {
                songPlayer.backGroundSound.stop();
            }
            gameEnded = true;  // game end
        }
    }

    public int getMaxColumn() {
        return screenColumns;
    }

    public int getMaxRows() {
        return screenRows;
    }

    public int getActualHeight() {
        return height;
    }

    public int getActualWidth() {
        return width;
    }

    public int getTileSize() {
        return gameTile;
    }

    public int brawlColumns() {
        return brawlColumns;
    }

    public int brawlRows() {
        return brawlRows;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        makeTile.animate(graphics);
        for (int i = 0; i < safe.length; i++) {
            if (safe[i] != null) {
                safe[i].create(graphics, this);
            }
        }
        hero.animate(graphics);
        safes.render(graphics);
    }
}

