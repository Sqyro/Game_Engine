package Save;

import java.io.*;

import Item.Item;
import Item.Items;
import Physics2D.LivingObject;
import Player.Player;

public class Save { //Klasse um Daten zu speichern
    public static void SaveObjectData(LivingObject livingObject, String PATH, int SaveID) {
        String FullPath = "gamesession/Save" + SaveID + PATH;
        File SaveFile = new File(FullPath);
        File ParentDirectory = SaveFile.getParentFile();
        if (!ParentDirectory.exists()) {
            ParentDirectory.mkdirs();
        }
        try (FileOutputStream FileOutStream = new FileOutputStream(SaveFile); // Öffnet ne File in die man was schreiben kann an dem Pfad, erstellt eine wenn keine da ist
            ObjectOutputStream ObjectOutStream = new ObjectOutputStream(FileOutStream)) { //Stream der Object Daten in Bytes schreiben kann
            ObjectOutStream.writeObject(livingObject); //Wandelt alle serializable object Daten in Bytes um und schreibt sie in die Datei
            System.out.println("Game data saved!"); //Nachricht für den Debug
        } catch (IOException e) { //Stacktrace falls es fehl schlägt
            e.printStackTrace();
        }
    }
    
    public static LivingObject LoadPlayerData(int SaveID) {
        try (FileInputStream fileIn = new FileInputStream("gamesession/Save" + SaveID + "/Playerdata/Player.ser"); //Öffnet die Datei um Bytes davon zu lesen
            ObjectInputStream in = new ObjectInputStream(fileIn)) { //Stream der Bytes in Objekte zurück verwandeln kann
            LivingObject loadedObject = (LivingObject) in.readObject(); //Liest die Bytes und wandelt sie in ein LivingObject um

            loadedObject.setTextureID(Player.Player.TextureID); // Setzt die Textur zurück

            Player Player = (Player) loadedObject;

            //Die Items haben vorher immer ihre Textur verloren, wenn man geladen hat
            for (int i = 0; i < Player.inventory.getInventorySize(); i++) { // Geht das gesamte Inventar vom Spieler durch
                Item ThisItem = Player.inventory.getItem(i);

                if (ThisItem != null) { //Wenn an diesem Slot ein Item ist
                    //Die Registry holen von diesem Item
                    String ThisItemRegistryName = ThisItem.getRegistryName();
                    Item ThisItemsRegistry = Items.ITEMS.getRegistry(ThisItemRegistryName);

                    //TexturID, sowie Breite und Höhe richtig setzen
                    ThisItem.setTextureID(ThisItemsRegistry.TextureID);
                    ThisItem.setTextureWidth(ThisItemsRegistry.TextureWidth);
                    ThisItem.setTextureHeight(ThisItemsRegistry.TextureHeight);
                }
            }

            System.out.println("Player data loaded!"); //Nachricht für den Debug
            return loadedObject; //Gibt das gelesene Objekt zurück

        } catch (IOException | ClassNotFoundException e) { //Wenn er die Klasse nicht findet, dann gib nen Stacktrace aus und gibt nichts zurück
            e.printStackTrace();
            return null;
        }
    }
}