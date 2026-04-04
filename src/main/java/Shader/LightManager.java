package Shader;

import Rendering.Camera;

import java.util.ArrayList;
import java.util.List;

public class LightManager {
    
    //Variabeln deklarieren
    
    //"Globales Licht", normale Ausleuchtung der ganzen Szene
    public static float GlobalLight = 0.5f;
    
    //Maximale Anzahl an Lichtern die da sein dürfen
    public static final int MAX_LIGHTS = 1; // OpenGL mag keine Dynamischen Arrays (Es muss ja beim compilen wissen, wie viel Platz da sein soll)
    //Liste von allen Point Lights die an den Shader geschickt werden
    public static final List<PointLight> PointLights = new ArrayList<>();
    //Liste wo alle Point Lights drin gespeichert werden
    public static final List<PointLight> AllPointLights = new ArrayList<>();
    
    public LightManager() { //Constructor for the Love of the Game
    
    }
    
    //Die Variablen sind zwar statisch, können desswegen also eigentlich Selber von außen geschrieben werden, ich mag aber Methoden für sowas lieber
    public static float getGlobalLight() {
        return GlobalLight;
    }
    
    public static void setGlobalLight(float newGlobalLight) {
        GlobalLight = newGlobalLight;
    }
    
    public static void addLight(PointLight PointLight) {
        AllPointLights.add(PointLight);
    }

    public static void removeLight(PointLight PointLight) {
        AllPointLights.remove(PointLight);
        PointLights.remove(PointLight);
    }
    
    public static void updateVisibleLights(int ScreenWidth, int ScreenHeight) {
        //Alte Liste leer machen, damit nur die die gerade sichtbaren Lichter da sind und nicht auch noch die, die mal sichtbar waren
        PointLights.clear();

        //Variablen, für die Randkoordinaten vom Screen als extra Variable, damit man es besser lesen kann und es nicht jedes mal im Loop neu berechnet werden muss
        int ScreenLeft   = (int) -Camera.PosX;
        int ScreenRight  = ScreenLeft + ScreenWidth;
        int ScreenTop    = (int) -Camera.PosY;
        int ScreenBottom = ScreenTop + ScreenHeight;

        for (PointLight PointLight : AllPointLights) { //Für alle PointLights in All Point Lights

            //Positionen vom Licht holen, extra Variablen, damit man es besser lesen kann
            float PointLightX = PointLight.PosX;
            float PointLightY = PointLight.PosY;

            //Ähnlich wie das culling bei den Enemies. Wenn das Licht nichtmehr auf dem Screen ist, dann wird es nicht in die Aktiven Lichter gadded.
            //Offset von der Range von diesem Light, weil die Position ja beim Mittelpunkt ist und man sonst sieht wie das Licht geht, weil es ja noch bei Range zu sehen ist
            if (PointLightX + PointLight.Range < ScreenLeft || PointLightX - PointLight.Range > ScreenRight || PointLightY + PointLight.Range < ScreenTop  || PointLightY - PointLight.Range > ScreenBottom) {
               continue; //Wenn nicht auf dem Screen, dann mach mit dem nächsten Licht weiter
            }
            
            //Alle Lichter die noch übrig und daher auf dem Screen sind in die Point Lights und damit active Lights hinzufügen
            if (PointLights.size() < MAX_LIGHTS) {
                PointLights.add(PointLight);
            }
        }
    }
}
