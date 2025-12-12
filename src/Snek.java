import java.awt.Dimension;
import javax.swing.*;

import GameEngine.*;

public class Snek {

    static final JFrame frame = new JFrame("Snek");
    static final GameState gs = new GameState();
    static final GamePanel panel = new GamePanel(gs);
    static final Keys keys = new Keys(gs);
    static final GameUpdate gu = new GameUpdate(keys, gs, panel);

    // Main Method
    public static void main(String args[]) {

        panel.addKeyListener(keys);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.setPreferredSize(new Dimension(gs.width, gs.height));
        panel.setBackground(gs.backgroundColor);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.add(panel);
        frame.setSize(gs.width, gs.height);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread thread = new Thread(gu);
        thread.start();
    }
}