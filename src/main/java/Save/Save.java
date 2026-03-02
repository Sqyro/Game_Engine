package Save;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Player.InputManager;

public class Save {
    public static void SaveData() {
        try {
         FileOutputStream fileOut = new FileOutputStream("gamesession/Playerdata/Player.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(InputManager.Player);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in /gamesession/Playerdata/Player.ser");
      } catch (IOException i) {
         i.printStackTrace();
      }
    }
    
    public static void LoadData() {
        
    }
}
