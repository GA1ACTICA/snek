package GameEngine;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EngineTools {

    public Image getImage(String filePathFromProject) {
        Image image = new ImageIcon(getClass().getResource("../" + filePathFromProject)).getImage();
        return image;
    }
}
