package GUI;

import Rendering.ImageHandler;
import Rendering.Camera;
import Scenes.GameScene;

public class GUIManager {
    public static GUIScreen currentScreen = null;

    public static boolean isScreenOpen() {
        if(currentScreen == null) { //Schau ob ein kein Screen offen/null ist
            return false; //Wenn keiner offen ist, dann sag nein
        } else {
            return true; //Wenn einer offen ist, dann sag ja
        }
    }

    public static void openScreen(GUIScreen guiScreen) {
        currentScreen = guiScreen; //Setzt den Momentan offenen Screen auf den der geöffnet werden soll
        guiScreen.setOpenScreen(true); //Setzt isOpen im Screen auf true mit der Hilfs Methode
    }

    public static void closeScreen() {
        currentScreen.setOpenScreen(false); //Setzt isOpen im Screen auf false mit der Hilfs Methode
        currentScreen = null; //Setzt den momentan offenen Screen auf leer
        GameScene.GameRunning = true;
    }
    
    //Methode um Text in den draw Que hinzu zu fügen. Braucht den Text für alle Variablen und den renderer (Image Handler) um es zu enquen
    public static void renderText(GUIText guiText, ImageHandler renderer) {
        String StringText = guiText.getText(); //Holt sich den Text als String

        //Farbe Holen
        float Red = guiText.TextColor.getRed() / 255f;
        float Green = guiText.TextColor.getGreen() / 255f;
        float Blue = guiText.TextColor.getBlue() / 255f;
        
        for(int i = 0; i < StringText.length(); i++) { //Für jeden Character im String
            char CharacterAt = StringText.charAt(i); //Nimm dir den Character

            //Holt sich die Position für jeden Buchstaben, Y ist ja immer gleich wie Text (außer man hat Zeilenumbruch, kommt noch) und X hängt von der Stelle im Text ab, also i.
            float LetterX = guiText.getPosX() + (i * guiText.getCharacterSpacing());
            float LetterY = guiText.getPosY();

            //Enqued den Buchstaben
            renderer.draw(guiText.getFontTextureID(), LetterX - Camera.PosX, LetterY - Camera.PosY, guiText.getCharacterSize(), guiText.getCharacterSize(), GUIBitmapFont.getTextureX(CharacterAt), GUIBitmapFont.getTextureY(CharacterAt), GUIBitmapFont.getWidth(), GUIBitmapFont.getHeight(), Red, Green, Blue);
        }
    }
}
