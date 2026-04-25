package Scenes;

import GUI.GUIText;

public abstract class Scene {
    public abstract void onCreation(long Window);
    
    public abstract void onLoadup(long Window);

    public abstract void onUnload();

    public abstract void onUpdate(float deltaTime);

    public abstract void clearOnScreenFields();

    public abstract void handleClick(long Window, double CursorX, double CursorY);

    public abstract void handleHovering(double CursorX, double CursorY);

    public abstract void addDisplayedText(GUIText addedText);

    public abstract void clearDisplayedTextQue();
}