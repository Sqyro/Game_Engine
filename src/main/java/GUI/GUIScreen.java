package GUI;

import Rendering.ImageHandler;

public abstract class GUIScreen {
    public boolean isOpen = false; //Variable um zu checken ob diese Instance vom Screen offen ist
    
    //Methode um Werte von allen Screens hoch zu passen
    //Hab gerade herausgefunden wie Abstrakte Klassen funktionieren (Schreibe nicht so häufig solche Sachen in java). Muss mal die anderen Klassen ändern
    public abstract void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight); 
    
    
    //Hilfs Methoden
    public boolean getOpenScreen() {
        return isOpen;
    }
    
    public void setOpenScreen(boolean newisOpen) {
        isOpen = newisOpen;
    }
}