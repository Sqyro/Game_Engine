package GUI;

import Rendering.Frame;
import Rendering.Camera;
import Rendering.ImageHandler;
import Rendering.ImageManager;

import java.awt.Color;

public abstract class GUIButton {
    private float PosX;
    private float PosY;
    
    private float ButtonWidth;
    private float ButtonHeight;
    
    private int TextureID;
    private float TextureWidth = 128;
    private float TextureHeight = 48;
    
    //Positionen auf der Textur sind immer in Anteilen an der Texture angegeben
    //Ich teile also die Position auf der Textur in Pixeln durch die Gesamte Pixel Größe der Textur um auf den Anteil zu kommen
    private float onTextureX = 0;
    private float onTextureY = 8/TextureHeight;
    private float onTextureWidth = 96/TextureWidth;
    private float onTextureHeight = 8/TextureHeight;
    
    private float Indicator1onTextureX = 96/TextureWidth;
    private float Indicator1onTextureY = 8/TextureHeight;
    private float Indicator1onTextureWidth = 8/TextureWidth;
    private float Indicator1onTextureHeight = 8/TextureHeight;
    
    private float Indicator2onTextureX = 104/TextureWidth;
    private float Indicator2onTextureY = 8/TextureHeight;
    private float Indicator2onTextureWidth = 8/TextureWidth;
    private float Indicator2onTextureHeight = 8/TextureHeight;
    
    private String ButtonText;
    private float ButtonTextSpacing;
    private float ButtonTextSize;
    
    public boolean isHoveringOver;
    
    public GUIButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        
        this.TextureID = ImageManager.GUI_ELEMENTS;
        
        this.ButtonText = ButtonText;
        this.ButtonTextSpacing = ButtonTextSpacing;
        this.ButtonTextSize = ButtonTextSize;
        
        this.isHoveringOver = false;
    }
    
    public abstract void onButtonClick(long Window);
     
    public void drawButton(ImageHandler renderer, Color TextColor) {
        renderer.draw(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, ButtonWidth, ButtonHeight, onTextureX, onTextureY, onTextureWidth, onTextureHeight, 1f, 1f, 1f);
        float ButtonCenterPosX = ButtonWidth / 2;
        float ButtonCenterPosY = ButtonHeight / 2;
        float TextTotalLengthOnScreen = (ButtonText.length() + 1) * ButtonTextSpacing;
        TextHandler.addDisplayedText(new GUIText(ButtonText, PosX + ButtonCenterPosX - TextTotalLengthOnScreen / 2, PosY + ButtonCenterPosY - ButtonTextSize / 2, ButtonTextSize, ButtonTextSpacing, ImageManager.GAMEFONT, TextColor));
        if(isHoveringOver) {
            float IndicatorOffsetX = Frame.NormalizedPixelWidth * (ButtonWidth - ButtonWidth/3);
            renderer.draw(TextureID, PosX - Camera.PosX + ButtonCenterPosX - IndicatorOffsetX, PosY - Camera.PosY, ButtonHeight, ButtonHeight, Indicator1onTextureX, Indicator1onTextureY, Indicator1onTextureWidth, Indicator1onTextureHeight, 1f, 1f, 1f);
            renderer.draw(TextureID, PosX - Camera.PosX + ButtonCenterPosX + IndicatorOffsetX - ButtonHeight, PosY - Camera.PosY, ButtonHeight, ButtonHeight, Indicator2onTextureX, Indicator2onTextureY, Indicator2onTextureWidth, Indicator2onTextureHeight, 1f, 1f, 1f);
        }
    }
    
    public boolean CursorHoveringOverButton(double CursorX, double CursorY) {
        isHoveringOver = PosX <= CursorX && PosX + ButtonWidth >= CursorX && PosY <= CursorY && PosY + ButtonHeight >= CursorY;
        //if(isHoveringOver) System.out.println("Cursor Hovering over Button");
        return isHoveringOver;
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
        TextureID = newTextureID;
    }
    
    public String getButtonText() {
        return ButtonText;
    }
    
    public void setButtonText(String newButtonText) {
        ButtonText = newButtonText;
    }
    
    public float getButtonTextSpacing() {
        return ButtonTextSpacing;
    }
    
    public void setButtonTextSpacing(float newButtonTextSpacing) {
        ButtonTextSpacing = newButtonTextSpacing;
    }
}
