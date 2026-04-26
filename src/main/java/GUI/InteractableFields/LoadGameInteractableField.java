package GUI.InteractableFields;

import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import Player.Player;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Scenes.GameScene;
import Scenes.Scene;
import Scenes.SceneManager;

import java.awt.*;

public class LoadGameInteractableField extends GUIInteractableField {

    private int FieldID;

    private String LoadGameFieldText;
    private final static float LoadGameFieldTextSpacing = 15;
    private final static float LoadGameFieldTextSize = 30;
    private final static float LoadGameFieldVerticalDistance = 50;

    public LoadGameInteractableField(float PosX, float PosY, int TextureID, int FieldID) {
        super(PosX, PosY + (Frame.NormalizedPixelHeight * 200 + LoadGameFieldVerticalDistance) * (FieldID), Frame.NormalizedPixelWidth * 800, Frame.NormalizedPixelHeight * 200, TextureID);
        this.LoadGameFieldText = "Save " + (FieldID + 1);
        this.FieldID = FieldID;
    }

    @Override
    public void drawField(ImageHandler renderer) {
        renderer.drawFull(TextureID, PosX, PosY, FieldWidth, FieldHeight, 1f, 1f, 1f, 1f);
        GUIManager.renderText(new GUIText(LoadGameFieldText,PosX + Frame.NormalizedPixelWidth * 25, PosY + Frame.NormalizedPixelHeight * 25, LoadGameFieldTextSize, LoadGameFieldTextSpacing, ImageManager.GAMEFONT, Color.WHITE), renderer);
    }

    @Override
    public void onFieldClick(long Window) {
        SceneManager.CreateNewScene(new GameScene(FieldID), Window);
        SceneManager.LoadScene(SceneManager.AllGameScenes.get(FieldID), Window);
        Player LoadedPlayerData = Save.Save.LoadPlayerData(FieldID); //Läd Spieldateien aus dem Speicher
        if (LoadedPlayerData != null) { //Darf nicht leer sein
            Player.Player = LoadedPlayerData;
        }
    }
}