package GUI;

import Rendering.ImageHandler;
import Rendering.ImageManager;

public abstract class GUIInteractableField {
    public float PosX;
    public float PosY;

    public float FieldWidth;
    public float FieldHeight;

    public int TextureID;

    public boolean isHoveringOver;

    public GUIInteractableField(float PosX, float PosY, float FieldWidth, float FieldHeight, int TextureID) {
        this.PosX = PosX;
        this.PosY = PosY;

        this.FieldWidth = FieldWidth;
        this.FieldHeight = FieldHeight;

        this.TextureID = TextureID;
        
        this.isHoveringOver = false;
    }
    
    public abstract void drawField(ImageHandler renderer);
    
    public abstract void onFieldClick(long Window);

    public boolean CursorHoveringOver(double CursorX, double CursorY) {
        isHoveringOver = PosX <= CursorX && PosX + FieldWidth >= CursorX && PosY <= CursorY && PosY + FieldHeight >= CursorY;
        //if(isHoveringOver) System.out.println("Cursor Hovering over");
        return isHoveringOver;
    }
}