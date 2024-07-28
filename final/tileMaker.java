import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class tileMaker {
    private GamePanel game;
    public gameTile[] tile;
    public int numTile[][];
    private int column;
    private int row;
    private int rectX;
    private int rectY;
    private int rectWidth;
    private int rectHeight;
    private int rectSpeed;
    private int x;
    private int y;
    private int size;
    private int speed;
    private int screenWidth;
    private int screenHeight;
    private long lastShotTime;
    private List<FireballSquare> fireballs;
    private long startTime;
    private boolean movingDown;
    private boolean movingRight;
    private boolean movingDownAgain;
    private int health;
    private Rectangle bounds;
    private SafeObject safe;
    private int collisionCount; 

    private BufferedImage robotImage; // Add heist image field

    public tileMaker(GamePanel game, int health) {
        this.game = game;
        tile = new gameTile[10];
        numTile = new int[game.brawlColumns()][game.brawlRows()];
        rectX = 290;
        rectY = 250;
        rectWidth = 50;
        rectHeight = 50;
        rectSpeed = 1;
        movingDown = true;
        movingRight = false;
        movingDownAgain = false;
        getImage();
        drawMap("./images/map/map.txt");
        fireballs = new ArrayList<>();
        lastShotTime = System.currentTimeMillis();
        this.health = health;
        this.bounds = new Rectangle(100, 100, 100, 100);
        safe = new SafeObject(100, 100, 100, health); // Initialize the safe object
        collisionCount = 0; // Initialize collision count

        // Load robot image
        try {
            robotImage = ImageIO.read(getClass().getResourceAsStream("./images/robots/eightBit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImage() {
        try {
            // grass
            tile[0] = new gameTile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("./images/gameTiles/grass.png"));
            tile[0].setCollision(false);
            // walls
            tile[1] = new gameTile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("./images/gameTiles/wall.png"));
            tile[1].setCollision(true);
            // water
            tile[2] = new gameTile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("./images/gameTiles/water.png"));
            tile[2].setCollision(true);
            // sand
            tile[3] = new gameTile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("./images/gameTiles/sand.png"));
            tile[3].setCollision(false);
            // cactus
            tile[4] = new gameTile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("./images/gameTiles/cactus.png"));
            tile[4].setCollision(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawMap(String path) {
        try {
            InputStream input = getClass().getResourceAsStream(path);
            BufferedReader read = new BufferedReader(new InputStreamReader(input));
            while (column < game.brawlColumns() && row < game.brawlRows()) {
                String map = read.readLine();
                for (int i = 0; i < game.brawlColumns(); i++) {
                    String numbers[] = map.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    numTile[column][row] = num;
                    column++;
                }
                if (column == game.brawlColumns()) {
                    column = 0;
                    row++;
                }
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animate(Graphics2D graphics) {
        int brawlColumn = 0;
        int brawlRow = 0;

        while (brawlColumn < game.brawlColumns() && brawlRow < game.brawlRows()) {
            int tileNumber = numTile[brawlColumn][brawlRow];
            int cameraX = (brawlColumn * game.gameTileSize()) - game.hero.getGlobalX() + game.hero.getScreenX();
            int cameraY = (brawlRow * game.gameTileSize()) - game.hero.getGlobalY() + game.hero.getScreenY();
            graphics.drawImage(tile[tileNumber].image, cameraX, cameraY, game.gameTileSize(), game.gameTileSize(), null);
            brawlColumn++;
            if (brawlColumn == game.brawlColumns()) {
                brawlColumn = 0;
                brawlRow++;
            }
        }

        moveRectangle(); // Move the rectangle

        int cameraX = rectX - game.hero.getGlobalX() + game.hero.getScreenX();
        int cameraY = rectY - game.hero.getGlobalY() + game.hero.getScreenY();
        graphics.drawImage(robotImage, cameraX, cameraY, rectWidth, rectHeight, null);

        shootFireball();

        Iterator<FireballSquare> iterator = fireballs.iterator();
        while (iterator.hasNext()) {
            FireballSquare fireball = iterator.next();
            fireball.move();
            fireball.render(graphics, game);
            if (fireball.shouldRemove(rectX, rectY)) {
                iterator.remove();
            } else {
                fireball.hitSafeObject(safe, fireball.x, fireball.x);
            }
        }

        safe.create(graphics, game);
    }

    public void moveRectangle() {
        if (movingDown) {
            if (rectY < 565) {
                rectY += rectSpeed;
            } else {
                movingDown = false;
                movingRight = true;
            }
        } else if (movingRight) {
            if (rectX < 575) {
                rectX += rectSpeed;
            } else {
                movingRight = false;
                movingDownAgain = true;
            }
        } else if (movingDownAgain) {
            if (rectY < 1050) {
                rectY += rectSpeed;
            }
        }
        bounds.setLocation(rectX, rectY);
    }

    public void hitByFireball(int shotX, int shotY) {
        Rectangle shotBounds = new Rectangle(shotX, shotY, 20, 20);
        if (bounds.intersects(shotBounds)) {
            collisionCount++; // Increment collision count
            health-=70;
            System.out.println("Total collisions with moving rectangle: " + collisionCount);
            System.out.println(health);
        }
    }

    public void shootFireball() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1500) {
            if (movingRight) {
                fireballs.add(new FireballSquare(rectX + rectWidth, rectY + rectHeight / 2 - 10, true));
                game.safes.hitByFireball((int)fireballs.getLast().getBounds().getX(), (int)fireballs.getLast().getBounds().getY());
            } else {
                fireballs.add(new FireballSquare(rectX + rectWidth / 2 - 10, rectY + rectHeight, false));
                game.safes.hitByFireball((int)fireballs.getLast().getBounds().getX(), (int)fireballs.getLast().getBounds().getY());
            }
            lastShotTime = currentTime;
        }
    }

    public int getHealth(){
        return health;
    }
}