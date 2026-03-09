package Shader;

import java.util.ArrayList;
import java.util.List;

public class LightManager {
    
    //Variabeln deklarieren
    
    //"Globales Licht", normale Ausleuchtung der ganzen Szene
    public static float GlobalLight = 0.5f;
    
    //Maximale Anzahl an Lichtern die da sein dürfen
    public static final int MAX_LIGHTS = 1; // OpenGL mag keine Dynamischen Arrays (Es muss ja beim compilen wissen, wie viel Platz da sein soll)
    //Liste wo alle Point Lights drin gespeichert werden
    public static final List<PointLight> PointLights = new ArrayList<>();
    
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
        if (PointLights.size() < MAX_LIGHTS) {
            PointLights.add(PointLight);
        }
    }

    public static void removeLight(PointLight PointLight) {
        PointLights.remove(PointLight);
    }
}
