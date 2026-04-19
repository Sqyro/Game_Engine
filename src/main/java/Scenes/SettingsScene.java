package Scenes;

import GUI.GUIInteractableField;
import GUI.GUIManager;
import GUI.GUIText;
import GUI.Screens.VideoSettingsScreen;
import Player.InputManager;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Shader.Shader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class SettingsScene extends Scene {
    
    private Shader settingsShader;
    private ImageHandler renderer;

    public List<GUIInteractableField> SettingsInteractableFields = new ArrayList<>();

    //Liste für alle Texte die gerendert werden sollen
    public static List<GUIText> SettingsDisplayedText = new ArrayList<>();

    public SettingsScene() {
        settingsShader = new Shader("src/main/resources/shaders/hudshader.vsh",
                              "src/main/resources/shaders/hudshader.fsh");
        renderer = new ImageHandler();
    }
    
    @Override
    public void onCreation(long Window) {
        
    }
    
    @Override
    public void onLoadup(long Window) {
        InputManager.ListenforSettingsKeys(Window);
        GUIManager.openScreen(new VideoSettingsScreen());
    }

    @Override
    public void onUnload() {
        GUIManager.closeScreen();

        clearOnScreenFields();
        clearDisplayedTextQue();
    }

    @Override
    public void onUpdate(float deltaTime) {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Schwarz setzen

        if(GUIManager.isScreenOpen()) {
            GUIManager.currentScreen.renderScreen(renderer, Frame.ScreenWidth, Frame.ScreenHeight);
        }

        for(GUIInteractableField CurrentField : SettingsInteractableFields) {
            CurrentField.drawField(renderer);
        }

        for(GUIText guiText : SettingsDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }
        
        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f, 1f);
        
        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(settingsShader, Frame.ScreenWidth, Frame.ScreenHeight);
    }

    @Override
    public void clearOnScreenFields() {
        SettingsInteractableFields.clear();
    }

    @Override
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : SettingsInteractableFields) {
            if(CurrentField.CursorHoveringOver(CursorX, CursorY)) {
                CurrentField.onFieldClick(Window);
                break;
            }
        }
    }

    @Override
    public void handleHovering(double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : SettingsInteractableFields) {
            CurrentField.CursorHoveringOver(CursorX, CursorY);
        }
    }

    @Override
    public void addDisplayedText(GUIText addedText) {
        SettingsDisplayedText.add(addedText);
    }

    @Override
    public void clearDisplayedTextQue() {
        SettingsDisplayedText.clear();
    }
}
