import javax.swing.ImageIcon;
import java.awt.*;

public class SnekGame implements Drawable {

    // config for grid style
    int gridStartX = 235;
    int gridStartY = 100;
    char gridSizeX = 11;
    char gridSizeY = 11;
    char rectInGridSizeX = 30;
    char rectInGridSizeY = 30;

    public int[][] snekGrid = new int[gridSizeX + 2][gridSizeY + 2];

    int snekUpdateGridX = 0;
    int snekUpdateGridY = 0;

    // appleIndexNumber is the number asigned to the cell that contains an apple.
    // It is larger by one from the max grid since the snake can't be longer than
    // the max amount of grids.
    int appleIndexNumber = gridSizeX * gridSizeY + 1;
    byte appleCountOnScreenCurrentFrame = 0;
    byte appleCountOnScreenPreviousFrame = 0;
    int maxAppleCountOnScreen = 3;
    public int applesEaten = 0;

    Image apple = new ImageIcon(getClass().getResource("sprites/Apple.png")).getImage();

    Image headEast = new ImageIcon(getClass().getResource("sprites/Head_East.png")).getImage();
    Image headNorth = new ImageIcon(getClass().getResource("sprites/Head_North.png")).getImage();
    Image headSouth = new ImageIcon(getClass().getResource("sprites/Head_South.png")).getImage();
    Image headWest = new ImageIcon(getClass().getResource("sprites/Head_West.png")).getImage();

    Image headEastDead = new ImageIcon(getClass().getResource("sprites/Head_East_Dead.png")).getImage();
    Image headNorthDead = new ImageIcon(getClass().getResource("sprites/Head_North_Dead.png")).getImage();
    Image headSouthDead = new ImageIcon(getClass().getResource("sprites/Head_South_Dead.png")).getImage();
    Image headWestDead = new ImageIcon(getClass().getResource("sprites/Head_West_Dead.png")).getImage();

    Image eastSouthBody = new ImageIcon(getClass().getResource("sprites/East_South_Body.png")).getImage();
    Image northEastBody = new ImageIcon(getClass().getResource("sprites/North_East_Body.png")).getImage();
    Image northSouthBody = new ImageIcon(getClass().getResource("sprites/North_South_Body.png")).getImage();
    Image northWestBody = new ImageIcon(getClass().getResource("sprites/North_West_Body.png")).getImage();
    Image westEastBody = new ImageIcon(getClass().getResource("sprites/West_East_Body.png")).getImage();
    Image westSouthBody = new ImageIcon(getClass().getResource("sprites/West_South_Body.png")).getImage();

    Image tailWest = new ImageIcon(getClass().getResource("sprites/West_Tail.png")).getImage();
    Image tailSouth = new ImageIcon(getClass().getResource("sprites/South_Tail.png")).getImage();
    Image tailNorth = new ImageIcon(getClass().getResource("sprites/North_Tail.png")).getImage();
    Image tailEast = new ImageIcon(getClass().getResource("sprites/East_Tail.png")).getImage();

    long random;

    boolean west;
    boolean east;
    boolean north;
    boolean south = true;

    int defaultUpdateinterval;

    Keys keys;
    Menu menu;
    GameState gs;

    SnekGame(Keys keys, GameState gs, Menu menu) {
        this.keys = keys;
        this.gs = gs;
        this.menu = menu;
        this.defaultUpdateinterval = gs.snekGameUpdateInterval;
    }

    public void updateGameLogic() {
        inputUpade();

        // exponential function to increase snek speed
        gs.snekGameUpdateInterval = (int) (defaultUpdateinterval + (-10 * Math.round(30 + (-30 * Math.pow(0.9,
                (double) applesEaten)))));
    }

    @Override
    public void draw(Graphics g) {

        if (gs.alive) {
            drawSnekGame(g);

        } else {
            gameOver(g);
        }

    }

    public void drawSnekGame(Graphics g) {

        drawBackground(snekUpdateGridX, snekUpdateGridY, g);

        g.setColor(Color.BLACK);

        // snek drawing logic loop
        for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
            for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                // responsible for drawing logic
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 0) {
                    drawSnek(snekUpdateGridX, snekUpdateGridY, gs.alive, g);
                }

                // draws apple without any extra logic
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == appleIndexNumber) {
                    g.drawImage(apple, 160 + (30 * snekUpdateGridX), 37 + (30 * snekUpdateGridY), 120, 120,
                            null);
                }
            }
        }

        // apple spawning loop
        for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
            for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                random = Math.round(Math.random() * 50);

                // responisble for placing tha apple
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == 0
                        && appleCountOnScreenCurrentFrame <= maxAppleCountOnScreen - 1 && 1 == random) {
                    appleCountOnScreenCurrentFrame++;
                    drawApple(snekUpdateGridX, snekUpdateGridY, g);
                }
            }
        }

        // display value for each cell
        if (gs.debug) {
            drawDebug(snekUpdateGridX, snekUpdateGridY, g);
        }

    }

    void inputUpade() {

        if (gs.debug) {
            System.out.println(" North: " + north + " South: " + south + " West: " + west + " East: " + east);
        }

        // TEMP: for menu testing
        if (applesEaten > 3) {
            menu.setSize(20, 30, 100, 100, 0, 0, false);
            menu.show();
        } else {
            menu.hide();
        }

        // reset appleCountOnScreenCurrentFrame
        appleCountOnScreenCurrentFrame = 0;

        // west = "a" or code "37"
        if ((keys.keyNamePressed == 'a' || keys.keyCodePressed == 37) && east == false) {
            west = true;
            north = east = south = false;
        }

        // east = "d" or code "39"
        else if ((keys.keyNamePressed == 'd' || keys.keyCodePressed == 39) && west == false) {
            east = true;
            north = west = south = false;
        }

        // north = "w" or code "38"
        else if ((keys.keyNamePressed == 'w' || keys.keyCodePressed == 38) && south == false) {
            north = true;
            west = east = south = false;
        }

        // south = "s" or code "40"
        else if ((keys.keyNamePressed == 's' || keys.keyCodePressed == 40) && north == false) {
            south = true;
            north = west = east = false;
        }

        // update cell value loop
        for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
            for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == applesEaten + 3) {
                    updateCellValue(snekUpdateGridX, snekUpdateGridY, west, east, north, south);
                }

                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == appleIndexNumber) {
                    appleCountOnScreenCurrentFrame++;
                }
            }
        }

        // apple eaten detection
        if (appleCountOnScreenCurrentFrame >= appleCountOnScreenPreviousFrame && gs.alive == true) {

            // Update entire grid
            for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 2; snekUpdateGridX++) {
                for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 2; snekUpdateGridY++) {
                    if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 0
                            && snekGrid[snekUpdateGridX][snekUpdateGridY] != appleIndexNumber) {
                        snekGrid[snekUpdateGridX][snekUpdateGridY]--;
                    }
                }
            }

        } else {
            applesEaten++;
        }

        appleCountOnScreenPreviousFrame = appleCountOnScreenCurrentFrame;
    }

    void gameOver(Graphics g) {

        drawBackground(snekUpdateGridX, snekUpdateGridY, g);

        deadHead: {

            // snek drawing logic loop
            for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                    // responsible for drawing logic
                    if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 0) {
                        drawSnek(snekUpdateGridX, snekUpdateGridY, gs.alive, g);
                    }

                    // draws apple without any extra logic
                    if (snekGrid[snekUpdateGridX][snekUpdateGridY] == appleIndexNumber) {
                        g.drawImage(apple, 160 + (30 * snekUpdateGridX), 37 + (30 * snekUpdateGridY), 120, 120,
                                null);
                    }
                }
            }

            for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {
                    if (snekGrid[snekUpdateGridX][snekUpdateGridY] == applesEaten + 2) {

                        // rule for deadHead east
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX -
                                1][snekUpdateGridY] + 1) {
                            g.drawImage(headEastDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                    snekUpdateGridY), 120, 120, null);

                            break deadHead;
                        }

                        // rule for deadHead south
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                - 1] + 1) {

                            g.drawImage(headSouthDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                    snekUpdateGridY), 120, 120, null);

                            break deadHead;
                        }

                        // rule for deadHead north
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                + 1] + 1) {

                            g.drawImage(headNorthDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                    snekUpdateGridY), 120, 120, null);

                            break deadHead;
                        }
                        // rule for deadHead west
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX +
                                1][snekUpdateGridY] + 1) {
                            g.drawImage(headWestDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                    snekUpdateGridY), 120, 120, null);

                            break deadHead;
                        }
                    }
                }
            }
        }

        // display value for each cell
        if (gs.debug) {
            drawDebug(snekUpdateGridX, snekUpdateGridY, g);
        }

        Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
        g.setFont(stringFont);

        g.setColor(Color.BLACK);
        g.drawString("Game Over!", 250, 100);

    }

    void updateCellValue(int snekUpdateGridX, int snekUpdateGridY, boolean west, boolean east, boolean north,
            boolean south) {

        updateHead: {
            // update head east / d or arrow right
            if (east == true) {
                if (snekUpdateGridX == gridSizeX
                        || snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != applesEaten + 3
                                && snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != appleIndexNumber
                                && snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != 0) {
                    gs.alive = false;
                }

                snekGrid[snekUpdateGridX + 1][snekUpdateGridY] = applesEaten + 4;
                break updateHead;
            }

            // update head west / a or arrow left
            if (west == true) {
                if (snekUpdateGridX == 1 || snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != applesEaten + 3
                        && snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != appleIndexNumber
                        && snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != 0) {
                    gs.alive = false;
                }

                snekGrid[snekUpdateGridX - 1][snekUpdateGridY] = applesEaten + 4;
                break updateHead;
            }

            // update head south / s or arrow down
            if (south == true) {
                if (snekUpdateGridY == gridSizeY
                        || snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != applesEaten + 3
                                && snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != appleIndexNumber
                                && snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != 0) {
                    gs.alive = false;
                }

                snekGrid[snekUpdateGridX][snekUpdateGridY + 1] = applesEaten + 4;
                break updateHead;
            }

            // update head north / w or arrow up
            if (north == true) {
                if (snekUpdateGridY == 1 || snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != applesEaten + 3
                        && snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != appleIndexNumber
                        && snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != 0) {
                    gs.alive = false;
                }

                snekGrid[snekUpdateGridX][snekUpdateGridY - 1] = applesEaten + 4;
                break updateHead;
            }
        }
    }

    void drawSnek(int snekUpdateGridX, int snekUpdateGridY, boolean alive, Graphics g) {

        /*
         * NOTE:
         * This block of code checks the nessesary surounding cells for a higer or lower
         * value by one. This would idicate that the next segment of the body should
         * come ether before of after
         * 
         * This means that it also draws the correct turn, head and tail orientation
         * 
         */

        // contains all the rules for head
        head: {
            if (snekGrid[snekUpdateGridX][snekUpdateGridY] == applesEaten + 3 && alive == true) {
                // rule for head east
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX -
                        1][snekUpdateGridY] + 1) {
                    g.drawImage(headEast, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }

                // rule for head south
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1]
                        + 1) {

                    g.drawImage(headSouth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }

                // rule for head north
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1]
                        + 1) {

                    g.drawImage(headNorth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }

                // rule for head west
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX +
                        1][snekUpdateGridY] + 1) {
                    g.drawImage(headWest, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }
            }
        }

        body: {
            // contains all the rules for straight and curved body
            if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 1
                    && snekGrid[snekUpdateGridX][snekUpdateGridY] != applesEaten + 3) {

                // rules for vertical snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1] - 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                - 1]
                                + 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                + 1]
                                + 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] - 1) {
                    g.drawImage(northSouthBody, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for horizontal snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] - 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                - 1][snekUpdateGridY]
                                + 1
                        ||
                        snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY]
                                + 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                        - 1][snekUpdateGridY] - 1) {
                    g.drawImage(westEastBody, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for north east snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                - 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                + 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] + 1) {
                    g.drawImage(northEastBody, 164 + (30 * snekUpdateGridX), 21 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for west south snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                + 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                - 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        + 1] + 1) {
                    g.drawImage(westSouthBody, 156 + (30 * snekUpdateGridX), 29 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for north west snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                - 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                - 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] + 1) {
                    g.drawImage(northWestBody, 156 + (30 * snekUpdateGridX), 21 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for west south snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                + 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                + 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        + 1] + 1) {
                    g.drawImage(eastSouthBody, 164 + (30 * snekUpdateGridX), 29 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }
            }
        }

        tail: {
            // contains all the rules for the tail
            if (snekGrid[snekUpdateGridX][snekUpdateGridY] == 1) {

                // rule for tail west
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY]
                        - 1) {
                    g.drawImage(tailWest, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break tail;
                }

                // rule for tail east
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX -
                        1][snekUpdateGridY] - 1) {
                    g.drawImage(tailEast, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);
                    break tail;
                }

                // rule for tail north
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1]
                        - 1) {
                    g.drawImage(tailNorth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);
                    break tail;
                }

                // rule for tail south
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1]
                        - 1) {
                    g.drawImage(tailSouth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);
                    break tail;
                }
            }
        }
    }

    void drawApple(int snekUpdateGridX, int snekUpdateGridY, Graphics g) {
        snekGrid[snekUpdateGridX][snekUpdateGridY] = appleIndexNumber;
        g.drawImage(apple, 160 + (30 * snekUpdateGridX), 37 + (30 * snekUpdateGridY), 120, 120, null);
    }

    void drawDebug(int snekUpdateGridX, int setupGreenGridY, Graphics g) {
        g.setColor(Color.BLACK);
        for (snekUpdateGridX = 0; snekUpdateGridX < gridSizeX + 2; snekUpdateGridX++) {
            for (snekUpdateGridY = 0; snekUpdateGridY < gridSizeY + 2; snekUpdateGridY++) {
                g.drawString(Integer.toString(snekGrid[snekUpdateGridX][snekUpdateGridY]),
                        217 + (30 * snekUpdateGridX), 90 + (30 * snekUpdateGridY));
            }
        }
    }

    void drawBackground(int setupGreenGridX, int setupGreenGridY, Graphics g) {
        // paints the green background
        for (setupGreenGridY = 0; setupGreenGridY < gridSizeY; setupGreenGridY++) {
            for (setupGreenGridX = 0; setupGreenGridX < gridSizeX; setupGreenGridX++) {
                if (setupGreenGridY % 2 == 0) {
                    if (setupGreenGridX % 2 == 0) {
                        g.setColor(new Color(10, 200, 50));
                    } else {
                        g.setColor(new Color(50, 180, 50));
                    }
                } else {
                    if (setupGreenGridX % 2 == 0) {
                        g.setColor(new Color(50, 180, 50));
                    } else {
                        g.setColor(new Color(10, 200, 50));
                    }
                }
                g.fillRect(gridStartX + rectInGridSizeX * setupGreenGridX,
                        gridStartY + rectInGridSizeY * setupGreenGridY, rectInGridSizeX, rectInGridSizeY);
            }
        }
    }

}
