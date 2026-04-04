package GUI.Buttons;

import GUI.GUIButton;
import GUI.GUIManager;

import java.awt.*;

public class ResumeButton extends GUIButton {
    public ResumeButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        GUIManager.closeScreen();
    }
}