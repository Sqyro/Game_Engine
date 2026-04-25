package Save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerSaveData implements Serializable {
    public float PosX;
    public float PosY;

    public List<String> Items = new ArrayList<>();
    public List<String> Spells = new ArrayList<>();
}
