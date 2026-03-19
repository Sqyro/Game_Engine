package GUI;

public class GUIButton {
    private float PosX;
    private float PosY;
    
    private float ButtonWidth;
    private float ButtonHeight;
    
    private int TextureID;
    
    private String ButtonText;
    
    public GUIButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, int TextureID, String ButtonText) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        
        this.TextureID = TextureID;
        
        this.ButtonText = ButtonText;
    }
    
    public boolean CursorOverButton(double CursorX, double CursorY) {
        return PosX <= CursorX && PosX + ButtonWidth >= CursorX && PosY <= CursorY && PosY + ButtonHeight >= CursorY;
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
   
    public float getButtonWidth() {
        return ButtonWidth;
    }
   
    public void setButtonWidth(float newButtonWidth) {
        ButtonWidth = newButtonWidth;
    }
   
    public float getButtonHeight() {
        return ButtonHeight;
    }
   
    public void setButtonHeight(float newButtonHeight) {
        ButtonHeight = newButtonHeight;
    }
   
    public int getTextureID() {
        return TextureID;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
    
    public String getButtonText() {
        return ButtonText;
    }
    
    public void setButtonText(String newButtonText) {
        this.ButtonText = newButtonText;
    }
}
