package GUI;

import java.awt.Image;
import java.util.ArrayList;

public class GUIHandler {
    
    public static ArrayList<HudElement> HudElements = new ArrayList<>();
    
    public static void PlaceNewHud(int PosX, int PosY, Image img) {
        HudElement hud = new HudElement(PosX, PosY, img);
        
        HudElements.add(hud);
    }
}
