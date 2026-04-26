package GUI.Buttons;

import GUI.GUIButton;
import Player.Player;
import Rendering.Frame;
import Scenes.GameScene;
import Scenes.SceneManager;

import java.awt.*;

public class QuitToMainMenuButton extends GUIButton {
    public QuitToMainMenuButton(float PosX, float PosY, float ButtonWidth, float ButtonHeight, String ButtonText, float ButtonTextSpacing, float ButtonTextSize, Color ButtonTextColor) {
        super(PosX, PosY, ButtonWidth, ButtonHeight, ButtonText, ButtonTextSpacing, ButtonTextSize, ButtonTextColor);
    }
    
    @Override
    public void onButtonClick(long Window) {
        System.out.println("Quit Button Works");
        if (SceneManager.ActiveScene instanceof GameScene) {
            Save.Save.SavePlayerData(Player.Player, ((GameScene) SceneManager.ActiveScene).SceneSaveID); //Speichert Daten vom Spieler
        }
        SceneManager.LoadScene(Frame.MainMenuScene, Window);
    }
}
