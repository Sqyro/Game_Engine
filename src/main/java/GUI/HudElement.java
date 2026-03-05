package GUI;

import java.awt.Image;

public class HudElement {
    private int PosX;
    private int PosY;
    
    private Image img;
    
    public HudElement(int PosX, int PosY, Image img) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.img = img;
    }
    
    public int getPosX() {
        return PosX;
    }
    
    public void setPosX(int newPosX) {
        PosX = newPosX;
    }
    
    public int getPosY() {
        return PosY;
    }
    
    public void setPosY(int newPosY) {
        PosY = newPosY;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void setImage(Image newimg) {
        img = newimg;
    }
}
