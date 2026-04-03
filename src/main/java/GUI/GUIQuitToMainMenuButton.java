package GUI;

import Player.Player;
import Rendering.Frame;
import Scenes.SceneManager;

public class GUIQuitToMainMenuButton extends GUIButton {
    public GUIQuitToMainMenuButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize);
    }
    
    @Override
    public void onButtonClick(long Window) {
        System.out.println("Quit Button Works");
        SceneManager.LoadScene(Frame.MainMenuScene, Window);
        Save.Save.SaveData(Player.Player, "gamesession/Playerdata/Player.ser"); //Speichert Daten vom Spieler
        //System.exit(0);
    }
}
