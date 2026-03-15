package GUI;

public class GUIInventorySlot {
    //Position des Slots
    public int PosX;
    public int PosY;
    
    //Größe des Slots
    public int SlotSize;

    //Index, also Position in der Liste
    public int SlotIndex;

    public GUIInventorySlot(int PosX, int PosY, int SlotSize, int SlotIndex) { //Constructor
        this.PosX = PosX;
        this.PosY = PosY;
        this.SlotSize = SlotSize;
        this.SlotIndex = SlotIndex;
    }

    //Ne Methode die schaut ob der Cursor über dem Slot ist und entsprechend true oder false zurückgibt
    public boolean isMouseOver(double CursorX, double CursorY) {
        //Ich weiß, dass ist super unübersichtlich, aber es check im Prinzip nur, ob der Cursor am linken, rechten, obeneren oder unteneren Rand vom Slot drüber/drauf ist
        return CursorX >= PosX && CursorX <= PosX + SlotSize && CursorY >= PosY && CursorY <= PosY + SlotSize;
    }
}
