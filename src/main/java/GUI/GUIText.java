package GUI;

import java.awt.Color;

public class GUIText {
    
    //Variablen deklarieren
    public String Text;

    public float PosX;
    public float PosY;

    public float CharacterSize;
    public float CharacterSpacing;

    public int FontTextureID;

    public Color TextColor;
    
    //Contructor für jeden Text, relativ unspektakulär
    public GUIText(String Text, float PosX, float PosY, float CharacterSize, float CharacterSpacing, int FontTextureID, Color TextColor) {
        this.Text = Text;
        this.PosX = PosX;
        this.PosY = PosY;
        this.CharacterSize = CharacterSize;
        this.CharacterSpacing = CharacterSpacing;
        this.FontTextureID = FontTextureID;
        this.TextColor = TextColor;
    }

    //Hilfsmethoden
    public void setText(String newText) {
        Text = newText;
    }
    
    public void setPosX(float newPosX) {
        PosX = newPosX;
    }

    public void setPosY(float newPosY) {
        PosY = newPosY;
    }
}