package GUI;

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
    
    private String ButtonText;
    private float ButtonTextSpacing;
    private float ButtonTextSize;
    
    public GUIButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, int TextureID, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.ButtonWidth = ButtonWidth;
        this.ButtonHeight = ButtonHeight;
        
        this.TextureID = TextureID;
        
        this.ButtonText = ButtonText;
        this.ButtonTextSpacing = ButtonTextSpacing;
        this.ButtonTextSize = ButtonTextSize;
    }
    
    public abstract void onButtonClick();
     
    public void drawButton(ImageHandler renderer, Color TextColor) {
        renderer.drawFull(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, ButtonWidth, ButtonHeight, 1f, 1f, 1f);
        TextHandler.addDisplayedText(new GUIText(ButtonText, PosX + ButtonWidth / 2 - (ButtonText.length() * ButtonTextSpacing + ButtonTextSize) / 2, PosY + ButtonHeight / 2 - ButtonTextSize / 2, ButtonTextSize, ButtonTextSpacing, ImageManager.GAMEFONT, TextColor));
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
