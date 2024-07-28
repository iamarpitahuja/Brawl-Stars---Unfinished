import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class objectHeader extends GameObject{
    public BufferedImage image;
    public String name;
    private boolean collisionNew = false;
    private int newGlobalX;
    private int newGlobalY;
    public BufferedImage normalSafe;
    public BufferedImage redSafe;
    public BufferedImage brokenSafe;
    public Rectangle safeRectangle;
    public void setX(int a){
        newGlobalX = a;
    }
    public void setY(int b){
        newGlobalY = b;
    }
    public int getGlobalX(){
        return newGlobalX;
    }
    public int getGlobalY(){
        return newGlobalY;
    }
    public boolean getColissionNew(){
        return collisionNew;
    }
    public void setCollisionNew(boolean a){
        collisionNew = a;
    }


    public void create(Graphics2D graphics, GamePanel game){
        int cameraX = newGlobalX - game.hero.getGlobalX() + game.hero.getScreenX();
        int cameraY = newGlobalY - game.hero.getGlobalY() + game.hero.getScreenY();

        if(image != null && 
        newGlobalX + game.gameTileSize() > game.hero.getGlobalX() - game.hero.getScreenX() &&
        newGlobalX - game.gameTileSize() < game.hero.getGlobalX() + game.hero.getScreenX() &&
        newGlobalY + game.gameTileSize() > game.hero.getGlobalY() - game.hero.getScreenY() &&
        newGlobalY - game.gameTileSize() < game.hero.getGlobalY() + game.hero.getScreenY()){
            graphics.drawImage(image, cameraX, cameraY, game.gameTileSize(), game.gameTileSize(), null);
        }
    }
}
