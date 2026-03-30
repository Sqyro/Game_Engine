package Sounds;

import javax.sound.sampled.*;

public class Sound { //Klasse für Sounds
    public Clip Clip; //Der Eigentliche Sound/Datei
    public FloatControl VolumeControl; //Die VolumeControl Instanz, also der Volume Regler

    //Variablen für Zeitpunkte
    public float RemainingTime;
    public float LastTime;

    //Laustärke des Geräuschs
    public float Volume;

    //Variablen für Position
    public boolean isPositional; //Ob das Geräusch Positionsabhängig ist
    //Position vom Sound
    public float PosX;
    public float PosY;
    public float Falloff; //Wie viel der Sound schwächer wird, wenn man von ihm wegläuft
    
    
    public Sound(Clip Clip, FloatControl VolumeControl, float RemainingTime, float LastTime, float Volume, boolean isPositional, float PosX, float PosY, float Falloff) { //Constructor
        //Setzt die ganzen Variablen pro Objekt
        this.Clip = Clip;
        this.VolumeControl = VolumeControl;
        this.RemainingTime = RemainingTime;
        this.LastTime = LastTime;
        this.Volume = Volume;
        this.isPositional = isPositional;
        this.PosX = PosX;
        this.PosY = PosY;
        this.Falloff = Falloff;
    }
}
