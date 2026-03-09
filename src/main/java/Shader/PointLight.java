package Shader;


public class PointLight extends LightEmitter { //Gibt Licht ab
    
    //Variabeln deklarieren
    
    //Reichweite des Lichtpunkts
    public float Range;

    public PointLight(float PosX, float PosY, float Red, float Green, float Blue, float Range) { //Constructor
        super(PosX, PosY, Red, Green, Blue); //Gibt Position und Farbe an LightEmitter weiter
        //Reichweite pro Objekt/Licht
        this.Range = Range;
    }
    
    //Methoden um Reichweite zu schreiben/lesen
    public float getRange() {
        return Range;
    }

    public void setRange(float newRange) {
        this.Range = newRange;
    }
}
