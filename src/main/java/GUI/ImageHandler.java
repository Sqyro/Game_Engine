package GUI;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class ImageHandler {
    
    public static int loadTexture(String Path, int ImgWidth, int ImgHeight) throws Exception {
        BufferedImage img = ImageIO.read(new File(Path)); // Image aus den Files lesen
        
        //Image Scalen auf die gewünschte Größe
        BufferedImage ScaledImg = new BufferedImage(ImgWidth, ImgHeight, BufferedImage.TYPE_INT_ARGB);
        ScaledImg.getGraphics().drawImage(img, 0, 0, ImgWidth, ImgHeight, null);

        //Alle Pixel aus dem Bild lesen und in RGB (+ Alpha Wert) Integer umwandeln
        int[] PixelRaw = new int[ImgWidth * ImgHeight];
        ScaledImg.getRGB(0, 0, ImgWidth, ImgHeight, PixelRaw, 0, ImgWidth);

        ByteBuffer Pixels = BufferUtils.createByteBuffer(ImgWidth * ImgHeight * 4); //Pixel Byte Buffer erstellen
        for (int y = 0; y < ImgHeight; y++) { // für y (Höhe)
            for (int x = 0; x < ImgWidth; x++) { //für x (Breite)
                int Pixel = PixelRaw[y * ImgWidth + x]; //Nimmt den richtigen Pixel
                //Liest für die Bytes des Pixels
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
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP); // Wenn das Bild koordinaten außerhalb x hat
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP); // Wenn das Bild koordinaten außerhalb y hat
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ImgWidth, ImgHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, Pixels);
        glBindTexture(GL_TEXTURE_2D, 0);

        // Gibt die ID der Textur zurück´
        return TextureID;
    }

    public static void draw(int TextureID, int PosX, int PosY, int width, int height) {
        //Textur zeichnen Einleiten
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, TextureID); // was wollen wir zeichnen (mit der ID von load)

        glBegin(GL_QUADS); // Viereck machen
        //Textur von oben auf die Coordinaten mappen
        glTexCoord2f(0, 0);
        glVertex2f(PosX, PosY);
        glTexCoord2f(1, 0);
        glVertex2f(PosX + width, PosY);
        glTexCoord2f(1, 1);
        glVertex2f(PosX + width, PosY + height);
        glTexCoord2f(0, 1);
        glVertex2f(PosX, PosY + height);
        glEnd();

        glBindTexture(GL_TEXTURE_2D, 0); // Textur unbinden, damit die später nicht nervt
    }
}