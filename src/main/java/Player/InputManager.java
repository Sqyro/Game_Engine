package Player;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class InputManager {
    //private static volatile boolean wPressed = false;

    public static void KeyEvent(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent Key) {
                synchronized (InputManager.class) {
                    switch (Key.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (Key.getKeyCode() == KeyEvent.VK_W) {
                            //wPressed = true;
                            System.out.println("W Pressed");
                            InputHandler.MoveY(1); // Direction bei Y = 1, also in Positive Richtung bei Y laufen
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_S) {
                            System.out.println("S Pressed");
                            InputHandler.MoveY(-1); // Direction bei Y = -1, also in Negative Richtung bei Y laufen
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A) {
                            System.out.println("A Pressed");
                            InputHandler.MoveX(1); // Direction bei X = 1, also in Positive Richtung bei X laufen
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_D) {
                            System.out.println("D Pressed");
                            InputHandler.MoveX(-1); // Direction bei X = -1, also in Negative Richtung bei X laufen
                        }
                        break;

                    /*
                    case KeyEvent.KEY_RELEASED:
                        if (Key.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        }
                        break; */
                    }
                    return false;
                       
                }
            }
        });
    }
    
    /* public static boolean isWPressed() {
        synchronized (InputManager.class) {
            return wPressed;
        }
    }
    */
}
