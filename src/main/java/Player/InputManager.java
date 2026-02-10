package Player;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class InputManager {
    //private static volatile boolean wPressed = false;

    private static int[] Direction = {0, 0};
    public static Player Player = new Player(0, 0, 0, Direction);
    
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
                            InputHandler.Move(Player);
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_S) {
                            System.out.println("S Pressed");
                            Player.setDirectionY(-1); // Direction bei Y = -1, also in Negative Richtung bei Y laufen
                            InputHandler.Move(Player);
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A) {
                            System.out.println("A Pressed");
                            Player.setDirectionX(1); // Direction bei X = 1, also in Positive Richtung bei X laufen
                            InputHandler.Move(Player);
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_D) {
                            System.out.println("D Pressed");
                            Player.setDirectionX(-1); // Direction bei X = -1, also in Negative Richtung bei X laufen
                            InputHandler.Move(Player);
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_C) {
                            System.out.println("C Pressed");
                            Enemy.Enemy.Spawn(new Enemy.Enemy(40, 40, 0, Direction));
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_V) {
                            System.out.println("V Pressed");
                            Enemy.Enemy.Spawn(new Enemy.Enemy(40, -50, 0, Direction));
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
