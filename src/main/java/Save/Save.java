package Save;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Player.InputManager;
import Player.Player;
import Physics2D.PhysicsObject2D;

public class Save {
    
    private static final String PATH = "gamesession/Playerdata/Player.ser";
    
    public static void SaveData(PhysicsObject2D player) {
        try (FileOutputStream fileOut = new FileOutputStream(PATH);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(player);
            System.out.println("Player data saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static PhysicsObject2D LoadData() {
        try (FileInputStream fileIn = new FileInputStream(PATH);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            PhysicsObject2D loadedPlayer = (PhysicsObject2D) in.readObject();

            loadedPlayer.setImage(InputManager.PlayerImg);

            System.out.println("Player position loaded!");
            return loadedPlayer;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
