package Item;

import Item.Weapons.SwordItem;
import Registry.DeferredRegister;
import Rendering.ImageManager;

public class Items {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>();

    public static void RegisterItems() {
        ITEMS.register(new SwordItem(ImageManager.SWORD, 64, 64, "sword"));
    }
}
