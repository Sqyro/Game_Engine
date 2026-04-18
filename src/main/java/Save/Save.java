package Save;

import java.io.*;

import Item.Item;
import Item.Items;
import Physics2D.CircleCollider;
import Physics2D.LivingObject;
import Player.Player;
import Rendering.ImageManager;
import Spell.Spell;
import Spell.Spells;

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

    public static void SavePlayerData(Player player, String PATH, int SaveID) {
        PlayerSaveData PlayerData = createSaveData(player);

        String FullPath = "gamesession/Save" + SaveID + PATH;
        File SaveFile = new File(FullPath);
        File ParentDirectory = SaveFile.getParentFile();
        if (!ParentDirectory.exists()) {
            ParentDirectory.mkdirs();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SaveFile))) {
            out.writeObject(PlayerData);
            System.out.println("Player data saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Player LoadPlayerData(int SaveID) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gamesession/Save" + SaveID + "/Playerdata/Player.ser"))) {
            PlayerSaveData PlayerData = (PlayerSaveData) in.readObject();

            Player player = new Player(
                    PlayerData.PosX,
                    PlayerData.PosY,
                    Player.PlayerSizeX,
                    Player.PlayerSizeY,
                    ImageManager.PLAYER,
                    0,
                    new float[]{0,0},
                    new CircleCollider(32, 0, 15),
                    Player.PLAYER_MAX_HP
            );

            player.HP = PlayerData.HP;

            for (int i = 0; i < PlayerData.Items.size(); i++) {
                String name = PlayerData.Items.get(i);
                if (name != null) {
                    player.inventory.setItem(i, Items.ITEMS.getRegistry(name));
                }
            }

            for (int i = 0; i < PlayerData.Spells.size(); i++) {
                String name = PlayerData.Spells.get(i);
                if (name != null) {
                    player.inventory.setSpell(i, Spells.SPELLS.getRegistry(name));
                }
            }

            return player;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PlayerSaveData createSaveData(Player player) {
        PlayerSaveData PlayerData = new PlayerSaveData();

        PlayerData.PosX = player.PosX;
        PlayerData.PosY = player.PosY;
        PlayerData.HP = player.HP;

        for (int i = 0; i < player.inventory.getInventorySize(); i++) {
            Item item = player.inventory.getItem(i);
            PlayerData.Items.add(item != null ? item.getRegistryName() : null);
        }

        for (int i = 0; i < player.inventory.getSpellSize(); i++) {
            Spell spell = player.inventory.getSpell(i);
            PlayerData.Spells.add(spell != null ? spell.getRegistryName() : null);
        }

        return PlayerData;
    }
}