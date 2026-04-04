package GUI.Buttons;

import GUI.GUIButton;
import Player.Player;
import Rendering.Frame;
import Scenes.SceneManager;

import java.awt.*;

public class QuitToMainMenuButton extends GUIButton {
    public QuitToMainMenuButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }
    
    @Override
    public void onButtonClick(long Window) {
        System.out.println("Quit Button Works");
        SceneManager.LoadScene(Frame.MainMenuScene, Window);
        Save.Save.SaveObjectData(Player.Player, "/Playerdata/Player.ser", 1); //Speichert Daten vom Spieler
    }
}
