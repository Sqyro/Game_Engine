package main;

import GUI.Frame;
import Player.InputManager;

public class Main {
    public static boolean running;
    
    public static int ScreenWidth = 1920;
    public static int ScreenHeight = 1080; // Wird je nach Setting überschrieben, Momentan auf HD Fullscreen gehardcoded
    
    public static void main(String[] args) {
        running = true;
        Frame frame = new Frame(ScreenWidth, ScreenHeight, "Sigma Ligma Game"); // Der Name war Niklas Idee
        System.out.println("Debug:");
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
