package GUI.Screens;

import GUI.Buttons.QuitToMainMenuButton;
import GUI.Buttons.ResumeButton;
import GUI.Buttons.SettingsButton;
import GUI.GUIScreen;
import Rendering.Frame;
import Rendering.ImageHandler;
import Scenes.GameScene;
import Scenes.SceneManager;

import java.awt.*;

public class PauseScreen extends GUIScreen {

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
    private final String CloseButtonText = "Save & Quit";
    private final float CloseButtonTextSpacing = 15;
    private final float CloseButtonTextSize = 30;
    
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {

    }
    
    public PauseScreen() {
        GameScene.GameRunning = false;
        if (SceneManager.ActiveScene instanceof GameScene) {
            ((GameScene) SceneManager.ActiveScene).GameInteractableFields.add(new ResumeButton(Frame.ScreenWidth / 2 - ResumeButtonWidth / 2, Frame.ScreenHeight / 2 - ResumeButtonHeight / 2 - 67 * Frame.NormalizedPixelHeight * 2, ResumeButtonWidth, ResumeButtonHeight, ResumeButtonText, ResumeButtonTextSpacing, ResumeButtonTextSize, Color.WHITE));
            ((GameScene) SceneManager.ActiveScene).GameInteractableFields.add(new SettingsButton(Frame.ScreenWidth / 2 - SettingsButtonWidth / 2, Frame.ScreenHeight / 2 - SettingsButtonHeight / 2 - 67 * Frame.NormalizedPixelHeight, SettingsButtonWidth, SettingsButtonHeight, SettingsButtonText, SettingsButtonTextSpacing, SettingsButtonTextSize, Color.WHITE));
            ((GameScene) SceneManager.ActiveScene).GameInteractableFields.add(new QuitToMainMenuButton(Frame.ScreenWidth / 2 - CloseButtonWidth / 2, Frame.ScreenHeight / 2 - CloseButtonHeight / 2, CloseButtonWidth, CloseButtonHeight, CloseButtonText, CloseButtonTextSpacing, CloseButtonTextSize, Color.WHITE));
        }
    }
}
