package GUI;

import Rendering.Camera;
import Rendering.ImageHandler;

public class GUISlider {
    public float PosX;
    public float PosY;
    
    public float BackgroundWidth;
    public float BackgroundHeight;
    
    public float SliderWidth;
    public float SliderHeight;
    
    public float SliderPercentageFilled;
    
    public int TextureID;
    public int SliderTextureID;
    
    public GUISlider(float PosX, float PosY, float BackgroundWidth, float BackgroundHeight, float SliderWidth, float SliderHeight, float SliderPercentageFilled, int TextureID, int SliderTextureID) {
        this.PosX = PosX;
        this.PosY = PosY;
        
        this.BackgroundWidth = BackgroundWidth;
        this.BackgroundHeight = BackgroundHeight;
        
        this.SliderWidth = SliderWidth;
        this.SliderHeight = SliderHeight;
        
        this.SliderPercentageFilled = SliderPercentageFilled;
        
        this.TextureID = TextureID;
        this.SliderTextureID = SliderTextureID;
    }
    
    public void drawSlider(ImageHandler renderer) {
        renderer.drawFull(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, BackgroundWidth, BackgroundHeight, 1f, 1f, 1f);
        renderer.drawFull(SliderTextureID, (PosX + SliderWidth * SliderPercentageFilled) - Camera.PosX, PosY - Camera.PosY, SliderWidth, SliderHeight, 1f, 1f, 1f);
    }
    
    public boolean CursorOverSlider(double CursorX, double CursorY) {
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
    
    public void setSliderTextureID(int newSliderTextureID) {
        SliderTextureID = newSliderTextureID;
    }
}
