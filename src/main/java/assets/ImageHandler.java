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
    
    public static Image AnimatedImage(String imgLoc, String FileType, int Animlenght) {
        if (Animlenght >= PosinAnim) {
            currentFrame = Toolkit.getDefaultToolkit().getImage(imgLoc + PosinAnim + FileType);
            PosinAnim += 1;
        } else {
            PosinAnim = 0;
        }
        return currentFrame;
    }
}
