import java.awt.Dimension;
import javax.swing.*;

public class Snek {

    // window rules
    static int width = 800;
    static int height = 600;

    static JFrame frame = new JFrame("Snek");
    static GameState gs = new GameState();
    static Keys keys = new Keys();
    static GamePanel panel = new GamePanel(gs);
    static GameUpdate gu = new GameUpdate(keys, gs, panel);

    // Main Method
    public static void main(String args[]) {

        panel.addKeyListener(keys);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setBackground(gs.backgroundColor);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.add(panel);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread thread = new Thread(gu);
        thread.start();
    }
}
