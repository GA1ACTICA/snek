import java.awt.*;

public class Menu implements Drawable {

        int sizeX = 300;
        int sizeY = 200;
        int startX = 100;
        int startY = 100;
        int boarderHeight = 10;
        int boarderWidth = 10;
        boolean resizable = false;
        boolean show = false;

        boolean hasBoarders = true;

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

        GameState gs;
        Graphics g;

        Menu(GameState gs) {
                this.gs = gs;
        }

        @Override
        public void draw(Graphics g) {

                this.g = g;

                if (show) {
                        g.setColor(new Color(255, 0, 0));
                        g.fillRect(startX, startY, sizeX, sizeY);
                }
        }

        public void setSize(int sizeX, int sizeY, int startX, int startY) {

                this.sizeX = sizeX;
                this.sizeY = sizeY;
                this.startX = startX;
                this.startY = startY;
                hasBoarders = false;
                resizable = false;

        }

        public void setSize(int sizeX, int sizeY, int startX, int startY, int boarderHeight, int boarderWidth,
                        boolean resizable) {

                // limit this to 0 < x
                this.sizeX = sizeX;
                this.sizeY = sizeY;
                this.startX = startX;
                this.startY = startY;
                this.boarderHeight = boarderHeight;
                this.boarderWidth = boarderWidth;

                this.resizable = resizable;
                show = true;
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

                iTopLeft = null;
                iTop = null;
                iTopRight = null;
                iLeft = null;
                iRight = null;
                iBottomLeft = null;
                iBottom = null;
                iBottomRight = null;
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

                cTopLeft = null;
                cTop = null;
                cTopRight = null;
                cLeft = null;
                cRight = null;
                cBottomLeft = null;
                cBottom = null;
                cBottomRight = null;

        }

        public void show() {
                show = true;
        }

        public void hide() {
                show = false;
        }
}