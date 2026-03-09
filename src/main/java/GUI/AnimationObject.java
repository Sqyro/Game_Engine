package GUI;

public class AnimationObject {
    private int TextureID;

    private int Columns;
    private int Rows;

    private int FrameCountPerAnimation;
    private int currentFrame = 0;

    private float TimePerFrame;
    private float Timer = 0;

    private float FrameWidth;
    private float FrameHeight;

    public AnimationObject(int TextureID, int Columns, int Rows, int FrameCountPerAnimation, float TimePerFrame) { //Constructor
        //Variablen hochgeben
        this.TextureID = TextureID;

        this.Columns = Columns;
        this.Rows = Rows;

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

    public float[] getPosOnTextureAsArray() { //Methode um sich das Frame von der richtigen Position auf der Textur zu holen
        //Position holen und zu Position auf der Textur in Pixeln umwandeln
        int X = currentFrame % Columns;
        int Y = currentFrame % Rows;
        float onTextureX = X * FrameWidth;
        float onTextureY = Y * FrameHeight;
        
        //Position ausgeben
        float[] PosOnTextureArray = {onTextureX, onTextureY, FrameWidth, FrameHeight};
        
        return PosOnTextureArray;
    }

    public int getTextureID() {
        return TextureID;
    }
    
    public void setTextureID(int newTextureID) {
        TextureID = newTextureID;
    }
}