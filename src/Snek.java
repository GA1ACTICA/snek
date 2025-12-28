import java.awt.Dimension;
import javax.swing.*;

import GameEngine.*;

public class Snek {

    static final JFrame frame = new JFrame("Snek");
    static final GameState gs = new GameState();
    static final GamePanel panel = new GamePanel(gs);
    static final Keys keys = new Keys(gs);
    static final GameUpdate gu = new GameUpdate(keys, gs, panel, frame);

    public static void main(String[] args) {

        // PANEL setup
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(gs.width, gs.height));
        panel.setBackground(gs.backgroundColor);
        panel.setFocusable(true);
        panel.addKeyListener(keys);

        // FRAME setup
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game loop
        new Thread(gu).start();
    }
}