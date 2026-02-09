package main;

import GUI.Frame;
import Player.InputManager;

public class Main {
    public static boolean running;
    
    public static void main(String[] args) {
        running = true;
        java.awt.EventQueue.invokeLater(() -> new Frame("Sigma Ligma Game")); // Der Name war Niklas Idee
        System.out.println("Debug:");
        InputManager.KeyEvent(args);
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
