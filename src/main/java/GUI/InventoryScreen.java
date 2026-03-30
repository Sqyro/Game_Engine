package GUI;

import Rendering.ImageHandler;
import Rendering.ImageManager;
import Rendering.Camera;
import Item.Item;
import Player.Player;

import java.util.List;
import java.util.ArrayList;

public class InventoryScreen extends GUIScreen {
    private int TextureID = ImageManager.INVENTORY; // TexturID von einem Inventory Screen
    
    //Größe der Inventar Textur auf dem Screen
    private int TextureWidth = 800;
    private int TextureHeight = 800;
    
    //Liste für die Inventar Slots
    private List<GUIInventorySlot> InventorySlots = new ArrayList<>();
    
    //Variable die Angibt, wie groß ein Item ist, wenn man es festhält
    private int PickedUpItemSize = 64;
    
    //Variable für das Item was man gerade mit dem Cursor festhält
    private Item HeldItem = null;
    
    //Varible, die den letzten Slot von dem Item welches gerade vom Cursor gehalten wird das letzte mal war
    private int HeldItemLastSlot = -1;
    
    //Render Methode von GUI Screen angepasst für InventoryScreen. (Passed die ganzen Werte fürs Rendering hoch in GUIScreen)
    @Override
    public void renderScreen(ImageHandler renderer, int ScreenWidth, int ScreenHeight) {
        //Position der Textur ausrechnen, Mitte des Bildschirms minus die hälfte der Länge der Textur, damit es zentriert ist
        int PosX = ScreenWidth / 2 - TextureWidth / 2 ;
        int PosY = ScreenHeight / 2 - TextureHeight / 2;

        //An Camera Position anpassen (folgt dann dem Spieler) und in den draw que packen
        renderer.drawFull(TextureID, PosX - Camera.PosX, PosY - Camera.PosY, TextureWidth, TextureHeight, 1f, 1f, 1f);
        
        //Spieler holen
        Player player = Player.Player;
        
        for(GUIInventorySlot CurrentSlot : InventorySlots) { //Jeden Slot durchgehen
            Item CurrentItem = player.inventory.getItem(CurrentSlot.SlotIndex); //Item im momentanen Slot holen
            if(CurrentItem != null) { //Wenn im Momentanen Slot ein Item ist
                //Item zeichnen an der Position vom Slot in der größe vom Slot
                renderer.drawFull(CurrentItem.getTextureID(), CurrentSlot.PosX - Camera.PosX, CurrentSlot.PosY - Camera.PosY, CurrentSlot.SlotSize, CurrentSlot.SlotSize, 1f, 1f, 1f);
            }
        }

        if(HeldItem != null) { // Wenn wir gerade ein Item mit der Cursor festhalten
            //Das item was wir gerade Festhalten an der Position vom Cursor zentriert zeichnen
            renderer.drawFull(HeldItem.getTextureID(), (float)Mouse.PosX - HeldItem.getTextureWidth() / 2 - Camera.PosX, (float)Mouse.PosY - HeldItem.getTextureWidth() / 2 - Camera.PosY, PickedUpItemSize, PickedUpItemSize, 1f, 1f, 1f);
        }
    }
    
    //Constructor für den Inventar Screen
    public InventoryScreen(int SlotSize, int SlotSpacingX, int SlotSpacingY, int Columns, int Rows, int SlotStartX, int SlotStartY) {
        //Index Variable, die angibt in welchem Slot wir jetzt insgesamt sind (Also X und Y zusammen so zu sagen)
        int SlotIndex = 0;

        for(int SlotY = 0; SlotY < Rows; SlotY++) { //Alle Slots vertikal durchgehen
            for(int SlotX = 0; SlotX < Columns; SlotX++) { //Alle Slots auf der vertikalen Ebene horizontal durchgehen
                //Einen neuen Inventar Slot an der momentanen Position hinzufügen
                InventorySlots.add(new GUIInventorySlot(SlotStartX + SlotX * (SlotSize + SlotSpacingX), SlotStartY + SlotY * (SlotSize + SlotSpacingY), SlotSize, SlotIndex));
                SlotIndex++; //Den globalen SlotIndex um eins erhöhen
            }
        }
    }
    
    //Methode, damit man Items anclicken und damit Dinge tuhen kann
    public void handleClick(double CursorX, double CursorY) {
        //Spieler sneaken
        Player player = Player.Player;

        for(GUIInventorySlot CurrentSlot : InventorySlots) { //Alle Slots im Inventar duchgehen
            if(CurrentSlot.isMouseOver(CursorX, CursorY)) { //Schauen ob der Cursor über diesem Slot ist
                //Item aus dem Momentanen Slot holen
                Item clickedItem = player.inventory.getItem(CurrentSlot.SlotIndex);

                if(HeldItem == null) { //Wenn gerade kein Item gehalten wird
                    HeldItem = clickedItem; //Das angeclickte Item festhalten
                    HeldItemLastSlot = CurrentSlot.SlotIndex; //Den Momentanen Slot als den letzten Slot von dem Item nehmen
                    player.inventory.setItem(CurrentSlot.SlotIndex, null); //Das Item aus dem Spieler Inventar an dieser Stelle nehmen
                } else { //Wenn gerade ein Item gehalten wird
                    player.inventory.setItem(CurrentSlot.SlotIndex, HeldItem); //Das Item was gerade festgehalten wird in das Spieler Inventar an diese Stelle packen
                    HeldItem = clickedItem; //Das festgehaltene Item auf das Item in dem Slot setzen, also mit einem Item tauschen, oder halt mit nichts tauschen -> ablegen
                    
                    if(HeldItem == null) { //Wenn jetzt das festgehaltene Item leer ist, also das davor gehaltene abgelegt, mit nichts getauscht, wurde
                        HeldItemLastSlot = -1; //Das den letzten Slot wieder zurück setzen
                    }
                }
                return; //Wenn der Momentane Slot angeclicked wurde, dann wird die Methode beendet, weil wir dann ja nichtmehr weiter durch gehen müssen (Kann ja nur ein Slot angeclicked werden) Spart Ressourcen
            }
        }
    }
    
    public void returnHeldItem() {
        if(HeldItem != null) { //Wenn ein Item festgehalten wird
            
            Player player = Player.Player; //Spieler sneaken
            player.inventory.setItem(HeldItemLastSlot, HeldItem); //Das Item was festgehalten wird in den Slot wo es vorher war packen

            HeldItem = null; //Das festgehaltene Item zurücksetzen
        }
    }
}