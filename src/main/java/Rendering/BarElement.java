package Rendering;

import java.awt.Color;

public class BarElement extends HudElement { // Ne bar ist nen Spezielles Hud Element, weil da halt noch nen Rechteck in ner Farbe drunter liegt
    public float BarFilledPercentage; //Wie viel von der Länge abgezogen wird, beim Rendern
    
    public int BarOffsetX; // Offset von der Bar im Vergleich zur Textur
    public int BarOffsetY;
    
    public Color BarColor; //Farbe von der Bar
    
    public BarElement(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, float BarFilledPercentage, int BarOffsetX, int BarOffsetY, Color BarColor) { //Constructor
        super(PosX, PosY, BarLength, BarHeight, TextureID); //Passed die Values an Hud Element
        //Eigene Werte pro Objekt
        this.BarFilledPercentage = BarFilledPercentage;
        this.BarOffsetX = BarOffsetX;
        this.BarOffsetY = BarOffsetY;
        this.BarColor = BarColor;
    }
    
    //Hilf Methoden um die Variablen zu setzen von den Objekten
    public void setBarFilledPercentage(float newBarFilledPercentage) {
        BarFilledPercentage = newBarFilledPercentage;
    }
    
    public void setBarOffsetX(int newBarOffsetX) {
        BarOffsetX = newBarOffsetX;
    }

    public void setBarOffsetY(int newBarOffsetY) {
        BarOffsetY = newBarOffsetY;
    }
    
    public void setColor(Color newColor) {
        BarColor = newColor;
    }
}
