package GUI;

import Rendering.Frame;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Mouse {
    public static double PosX;
    public static double PosY;
    
    public static void UpdateMousePos(long window) {
        double[] X = new double[1];
        double[] Y = new double[1];

        glfwGetCursorPos(window, X, Y);

        if(X[0] >= 0 && X[0] <= Frame.ScreenWidth && Y[0] >= 0 && Y[0] <= Frame.ScreenHeight) {
            PosX = X[0];
            PosY = Y[0];
            //System.out.println(PosX + ":" + PosY);
        }
    }
}
