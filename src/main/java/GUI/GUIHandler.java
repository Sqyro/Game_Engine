package GUI;

import java.awt.Color;
import java.util.ArrayList;

public class GUIHandler {
    
    public static ArrayList<HudElement> HudElements = new ArrayList<>();
    
    public static void PlaceNewHud(int PosX, int PosY, int HudLength, int HudHeight, int TextureID) {
        HudElement hud = new HudElement(PosX, PosY, HudLength, HudHeight, TextureID);
        
        HudElements.add(hud);
    }
    
    public static void PlaceNewBar(int PosX, int PosY, int BarLength, int BarHeight, int TextureID, int BarDamage, Color BarColor) {
        BarElement bar = new BarElement(PosX, PosY, BarLength, BarHeight, TextureID, BarDamage, BarColor);
        
        HudElements.add(bar);
    }
}
