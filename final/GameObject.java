import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class GameObject extends JLabel{
    private int globalX;
    private int globalY;
    public int speed;
    public String direction;
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage up3;
    public BufferedImage up4;
    public BufferedImage right1;
    public BufferedImage right2;
    public BufferedImage right3;
    public BufferedImage right4;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage left3;
    public BufferedImage left4;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage down3;
    public BufferedImage down4;
    public Rectangle collides;
    public int collidesAreaX;
    public int collidesAreaY;
    public boolean shotSeen;
    public int getGlobalX(){
        return globalX;
    }
    public int getGlobalY(){
        return globalY;
    }

    public void setGlobalX(int a){
        globalX = a;
    }

    public void setGlobalY(int b){
        globalY = b;
    }
}
