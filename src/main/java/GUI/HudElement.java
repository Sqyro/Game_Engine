package GUI;

public class HudElement {
    
    //Alle Variablen Deklarieren
    
    //Position
    private int PosX;
    private int PosY;
    
    //Größe
    private int HudLength;
    private int HudHeight;
    
    //TexturID für OpenGL
    private int TextureID;
    
    public HudElement(int PosX, int PosY, int HudLength, int HudHeight, int TextureID) { //Constructor für alle Bar Elemente
        this.PosX = PosX;
        this.PosY = PosY;
        this.HudLength = HudLength;
        this.HudHeight = HudHeight;
        this.TextureID = TextureID;
        
    }
    
    //Hilfsmethoden, um die Variablen pro Objekt benutzen zu können
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
    
    public int getHudLength() {
        return HudLength;
    }
    
    public void setHudLength(int newHudLength) {
        HudLength = newHudLength;
    }
    
    public int getHudHeight() {
        return HudHeight;
    }
    
    public void setHudHeight(int newHudHeight) {
        HudHeight = newHudHeight;
    }
    
    public int getTextureID() {
        return TextureID;
    }
    
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
}
