package GUI;

import Rendering.Frame;
import Scenes.MainMenuScene;
import Scenes.SceneManager;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Mouse {
    //Variablen für die Position
    public static double PosX;
    public static double PosY;
    
    public static void UpdateMousePos(long Window) {
        //Temporäre Variblen, weil es ne Liste ausgibt
        double[] X = new double[1];
        double[] Y = new double[1];

        //Position in die Liste schreiben
        glfwGetCursorPos(Window, X, Y);

        //Wenn der Cursor noch auf dem Bildschirm ist
        if(X[0] >= 0 && X[0] <= Frame.ScreenWidth && Y[0] >= 0 && Y[0] <= Frame.ScreenHeight) {
            //die Position aus der Liste in die tatsächliche Echte Position schreiben
            PosX = X[0];
            PosY = Y[0];
            //System.out.println(PosX + ":" + PosY);
        }
        SceneManager.ActiveScene.handleHovering(PosX, PosY);
    }
}
