package Scenes;

import GUI.GUIManager;
import GUI.GUIText;
import GUI.TextHandler;
import Player.InputManager;
import Rendering.Camera;
import Rendering.Frame;
import Rendering.ImageHandler;
import Rendering.ImageManager;
import Shader.Shader;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class SettingsScene extends Scene {
    
    private Shader settingsShader;
    private ImageHandler renderer;
    
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
        
        TextHandler.clearDisplayedTextQue();
    }
    
    @Override
    public void onUpdate(float deltaTime) {
        glClearColor(0f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Schwarz setzen
        
        for(GUIText guiText : TextHandler.ToBeDisplayedText) { //Für jeden Text im ToBeDisplayed Text
            GUIManager.renderText(guiText, renderer); //Fügt den Text in den Render Que hinzu
        }
        
        //Einen eigenen Cursor zeichnen an der Position vom System Cursor
        renderer.drawFull(ImageManager.CURSOR, (float)GUI.Mouse.PosX - Camera.PosX, (float)GUI.Mouse.PosY - Camera.PosY, 32, 32, 1f, 1f, 1f);
        
        //Flushed den Screen Render durch mit dem Hud Shader
        renderer.flush(settingsShader, Frame.ScreenWidth, Frame.ScreenHeight);
    }
}
