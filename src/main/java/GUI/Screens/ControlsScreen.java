package GUI.Screens;

import GUI.Buttons.AudioSettingsButton;
import GUI.Buttons.ControlsButton;
import GUI.Buttons.VideoSettingsButton;
import GUI.GUIScreen;
import Rendering.Frame;
import Rendering.ImageHandler;

import java.awt.*;

public class ControlsScreen extends GUIScreen {

    private final float SwitchTabButtonsOffsetX = 100 * Frame.NormalizedPixelWidth;
    private final float SwitchTabButtonsOffsetY = 350 * Frame.NormalizedPixelHeight;

    private final float SwitchTabButtonsWidth = 300;
    private final float SwitchTabButtonsHeight = 50;
    private final String VideoSettingsTabButtonText = "Video Settings";
    private final String AudioSettingsTabButtonText = "Audio Settings";
    private final String ControlsTabButtonText = "Controls";
    private final float SwitchTabButtonsTextSpacing = 15;
    private final float SwitchTabButtonsTextSize = 30;


    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        Frame.SettingsScene.SettingsInteractableFields.add(new VideoSettingsButton(SwitchTabButtonsOffsetX, SwitchTabButtonsOffsetY, SwitchTabButtonsWidth, SwitchTabButtonsHeight, VideoSettingsTabButtonText, SwitchTabButtonsTextSpacing, SwitchTabButtonsTextSize, Color.WHITE));
        Frame.SettingsScene.SettingsInteractableFields.add(new AudioSettingsButton(SwitchTabButtonsOffsetX, SwitchTabButtonsOffsetY + SwitchTabButtonsHeight, SwitchTabButtonsWidth, SwitchTabButtonsHeight, AudioSettingsTabButtonText, SwitchTabButtonsTextSpacing, SwitchTabButtonsTextSize, Color.WHITE));
        Frame.SettingsScene.SettingsInteractableFields.add(new ControlsButton(SwitchTabButtonsOffsetX, SwitchTabButtonsOffsetY + SwitchTabButtonsHeight * 2, SwitchTabButtonsWidth, SwitchTabButtonsHeight, ControlsTabButtonText, SwitchTabButtonsTextSpacing, SwitchTabButtonsTextSize, Color.WHITE));
    }
}
