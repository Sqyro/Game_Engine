package GUI.Buttons;

import GUI.GUIButton;
import GUI.GUIManager;
import GUI.Screens.VideoSettingsScreen;

import java.awt.*;

public class VideoSettingsButton extends GUIButton {
    public VideoSettingsButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        if (!(GUIManager.currentScreen instanceof VideoSettingsScreen)) {
            GUIManager.openScreen(new VideoSettingsScreen());
        }
    }
}
