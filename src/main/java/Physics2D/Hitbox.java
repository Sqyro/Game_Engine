package Physics2D;

public class Hitbox {
    private float Radius;
    
    private float OffsetX;
    private float OffsetY;
    
    public Hitbox (float Radius, float OffsetX, float OffsetY) {
        this.Radius = Radius;
        this.OffsetX = OffsetX;
        this.OffsetY = OffsetY;
    }
    
    //Methoden um die Variablen von den Hitboxen zu verändern und neu zu setzen
    public void setRadius (float newRadius) {
        Radius = newRadius;
    }
    
    public float getRadius () {
        return Radius;
    }
    
    public void setOffsetX (float newOffsetX) {
        OffsetX = newOffsetX;
    }
    
    public float getOffsetX () {
        return OffsetX;
    }
    
    public void setOffsetY (float newOffsetY) {
        OffsetY = newOffsetY;
    }
    
    public float getOffsetY () {
        return OffsetY;
    }
}
