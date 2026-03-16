package GUI;

import Rendering.Camera;
import Rendering.ImageHandler;
import Rendering.ImageManager;

public abstract class PauseScreen extends GUIScreen {
    private int TextureID = ImageManager.INVENTORY; // TexturID von einem Pause Screen
    
    //Größe von der Pause Screen Textur auf dem Screen
    private int TextureWidth = 1080;
    private int TextureHeight = 1920;
    
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        //Position der Textur ausrechnen, Mitte des Bildschirms minus die hälfte der Länge der Textur, damit es zentriert ist
        int PosX = ScreenWidth / 2 - TextureWidth / 2 ;
        int PosY = ScreenHeight / 2 - TextureHeight / 2;

        //An Camera Position anpassen (folgt dann dem Spieler) und in den draw que packen
        renderer.drawFull(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, TextureWidth, TextureHeight);
    }
}
