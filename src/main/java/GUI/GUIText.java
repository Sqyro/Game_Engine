package GUI;

public class GUIText {
    
    //Variablen deklarieren
    private String Text;

    private float PosX;
    private float PosY;

    private float CharacterSize;
    private float CharacterSpacing;

    private int FontTextureID;

    //Contructor für jeden Text, relativ unspektakulär
    public GUIText(String Text, float PosX, float PosY, float CharacterSize, float CharacterSpacing, int FontTextureID) {

        this.Text = Text;
        this.PosX = PosX;
        this.PosY = PosY;
        this.CharacterSize = CharacterSize;
        this.CharacterSpacing = CharacterSpacing;
        this.FontTextureID = FontTextureID;
    }

    //Hilfsmethoden
    public String getText() {
        return Text;
    }

    public void setText(String newText) {
        Text = newText;
    }

    public float getPosX() {
        return PosX;
    }
    
    public void setPosX(float newPosX) {
        PosX = newPosX;
    }
    
    public float getPosY() {
        return PosY;
    }

    public void setPosY(float newPosY) {
        PosY = newPosY;
    }

    public float getCharacterSize() {
        return CharacterSize;
    }

    public float getCharacterSpacing() {
        return CharacterSpacing;
    }

    public int getFontTextureID() {
        return FontTextureID;
    }
}
