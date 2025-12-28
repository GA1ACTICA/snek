package GameEngine;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import AdvancedRendering.*;
import Game.*;

public class GameUpdate implements Runnable, ComponentListener {

    private final Keys keys; // if you want to have logic for menus or other classes in the loop below you
                             // also have acces to key inputs eventhough they are not used in this example
    private final Menu menu;
    private final SnekGame sg;
    private final GameState gs;
    private final GamePanel panel;
    private final EngineTools tools;
    private final AdvancedGraphics ag;

    public static List<Drawable> drawables = new ArrayList<>();

    boolean running = true;
    long lastUpdateTime;
    long currentTime;

    public GameUpdate(Keys keys, GameState gs, GamePanel panel, JFrame frame) {
        this.gs = gs;
        this.keys = keys;
        this.panel = panel;
        this.tools = new EngineTools();
        this.menu = new Menu(gs, frame);
        this.ag = new AdvancedGraphics(gs);
        this.sg = new SnekGame(keys, gs, menu, ag, tools);
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

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {

        if (gs.debug) {
            System.out.println("resized");
        }

        gs.width = panel.getWidth();
        gs.height = panel.getHeight();

        gs.x1 = (gs.width - 800) / 2;
        gs.y1 = (gs.height - 600) / 2;

    }

    @Override
    public void componentShown(ComponentEvent e) {
    }
}
