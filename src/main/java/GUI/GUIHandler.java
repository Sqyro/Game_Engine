package GUI;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

public class GUIHandler {
    
    public static ArrayList<HudElement> HudElements = new ArrayList<>();
    
    public static void PlaceNewHud(int PosX, int PosY, Image img) {
        HudElement hud = new HudElement(PosX, PosY, img);
        
        HudElements.add(hud);
    }
    
    public static void PlaceNewBar(int PosX, int PosY, Image img, int BarLength, int BarHeight, int BarDamage,Color BarColor) {
        BarElement bar = new BarElement(PosX, PosY, img, BarLength, BarHeight, BarDamage, BarColor);
        
        HudElements.add(bar);
    }
}
