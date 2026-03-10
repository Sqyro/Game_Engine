package GUI;

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
    }
}
