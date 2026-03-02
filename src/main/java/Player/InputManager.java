package Player;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import java.awt.Image;
import java.awt.Toolkit;


public class InputManager {
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;

    private static final Image PlayerorigImg = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/textures/player/Player.png");
    public static Image PlayerImg = PlayerorigImg.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    
    private static final Image EnemyorigImg = Toolkit.getDefaultToolkit().getImage("src/main/resources/assets/textures/enemy/Enemy.png");
    public static Image EnemyImg = EnemyorigImg.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    
    private static final int[] Direction = {0, 0};
    public static Player Player = new Player(0, 0, 0, Direction, PlayerImg);
    
    public static void KeyEvent(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent Key) {
                synchronized (InputManager.class) {
                    switch (Key.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (Key.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = true;
                            System.out.println("W Pressed");
                            Player.setDirectionY(1); // Direction bei Y = 1, also in Positive Richtung bei Y laufen
                            InputHandler.Move(Player);
                            PlayerAnimationHandler.PlayWalkingAnimation();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = true;
                            System.out.println("S Pressed");
                            Player.setDirectionY(-1); // Direction bei Y = -1, also in Negative Richtung bei Y laufen
                            InputHandler.Move(Player);
                            PlayerAnimationHandler.PlayWalkingAnimation();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A) {
                            aPressed = true;
                            System.out.println("A Pressed");
                            Player.setDirectionX(1); // Direction bei X = 1, also in Positive Richtung bei X laufen
                            InputHandler.Move(Player);
                            PlayerAnimationHandler.PlayWalkingAnimation();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = true;
                            System.out.println("D Pressed");
                            Player.setDirectionX(-1); // Direction bei X = -1, also in Negative Richtung bei X laufen
                            InputHandler.Move(Player);
                            PlayerAnimationHandler.PlayWalkingAnimation();
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_C) {
                            System.out.println("C Pressed");
                            Enemy.Enemy.Spawn(new Enemy.Enemy(40, 40, 0, Direction, EnemyImg));
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_V) {
                            System.out.println("V Pressed");
                            Enemy.Enemy.Spawn(new Enemy.Enemy(200, 100, 0, Direction, EnemyImg));
                        }
                        break;

                    
                    case KeyEvent.KEY_RELEASED:
                        if (Key.getKeyCode() == KeyEvent.VK_W || Key.getKeyCode() == KeyEvent.VK_S) {
                            Player.setDirectionY(0);
                            if(Key.getKeyCode() == KeyEvent.VK_W) {
                                wPressed = false;
                            } else if(Key.getKeyCode() == KeyEvent.VK_S) {
                                sPressed = false;
                            }
                        }
                        if (Key.getKeyCode() == KeyEvent.VK_A || Key.getKeyCode() == KeyEvent.VK_D) {
                            Player.setDirectionX(0);
                            if(Key.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = false;
                            } else if(Key.getKeyCode() == KeyEvent.VK_D) {
                                dPressed = false;
                            }
                        }
                        if(!wPressed && !sPressed && !aPressed && !dPressed) {
                            InputHandler.Stop(Player);
                            PlayerAnimationHandler.StopAnimation();
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
