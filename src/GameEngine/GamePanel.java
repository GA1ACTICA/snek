package GameEngine;

import java.awt.geom.AffineTransform;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameState gs;

    final int LOGICAL_WIDTH = 1000;
    final int LOGICAL_HEIGHT = 1000;

    public GamePanel(GameState gs) {
        this.gs = gs;
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Save transform
        AffineTransform old = g2d.getTransform();

        double scaleX = getWidth() / (double) LOGICAL_WIDTH;
        double scaleY = getHeight() / (double) LOGICAL_HEIGHT;
        double scale = Math.min(scaleX, scaleY);

        // Center + scale
        g2d.translate(
                (getWidth() - LOGICAL_WIDTH * scale) / 2,
                (getHeight() - LOGICAL_HEIGHT * scale) / 2);
        g2d.scale(scale, scale);

        // Draw background in LOGICAL space
        g2d.setColor(gs.backgroundColor);
        g2d.fillRect(0, 0, LOGICAL_WIDTH, LOGICAL_HEIGHT);

        // Draw game objects
        for (Drawable d : GameUpdate.drawables) {
            d.draw(g2d);
        }

        // Restore transform
        g2d.setTransform(old);
    }
}