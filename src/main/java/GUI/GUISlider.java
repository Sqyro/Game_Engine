package GUI;

import Rendering.Camera;
import Rendering.ImageManager;
import Rendering.ImageHandler;

public class GUISlider {
    public float PosX;
    public float PosY;
    
    public float BackgroundWidth;
    public float BackgroundHeight;
    
    public float SliderWidth;
    public float SliderHeight;
    
    public float SliderPercentageFilled;
    
    //Positionen auf der Textur sind immer in Anteilen an der Texture angegeben
    //Ich teile also die Position auf der Textur in Pixeln durch die Gesamte Pixel Größe der Textur um auf den Anteil zu kommen
    public int TextureID;
    private float TextureWidth = 128;
    private float TextureHeight = 48;
    
    private float onTextureX = 0/TextureWidth;
    private float onTextureY = 0/TextureHeight;
    private float onTextureWidth = 96/TextureWidth;
    private float onTextureHeight = 8/TextureHeight;
    
    private float SlideronTextureX = 96/TextureWidth;
    private float SlideronTextureY = 0/TextureHeight;
    private float SlideronTextureWidth = 2/TextureWidth;
    private float SlideronTextureHeight = 8/TextureHeight;
    
    public GUISlider(float PosX, float PosY, float BackgroundWidth, float BackgroundHeight, float SliderWidth, float SliderHeight, float SliderPercentageFilled) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.BackgroundWidth = BackgroundWidth;
        this.BackgroundHeight = BackgroundHeight;
        
        this.SliderWidth = SliderWidth;
        this.SliderHeight = SliderHeight;
        
        this.SliderPercentageFilled = SliderPercentageFilled;
        
        this.TextureID = ImageManager.GUI_ELEMENTS;
    }
    
    public void drawSlider(ImageHandler renderer) {
        renderer.draw(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, BackgroundWidth, BackgroundHeight, onTextureX, onTextureY, onTextureWidth, onTextureHeight, 1f, 1f, 1f);
        renderer.draw(TextureID, (PosX + SliderWidth * SliderPercentageFilled) - Camera.PosX, PosY - Camera.PosY, SliderWidth, SliderHeight, SlideronTextureX, SlideronTextureY, SlideronTextureWidth, SlideronTextureHeight, 1f, 1f, 1f);
    }
    
    public boolean CursorHoveringOverSlider(double CursorX, double CursorY) {
        if (PosX <= CursorX && PosX + BackgroundWidth >= CursorX && PosY <= CursorY && PosY + BackgroundHeight >= CursorY) {
            SetFilledPercentageToCursorPos(CursorX, CursorY);
            return true;
        } else return false;
    }
    
    public void SetFilledPercentageToCursorPos(double CursorX, double CursorY) {
        SliderPercentageFilled = ((float)CursorX - PosX)/BackgroundWidth;
    }
   
    public void setPosX(float newPosX) {
        PosX = newPosX;
    }
    
    public void setPosY(float newPosY) {
        PosY = newPosY;
    }
    
    public void setSliderWidth(float newSliderWidth) {
        SliderWidth = newSliderWidth;
    }
   
    public void setSliderHeight(float newSliderHeight) {
        SliderHeight = newSliderHeight;
    }
   
    public void SliderPercentageFilled(float newSliderPercentageFilled) {
        SliderPercentageFilled = newSliderPercentageFilled;
    }
    
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
}
