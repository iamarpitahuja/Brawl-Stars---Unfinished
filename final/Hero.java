import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero extends GameObject {
    private GamePanel game;
    private PlayerMover listen;
    private BufferedImage[] downPics = new BufferedImage[4];
    private BufferedImage[] upPics = new BufferedImage[4];
    private BufferedImage[] leftPics = new BufferedImage[4];
    private BufferedImage[] rightPics = new BufferedImage[4];
    private int spriteCount = 0;
    private int spriteNum = 0;
    private final int screenx;
    private final int screenY;
    private boolean onCollision = false;
    private boolean shooting = false;
    private int shootX;
    private int shootY;
    private String shootDirection;
    private Sound nitaSound;
    private int trackerX;
    private int trackerY;
    private Rectangle collides;
    public Object spriteShot;


    public Hero(GamePanel game, PlayerMover listen) {
        this.game = game;
        this.listen = listen;
        screenx = game.getActualWidth() / 2 - (game.getTileSize() / 2);
        screenY = game.getActualHeight() / 2 - (game.getTileSize() / 2);
        values();
        getImage();
        startHero();
    }

    public void values() {
        setGlobalX(game.gameTileSize() * 19);
        setGlobalY(game.gameTileSize() * 7);
        speed = 4;
        direction = "down";

        collides = new Rectangle(getGlobalX(), getGlobalY(), game.gameTileSize(), game.gameTileSize());
    }

    public void startHero() {
        setGlobalX(getGlobalX() - 100);
        setGlobalY(getGlobalY() - 100);
    }

    public int getScreenX() {
        return screenx;
    }

    public int getScreenY() {
        return screenY;
    }

    public void getImage() {
        try {
            for (int i = 0; i < downPics.length; i++) {
                downPics[i] = ImageIO.read(getClass().getResourceAsStream("./images/character/sprite_0_" + i + ".png"));
            }
            for (int i = 0; i < rightPics.length; i++) {
                rightPics[i] = ImageIO.read(getClass().getResourceAsStream("./images/character/sprite_1_" + i + ".png"));
            }
            for (int i = 0; i < upPics.length; i++) {
                upPics[i] = ImageIO.read(getClass().getResourceAsStream("./images/character/sprite_2_" + i + ".png"));
            }
            for (int i = 0; i < leftPics.length; i++) {
                leftPics[i] = ImageIO.read(getClass().getResourceAsStream("./images/character/sprite_3_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        onCollision = false;

        int newX = getGlobalX();
        int newY = getGlobalY();
        trackerX = getGlobalX();
        trackerY = getGlobalY();

        if (listen.isUp() || listen.isDown() || listen.isLeft() || listen.isRight()) {
            if (listen.isUp()) {
                direction = "up";
                newY -= speed;
            } else if (listen.isDown()) {
                direction = "down";
                newY += speed;
            } else if (listen.isLeft()) {
                direction = "left";
                newX -= speed;
            } else if (listen.isRight()) {
                direction = "right";
                newX += speed;
            }

            collides.setLocation(newX, newY);

            int leftSide = newX / game.gameTileSize();
            int rightSide = (newX + collides.width - 1) / game.gameTileSize();
            int top = newY / game.gameTileSize();
            int bottom = (newY + collides.height - 1) / game.gameTileSize();

            if ((boundsCheck(rightSide, top) && collisionCheck(rightSide, top)) ||
                (boundsCheck(rightSide, bottom) && collisionCheck(rightSide, bottom)) ||
                (boundsCheck(leftSide, top) && collisionCheck(leftSide, top)) ||
                (boundsCheck(leftSide, bottom) && collisionCheck(leftSide, bottom))) {
                onCollision = true;
            }

            if (!onCollision) {
                setGlobalX(newX);
                setGlobalY(newY);
            }
        }

        transition();

        if (listen.isShoot()) {
            shooting = true;
            shootX = screenx;
            shootY = screenY;
            shootDirection = direction;
            nitaSound = new Sound("./sound/Voicy_NITA say nitaaaaa!!.wav");
            nitaSound.setFile();
            nitaSound.play();
        }

        if (shooting) {
            if (shootDirection.equals("up")) {
                shootY -= 8;
                trackerY -=8;
                if (shootY < 0) {
                    shooting = false;
                }
            } else if (shootDirection.equals("down")) {
                shootY += 8;
                trackerY += 8;
                if (shootY > game.getHeight()) {
                    shooting = false;
                }
            } else if (shootDirection.equals("left")) {
                shootX -= 8;
                trackerY -= 8;
                if (shootX < 0) {
                    shooting = false;
                }
            } else if (shootDirection.equals("right")) {
                shootX += 8;
                trackerX += 8;
                if (shootX > game.getWidth()) {
                    shooting = false;
                }
            }
            
            game.makeTile.hitByFireball(trackerX, trackerY);
            //game.safes.hitByFireball(trackerX, trackerY);
        }
    }

    

    public void transition() {
        spriteCount++;
        if (spriteCount > 10) {
            spriteNum++;
            if (spriteNum >= 4) {
                spriteNum = 0;
            }
            spriteCount = 0;
        }
    }

    public boolean boundsCheck(int a, int b) {
        return a >= 0 && a < game.brawlColumns() && b >= 0 && b < game.brawlRows();
    }

    public boolean collisionCheck(int a, int b) {
        int tileNum = game.makeTile.numTile[a][b];
        return game.makeTile.tile[tileNum].getCollision();
    }

    public void animate(Graphics2D graphics) {
        BufferedImage pic = null;
        if (direction.equals("up")) {
            pic = upPics[spriteNum];
        } else if (direction.equals("down")) {
            pic = downPics[spriteNum];
        } else if (direction.equals("left")) {
            pic = leftPics[spriteNum];
        } else if (direction.equals("right")) {
            pic = rightPics[spriteNum];
        }
        graphics.drawImage(pic, screenx, screenY, game.gameTileSize(), game.gameTileSize(), null);

        if (shooting) {
            graphics.setColor(Color.CYAN);
            graphics.fillOval(shootX, shootY, 20, 20);
        }
    }

    public Rectangle getCollides() {
        return collides;
    }

    public int getShootX() {
        return shootX;
    }

    public int getShootY() {
        return shootY;
    }
    public int getTrackerX(){
        return trackerX;
    }
    public int getTrackerY(){
        return trackerY;
    }
}