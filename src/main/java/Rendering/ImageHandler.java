package Rendering;

import Shader.Shader;
import Shader.LightManager;
import Shader.PointLight;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ImageHandler {
    
    //Varibalen deklarieren
    private int VAO;
    private int VBO;
    
    private Map<Integer, List<RenderCommand>> renderQueue = new HashMap<>(); //Liste für den Draw que
    
    public ImageHandler() {

        float[] QuadratVertexData = { //Ein Array der die Vertex Data (Position) von Quadraten hält. Hält die Variablen für die Position und die Textur Koordinaten
           //Position //Texture
            0f, 0f,    0f, 0f, //Geht von 0, 0 zu 1, 1 in Lokalen Koordinaten
            1f, 0f,    1f, 0f,
            1f, 1f,    1f, 1f,
            0f, 1f,    0f, 1f
        };

        //Erstellt einen Vertex Array Object und speichert seine ID in der Variable. Das VAO speichert die Vertex Data für OpenGL
        VAO = glGenVertexArrays();
        //Erstellt einen Vertex Buffer Object und speichert seine ID in der Variable. Das VBO ist ein Memory Buffer, der tatsächlich die Werte hält
        VBO = glGenBuffers();

        //Aktiviert den VAO, also alle VAO calls werden hierdrin gespeichert
        glBindVertexArray(VAO);

        //Binded den VBO an den GL_ARRAY_BUFFER, heißt alle VBO Operations Zählen jetzt für diesen Buffer
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        
        //Läd die Vertext Data in den VBO für die GPU
        glBufferData(GL_ARRAY_BUFFER, QuadratVertexData, GL_STATIC_DRAW); // GL_STATIC_DRAW sagt, dass die data nicht oft geändert wird und die GPU entsprechend laufen soll

        //GL's Interpretation der Vertex Data (Position oben)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0); //(Position im Shader, Anzahl der floats pro Vertex(2 -> x,y), DatenTyp, soll Normalisiert werden?, Länge von jedem Vertex, Offset im Buffer (0 -> startet beim ersten))
        glEnableVertexAttribArray(0); //Schaltet Shader Attribut 0 (Das eben gebaute) an, damit die GPU es tatsächlich benutzt

        //Ähnlich wie eben, nur für Textur Koordinaten
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES); //Wie eben. 1 weil zweite Position im Shader
        glEnableVertexAttribArray(1); //Schaltet es wieder an
    }
    
    public static int loadTexture(String Path) throws Exception {
        BufferedImage img = ImageIO.read(new File(Path)); // Image aus den Files lesen
        
        //Alle Pixel aus dem Bild lesen und in RGB (+ Alpha Wert) Integer umwandeln
        int[] PixelRaw = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), PixelRaw, 0, img.getWidth());

        ByteBuffer Pixels = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4); //Pixel Byte Buffer erstellen
        for (int y = 0; y < img.getHeight(); y++) { // für y (Höhe)
            for (int x = 0; x < img.getWidth(); x++) { //für x (Breite)
                int Pixel = PixelRaw[y * img.getWidth() + x]; //Nimmt den richtigen Pixel
                //Liest für den Pixels und speichert seine Daten in dem Byte Buffer
                Pixels.put((byte) ((Pixel >> 16) & 0xFF)); // R(ed)
                Pixels.put((byte) ((Pixel >> 8) & 0xFF));  // G(reen)
                Pixels.put((byte) (Pixel & 0xFF));         // B(lue)
                Pixels.put((byte) ((Pixel >> 24) & 0xFF)); // Alpha Wert (Transparenz)
            }
        }
        Pixels.flip(); // Damit OpenGL den richtig lesen kann

        // Jede Textur bekommt eine ID mit der man sie dann lesen kann
        int TextureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, TextureID);
        
        //Kein Smoothing, Clamp damit die Textur out of Bounds sich nicht wiederholt
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //Wenn zu klein
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //Wenn zu groß
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE); // Wenn das Bild koordinaten außerhalb x hat
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE); // Wenn das Bild koordinaten außerhalb y hat
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, Pixels);
        glBindTexture(GL_TEXTURE_2D, 0);

        // Gibt die ID der Textur zurück´
        return TextureID;
    }

    public void drawFull(int TextureID, float TextureX, float TextureY, float TextureWidth, float TextureHeight, float Red, float Green, float Blue) { //Wenn man die Textur komplett zeichnen will, setzt einfach Position auf 0 und größe auf 1
        draw(TextureID, TextureX, TextureY, TextureWidth, TextureHeight, 0f, 0f, 1f, 1f, Red, Green, Blue);
    }
    
    public void drawRectangle(int TextureID, float TextureX, float TextureY, float TextureWidth, float TextureHeight, float Red, float Green, float Blue) { //Methode um Rechtecke zu zeichnen, nimmt ne 1x1 Weiße Textur, eine Position, Größe und Farbwerte und passt die Textur entsprechend an
        renderQueue.computeIfAbsent(TextureID, k -> new ArrayList<>()) //wenn es noch keine Liste für diese Textur gibt mach eine, heißt wenn wir die gleiche textur 1000 mal haben wird sie nicht 1000 mal neu gemacht
                   .add(new RenderCommand(TextureX, TextureY, TextureWidth, TextureHeight, 0f, 0f, 1f, 1f, Red, Green, Blue)); //Render Command mit den Werten machen, onTexture Größe und Position ist 0, 0 und 1, 1, weil die Textur 1x1 ist
    }
    
    public void draw(int TextureID, float TextureX, float TextureY, float TextureWidth, float TextureHeight, float onTextureX, float onTextureY, float onTextureWidth, float onTextureHeight, float Red, float Green, float Blue) { //fügt Texturen in den draw que hinzu für da Frame
        renderQueue.computeIfAbsent(TextureID, k -> new ArrayList<>()) //wenn es noch keine Liste für diese Textur gibt mach eine, heißt wenn wir die gleiche textur 1000 mal haben wird sie nicht 1000 mal neu gemacht
                   .add(new RenderCommand(TextureX, TextureY, TextureWidth, TextureHeight, onTextureX, onTextureY, onTextureWidth, onTextureHeight, Red, Green, Blue)); //Render Command für diese Textur in die Liste hinzufügen
    }
    
    public void flush(Shader Shader, int ScreenWidth, int ScreenHeight) { //Führt jetzt für jede Textur das zeichnen aus
        //Aktiviert den Shader
        Shader.bind();
        
        //Liste an aktiven Lichtern updaten
        LightManager.updateVisibleLights(ScreenWidth, ScreenHeight);
        
        //Gibt die Werte die der Shader zum Rechnen braucht an ihn weiter
        Shader.setUniform1f("globalLight", LightManager.getGlobalLight());
        Shader.setUniform1i("activeLights", LightManager.PointLights.size());
        glUniform2f(Shader.ScreenSizeLocation, ScreenWidth, ScreenHeight);
        
        for (int i = 0; i < LightManager.PointLights.size(); i++) {
            PointLight pointlight = LightManager.PointLights.get(i);
            Shader.setUniform2f("lightPositions[" + i + "]", pointlight.PosX + Camera.PosX, pointlight.PosY + Camera.PosY);
            Shader.setUniform3f("lightColors[" + i + "]", pointlight.Red, pointlight.Green, pointlight.Blue);
            Shader.setUniform1f("lightIntensities[" + i + "]", pointlight.Range);
        }
        
        //Aktiviert VAO wieder, also alles hiernach verwendet das Quadrat Shape
        glBindVertexArray(VAO);

        for (int TextureID : renderQueue.keySet()) { //für jede Textur in diesem Frame
            glBindTexture(GL_TEXTURE_2D, TextureID); // Textur in OpenGL binden

            for (RenderCommand renderCommand : renderQueue.get(TextureID)) { // Für jeden Render Command
                
                //Position der Textur mit Kamera Offset
                float fixedX = Math.round(renderCommand.PosX + Camera.PosX);
                float fixedY = Math.round(renderCommand.PosY + Camera.PosY);

                //Positionen von Frames der Spritesheets auf der Textur
                glUniform2f(Shader.onTextureOffsetLocation, renderCommand.onTextureX, renderCommand.onTextureY);
                glUniform2f(Shader.onTextureScaleLocation, renderCommand.onTextureWidth, renderCommand.onTextureHeight);
                
                //Variablen für den Vertex Shader zum rechnen Setzen
                glUniform2f(Shader.OffsetLocation, fixedX, fixedY);
                glUniform2f(Shader.ScaleLocation, renderCommand.TextureWidth, renderCommand.TextureHeight);
                glUniform4f(Shader.ColorLocation, renderCommand.Red, renderCommand.Green, renderCommand.Blue, 1f);
                
                glDrawArrays(GL_TRIANGLE_FAN, 0, 4); // Quadrat mit der momentanen Textur zeichnen
            }
        }

        renderQueue.clear(); //Render Que leeren, damit das nächste Frame, oder nächste Objekt nicht weiter hier reingeschrieben wird
    }
    
    // Helfer Klasse, private, hat sich nicht gelohnt dafür ne eigene Klasse zu erstellen, desswegen ist die hier.
    private static class RenderCommand { //Hält einfach nur Werte für jeden render Command
        float PosX;
        float PosY;
        float TextureWidth;
        float TextureHeight;
        
        float onTextureX;
        float onTextureY;
        float onTextureWidth;
        float onTextureHeight;
        
        float Red;
        float Green;
        float Blue;
        
        public RenderCommand(float PosX, float PosY, float TextureWidth, float TextureHeight, float onTextureX, float onTextureY, float onTextureWidth, float onTextureHeight, float Red, float Green, float Blue) {
            this.PosX = PosX;
            this.PosY = PosY;
            this.TextureWidth = TextureWidth;
            this.TextureHeight = TextureHeight;
            
            this.onTextureX = onTextureX;
            this.onTextureY = onTextureY;
            this.onTextureWidth = onTextureWidth;
            this.onTextureHeight = onTextureHeight;
            
            this.Red = Red;
            this.Green = Green;
            this.Blue = Blue;
        }
    }
}