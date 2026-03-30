package GUI;

import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;

import java.util.List;
import java.util.ArrayList;

import java.awt.Color;

public class PauseScreen extends GUIScreen {
    private int CenterX;
    private int CenterY;
    
    private final String CloseButtonText = "Close Game";
    private final float CloseButtonWidth = 300;
    private final float CloseButtonHeight = 50;
    private final float CloseButtonTextSpacing = 15;
    private final float CloseButtonTextSize = 30;
    
    public List<GUIButton> PauseButtons = new ArrayList<>();
    
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        //Mitte vom Bildschirm ausrechnen, damit es zentriert ist
        CenterX = ScreenWidth / 2;
        CenterY = ScreenHeight / 2;
        
        for(GUIButton CurrentButton : PauseButtons) {
            CurrentButton.drawButton(renderer, Color.WHITE);
        }
    }
    
    public PauseScreen() {
        Frame.GameRunning = false;
        PauseButtons.add(new GUIButton(Frame.ScreenWidth /2 - CloseButtonWidth/2, Frame.ScreenHeight /2 - CloseButtonHeight/2, CloseButtonWidth, CloseButtonHeight, ImageManager.PLAYER, CloseButtonText, CloseButtonTextSpacing, CloseButtonTextSize));
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
