package Physics2D;

public class BoxCollider {
    public float PosX;
    public float PosY;
    
    public float Height;
    public float Length;
    
    public BoxCollider (float PosX, float PosY, float Height, float Length) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.Height = Height;
        this.Length = Length;
    }
    
    public void setPosX (float newPosX) {
        PosX = newPosX;
    }
    
    public void setPosY (float newPosY) {
        PosY = newPosY;
    }
    
    public void setHeight (float newHeight) {
        Height = newHeight;
    }
    
    public void setLength (float newLength) {
        Length = newLength;
    }
}
