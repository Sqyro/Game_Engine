package Rendering;

import java.awt.Color;

public class BarElement extends HudElement { // Ne bar ist nen Spezielles Hud Element, weil da halt noch nen Rechteck in ner Farbe drunter liegt
    private int BarDamage; //Wie viel von der Länge abgezogen wird, beim Rendern
    
    private int BarOffset; // Offset X von der Bar im Vergleich zur Textur
    
    private Color BarColor; //Farbe von der Bar
    
    public BarElement(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, int BarDamage, int BarOffset, Color BarColor) { //Constructor
        super(PosX, PosY, BarLength, BarHeight, TextureID); //Passed die Values an Hud Element
        this.BarDamage = BarDamage; //Eigener Wert pro Objekt
        this.BarOffset = BarOffset;
        this.BarColor = BarColor; //Eigener Wert pro Objekt
    }
    
    //Hilf Methoden um die Variablen zu setzen von den Objekten
    public int getBarDamage() {
        return BarDamage;
    }
    
    public void setBarDamage(int newBarDamage) {
        BarDamage = newBarDamage;
    }
    
    public int getBarOffset() {
        return BarOffset;
    }
    
    public void setBarOffset(int newBarOffset) {
        BarOffset = newBarOffset;
    }
    
    public Color getColor() {
        return BarColor;
    }
    
    public void setColor(Color newColor) {
        BarColor = newColor;
    }
}
