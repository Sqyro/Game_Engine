package Scenes;

import Rendering.ImageManager;

public class MainMenuScene extends Scene {
    public MainMenuScene() {
    
    }
    
    @Override
    public void onCreation(long Window) {
        
    }
    
    @Override
    public void onLoadup(long Window) {
        //Alle nötigen Texturen laden
        ImageManager.loadStartTextures();
    }
    
    @Override
    public void onUpdate(float deltaTime) {
        
    }
}