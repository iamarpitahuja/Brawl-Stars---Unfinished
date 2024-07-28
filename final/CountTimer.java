import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class CountTimer extends JLabel{
    private int seconds = 49;
    private Timer timer;
    public CountTimer(){
        super("Time --> 0:49");
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(seconds > 0){
                    seconds--;
                    setText(timeFormat(seconds));
                }
                else{
                    timer.stop();
                    setText("Game Over");
                }
            }
        });
    }

    public void startTimer(){
        timer.start();
    }
    public void stopTimer(){
        timer.stop();
    }
    public void resetTimer(){
        timer.stop();
        seconds=120;
        setText(timeFormat(seconds));
    }


    public int currentTime(){
        return seconds;
    }
    
    public String timeFormat(int seconds){
        int minute = seconds/60;
        int second = seconds%60;
        return String.format("Time --> %d:%02d", minute, second);
    }
}
