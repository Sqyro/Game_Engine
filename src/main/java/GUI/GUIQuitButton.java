package GUI;

public class GUIQuitButton extends GUIButton {
    public GUIQuitButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, int TextureID, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, TextureID, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }
    
    @Override
    public void onButtonClick() {
        System.out.println("Quit Button Works");
        System.exit(0);
    }
}
