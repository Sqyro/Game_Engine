package GUI;

import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Scenes.SceneManager;
import Scenes.GameScene;

import java.util.List;
import java.util.ArrayList;

import java.awt.Color;

public class PauseScreen extends GUIScreen {
    private int CenterX;
    private int CenterY;
    
    private final float ResumeButtonWidth = 300;
    private final float ResumeButtonHeight = 50;
    private final String ResumeButtonText = "Resume";
    private final float ResumeButtonTextSpacing = 15;
    private final float ResumeButtonTextSize = 30;
    
    private final float SettingsButtonWidth = 300;
    private final float SettingsButtonHeight = 50;
    private final String SettingsButtonText = "Settings";
    private final float SettingsButtonTextSpacing = 15;
    private final float SettingsButtonTextSize = 30;
    
    private final float CloseButtonWidth = 300;
    private final float CloseButtonHeight = 50;
    private final String CloseButtonText = "Close Game";
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
        GameScene.GameRunning = false;
        PauseButtons.add(new GUIResumeButton(Frame.ScreenWidth /2 - ResumeButtonWidth/2, Frame.ScreenHeight /2 - ResumeButtonHeight/2 - 67 * Frame.NormalizedPixelHeight * 2, ResumeButtonWidth, ResumeButtonHeight, ResumeButtonText, ResumeButtonTextSpacing, ResumeButtonTextSize));
        PauseButtons.add(new GUISettingsButton(Frame.ScreenWidth /2 - SettingsButtonWidth/2, Frame.ScreenHeight /2 - SettingsButtonHeight/2 - 67 * Frame.NormalizedPixelHeight, SettingsButtonWidth, SettingsButtonHeight, SettingsButtonText, SettingsButtonTextSpacing, SettingsButtonTextSize));
        PauseButtons.add(new GUIQuitButton(Frame.ScreenWidth /2 - CloseButtonWidth/2, Frame.ScreenHeight /2 - CloseButtonHeight/2, CloseButtonWidth, CloseButtonHeight, CloseButtonText, CloseButtonTextSpacing, CloseButtonTextSize));
    }
    
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIButton CurrentButton : PauseButtons) {
            if(CurrentButton.CursorHoveringOverButton(CursorX, CursorY)) {
                CurrentButton.onButtonClick(Window);
            }
        }
    }
    
    public void handleHovering(long Window, double CursorX, double CursorY) {
        for(GUIButton CurrentButton : PauseButtons) {
            CurrentButton.CursorHoveringOverButton(CursorX, CursorY);
        }
    }
}
