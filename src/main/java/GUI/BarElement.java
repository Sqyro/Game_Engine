package GUI;

import java.awt.Color;

public class BarElement extends HudElement {
    private int BarDamage;
    
    public static float BarAlpha = 0.4f;
    
    private Color BarColor;
    
    public BarElement(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, int BarDamage, Color BarColor) {
        super(PosX, PosY, BarLength, BarHeight, TextureID);
        this.BarDamage = BarDamage;
        this.BarColor = BarColor;
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
