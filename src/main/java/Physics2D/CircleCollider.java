package Physics2D;

public class CircleCollider {
    public float Radius;
    
    public float OffsetX;
    public float OffsetY;
    
    public CircleCollider (float Radius, float OffsetX, float OffsetY) {
        this.Radius = Radius;
        this.OffsetX = OffsetX;
        this.OffsetY = OffsetY;
    }
    
    //Methoden um die Variablen von den Hitboxen zu verändern und neu zu setzen
    public void setRadius (float newRadius) {
        Radius = newRadius;
    }
    
    public void setOffsetX (float newOffsetX) {
        OffsetX = newOffsetX;
    }
    
    public void setOffsetY (float newOffsetY) {
        OffsetY = newOffsetY;
    }
}
