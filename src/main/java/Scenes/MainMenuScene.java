package Scenes;

import GUI.*;
import Player.InputManager;
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

    public List<GUIButton> MainMenuButtons = new ArrayList<>();

    private final float ExitButtonWidth = 300;
    private final float ExitButtonHeight = 50;
    private final String ExitButtonText = "Exit";
    private final float ExitButtonTextSpacing = 15;
    private final float ExitButtonTextSize = 30;

    public MainMenuScene() {
        mainMenuShader = new Shader("src/main/resources/shaders/hudshader.vsh",
                "src/main/resources/shaders/hudshader.fsh");
        renderer = new ImageHandler();
}
    
    @Override
    public void onCreation(long Window) {
        //Alle nötigen Texturen laden
        ImageManager.loadStartTextures();
        }
    
    @Override
    public void onLoadup(long Window) {
        InputManager.ListenforMainMenuKeys(Window);

        TextHandler.clearDisplayedTextQue();

        MainMenuButtons.add(new GUIExitGameButton(Frame.ScreenWidth /2 - ExitButtonWidth/2, Frame.ScreenHeight /2 - ExitButtonHeight/2, ExitButtonWidth, ExitButtonHeight, ExitButtonText, ExitButtonTextSpacing, ExitButtonTextSize));
    }

    @Override
    public void onUpdate(float deltaTime) {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Schwarz setzen

        for(GUIButton CurrentButton : MainMenuButtons) {
            CurrentButton.drawButton(renderer, Color.WHITE);
        }

        for(GUIText guiText : TextHandler.ToBeDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }

        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f);

        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(mainMenuShader, Frame.ScreenWidth, Frame.ScreenHeight);
    }

    @Override
    public void clearOnScreenButtons() {
        MainMenuButtons.clear();
    }

    @Override
    public void handleClick(long Window, double CursorX, double CursorY) {
        for(GUIButton CurrentButton : MainMenuButtons) {
            if(CurrentButton.CursorHoveringOverButton(CursorX, CursorY)) {
                CurrentButton.onButtonClick(Window);
                break;
            }
        }
    }

    @Override
    public void handleHovering(double CursorX, double CursorY) {
        for(GUIButton CurrentButton : MainMenuButtons) {
            CurrentButton.CursorHoveringOverButton(CursorX, CursorY);
        }
    }
}