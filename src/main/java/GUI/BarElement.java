package GUI;

import java.awt.Image;
import java.awt.Color;

public class BarElement extends HudElement {
    
    private int BarLength;
    private int BarHeight;
    private int BarDamage;
    
    private Color BarColor;
    
    public BarElement(int PosX, int PosY, Image img, int BarLength, int BarHeight, int BarDamage,Color BarColor) {
        super(PosX, PosY, img);
        this.BarLength = BarLength;
        this.BarHeight = BarHeight;
        this.BarDamage = BarDamage;
        this.BarColor = BarColor;
    }
    
    public int getBarLength() {
        return BarLength;
    }
    
    public void setBarLength(int newBarLength) {
        BarLength = newBarLength;
    }
    
    public int getBarHeight() {
        return BarHeight;
    }
    
    public void setBarHeight(int newBarHeight) {
        BarHeight = newBarHeight;
    }
    
    public int getBarDamage() {
        return BarDamage;
    }
    
    public void setBarDamage(int newBarDamage) {
        BarDamage = newBarDamage;
    }
    
    public Color getColor() {
        return BarColor;
    }
    
    public void setColor(Color newColor) {
        BarColor = newColor;
    }
}
