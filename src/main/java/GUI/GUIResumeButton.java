package GUI;

public class GUIResumeButton extends GUIButton {
    public GUIResumeButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }

    @Override
    public void onButtonClick(long Window) {
        GUIManager.closeScreen();
    }
}