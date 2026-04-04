package Scenes;

import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import GUI.InteractableFields.LoadGameInteractableField;
import Player.InputManager;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Shader.Shader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class LoadGameScene extends Scene {

    private Shader loadGameShader;
    private ImageHandler renderer;

    public List<GUIInteractableField> LoadGameInteractableFields = new ArrayList<>();

    //Liste für alle Texte die gerendert werden sollen
    public static List<GUIText> LoadGameDisplayedText = new ArrayList<>();

    public LoadGameScene() {
        loadGameShader = new Shader("src/main/resources/shaders/hudshader.vsh",
                "src/main/resources/shaders/hudshader.fsh");
        renderer = new ImageHandler();
    }
    
    @Override
    public void onCreation(long Window) {
        
    }
    
    @Override
    public void onLoadup(long Window) {
        InputManager.ListenforLoadGameKeys(Window);

        LoadGameInteractableFields.add(new LoadGameInteractableField(Frame.NormalizedPixelWidth * 50, Frame.NormalizedPixelHeight * 50, ImageManager.PLAYER, 1));
    }

    @Override
    public void onUnload() {
        clearOnScreenFields();
        clearDisplayedTextQue();
    }
    
    @Override
    public void onUpdate(float deltaTime) {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Schwarz setzen

        for(GUIInteractableField CurrentField : LoadGameInteractableFields) {
            CurrentField.drawField(renderer);
        }

        for(GUIText guiText : LoadGameDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }

        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f);

        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(loadGameShader, Frame.ScreenWidth, Frame.ScreenHeight);
    }

    @Override
    public void clearOnScreenFields() {
        LoadGameInteractableFields.clear();
    }

    @Override
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : LoadGameInteractableFields) {
            if(CurrentField.CursorHoveringOver(CursorX, CursorY)) {
                CurrentField.onFieldClick(Window);
                break;
            }
        }
    }

    @Override
    public void handleHovering(double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : LoadGameInteractableFields) {
            CurrentField.CursorHoveringOver(CursorX, CursorY);
        }
    }

    @Override
    public void addDisplayedText(GUIText addedText) {
        LoadGameDisplayedText.add(addedText);
    }

    @Override
    public void clearDisplayedTextQue() {
        LoadGameDisplayedText.clear();
    }
}