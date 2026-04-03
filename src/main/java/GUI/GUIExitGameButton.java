package GUI;

public class GUIExitGameButton extends GUIButton{
    public GUIExitGameButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }

    @Override
    public void onButtonClick(long Window) {
        System.out.println("Exit Game Button works");
        System.exit(0);
    }
}
