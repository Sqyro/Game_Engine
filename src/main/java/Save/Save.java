package Save;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Player.Player;
import Physics2D.PhysicsObject2D;

public class Save { //Klasse um Daten zu speichern
    
    private static final String PATH = "gamesession/Playerdata/Player.ser"; //Pfad wo alles gespeichert wird
    
    public static void SaveData(PhysicsObject2D player) {
        try (FileOutputStream FileOutStream = new FileOutputStream(PATH); // Öffnet ne File in die man was schreiben kann an dem Pfad, erstellt eine wenn keine da ist
             ObjectOutputStream ObjectOutStream = new ObjectOutputStream(FileOutStream)) { //Stream der Object Daten in Bytes schreiben kann
            ObjectOutStream.writeObject(player); //Wandelt alle serializable Spieler Daten in Bytes um und schreibt sie in die Datei
            System.out.println("Player data saved!"); //Nachricht für den Debug
        } catch (IOException e) { //Stacktrace falls es fehl schlägt
            e.printStackTrace();
        }
    }
    
    public static PhysicsObject2D LoadData() {
        try (FileInputStream fileIn = new FileInputStream(PATH); //Öffnet die Datei um Bytes davon zu lesen
             ObjectInputStream in = new ObjectInputStream(fileIn)) { //Stream der Bytes in Objekte zurück verwandeln kann
            PhysicsObject2D loadedPlayer = (PhysicsObject2D) in.readObject(); //Liest die Bytes und wandelt sie in ein PhysicsObjekt2D um

            loadedPlayer.setTextureID(Player.Player.getTextureID()); // Setzt die Textur zurück

            System.out.println("Player position loaded!"); //nachricht für den Debug
            return loadedPlayer; //Gibt das gelesene Objekt zurück

        } catch (IOException | ClassNotFoundException e) { //Wenn er die Klasse nicht findet, dann gib nen Stacktrace aus und gibt nichts zurück
            e.printStackTrace();
            return null;
        }
    }
}
