package GUI.Buttons;

import GUI.GUIButton;
import Rendering.Frame;
import Scenes.SceneManager;

import java.awt.*;

public class SettingsButton extends GUIButton {
    public SettingsButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        SceneManager.LoadScene(Frame.SettingsScene, Window);
    }
}
