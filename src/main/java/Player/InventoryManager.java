package Player;

import Item.Item;

import java.io.Serializable;

public class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Item[] Inventory;

    public InventoryManager(int Size) { //Constructor
        Inventory = new Item[Size]; //Erstellt eine Item Liste mit der gegebenen Größe
    }

    //Helfer Methoden um Items zu bekommen und zu setzen
    public Item getItem(int Slot) {
        return Inventory[Slot];
    }

    public void setItem(int Slot, Item newItem) {
        Inventory[Slot] = newItem;
    }

    //Helfer Methode um die Inventar Größe zu bekommen
    public int getInventorySize() {
        return Inventory.length;
    }
}
