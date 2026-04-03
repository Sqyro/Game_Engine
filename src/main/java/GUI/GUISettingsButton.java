package GUI;

import Rendering.Frame;
import Scenes.SceneManager;

public class GUISettingsButton extends GUIButton {
    public GUISettingsButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }

    @Override
    public void onButtonClick(long Window) {
        SceneManager.LoadScene(Frame.SettingsScene, Window);
    }
}
