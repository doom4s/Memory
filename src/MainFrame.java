import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Vlajko on 17-Mar-17.
 */
public class MainFrame extends JFrame implements ActionListener {
    private JPanel panel;
    private MyJButton myJButton;
    private int number;
    long time = 1750;
    private ArrayList<MyJButton> buttons;
    private MyJButton activeButton = null;

    public MainFrame(int number) {
        super.setTitle("@doom4s");
        this.number = number;
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int size = 300;
        if (number == 64) {
            size = size * 2;
        }
        Dimension d = new Dimension(size, size);
        GridLayout layout = new GridLayout((int) Math.sqrt(number), (int) Math.sqrt(number));
        panel = new JPanel();
        panel.setSize(d);

        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> numbers1 = new ArrayList<>();
        ArrayList<Integer> numbers2 = new ArrayList<>();
        for (int i = 0; i < number / 2; i++) {
            numbers1.add(i);
        }
        for (int i = 0; i < number / 2; i++) {
            numbers2.add(i);
        }

        addToArray(numbers, numbers1);
        addToArray(numbers, numbers2);
        Collections.shuffle(numbers);

        buttons = new ArrayList<>();
        panel.setLayout(layout);
        if (numbers.size() == number) {
            for (int i = 0; i < number; i++) {
                myJButton = new MyJButton();
                myJButton.setNumber(numbers.get(i) + 1);
                myJButton.addActionListener(this);
                myJButton.setVisible(true);
                buttons.add(myJButton);
                panel.add(myJButton);
            }
        }
        panel.setVisible(true);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setSize(d);
        this.setVisible(true);
    }

    public MainFrame(int number, int time) {
        this(number);
        this.number = number;
        this.time = time;
    }

    private void addToArray(ArrayList niz, ArrayList niz1) {
        for (int i = 0; i < niz1.size(); i++) {
            niz.add(niz1.indexOf(i));
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MyJButton) {
            MyJButton button = (MyJButton) e.getSource();
            SwingUtilities.invokeLater(() -> {
                button.setNumberAsText();
                button.setEnabled(false);
            });
            if (activeButton == null) {
                activeButton = button;
            }
            if (activeButton == button) {
                return;
            }
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    if (button != activeButton) {
                        toggleDisable(activeButton, button);
                        if (activeButton.getNumber() != button.getNumber()) {
                            try {
                                Thread.sleep(time);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            SwingUtilities.invokeLater(() -> {
                                invalidateGuess(activeButton, button);
                                activeButton = null;
                            });

                        }

                        if (activeButton.getNumber() == button.getNumber()) {
                            SwingUtilities.invokeLater(() -> {
                                validateGuess(activeButton, button);
                                activeButton = null;
                            });
                        }
                        toggleEnable(activeButton, button);
                    }

                    return null;
                }
            }.execute();
        }
    }

    private synchronized  void invalidateGuess(MyJButton activeButton, MyJButton button) {
        button.clearNumber();
        button.setEnabled(true);
        activeButton.setEnabled(true);
        activeButton.clearNumber();
    }

    private synchronized  void validateGuess(MyJButton activeButton, MyJButton button){
        button.setEnabled(false);
        button.setGuessed(true);
        activeButton.setEnabled(false);
        activeButton.setGuessed(true);
        buttons.remove(activeButton);
        buttons.remove(button);
        checkWinCondition();
    }

    private synchronized void toggleDisable(MyJButton activeButton, MyJButton button) {
        for (MyJButton btn : buttons) {
            if (btn == activeButton || btn == button) {
            } else {
                if (!btn.isAlreadyGuessed()) {
                    btn.setEnabled(false);
                }
            }
        }
    }

    private synchronized void toggleEnable(MyJButton activeButton, MyJButton button) {
        for (MyJButton btn : buttons) {
            if (btn == activeButton || btn == button) {
            } else {
                if (!btn.isAlreadyGuessed()) {
                    btn.setEnabled(true);
                }
            }
        }
    }

    private synchronized void checkWinCondition() {
        if (buttons.size() == 0) {
            Duration diff = Duration.between(LocalDateTime.now(), Main.startTime);
            int result = JOptionPane.showConfirmDialog(null, "Don't think you're smart.\n Your time is: " + diff.abs().getSeconds() + " seconds.", "You WON !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == result) {
                dispose();
                new MainFrame(this.number);
                Main.startTime = LocalDateTime.now();
            }
            if (JOptionPane.NO_OPTION == result) {
                dispose();
            }
        }
    }
}

