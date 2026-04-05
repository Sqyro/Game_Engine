package Rendering;

public class HudElement {
    
    //Alle Variablen Deklarieren
    
    //Position
    public int PosX;
    public int PosY;
    
    //Größe
    public int HudLength;
    public int HudHeight;
    
    //TexturID für OpenGL
    public int TextureID;
    
    public HudElement(int PosX, int PosY, int HudLength, int HudHeight, int TextureID) { //Constructor für alle Bar Elemente
        this.PosX = PosX;
        this.PosY = PosY;
        this.HudLength = HudLength;
        this.HudHeight = HudHeight;
        this.TextureID = TextureID;
        
    }
    
    //Hilfsmethoden, um die Variablen pro Objekt benutzen zu können
    public void setPosX(int newPosX) {
        PosX = newPosX;
    }
    
    public void setPosY(int newPosY) {
        PosY = newPosY;
    }
    
    public void setHudLength(int newHudLength) {
        HudLength = newHudLength;
    }
    
    public void setHudHeight(int newHudHeight) {
        HudHeight = newHudHeight;
    }
    
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
}
