package GUI.Buttons;

import GUI.GUIButton;
import GUI.GUIManager;
import GUI.Screens.AudioSettingsScreen;
import GUI.Screens.VideoSettingsScreen;

import java.awt.*;

public class AudioSettingsButton extends GUIButton {
    public AudioSettingsButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        if (!(GUIManager.currentScreen instanceof AudioSettingsScreen)) {
            GUIManager.openScreen(new AudioSettingsScreen());
        }
    }
}
