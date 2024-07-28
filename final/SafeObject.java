import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SafeObject extends objectHeader {
    private Rectangle bounds;
    private Color color;
    private int x;
    private int y;
    private int size;
    private int counter;
    public int health;
    public int maxHealth;
    public int healthBarWidth;

    public SafeObject(int startX, int startY, int size, int health) {
        name = "Safe";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("./images/safe/heistSafe0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.color = new Color(255, 255, 255, 0);
        this.x = startX;
        this.y = startY;
        this.size = size;
        this.maxHealth = health;
        this.health = maxHealth;
        this.healthBarWidth = size;
        this.bounds = new Rectangle(x, y, size, size);
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    
        // Draw health bar
       
        g.setColor(Color.RED);
        g.fillRect(x, y - 10, healthBarWidth, 5); // Health bar above the safe
    }

    @Override
    public void create(Graphics2D graphics, GamePanel game) {
        super.create(graphics, game);
        int rectX = bounds.x - game.hero.getGlobalX() + game.hero.getScreenX();
        int rectY = bounds.y - game.hero.getGlobalY() + game.hero.getScreenY();
        int rectWidth = bounds.width;
        int rectHeight = bounds.height;
        graphics.setColor(color);
        graphics.fillRect(rectX, rectY, rectWidth, rectHeight);

        //health
        graphics.setColor(Color.RED);
        graphics.fillRect(rectX, rectY - 10, healthBarWidth, 5);
    }

    public void hitByFireball(int shotX, int shotY) {
        Rectangle shotBounds = new Rectangle(shotX, shotY, 20, 20);
        if (bounds.intersects(shotBounds)) {
            System.out.println("collision");
            health-=25000;
            System.out.println(health);
        }
    }
    public Rectangle getBounds() {
        return bounds;
    }
}