package GUI.InteractableFields;

import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Scenes.GameScene;
import Scenes.SceneManager;

import java.awt.*;

public class CreateNewGameInteractableField extends GUIInteractableField {
    private int FieldID;


    private final static float CreateNewGameFieldTextSpacing = 30;
    private final static float CreateNewGameFieldTextSize = 60;
    private final static float CreateNewGameFieldVerticalDistance = 50;

    public CreateNewGameInteractableField(float PosX, float PosY, int FieldID) {
        super(PosX, PosY + (Frame.NormalizedPixelHeight * 200 + CreateNewGameFieldVerticalDistance) * (FieldID), Frame.NormalizedPixelWidth * 800, Frame.NormalizedPixelHeight * 200, 0);
        this.FieldID = FieldID;
    }

    @Override
    public void drawField(ImageHandler renderer) {
        GUIManager.renderText(new GUIText("+ Create new Save",PosX + Frame.NormalizedPixelWidth * 25, PosY + FieldHeight/2, CreateNewGameFieldTextSize, CreateNewGameFieldTextSpacing, ImageManager.GAMEFONT, Color.WHITE), renderer);
    }

    @Override
    public void onFieldClick(long Window) {

        SceneManager.CreateNewScene(new GameScene(FieldID), Window);
        SceneManager.LoadScene(SceneManager.AllGameScenes.get(FieldID), Window);
    }
}
