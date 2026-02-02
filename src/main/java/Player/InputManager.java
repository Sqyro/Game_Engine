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
                            Player.setDirectionY(1); // Direction bei Y = 1, also in Positive Richtung bei Y laufen
                            InputHandler.Move();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_S) {
                            System.out.println("S Pressed");
                            Player.setDirectionY(-1); // Direction bei Y = -1, also in Negative Richtung bei Y laufen
                            InputHandler.Move();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A) {
                            System.out.println("A Pressed");
                            Player.setDirectionX(1); // Direction bei X = 1, also in Positive Richtung bei X laufen
                            InputHandler.Move();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_D) {
                            System.out.println("D Pressed");
                            Player.setDirectionX(-1); // Direction bei X = -1, also in Negative Richtung bei X laufen
                            InputHandler.Move();
                        }
                        break;

                    
                    case KeyEvent.KEY_RELEASED:
                        if (Key.getKeyCode() == KeyEvent.VK_W || Key.getKeyCode() == KeyEvent.VK_S) {
                            Player.setDirectionY(0);
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A || Key.getKeyCode() == KeyEvent.VK_D) {
                            Player.setDirectionX(0);
                        }
                        break;
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
