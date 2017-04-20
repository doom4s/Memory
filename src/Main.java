import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Vlajko on 17-Mar-17.
 */
public class Main {
    public static LocalDateTime startTime;

    public static void main(String args[]) throws IOException {

        String[] choices = {"Sir Forgetalot", "Average Joe/Joan", "Knows where The sock is", "You know if the iron is turned on", "Remembers the time before was born"};
        String input = (String) JOptionPane.showInputDialog(null, "Rate your memory !",
                "Feel deja vu ?", JOptionPane.QUESTION_MESSAGE, null,
                choices,
                choices[1]);

        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == input) {
                switch (i) {
                    case 0:
                        new MainFrame(4);
                        startTime = LocalDateTime.now();
                        break;
                    case 1:
                        new MainFrame(16);
                        startTime = LocalDateTime.now();
                        break;
                    case 2:
                        new MainFrame(64);
                        startTime = LocalDateTime.now();
                        break;
                    case 3:
                        new MainFrame(64, 1250);
                        startTime = LocalDateTime.now();
                        break;
                    case 4:
                        new MainFrame(64, 750);
                        startTime = LocalDateTime.now();
                        break;
                }
            }
        }
    }
}
