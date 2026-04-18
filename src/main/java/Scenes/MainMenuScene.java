package Scenes;

import GUI.*;
import GUI.Buttons.ExitGameButton;
import GUI.Buttons.LoadGameButton;
import Player.InputManager;
import Player.PlayerAnimationManager;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Shader.Shader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class MainMenuScene extends Scene {
    private Shader mainMenuShader;
    private ImageHandler renderer;
    private BackgroundAnimationManager backgroundAnimationManager;

    public List<GUIInteractableField> MainMenuInteractableFields = new ArrayList<>();

    private final float ButtonYOffset = -150;

    private final float ExitButtonWidth = 300;
    private final float ExitButtonHeight = 50;
    private final String ExitButtonText = "Exit";
    private final float ExitButtonTextSpacing = 15;
    private final float ExitButtonTextSize = 30;

    private final float LoadGameButtonWidth = 300;
    private final float LoadGameButtonHeight = 50;
    private final String LoadGameButtonText = "Load Game";
    private final float LoadGameButtonTextSpacing = 15;
    private final float LoadGameButtonTextSize = 30;

    //Liste für alle Texte die gerendert werden sollen
    public static List<GUIText> MainMenuDisplayedText = new ArrayList<>();

    public MainMenuScene() {
        mainMenuShader = new Shader("src/main/resources/shaders/hudshader.vsh",
                "src/main/resources/shaders/hudshader.fsh");
        renderer = new ImageHandler();

        backgroundAnimationManager = new BackgroundAnimationManager();
    }
    
    @Override
    public void onCreation(long Window) {
        //Alle nötigen Texturen laden
        ImageManager.loadStartTextures();

        backgroundAnimationManager.createBackgroundAnimations();
    }
    
    @Override
    public void onLoadup(long Window) {
        InputManager.ListenforMainMenuKeys(Window);

        MainMenuInteractableFields.add(new LoadGameButton(Frame.ScreenWidth /2 - LoadGameButtonWidth/2, Frame.ScreenHeight /2 - LoadGameButtonHeight/2 - Frame.NormalizedPixelHeight * ExitButtonHeight - ButtonYOffset, LoadGameButtonWidth, LoadGameButtonHeight, LoadGameButtonText, LoadGameButtonTextSpacing, LoadGameButtonTextSize, Color.WHITE));
        MainMenuInteractableFields.add(new ExitGameButton(Frame.ScreenWidth /2 - ExitButtonWidth/2, Frame.ScreenHeight /2 - ExitButtonHeight/2 - ButtonYOffset, ExitButtonWidth, ExitButtonHeight, ExitButtonText, ExitButtonTextSpacing, ExitButtonTextSize, Color.WHITE));
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

        backgroundAnimationManager.updateBackgroundAnimation(deltaTime);

        backgroundAnimationManager.currentAnimation = backgroundAnimationManager.MainMenuAnimation;
        backgroundAnimationManager.currentAnimation.renderAnimation(0, 0, Frame.ScreenWidth, Frame.ScreenHeight, false, renderer);

        for(GUIInteractableField CurrentField : MainMenuInteractableFields) {
            CurrentField.drawField(renderer);
        }

        for(GUIText guiText : MainMenuDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }

        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f, 1f);

        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(mainMenuShader, Frame.ScreenWidth, Frame.ScreenHeight);
    }

    @Override
    public void clearOnScreenFields() {
        MainMenuInteractableFields.clear();
    }

    @Override
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : MainMenuInteractableFields) {
            if(CurrentField.CursorHoveringOver(CursorX, CursorY)) {
                CurrentField.onFieldClick(Window);
                break;
            }
        }
    }

    @Override
    public void handleHovering(double CursorX, double CursorY) {
        for(GUIInteractableField CurrentField : MainMenuInteractableFields) {
            CurrentField.CursorHoveringOver(CursorX, CursorY);
        }
    }

    @Override
    public void addDisplayedText(GUIText addedText) {
        MainMenuDisplayedText.add(addedText);
    }

    @Override
    public void clearDisplayedTextQue() {
        MainMenuDisplayedText.clear();
    }
}