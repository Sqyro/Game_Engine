package Sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class SoundHandler {
    
    
    public static synchronized void playSound(final String SoundName, int TimeInSeconds) { //Synchronisiert, damit nur ein Thread aufgemacht wird für alle Sounds (Ich bin Albert Einstein)
        new Thread(new Runnable() { //Wir machen nen neuen Thread auf, damit der Sound nicht den main Game Loop anhält
            @Override
            public void run() {
                try {
                    Clip Clip = AudioSystem.getClip(); //Neues AudioClip Objekt
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile()); //Läd die File mit nem Audio Input Stream
                    Clip.open(inputStream); //Clip sneaked sich den Inout Stream
                    Clip.start(); //Startet die Audio Datei
                    TimeUnit.SECONDS.sleep(TimeInSeconds); //Wartet die Dauer des Sounds ab
                    Clip.stop(); //Stoppt den Sound nach der Dauer
                    Clip.close(); //Schließt den Clip um das Memory, welches der Sound verwendet, wieder frei zu geben
                } catch (Exception e) { //Schmeißt ne Exception mit ner Error Message wenn nicht klappt
                    System.err.println(e.getMessage());
                }
            }
        }).start(); //Thread starten
    } 
}