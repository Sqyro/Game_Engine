package main;

import GUI.Frame;
import Player.InputManager;

public class Main {
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(() -> new Frame("Sigma Ligma Game")); // Der Name war Niklas Idee
         InputManager.KeyEvent(args);
    }
}
