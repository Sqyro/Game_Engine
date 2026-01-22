package main;

import GUI.Frame;
import Player.InputManager;
import javax.swing.Timer;

public class Main {
    public static boolean running;
    Timer timer;
    
    public static void main(String[] args) {
        running = true;
        java.awt.EventQueue.invokeLater(() -> new Frame("Sigma Ligma Game")); // Der Name war Niklas Idee
        InputManager.KeyEvent(args);
        Update();
    }
    
    public static void Update() {
        new Thread(new Runnable() {
        @Override
        public void run() {
            // Game Tick Methode
        }
        }).start(); 
    }
}
