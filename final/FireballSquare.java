import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FireballSquare {
    int x;
    private int y;
    private int speed;
    private int dist;
    private boolean movingRight;

    public FireballSquare(int x, int y, boolean movingRight) {
        this.x = x;
        this.y = y;
        this.speed = 2;
        this.dist = 100;
        this.movingRight = movingRight;
    }

    public void move() {
        if (movingRight) {
            x += speed;
        } else {
            y += speed;
        }
    }

    public void render(Graphics2D g, GamePanel game) {
        int cameraX = x - game.hero.getGlobalX() + game.hero.getScreenX();
        int cameraY = y - game.hero.getGlobalY() + game.hero.getScreenY();
        g.setColor(Color.RED);
        g.fillOval(cameraX, cameraY, 20, 20);
    }

    public boolean shouldRemove(int rectX, int rectY) {
        if (movingRight) {
            return x - rectX > dist;
        } else {
            return y - rectY > dist;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    public void hitSafeObject(SafeObject safe, int shotX, int shotY) {
        Rectangle shotBounds = new Rectangle(shotX, shotY, 20, 20);
        if (safe.getBounds().intersects(shotBounds)) {
            safe.hitByFireball(shotX, shotY);
        }
    }
}
