package Sounds;

import Player.Player;
import Rendering.Frame;

import javax.sound.sampled.*;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class SoundHandler {
    public static List<Sound> AllSounds = new ArrayList<>();
    
    public static void playSound(String SoundName, float TimeInSeconds, float Volume) {
        try {
            Clip Clip = AudioSystem.getClip(); //Neues AudioClip Objekt
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile()); //Läd die File mit nem Audio Input Stream
            Clip.open(inputStream); //Clip sneaked sich den Inout Stream
            //Lautstärke regeln
            FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN); //Ein Lautstärkeregler für diesen Sound Definieren
            VolumeControl.setValue(Volume); //Den Wert von dem Lautstärkeregler setzen auf die Gegebene Laustärke, damit wird also die Laustärke vom Sound angepasst, weil der Lautstärke regler dem Sound schon zugewiesen wurde
                    
            Clip.start(); //Startet die Audio Datei
            
            //Wir definieren einen neuen Sound ohne Position
            Sound sound = new Sound(Clip, VolumeControl, TimeInSeconds, System.currentTimeMillis(), Volume, false, 0, 0, 0);
            
            //Wir fügen den neuen Sound in die Liste mit allen Sounds hinzu
            AllSounds.add(sound);
            
        } catch (Exception e) { //Schmeißt ne Exception mit ner Error Message wenn nicht klappt
            System.err.println(e.getMessage());
        }
    }
    
    public static void playSoundAtPos(final String SoundName, float TimeInSeconds, float Volume, float PosX, float PosY, float Falloff) {
        try {
            Clip Clip = AudioSystem.getClip(); //Neues AudioClip Objekt
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile()); //Läd die File mit nem Audio Input Stream
            Clip.open(inputStream); //Clip sneaked sich den Inout Stream
            //Lautstärke regeln
            FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN); //Ein Lautstärkeregler für diesen Sound Definieren              
            VolumeControl.setValue(Volume); //Den Wert von dem Lautstärkeregler setzen auf die Gegebene Laustärke, damit wird also die Laustärke vom Sound angepasst, weil der Lautstärke regler dem Sound schon zugewiesen wurde
            
            Clip.start(); //Startet die Audio Datei
            
            //Wir definieren einen neuen Sound mit Position
            Sound sound = new Sound(Clip, VolumeControl, TimeInSeconds, System.currentTimeMillis(), Volume, true, PosX, PosY, Falloff);
            
            //Wir fügen den neuen Sound in die Liste mit allen Sounds hinzu
            AllSounds.add(sound);
            
        } catch (Exception e) { //Schmeißt ne Exception mit ner Error Message wenn nicht klappt
            System.err.println(e.getMessage());
        }
    }
    
    public static void updateSounds(float deltaTime) { //Methode um alle Sounds zu updaten
        float Now = Frame.Gametime; // Jetzt holen

        for (int i = 0; i < AllSounds.size(); i++) { //Einen Loop für alle Sounds, kein Sound CurrentSound : AllSounds, weil ich später den momentanen Sound removed will und keine Ahnung hab wie das so geht
            Sound CurrentSound = AllSounds.get(i); //Den Sound an der jetzigen Position holen

            CurrentSound.RemainingTime -= deltaTime; //Übrige Zeit wird verniedrigt um die Zeit von diesem Frame
            CurrentSound.LastTime = Now; //Vergangene Zeit ist die jetzige Zeit

            if (CurrentSound.RemainingTime <= 0) { //Wenn der Clip keine Laufzeit mehr hat
                CurrentSound.Clip.stop(); //Anhalten
                CurrentSound.Clip.close(); //Stoppen, damit im System wieder Memory freigegeben wird
                AllSounds.remove(i); //Aus der Sound Liste entfernen
                i--; //Sound Liste Iterator um eins verringern, weil ja jetzt dieser Sound fehlt
            }

            if (CurrentSound.isPositional) { //Wenn dieser Sound ein Positions basierter Sound ist
                Player player = Player.Player; //Den Spieler Sneaken, um die Position zu bekommen
                
                //Abstand zwischen Spieler und Sound für X und Y separat holen
                float DeltaPosX = CurrentSound.PosX - player.getPosX();
                float DeltaPosY = CurrentSound.PosY - player.getPosY();

                //Satz des Pythagoras, um den Abstand in eine Länge zu bekommen (Hypothenuse in nem Dreieck mit den beiden Längen)
                float Distance = (float)Math.sqrt(DeltaPosX * DeltaPosX + DeltaPosY * DeltaPosY);

                //Lautstärke entsprechend anpassen
                CurrentSound.VolumeControl.setValue(Math.max(-80f, CurrentSound.Volume - (Distance / CurrentSound.Falloff)));
            }
        }
    }
    
    //Methoden um alle Sounds zu pausieren/zu resumen
    public static void pauseAll() {
        for (Sound CurrentSound : AllSounds) { //Gehe alle Sounds durch und pausiere sie, wenn sie laufen
            if (CurrentSound.Clip.isRunning()) {
                CurrentSound.Clip.stop();
            }
        }
    }
    
    public static void resumeAll() {
        long currentTime = System.currentTimeMillis(); //Hole dir die Jetzige Zeit

        for (Sound CurrentSound : AllSounds) { //Gehe alle Sounds durch und resume sie. Außerdem ist das jetzt nun vergangen, also die vergangenheite, ist einfach damit die Zeit von der Pausierung vorbei ist
            CurrentSound.Clip.start();
            CurrentSound.LastTime = currentTime;
        }
    }
}