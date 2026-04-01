package GUI;

public class GUIResumeButton extends GUIButton {
    public GUIResumeButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, int TextureID, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, TextureID, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }

    @Override
    public void onButtonClick(long Window) {
        TextHandler.clearDisplayedTextQue();
        GUIManager.closeScreen();
    }
    
}
