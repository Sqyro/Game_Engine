package Main;

import Rendering.Frame;

public class Main {
    
    //Wenn das Programm startet
    public static void main(String[] args) {
        System.out.println("Debug:"); //Debug: (Alles was folgt ist teil vom Debug und dies soll sichtbar sein)
        HelloWorld("print");
        
        //Alle anderen Dinge die bei Start passieren sind in Frame
        Frame frame = new Frame("Keys To Hell"); // Erstellt einen Frame mit Titel. Der Name war Niklas Idee
    }
    
    public static void HelloWorld(String print) {
        if(print == "print") {
            System.out.println("HelloWorld");
        }
    }
}
