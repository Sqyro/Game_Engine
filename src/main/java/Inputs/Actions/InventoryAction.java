package Inputs.Actions;

import GUI.GUIManager;
import GUI.Screens.InventoryScreen;
import Inputs.KeyAction;
import Rendering.Frame;

public class InventoryAction extends KeyAction {

    @Override
    public void onPress() {
        if(GUIManager.isScreenOpen()) { //Wenn der Bildschirm schon offen ist
            if(GUIManager.currentScreen instanceof InventoryScreen) { //Wenn der Momentane Screen ein Inventar ist
                InventoryScreen Inventory = (InventoryScreen) GUIManager.currentScreen; //Screen holen
                Inventory.returnHeldItem(); //Die Methode callen, um das festgeahltene Item in seinen vorherigen Slot zu legen
                GUIManager.closeScreen(); //Bildschirm schließen
            }
        } else { //Wenn keiner offen ist, dann machen wir einen neuen aus
            if(GUIManager.currentScreen == null) {
                GUIManager.openScreen(new InventoryScreen(48, 2, 2, 13, 5, 625 * Frame.NormalizedPixelWidth, 502 * Frame.NormalizedPixelHeight));
            }
        }
    }

    @Override
    public void onRelease() {

    }
}
