package GUI;

public class GUIQuitButton extends GUIButton {
    public GUIQuitButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }
    
    @Override
    public void onButtonClick(long Window) {
        System.out.println("Quit Button Works");
        System.exit(0);
    }
}
