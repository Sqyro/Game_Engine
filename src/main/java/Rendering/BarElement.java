package Rendering;

import java.awt.Color;

public class BarElement extends HudElement { // Ne bar ist nen Spezielles Hud Element, weil da halt noch nen Rechteck in ner Farbe drunter liegt
    private int BarDamage; //Wie viel von der Länge abgezogen wird, beim Rendern
    
    private int BarOffsetX; // Offset von der Bar im Vergleich zur Textur
    private int BarOffsetY;
    
    private Color BarColor; //Farbe von der Bar
    
    public BarElement(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, int BarDamage, int BarOffsetX, int BarOffsetY, Color BarColor) { //Constructor
        super(PosX, PosY, BarLength, BarHeight, TextureID); //Passed die Values an Hud Element
         //Eigene Werte pro Objekt
         this.BarDamage = BarDamage;
        this.BarOffsetX = BarOffsetX;
        this.BarOffsetY = BarOffsetY;
        this.BarColor = BarColor;
    }
    
    //Hilf Methoden um die Variablen zu setzen von den Objekten
    public int getBarDamage() {
        return BarDamage;
    }
    
    public void setBarDamage(int newBarDamage) {
        BarDamage = newBarDamage;
    }
    
    public int getBarOffsetX() {
        return BarOffsetX;
    }
    
    public void setBarOffsetX(int newBarOffsetX) {
        BarOffsetX = newBarOffsetX;
    }
    
    public int getBarOffsetY() {
        return BarOffsetY;
    }
    
    public void setBarOffsetY(int newBarOffsetY) {
        BarOffsetY = newBarOffsetY;
    }
    
    public Color getColor() {
        return BarColor;
    }
    
    public void setColor(Color newColor) {
        BarColor = newColor;
    }
}
