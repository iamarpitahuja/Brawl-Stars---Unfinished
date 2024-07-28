import java.awt.image.BufferedImage;

public class gameTile {
    public BufferedImage image;
    private boolean Collision = false;
    public void setCollision(boolean a){
        this.Collision = a;
    }
    public boolean getCollision(){
        return Collision;
    }
}
