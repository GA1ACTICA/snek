
/*
define a menu
add elements to menu

menu.show
menu.close

menu.backgroundColor/Image
menu.borerColors/Images
*/

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Menu {

        int sizeX = 300;
        int sizeY = 200;
        int startX = 100;
        int startY = 100;
        int boarderHeight = 10;
        int boarderWidth = 10;
        boolean resizable = false;
        boolean show = false;

        Color cTopLeft = Color.BLUE;
        Color cTop = Color.CYAN;
        Color cTopRight = Color.GREEN;
        Color cLeft = Color.MAGENTA;
        Color cRight = Color.ORANGE;
        Color cBottomLeft = Color.PINK;
        Color cBottom = Color.RED;
        Color cBottomRight = Color.YELLOW;

        Image iTopLeft = null;
        Image iTop = null;
        Image iTopRight = null;
        Image iLeft = null;
        Image iRight = null;
        Image iBottomLeft = null;
        Image iBottom = null;
        Image iBottomRight = null;

        Color backgroundColor = Color.WHITE;
        Image backgroundImage = null;

        BufferStrategy backBuffer;
        Graphics2D g;
        Canvas c;

        GameState gs = new GameState();


        public Menu(Canvas c) {
                
        }

        public void show(int sizeX, int sizeY, int startX, int startY, int boarderHeight, int boarderWidth,
                        boolean resizable) {

                this.sizeX = sizeX;
                this.sizeY = sizeY;
                this.startX = startX;
                this.startY = startY;
                this.boarderHeight = boarderHeight;
                this.boarderWidth = boarderWidth;
                this.resizable = resizable;
                show = true;

        }

        public void close() {

                show = false;

        }

        public void backgroundColor(Color backgroundColor) {

                this.backgroundColor = backgroundColor;
                backgroundImage = null;

        }

        public void backgroundImage(Image backgroundImage) {

                this.backgroundImage = backgroundImage;
                backgroundColor = null;

        }

        public void boarderColors(Color topLeft, Color top, Color topRight, Color left, Color right, Color bottomLeft,
                        Color bottom, Color bottomRight) {

                this.cTopLeft = topLeft;
                this.cTop = top;
                this.cTopRight = topRight;
                this.cLeft = left;
                this.cRight = right;
                this.cBottomLeft = bottomLeft;
                this.cBottom = bottom;
                this.cBottomRight = bottomRight;

        }

        public void boarderImage(Image topLeft, Image top, Image topRight, Image left, Image right, Image bottomLeft,
                        Image bottom, Image bottomRight) {

                this.iTopLeft = topLeft;
                this.iTop = top;
                this.iTopRight = topRight;
                this.iLeft = left;
                this.iRight = right;
                this.iBottomLeft = bottomLeft;
                this.iBottom = bottom;
                this.iBottomRight = bottomRight;

        }

}