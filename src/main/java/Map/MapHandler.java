package Map;

import Physics2D.BoxCollider;
import Rendering.ImageHandler;
import Rendering.Camera;
import Shader.LightEmitters.PointLight;
import Shader.Shader;
import Rendering.ImageManager;
import com.google.gson.Gson;
import Rendering.Camera;
import java.io.File;
import java.io.FileReader;

public class MapHandler {

    //Variabel Deklaration
    public static final float TileSize = 56;
    public static final float ObjectSize = 112;


    private int[][] tiles;
    private String[][] mapObjects;
    private PointLight[] lights;
    private BoxCollider[] hitboxen;

    //Leerer Contructor for the love of the Game
    public MapHandler() {
        MapObjects.RegisterMapObjects();
        importMap();
    }

    public void importMap() {
        try {
            Gson gson = new Gson();

            File folder = new File("src/main/resources/data");

            tiles = gson.fromJson(new FileReader(new File(folder, "tiles.json")), int[][].class);
            mapObjects = gson.fromJson(new FileReader(new File(folder, "objects.json")), String[][].class);
            PointLight[] lights = gson.fromJson(new FileReader(new File(folder, "lights.json")), PointLight[].class); //gson erkennt keine liste deswegen speichern wir sie erstmal in ein normales array
            BoxCollider[] hitboxen = gson.fromJson(new FileReader(new File(folder, "hitboxen.json")), BoxCollider[].class);


            System.out.println("Map geladen");

        } catch (Exception e) {
            System.err.println("on god wehe das passiert");
            e.printStackTrace();
        }
    }

    public void drawTiles(Shader shader, ImageHandler renderer, int ScreenWidth, int ScreenHeight) { //Methode um die Map zu zeichnen

        int startTileX = Math.max(0, (int) -Camera.PosX / (int) TileSize); //die ersten Tiles die gerendert werden. Start für den Loop
        int startTileY = Math.max(0, (int) -Camera.PosY / (int) TileSize);

        //Die letzten Tiles die gerendert werden, eine Seite. Ende für den Loop
        int endTileX = Math.min(tiles[0].length, startTileX + (ScreenWidth / (int) TileSize) + 2); //Bildschirm wird in tiles unterteilt durch Width/TileSize. Plus StartTile, sonst würde man ja nur die ersten paar tiles in der Liste dsehen können. +2 damit man es nicht merkt
        int endTileY = Math.min(tiles.length, startTileY + (ScreenHeight / (int) TileSize) + 2);

        for (int j = startTileY; j < endTileY; j++) { //geht auf dieser Achse von oben nach unten die Tiles durch
            for (int i = startTileX; i < endTileX; i++) { //Geht von Links Nach Rechts die Tiles durch
                //Variablen, damit das besser lesbar ist und ich nicht das gleiche tausendmal schreiben muss
                float PosX = i * TileSize;
                float PosY = j * TileSize;

                if (tiles[j][i] < ImageManager.TileTextures.size()) { // Damit falls die Textur nicht existiert das Spiel nicht abstürzt, sondern die Textur einfach nicht angezeigt wird
                    // Die Tile TextureIDs werden extra vorher in nen Array geschrieben, damit sie clean gelesen werden können
                    int id = tiles[j][i];

                    if (id < ImageManager.TileTextures.size()) {
                        int tileTexture = ImageManager.TileTextures.get(id);
                        float[] data = ImageManager.TileData.get(id);

                        renderer.draw(tileTexture, PosX, PosY, TileSize, TileSize, data[0], data[1], data[2], data[3], 1, 1, 1, 1);
                    }
                }
            }
        }
        // Flushed die Texturen für die Map.
        renderer.flush(shader, ScreenWidth, ScreenHeight);
    }

    public void drawObjects(Shader shader, ImageHandler renderer, int ScreenWidth, int ScreenHeight) { //Methode um die Map zu zeichnen

        int startTileX = Math.max(0, (int) -Camera.PosX / (int) TileSize); //die ersten Tiles die gerendert werden. Start für den Loop
        int startTileY = Math.max(0, (int) -Camera.PosY / (int) TileSize);

        //Die letzten Tiles die gerendert werden, eine Seite. Ende für den Loop
        int endTileX = Math.min(tiles[0].length, startTileX + (ScreenWidth / (int) TileSize) + 2); //Bildschirm wird in tiles unterteilt durch Width/TileSize. Plus StartTile, sonst würde man ja nur die ersten paar tiles in der Liste dsehen können. +2 damit man es nicht merkt
        int endTileY = Math.min(tiles.length, startTileY + (ScreenHeight / (int) TileSize) + 2);

        for (int j = startTileY; j < endTileY; j++) { //geht auf dieser Achse von oben nach unten die Tiles durch
            for (int i = startTileX; i < endTileX; i++) { //Geht von Links Nach Rechts die Tiles durch
                //Variablen, damit das besser lesbar ist und ich nicht das gleiche tausendmal schreiben muss
                float PosX = i * TileSize;
                float PosY = j * TileSize;

                if (mapObjects != null && i < mapObjects.length && i < mapObjects[j].length) {
                    String name = mapObjects[j][i];

                    if (name != null && !name.isEmpty() && !name.equalsIgnoreCase("null")) {
                        MapObject obj = MapObjects.MAP_OBJECTS.getRegistry(name);

                        if (obj != null) {
                            // Direkt in den Renderer schieben
                            renderer.drawFull(obj.TextureID, PosX, PosY, ObjectSize, ObjectSize,  1, 1, 1, 1);
                        }
                    }
                }
            }

        }
        // Flushed die Texturen für die Map.
        renderer.flush(shader, ScreenWidth, ScreenHeight);
    }
}