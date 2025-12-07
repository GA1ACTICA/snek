import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    GameState gs;

    public GamePanel(GameState gs) {
        this.gs = gs;
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(gs.backgroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Drawable d : GameUpdate.drawables) {
            d.draw(g2d);
        }
    }
}