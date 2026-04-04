package GUI.InteractableFields;

import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import Player.Player;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Scenes.SceneManager;

import java.awt.*;

public class LoadGameInteractableField extends GUIInteractableField {

    private int FieldID;

    private String LoadGameFieldText;
    private final float LoadGameFieldTextSpacing = 15;
    private final float LoadGameFieldTextSize = 30;

    public LoadGameInteractableField(float PosX, float PosY, int TextureID, int FieldID) {
        super(PosX, PosY, Frame.NormalizedPixelWidth * 800, Frame.NormalizedPixelHeight * 200, TextureID);
        this.LoadGameFieldText = "Save " + FieldID;
        this.FieldID = FieldID;
    }

    @Override
    public void drawField(ImageHandler renderer) {
        renderer.drawFull(TextureID, PosX, PosY, FieldWidth, FieldHeight, 1f, 1f, 1f);
        GUIManager.renderText(new GUIText(LoadGameFieldText,PosX + Frame.NormalizedPixelWidth * 25, PosY + Frame.NormalizedPixelHeight * 25, LoadGameFieldTextSize, LoadGameFieldTextSpacing, ImageManager.GAMEFONT, Color.WHITE), renderer);
    }

    @Override
    public void onFieldClick(long Window) {
        SceneManager.LoadScene(Frame.GameScene, Window);
        Player LoadedData = (Player) Save.Save.LoadObjectData(FieldID); //Läd Spieldateinen aus dem Speicher
        if (LoadedData != null) { //Darf nicht leer sein
            Player.Player.setPosX(LoadedData.getPosX()); //Holt sich die Positonen aus den Daten
            Player.Player.setPosY(LoadedData.getPosY());
            Player.Player.inventory = LoadedData.inventory; //Holt sich das Inventar aus dem Speicher
        }
    }
}
