import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class GameUpdate implements Runnable {

    BufferStrategy backBuffer;
    Graphics2D g;
    Canvas c;
    SnekGame sg;
    Keys keys;

    GameState gs = new GameState();

    public GameUpdate(Canvas c, Keys keys) {
        this.c = c;
        this.keys = keys;
        this.sg = new SnekGame(keys);
    }

    private List<Drawable> drawables = new ArrayList<>();
    boolean running = true;

    public void init() {

        c.createBufferStrategy(2);
        backBuffer = c.getBufferStrategy();

        if (backBuffer == null) {
            throw new IllegalStateException("BufferStrategy falied to initialize");
        }

        drawables.add(sg);
    }

    @Override
    public void run() {

        // Setup for starting position
        sg.snekGrid[1 + 1][1 + 1] = 1;
        sg.snekGrid[1 + 1][2 + 1] = 2;
        sg.snekGrid[1 + 1][3 + 1] = 3;

        while (running) {

            render();

            try {
                Thread.sleep(750 + (-10 * Math.round(30 + (-30 * Math.pow(0.9, sg.applesEaten)))));

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void render() {

        g = (Graphics2D) backBuffer.getDrawGraphics();

        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());

        for (Drawable d : drawables) {
            d.draw(g);
        }

        g.dispose();
        backBuffer.show();
        Toolkit.getDefaultToolkit().sync(); // prevents tearing on Linux
    }
}
