import javax.swing.*;
import java.awt.*;

/**
 * Created by Vlajko on 17-Mar-17.
 */
public class MyJButton extends JButton {

    private int number;
    private boolean guessed;

    public MyJButton(){
        this.setFont(new Font("Courier New", Font.BOLD, 32));
    }

    public int getNumber(){
        return this.number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public boolean isAlreadyGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }

    public String toString(){
        return Integer.toString(number);
    }

    public void setNumberAsText() {
        this.setText(toString());
    }
    public void clearNumber() {
        this.setText("");
    }
}
