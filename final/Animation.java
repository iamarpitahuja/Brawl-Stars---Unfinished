import javax.swing.JFrame;

public class Animation{
    private JFrame frame;
    private Windows game;

    public Animation(){
        game = new Windows(this);
    }
    public void displayGamePanel(){
        game.showGamePanel();
    }
    public static void main(String[] args) {
        new Animation();
    }
}