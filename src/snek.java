import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

public class snek extends JFrame implements ComponentListener {

    gameUpdate gu;
    JFrame frame;
    Canvas canvas = new Canvas();

    // window rules
    int width = 800;
    int height = 600;

    public snek(String title) {

        super("snek");
        frame = new JFrame();

        frame.add(canvas);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true);

        canvas.setBackground(Color.YELLOW);
        canvas.requestFocus(); // Request focus for key events
        canvas.setIgnoreRepaint(true);
        frame.addComponentListener(this);

        gu = new gameUpdate(canvas);
        canvas.addKeyListener(gu);

        Thread thread = new Thread(gu);
        thread.start();

    }

    // Main Method
    public static void main(String args[]) {
        new snek("snek");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

}