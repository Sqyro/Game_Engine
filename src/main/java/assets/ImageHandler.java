package assets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageHandler {
    private static int PosinAnim;
    private static Image currentFrame;
    
    public static void draw(Graphics g ,Image img, int PosX, int PosY) {
        g.drawImage(img, PosX, PosY, null);
    }
}
