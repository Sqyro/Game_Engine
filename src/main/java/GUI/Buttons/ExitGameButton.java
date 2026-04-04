package GUI.Buttons;

import GUI.GUIButton;

import java.awt.*;

public class ExitGameButton extends GUIButton {
    public ExitGameButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }

    @Override
    public void onButtonClick(long Window) {
        System.out.println("Exit Game Button works");
        System.exit(0);
    }
}
