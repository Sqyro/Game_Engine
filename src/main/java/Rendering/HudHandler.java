package Rendering;

import java.awt.Color;
import java.util.ArrayList;

public class HudHandler {
    
    public static ArrayList<HudElement> HudElements = new ArrayList<>(); //Speicherort für alle HudElemente
    
    //Methode um neues Element zu placen
    public static void PlaceNewHud(int PosX, int PosY, int HudLength, int HudHeight, int TextureID) {
        HudElement hud = new HudElement(PosX, PosY, HudLength, HudHeight, TextureID); //Erstellt nen neues HudElement mit allen reingegbenen Variablen
        
        HudElements.add(hud); //Fügt das Element in die List hinzu
    }
    
    //Methode um neue Bar zu placen
    public static void PlaceNewBar(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, int BarDamage, int BarOffset, Color BarColor) {
        BarElement bar = new BarElement(PosX, PosY, BarLength, BarHeight, TextureID, BarDamage, BarOffset, BarColor); //Erstellt nen neues BarElement mit den Reingegebenen Variablen
        
        HudElements.add(bar); //Fügt die Bar in die Liste mit allen Elementen hinzu
    }
}
