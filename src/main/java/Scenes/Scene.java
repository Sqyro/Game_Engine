package Scenes;


public abstract class Scene {
    public abstract void onCreation(long Window);
    
    public abstract void onLoadup(long Window);
    
    public abstract void onUpdate(float deltaTime);

    public abstract void clearOnScreenButtons();

    public abstract void handleClick(long Window, double CursorX, double CursorY);

    public abstract void handleHovering(double CursorX, double CursorY);
}