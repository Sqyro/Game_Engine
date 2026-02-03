package main;

import GUI.Frame;
import Physics2D.VelocityHandler;
import Player.InputManager;
import javax.swing.Timer;

public class Main {
    public static boolean running;
    Timer timer;
    
    public static void main(String[] args) {
        running = true;
        java.awt.EventQueue.invokeLater(() -> new Frame("Sigma Ligma Game")); // Der Name war Niklas Idee
        System.out.println("Debug:");
        InputManager.KeyEvent(args);
        Enemy.Enemy.Spawn(40, 40, 1);
        //Update();
    }
    /*
    public static void Update() {
        new Thread(new Runnable() {
        @Override
        public void run() {
            // Game Tick Methode
        }
        }).start(); 
    }
    */
}
