package GUI;

import java.awt.Graphics;
import java.awt.Image;

public class ImageHandler {
    
    public static void draw(Graphics g ,Image img, int PosX, int PosY) {
        g.drawImage(img, PosX, PosY, null);
    }
}
