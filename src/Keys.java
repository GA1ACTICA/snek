import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    int keyCodePressed;
    char keyNamePressed;

    int keyCodeReleased;
    char keyNameReleased;

    int keyCodeTyped;
    char keyNameTyped;

    GameState gs = new GameState();

    @Override
    public void keyPressed(KeyEvent e) {
        keyCodePressed = e.getKeyCode();
        keyNamePressed = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed);
            System.out.println("keyNamePressed: " + keyNamePressed);
            System.out.println("");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyCodeReleased = e.getKeyCode();
        keyNameReleased = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed);
            System.out.println("keyNamePressed: " + keyNamePressed);
            System.out.println("");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyCodeTyped = e.getKeyCode();
        keyNameTyped = e.getKeyChar();

        if (gs.debug) {
            System.out.println("keyCodePressed: " + keyCodePressed);
            System.out.println("keyNamePressed: " + keyNamePressed);
            System.out.println("");
        }

    }
}