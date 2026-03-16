package GUI;

public class GUIButton {
    private float PosX;
    private float PosY;
    
    private float ButtonWidth;
    private float ButtonLength;
    
    private int TextureID;
    
    private String ButtonText;
    
    public GUIButton(float PosX, float PosY, float ButtonWidth, float ButtonLength, int TextureID, String ButtonText) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.ButtonWidth = ButtonWidth;
        this.ButtonLength = ButtonLength;
        
        this.TextureID = TextureID;
        
        this.ButtonText = ButtonText;
    }
    
    public boolean CursorOverButton(double CursorX, double CursorY) {
        return PosX <= CursorX && PosX + ButtonWidth >= CursorX && PosY <= CursorY && PosY + ButtonLength >= CursorY;
    }
}
