package GUI;

import java.util.ArrayList;
import java.util.List;

public class TextHandler {
    //Liste für alle Texte die gerendert werden sollen
    public static List<GUIText> ToBeDisplayedText = new ArrayList<>();
    
    
    //Hilfs Methoden um Text in den ToBeDisplayedText zu packen und den ToBeDisplayedText zu leeren
    public static void addDisplayedText(GUIText adddedText) {
        ToBeDisplayedText.add(adddedText);
    }
    
    public static void clearDisplayedTextQue() {
        ToBeDisplayedText.clear();
    }
}