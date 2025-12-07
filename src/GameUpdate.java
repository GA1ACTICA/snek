import java.util.ArrayList;
import java.util.List;

public class GameUpdate implements Runnable {

    GameState gs;
    Menu menu;
    SnekGame sg;
    Keys keys;
    GamePanel panel;

    public static List<Drawable> drawables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

    public GameUpdate(Keys keys, GameState gs, GamePanel panel) {

        this.gs = gs;
        this.keys = keys;
        this.panel = panel;
        this.menu = new Menu(gs);
        this.sg = new SnekGame(keys, gs, menu);
    }

    @Override
    public void run() {

        drawables.add(sg);
        drawables.add(menu);

        // Setup for starting position
        sg.snekGrid[1 + 1][1 + 1] = 1;
        sg.snekGrid[1 + 1][2 + 1] = 2;
        sg.snekGrid[1 + 1][3 + 1] = 3;

        lastUpdateTime = System.currentTimeMillis();

        while (running) {

            currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdateTime >= gs.snekGameUpdateInterval) {
                sg.updateGameLogic();
                lastUpdateTime = currentTime;
            }

            panel.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}