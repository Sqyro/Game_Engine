package Shader;

public class LightEmitter { // Objekt für alles was Licht abgibt
    
    //Variabeln deklarieren
    
    //Position
    public float PosX;
    public float PosY;
    
    //Farben
    public float Red;
    public float Green;
    public float Blue;

    public LightEmitter(float PosX, float PosY, float Red, float Green, float Blue) { // Constructor
        //Pro Objekt setzen
        this.PosX = PosX;
        this.PosY = PosY;
        this.Red = Red;
        this.Green = Green;
        this.Blue = Blue;
    }

    //Methoden, damit auf die Variablen pro Objekt zugegriffen werden kann
    public float getPosX() {
        return PosX;
    }
    
    public void setPosX(float newPosX) {
        this.PosX = newPosX;
    }
    
    public float getPosY() {
        return PosY;
    }
    
    public void setPosY(float newPosY) {
        this.PosY = newPosY;
    }
    
    public float getRed() {
        return Red;
    }
    
    public void setRed(float newRed) {
        this.Red = newRed;
    }
    
    public float getGreen() {
        return Green;
    }
    
    public void setGreen(float newGreen) {
        this.Green = newGreen;
    }
    
    public float getBlue() {
        return Blue;
    }
    
    public void setBlue(float newBlue) {
        this.Blue = newBlue;
    }
}
