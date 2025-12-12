package GameEngine;

import java.awt.Color;

public class GameState {

    public boolean debug = false;

    public Color backgroundColor = Color.LIGHT_GRAY;
    public int snekGameUpdateInterval = 750;
    public boolean alive = true;

    // first used to set window dimension and then later used for windown scaling
    // and drawing alignment
    public int width = 800;
    public int height = 600;

    public int x1 = 0;
    public int y1 = 0;
}
