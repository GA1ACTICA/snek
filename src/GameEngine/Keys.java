package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    private final GameState gs;

    public int keyCodePressed;
    public char keyNamePressed;

    int keyCodeReleased;
    char keyNameReleased;

    int keyCodeTyped;
    char keyNameTyped;

    public Keys(GameState gs) {
        this.gs = gs;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCodePressed = e.getKeyCode();
        keyNamePressed = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed + '\n');
            System.out.println("keyNamePressed: " + keyNamePressed + '\n');
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCodeReleased = e.getKeyCode();
        keyNameReleased = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed + '\n');
            System.out.println("keyNamePressed: " + keyNamePressed + '\n');
            System.out.println("");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyCodeTyped = e.getKeyCode();
        keyNameTyped = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed + '\n');
            System.out.println("keyNamePressed: " + keyNamePressed + '\n');
            System.out.println("");
        }

    }
}