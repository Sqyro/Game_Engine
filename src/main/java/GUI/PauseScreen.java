package GUI;

import Rendering.Camera;
import Rendering.ImageHandler;
import Rendering.ImageManager;

import java.util.List;
import java.util.ArrayList;

public class PauseScreen extends GUIScreen {
    private int CenterX;
    private int CenterY; 
    
    public List<GUIButton> PauseButtons = new ArrayList<>();
    
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        //Mitte vom Bildschirm ausrechnen, damit es zentriert ist
        CenterX = ScreenWidth / 2;
        CenterY = ScreenHeight / 2;
        
        for(GUIButton CurrentButton : PauseButtons) {
            renderer.drawFull(CurrentButton.getTextureID(), CurrentButton.getPosX() - Camera.PosX, CurrentButton.getPosY() - Camera.PosY, CurrentButton.getButtonWidth(), CurrentButton.getButtonHeight());
            TextHandler.addDisplayedText(new GUIText(CurrentButton.getButtonText(), CurrentButton.getPosX(), CurrentButton.getPosY(), CurrentButton.getButtonHeight(), 50,ImageManager.GAMEFONT));
        }
    }
    
    public PauseScreen() {
        PauseButtons.add(new GUIButton(1920 /2 - 300/2, 1080 /2 - 50/2, 300, 50, ImageManager.ENEMY, "Close Game"));
    }
    
    public void handleClick(double CursorX, double CursorY) {
        for(GUIButton CurrentButton : PauseButtons) {
            if(CurrentButton.CursorOverButton(CursorX, CursorY)) {
                System.out.println("Button Works");
                System.exit(0);
            }
        }
    }
}
