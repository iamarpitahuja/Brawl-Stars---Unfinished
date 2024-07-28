import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Fireball extends GameObject {
    private GamePanel game;
    private PlayerMover mover;
    private String shootDirection;
    public int shootX;
    public int shootY;
    public int trackerX;
    private int trackerY;
    private Hero hero;
    private boolean shooting;
    private Sound nitaSound;
    
    public Fireball(GamePanel game, PlayerMover listen, Hero hero) {
        this.game = game;
        this.mover = listen;
        this.hero = hero;
        shooting = false;
    }

    public boolean isShooting(){
        return shooting;
    }

    public void setShooting(boolean a) {
        shooting = a;
    }

    public void setShootX(int a) {
        shootX = a;
    }

    public void setShootY(int b) {
        shootY = b;
    }

    public void setDirection(String c) {
        shootDirection = c;
    }
    public int getShootX(){
        return shootX;
    }
    public int getShootY(){
        return shootY;
    }

    public void update(SafeObject target) {
        if (mover.isShoot()) {
            shooting = true;
            shootX = hero.getScreenX();
            shootY = hero.getScreenY();
            trackerX = hero.getGlobalX();
            trackerY = hero.getGlobalY();
            System.out.println(" " + shootX);
            System.out.println(" " + shootY);
            shootDirection = hero.direction;
            nitaSound = new Sound("./sound/Voicy_NITA say nitaaaaa!!.wav");
            nitaSound.setFile();
            nitaSound.play();
        }

        if (shooting) {
            move();
            if (hasCollidedWith(target)) {
                System.out.println("collison");
                shooting = false;
            }
        }
    }

    private void move() {
        switch (shootDirection) {
            case "up":
                shootY -= 8;
                trackerY-=8;
                if (shootY < 0) {
                    shooting = false;
                }
                break;
            case "down":
                shootY += 8;
                trackerY+=8;
                if (shootY > game.getActualHeight()) {
                    shooting = false;
                }
                break;
            case "left":
                shootX -= 8;
                trackerX-=8;
                if (shootX < 0) {
                    shooting = false;
                }
                break;
            case "right":
                shootX += 8;
                trackerX+=8;
                if (shootX > game.getActualWidth()) {
                    shooting = false;
                }
                break;
        }
    }

    public boolean hasCollidedWith(SafeObject other) {
        if (other != null) {
            Rectangle fireballBounds = new Rectangle(trackerX, trackerY, 16, 16);
            Rectangle targetBounds = new Rectangle(other.getGlobalX(), other.getGlobalY(), 16, 16);
            return fireballBounds.intersects(targetBounds); 
        }
        return false;
    }

    public void animate(Graphics2D graphics) {
        if (shooting) {
            graphics.setColor(Color.CYAN);
            graphics.fillOval(shootX, shootY, 20, 20);
        }
    }
}
