package Sounds;

import Player.Player;
import Rendering.Frame;

import javax.sound.sampled.*;

import java.io.File;

public class SoundHandler {
    
    public volatile boolean ShouldPlay = true;
    
    public void setShouldPlay(boolean newShouldPlay) {
        ShouldPlay = newShouldPlay;
    }
    
    public static synchronized void playSound(final String SoundName, int TimeInMilliseconds, float Volume) { //Synchronisiert, damit nur ein Thread aufgemacht wird für alle Sounds (Ich bin Albert Einstein)
        new Thread(new Runnable() { //Wir machen nen neuen Thread auf, damit der Sound nicht den main Game Loop anhält
            @Override
            public void run() {
                try {
                    Clip Clip = AudioSystem.getClip(); //Neues AudioClip Objekt
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile()); //Läd die File mit nem Audio Input Stream
                    Clip.open(inputStream); //Clip sneaked sich den Inout Stream
                    //Lautstärke regeln
                    FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN); //Ein Lautstärkeregler für diesen Sound Definieren
                    VolumeControl.setValue(Volume); //Den Wert von dem Lautstärkeregler setzen auf die Gegebene Laustärke, damit wird also die Laustärke vom Sound angepasst, weil der Lautstärke regler dem Sound schon zugewiesen wurde
                    
                    Clip.start(); //Startet die Audio Datei
                    
                    long RemainingTime = TimeInMilliseconds; //übrige Zeit vom Sound in ne lokale Variable schreiben, damit ich die später verringern kann
                    long LastTime = System.currentTimeMillis(); //Zeit von jetzt, gleich eben holen

                    while (RemainingTime > 0) { //Wärend die Zeit nicht vorbei ist
                        if (!Frame.GameRunning) { //Wenn das Spiel nicht läuft
                            Clip.stop(); //Dann pausiere den Sound

                            while (!Frame.GameRunning) { //Ein Loop der solange läuft wie das Spiel auf pause ist
                                Thread.sleep(10); //Einfach immer für 10 Millisekunden schlafen
                            }
                            
                            //Nachdem der Loop vorbei ist => Das Spiel läuft also wieder

                            Clip.start(); //Spiele den Sound weiter ab
                            LastTime = System.currentTimeMillis(); //Zeit von eben in eine Variable schreiben, damit wir die Zeit wo der Sound Pausiert war rausrechnen können
                        }

                        long CurrentTime = System.currentTimeMillis(); //Den jetzigen Zeitpunkt holen
                        RemainingTime -= (CurrentTime - LastTime); //Die übrige Zeit ist die Differenz aus dem jetzigen Zeitpunkt und der Zeit davor, weil das die Zeit ist wie lang der Sound tatsächlich gespielt hat und nicht auf Pause war
                        LastTime = CurrentTime; //Das jetzt von eben ist jetzt die vergangene Zeit

                        Thread.sleep(10); //Nur alle 10 Millisekunden checken, um performance zu sparen
                    }
                    
                    Clip.stop(); //Stoppt den Sound nach der Dauer
                    Clip.close(); //Schließt den Clip um das Memory, welches der Sound verwendet, wieder frei zu geben
                } catch (Exception e) { //Schmeißt ne Exception mit ner Error Message wenn nicht klappt
                    System.err.println(e.getMessage());
                }
            }
        }).start(); //Thread starten
    }
    
    public static synchronized void playSoundAtPos(final String SoundName, int TimeInMilliseconds, float Volume, float PosX, float PosY, float Falloff) { //Synchronisiert, damit nur ein Thread aufgemacht wird für alle Sounds (Ich bin Albert Einstein)
        new Thread(new Runnable() { //Wir machen nen neuen Thread auf, damit der Sound nicht den main Game Loop anhält
            @Override
            public void run() {
                try {
                    Clip Clip = AudioSystem.getClip(); //Neues AudioClip Objekt
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/assets/sounds" + SoundName).getAbsoluteFile()); //Läd die File mit nem Audio Input Stream
                    Clip.open(inputStream); //Clip sneaked sich den Inout Stream
                    //Lautstärke regeln
                    FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN); //Ein Lautstärkeregler für diesen Sound Definieren              
                    Clip.start(); //Startet die Audio Datei
                    Player player = Player.Player; //Den Spieler Sneaken, um die Position zu bekommen
                    long StopAt = System.currentTimeMillis() + TimeInMilliseconds; //Berechnen wann der Sound aufhören soll, System Time plus TimeInSeconds zu Millisekunden konvertiert
                    while (System.currentTimeMillis() <= StopAt) { //Solange die Momentane Zeit nicht die Zeit ist wo er aufhören soll
                        
                        if (!Frame.GameRunning) { //Wenn das Spiel nicht läuft
                            Clip.stop(); //Dann pausiere den Sound

                            while (!Frame.GameRunning) { //Ein Loop der solange läuft wie das Spiel auf pause ist
                                Thread.sleep(10); //Einfach immer für 10 Millisekunden schlafen
                            }
                            
                            //Nachdem der Loop vorbei ist => Das Spiel läuft also wieder

                            Clip.start(); //Spiele den Sound weiter ab
                        }
                        
                        //Abstand zwischen Spieler und Sound für X und Y separat holen
                        float DeltaPosX = PosX - player.getPosX();
                        float DeltaPosY = PosY - player.getPosY();
        
                        //Satz des Pythagoras, um den Abstand in eine Länge zu bekommen (Hypothenuse in nem Dreieck mit den beiden Längen)
                        float Distance = (float)Math.sqrt((DeltaPosX * DeltaPosX) + (DeltaPosY * DeltaPosY));
                    
                        VolumeControl.setValue(Math.max(-80f, Volume - (Distance / Falloff))); //Lautstärke entsprechend anpassen
                        
                        Thread.sleep(50); //Nur alle 50 millisekunden checken statt immer, um Performance zu sparen
                    }
                    Clip.stop(); //Stoppt den Sound nach der Dauer
                    Clip.close(); //Schließt den Clip um das Memory, welches der Sound verwendet, wieder frei zu geben
                } catch (Exception e) { //Schmeißt ne Exception mit ner Error Message wenn nicht klappt
                    System.err.println(e.getMessage());
                }
            }
        }).start(); //Thread starten
    } 
}