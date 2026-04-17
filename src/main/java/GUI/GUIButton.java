package GUI;

import Rendering.Frame;
import Rendering.Camera;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Scenes.SceneManager;

import java.awt.*;

public abstract class GUIButton extends GUIInteractableField {
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
    private Color ButtonTextColor;
    
    public GUIButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ImageManager.GUI_ELEMENTS);
        
        this.ButtonText = ButtonText;
        this.ButtonTextSpacing = ButtonTextSpacing;
        this.ButtonTextSize = ButtonTextSize;
        this.ButtonTextColor = ButtonTextColor;

        this.isHoveringOver = false;
    }
    
    @Override
    public void onFieldClick(long Window) {
        onButtonClick(Window);
    }

    @Override
    public void drawField(ImageHandler renderer) {
        renderer.draw(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, FieldWidth, FieldHeight, onTextureX, onTextureY, onTextureWidth, onTextureHeight, 1f, 1f, 1f, 1f);
        float ButtonCenterPosX = FieldWidth / 2;
        float ButtonCenterPosY = FieldHeight / 2;
        float TextTotalLengthOnScreen = (ButtonText.length() + 1) * ButtonTextSpacing;
        GUIManager.renderText(new GUIText(ButtonText, PosX + ButtonCenterPosX - TextTotalLengthOnScreen / 2, PosY + ButtonCenterPosY - ButtonTextSize / 2, ButtonTextSize, ButtonTextSpacing, ImageManager.GAMEFONT, ButtonTextColor), renderer);
        if(isHoveringOver) {
            float IndicatorOffsetX = Frame.NormalizedPixelWidth * (FieldWidth - FieldWidth/3);
            renderer.draw(TextureID, PosX - Camera.PosX + ButtonCenterPosX - IndicatorOffsetX, PosY - Camera.PosY, FieldHeight, FieldHeight, Indicator1onTextureX, Indicator1onTextureY, Indicator1onTextureWidth, Indicator1onTextureHeight, 1f, 1f, 1f, 1f);
            renderer.draw(TextureID, PosX - Camera.PosX + ButtonCenterPosX + IndicatorOffsetX - FieldHeight, PosY - Camera.PosY, FieldHeight, FieldHeight, Indicator2onTextureX, Indicator2onTextureY, Indicator2onTextureWidth, Indicator2onTextureHeight, 1f, 1f, 1f, 1f);
        }
    }
    
    public abstract void onButtonClick(long Window);
    
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
   
    public void setButtonWidth(float newButtonWidth) {
        FieldWidth = newButtonWidth;
    }
   
    public void setButtonHeight(float newButtonHeight) {
        FieldHeight = newButtonHeight;
    }
   
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
    
    public void setButtonText(String newButtonText) {
        ButtonText = newButtonText;
    }
    
    public void setButtonTextSpacing(float newButtonTextSpacing) {
        ButtonTextSpacing = newButtonTextSpacing;
    }
}
