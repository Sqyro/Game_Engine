package Scenes;

public abstract class Scene {
    public abstract void onCreation(long Window);
    
    public abstract void onLoadup(long Window);
    
    public abstract void onUpdate(float deltaTime);
}
