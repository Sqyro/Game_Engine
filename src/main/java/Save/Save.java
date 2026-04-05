package Save;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import Player.Player;
import Physics2D.PhysicsObject2D;

public class Save { //Klasse um Daten zu speichern
    public static void SaveObjectData(PhysicsObject2D object2D, String PATH, int SaveID) {
        try (FileOutputStream FileOutStream = new FileOutputStream("gamesession/Save" + SaveID + PATH); // Öffnet ne File in die man was schreiben kann an dem Pfad, erstellt eine wenn keine da ist
            ObjectOutputStream ObjectOutStream = new ObjectOutputStream(FileOutStream)) { //Stream der Object Daten in Bytes schreiben kann
            ObjectOutStream.writeObject(object2D); //Wandelt alle serializable object Daten in Bytes um und schreibt sie in die Datei
            System.out.println("Game data saved!"); //Nachricht für den Debug
        } catch (IOException e) { //Stacktrace falls es fehl schlägt
            e.printStackTrace();
        }
    }
    
    public static PhysicsObject2D LoadObjectData(int SaveID) {
        try (FileInputStream fileIn = new FileInputStream("gamesession/Save" + SaveID + "/Playerdata/Player.ser"); //Öffnet die Datei um Bytes davon zu lesen
            ObjectInputStream in = new ObjectInputStream(fileIn)) { //Stream der Bytes in Objekte zurück verwandeln kann
            PhysicsObject2D loadedObject = (PhysicsObject2D) in.readObject(); //Liest die Bytes und wandelt sie in ein PhysicsObjekt2D um

            loadedObject.setTextureID(Player.Player.TextureID); // Setzt die Textur zurück

            System.out.println("Player data loaded!"); //Nachricht für den Debug
            return loadedObject; //Gibt das gelesene Objekt zurück

        } catch (IOException | ClassNotFoundException e) { //Wenn er die Klasse nicht findet, dann gib nen Stacktrace aus und gibt nichts zurück
            e.printStackTrace();
            return null;
        }
    }
}
