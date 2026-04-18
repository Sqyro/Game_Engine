package Player;

import Item.Item;
import Spell.Spell;
import Spell.UltSpell;

import java.io.Serializable;

public class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Item[] Inventory;
    private Spell[] Spells;
    private UltSpell[] UltSpells;

    public InventoryManager(int InventorySize, int SpellSize, int UltSpellSize) { //Constructor
        Inventory = new Item[InventorySize]; //Erstellt eine Item Liste mit der gegebenen Größe
        Spells = new Spell[SpellSize];
        UltSpells = new UltSpell[UltSpellSize];
    }

    //Helfer Methoden um Items zu bekommen und zu setzen
    public Item getItem(int Slot) {
        return Inventory[Slot];
    }

    public void setItem(int Slot, Item newItem) {
        Inventory[Slot] = newItem;
    }

    public Spell getSpell(int Slot) {
        return Spells[Slot];
    }

    public void setSpell(int Slot, Spell newSpell) {
        Spells[Slot] = newSpell;
    }

    public UltSpell getUltSpell(int Slot) {
        return UltSpells[Slot];
    }

    public void setUltSpell(int Slot, UltSpell newUltSpell) {
        UltSpells[Slot] = newUltSpell;
    }

    //Helfer Methode um die Inventar Größe zu bekommen
    public int getInventorySize() {
        return Inventory.length;
    }

    public int getSpellSize() {
        return Spells.length;
    }

    public int getUltSpellSize() {
        return UltSpells.length;
    }
}
