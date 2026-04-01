package main;

import Rendering.Frame;

public class Main {
    
    //Wenn das Programm startet
    public static void main(String[] args) {
        System.out.println("Debug:"); //Debug: (Alles was folgt ist teil vom Debug)
        HelloWorld("print");
        
        //Alle anderen Dinge die bei Start passieren sind in Frame
        Frame frame = new Frame("Sigma Ligma Game"); // Erstellt nen Frame mit Titel auf Bildschirmgröße. Der Name war Niklas Idee
    }
    
    public static void HelloWorld(String print) {
        if(print == "print") {
            System.out.println("HelloWorld");
        }
    }
}
