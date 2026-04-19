package GUI.Buttons;

import GUI.GUIButton;
import GUI.GUIManager;
import GUI.Screens.ControlsScreen;

import java.awt.*;

public class ControlsButton extends GUIButton {
    public ControlsButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        if (!(GUIManager.currentScreen instanceof ControlsScreen)) {
            GUIManager.openScreen(new ControlsScreen());
        }
    }
}
