package Rendering;

public class AnimationObject {
    public int TextureID;

    private int FrameCountPerAnimation;
    private int currentFrame = 0;
    private int animationColumn;

    private float TimePerFrame;
    private float Timer = 0;

    public float FrameWidth;
    public float FrameHeight;

    public AnimationObject(int TextureID, int Columns, int Rows, int AnimationColumn, int FrameCountPerAnimation, float TimePerFrame) { //Constructor
        //Variablen für das Objekt schreiben
        this.TextureID = TextureID;

        this.animationColumn = AnimationColumn;
        
        this.FrameCountPerAnimation = FrameCountPerAnimation;
        this.TimePerFrame = TimePerFrame;

        //Anteil an der ganzen Textur von jedem Frame
        this.FrameWidth = 1f / Columns;
        this.FrameHeight = 1f / Rows;
    }

    //Methode, um Animationen up- zu daten
    public void UpdateAnimation(float deltaTime) {
        Timer += deltaTime; //Erhöht den Time jedes mal wenn die Methode gecallt wird um deltaTime

        if (Timer >= TimePerFrame) { //Wenn die verstrichene Zeit der ganzen Global Frames größer ist als die gewünschte Zeit pro Animations Frame

            Timer = 0; //Timer zurücksetzen
            currentFrame++; //Das Momentane Frame um eins erhöhen

            if (currentFrame >= FrameCountPerAnimation) { //Sobald das currentFrame gleich der Frame Anzahl der Animation ist
                currentFrame = 0; //Current Frame zurücksetzen
            }
        }
    }

    public float[] getPosOnTextureAsArray(boolean FlippedX) { //Methode um sich das Frame von der richtigen Position auf der Textur zu holen
        //Position holen und in Position auf dem Frame als Verhältniss zum ganzen Frame schreiben
        int X = animationColumn;
        int Y = currentFrame;
        float onTextureX = X * FrameWidth;
        float onTextureY = Y * FrameHeight;
        
        float[] PosOnTextureArray;
        
        if (FlippedX) { //falls der Sprite flipped ist
            //Frame auf dem Spritesheet umdrehen
            onTextureX += FrameWidth; //Position verschieben, weil wir sie später subtrahieren für den Flip
            PosOnTextureArray = new float[] {onTextureX, onTextureY, -FrameWidth, FrameHeight}; //Negative vom FrameWidth nehmen, damit die Textur geflipped wird (ist jetzt subtrahiert)
        } else {
            PosOnTextureArray = new float[] {onTextureX, onTextureY, FrameWidth, FrameHeight};
        }
        
        //Position ausgeben
        return PosOnTextureArray;
    }
    
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
}