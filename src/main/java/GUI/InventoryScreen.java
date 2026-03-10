package GUI;

import Rendering.ImageHandler;
import Rendering.ImageManager;
import Rendering.Camera;

public class InventoryScreen extends GUIScreen {
    private int TextureID = ImageManager.ENEMY; // TexturID von einem Inventory Screen
    
    private int TextureWidth = 800;
    private int TextureHeight = 800;
    
    //Constructor von GUI Screen angepasst für InventoryScreen. (Den Contructor zu Overwriten bei jedem Object ist lowkey besser als die Werte hoch zu passen)
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        //Position der Textur ausrechnen, Mitte des Bildschirms minus die hälfte der Länge der Textur, damit es zentriert ist
        int PosX = ScreenWidth / 2 - TextureWidth / 2 ;
        int PosY = ScreenHeight / 2 - TextureHeight / 2;

        //An Camera Position anpassen (folgt dann dem Spieler) und in den draw que packen
        renderer.drawFull(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, TextureWidth, TextureHeight);
    }
}
