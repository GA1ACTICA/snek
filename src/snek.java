import java.awt.*;
import javax.swing.*;

public class snek extends Canvas {

    // window rules
    static int width = 800;
    static int height = 600;

    // Main Method
    public static void main(String args[]) {

        JFrame frame = new JFrame("Snek");
        snek canvas = new snek();

        frame.add(canvas);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setBackground(Color.YELLOW);
        canvas.requestFocus(); // Request focus for key events

        gameUpdate gu = new gameUpdate(canvas);
        canvas.addKeyListener(gu);

        Thread thread = new Thread(gu);
        thread.start();
    }
}