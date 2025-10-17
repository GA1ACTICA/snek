import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class gameUpdate implements Runnable, KeyListener {
    BufferStrategy backBuffer;

    // implement KeyListener method
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = e.getKeyCode();

        if (debug) {
            System.out.println("keyPressed: " + keyPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyReleased = e.getKeyCode();

        if (debug) {
            System.out.println("keyTyped: " + keyReleased);
        }

        if (keyReleased == 0 && debug == false) {
            debug = true;
        } else if (keyReleased == 0 && debug == true) {
            debug = false;
        }
    }

    Graphics g;
    Canvas c;

    // set up buffer strategy
    public gameUpdate(Canvas c) {
        this.c = c;
        c.createBufferStrategy(2);
        backBuffer = c.getBufferStrategy();
        while (backBuffer.getDrawGraphics() == null)
            ;
    }

    // DEBUG: Turns on and overlay that shows the value for all cells
    boolean debug = false;

    // config for grid style
    int gridStartX = 235;
    int gridStartY = 100;
    char gridSizeX = 7;
    char gridSizeY = 7;
    char rectInGridSizeX = 30;
    char rectInGridSizeY = 30;

    int[][] snekGrid = new int[gridSizeX + 2][gridSizeY + 2];

    int snekUpdateGridX = 0;
    int snekUpdateGridY = 0;

    // appleIndexNumber is the number asigned to the cell that contains an apple.
    // It is larger by one from the max grid since the snake can't be longer.
    int appleIndexNumber = gridSizeX * gridSizeY + 1;
    byte appleCountOnScreenCurrentFrame = 0;
    byte appleCountOnScreenPreviousFrame = 0;
    int maxAppleCountOnScreen = 2 - 1;
    int applesEaten = 0;

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

    // stores the numerical value for the latest pressed key
    int keyPressed;
    int keyReleased;

    long random;

    boolean west;
    boolean east;
    boolean north;
    boolean south = true;

    int timeDelayed = 0;

    boolean alive = true;

    /*
     * Order of operation:
     * 
     * Get user input ###
     * 
     * Update value for everythin acording to rules ###
     * 
     * Decrement everythin by one ###
     * 
     * Draw snek ###
     * 
     * Draw apple ###
     * 
     * Time delay ###
     */

    @Override
    public void run() {

        // Setup for starting position
        snekGrid[1 + 1][1 + 1] = 1;
        snekGrid[1 + 1][2 + 1] = 2;
        snekGrid[1 + 1][3 + 1] = 3;

        while (alive) {

            if (timeDelayed < 750 + (-10 * Math.round(30 + (-30 * Math.pow(0.9, applesEaten))))) {
                // starts buffer
                g = (Graphics2D) backBuffer.getDrawGraphics();
                g.clearRect(0, 0, 800, 600);

                draBackGround(snekUpdateGridX, snekUpdateGridY);

                g.setColor(Color.BLACK);

                // snek drawing logic loop
                for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                    for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                        // responsible for drawing logic
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 0) {
                            drawSnek(snekUpdateGridX, snekUpdateGridY, alive);
                        }

                        // draws apple without any extra logic
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == appleIndexNumber) {
                            g.drawImage(apple, 160 + (30 * snekUpdateGridX), 37 + (30 * snekUpdateGridY), 120, 120,
                                    null);
                        }
                    }
                }

                // display value for each cell
                if (debug) {
                    drawDebug(snekUpdateGridX, snekUpdateGridY);
                }

                // send graphics to Canvas
                g.dispose();
                backBuffer.show();

                // dealys the program for 0.5 second
                try {
                    Thread.sleep(20);
                    timeDelayed += 20;
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {

                // reset appleCountOnScreenCurrentFrame
                appleCountOnScreenCurrentFrame = 0;

                // get inputs
                if (keyPressed == 65 || keyPressed == 37) {
                    west = true;
                    north = false;
                    east = false;
                    south = false;
                }

                else if (keyPressed == 68 || keyPressed == 39) {
                    east = true;
                    north = false;
                    west = false;
                    south = false;
                }

                else if (keyPressed == 87 || keyPressed == 38) {
                    north = true;
                    west = false;
                    east = false;
                    south = false;
                }

                else if (keyPressed == 83 || keyPressed == 40) {
                    south = true;
                    north = false;
                    west = false;
                    east = false;
                }

                // update cell value loop
                for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                    for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == applesEaten + 3) {
                            updateCellValue(snekUpdateGridX, snekUpdateGridY, west, east, south, north);
                        }

                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == appleIndexNumber) {
                            appleCountOnScreenCurrentFrame++;
                        }
                    }
                }

                // apple eaten detection
                if (appleCountOnScreenCurrentFrame >= appleCountOnScreenPreviousFrame && alive == true) {

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

                // apple spawning loop
                for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                    for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                        random = Math.round(Math.random() * 50);

                        // responisble for placing tha apple
                        if (snekGrid[snekUpdateGridX][snekUpdateGridY] == 0
                                && appleCountOnScreenCurrentFrame <= maxAppleCountOnScreen && 1 == random) {
                            appleCountOnScreenCurrentFrame++;
                            drawApple(snekUpdateGridX, snekUpdateGridY);
                        }
                    }
                }

                timeDelayed = 0;

            }

            if (!alive) {

                // starts buffer
                g = (Graphics2D) backBuffer.getDrawGraphics();
                g.clearRect(0, 0, 800, 600);

                draBackGround(snekUpdateGridX, snekUpdateGridY);

                deadHead: {

                    // snek drawing logic loop
                    for (snekUpdateGridX = 1; snekUpdateGridX < gridSizeX + 1; snekUpdateGridX++) {
                        for (snekUpdateGridY = 1; snekUpdateGridY < gridSizeY + 1; snekUpdateGridY++) {

                            // responsible for drawing logic
                            if (snekGrid[snekUpdateGridX][snekUpdateGridY] != 0) {
                                drawSnek(snekUpdateGridX, snekUpdateGridY, alive);
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

                                // rule for head east
                                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX -
                                        1][snekUpdateGridY] + 1) {
                                    g.drawImage(headEastDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                            snekUpdateGridY), 120, 120, null);

                                    break deadHead;
                                }

                                // rule for head south
                                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] + 1) {

                                    g.drawImage(headSouthDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                            snekUpdateGridY), 120, 120, null);

                                    break deadHead;
                                }

                                // rule for head north
                                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        + 1] + 1) {

                                    g.drawImage(headNorthDead, 160 + (30 * snekUpdateGridX), 25 + (30 *
                                            snekUpdateGridY), 120, 120, null);

                                    break deadHead;
                                }
                                // rule for head west
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
                if (debug) {
                    drawDebug(snekUpdateGridX, snekUpdateGridY);
                }

                Font stringFont = new Font("SansSerif", Font.PLAIN, 50);
                g.setFont(stringFont);

                g.setColor(Color.BLACK);
                g.drawString("Game Over!", 250, 100);

                // send graphics to Canvas
                g.dispose();
                backBuffer.show();
            }

        }

    }

    void updateCellValue(int snekUpdateGridX, int snekUpdateGridY, boolean west, boolean east, boolean north,
            boolean south) {

        updateHead: {
            // update head east / d or arrow right
            if (east == true) {
                if (snekUpdateGridX == gridSizeX || snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != applesEaten + 3
                        && snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != appleIndexNumber
                        && snekGrid[snekUpdateGridX + 1][snekUpdateGridY] != 0) {
                    alive = false;
                }

                snekGrid[snekUpdateGridX + 1][snekUpdateGridY] = applesEaten + 4;
                break updateHead;
            }

            // update head west / a or arrow left
            if (west == true) {
                if (snekUpdateGridX == 1 || snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != applesEaten + 3
                        && snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != appleIndexNumber
                        && snekGrid[snekUpdateGridX - 1][snekUpdateGridY] != 0) {
                    alive = false;
                }

                snekGrid[snekUpdateGridX - 1][snekUpdateGridY] = applesEaten + 4;
                break updateHead;
            }

            // update head north / w or arrow up
            if (north == true) {
                if (snekUpdateGridY == gridSizeY || snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != applesEaten + 3
                        && snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != appleIndexNumber
                        && snekGrid[snekUpdateGridX][snekUpdateGridY + 1] != 0) {
                    alive = false;
                }

                snekGrid[snekUpdateGridX][snekUpdateGridY + 1] = applesEaten + 4;
                break updateHead;
            }

            // update head south / s or arrow down
            if (south == true) {
                if (snekUpdateGridY == 1 || snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != applesEaten + 3
                        && snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != appleIndexNumber
                        && snekGrid[snekUpdateGridX][snekUpdateGridY - 1] != 0) {
                    alive = false;
                }

                snekGrid[snekUpdateGridX][snekUpdateGridY - 1] = applesEaten + 4;
                break updateHead;
            }
        }
    }

    void drawSnek(int snekUpdateGridX, int snekUpdateGridY, boolean alive) {

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
                System.out.println("dead?");
                // rule for head east
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX -
                        1][snekUpdateGridY] + 1) {
                    g.drawImage(headEast, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }

                // rule for head south
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1] + 1) {

                    g.drawImage(headSouth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);

                    break head;
                }

                // rule for head north
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1] + 1) {

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
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1]
                                + 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1]
                                + 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] - 1) {
                    g.drawImage(northSouthBody, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for horizontal snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] - 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY]
                                + 1
                        ||
                        snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] + 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX
                                        - 1][snekUpdateGridY] - 1) {
                    g.drawImage(westEastBody, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for north east snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] + 1) {
                    g.drawImage(northEastBody, 164 + (30 * snekUpdateGridX), 21 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for west south snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        + 1] + 1) {
                    g.drawImage(westSouthBody, 156 + (30 * snekUpdateGridX), 29 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for north west snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX - 1][snekUpdateGridY]
                                - 1
                                && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY
                                        - 1] + 1) {
                    g.drawImage(northWestBody, 156 + (30 * snekUpdateGridX), 21 + (30 * snekUpdateGridY), 120, 120,
                            null);
                    break body;
                }

                // rule for west south snake body
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] + 1
                        && snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1]
                                - 1
                        || snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY]
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
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX + 1][snekUpdateGridY] - 1) {
                    g.drawImage(tailWest, 160 + (30 * snekUpdateGridX), 25 + (30 * snekUpdateGridY), 120, 120, null);
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
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY + 1] - 1) {
                    g.drawImage(tailNorth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);
                    break tail;
                }

                // rule for tail south
                if (snekGrid[snekUpdateGridX][snekUpdateGridY] == snekGrid[snekUpdateGridX][snekUpdateGridY - 1] - 1) {
                    g.drawImage(tailSouth, 160 + (30 * snekUpdateGridX), 25 + (30 *
                            snekUpdateGridY), 120, 120, null);
                    break tail;
                }
            }
        }
    }

    void drawApple(int snekUpdateGridX, int snekUpdateGridY) {
        snekGrid[snekUpdateGridX][snekUpdateGridY] = appleIndexNumber;
        g.drawImage(apple, 160 + (30 * snekUpdateGridX), 37 + (30 * snekUpdateGridY), 120, 120, null);
    }

    void drawDebug(int snekUpdateGridX, int setupGreenGridY) {
        for (snekUpdateGridX = 0; snekUpdateGridX < gridSizeX + 2; snekUpdateGridX++) {
            for (snekUpdateGridY = 0; snekUpdateGridY < gridSizeY + 2; snekUpdateGridY++) {
                g.drawString(Integer.toString(snekGrid[snekUpdateGridX][snekUpdateGridY]),
                        217 + (30 * snekUpdateGridX), 90 + (30 * snekUpdateGridY));
            }
        }
    }

    void draBackGround(int setupGreenGridX, int setupGreenGridY) {
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
