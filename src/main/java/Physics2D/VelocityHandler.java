package Physics2D;

public class VelocityHandler {
    
    //benutzt die momentane geschwindigkeit von diesem Objekt um seine Position fürs nächste Frame auszurechnen
    public static void calculatePosition(LivingObject Object, float deltaTime) {
        //Holt sich die Geschwindigkeit
        float Velocity = Object.getVelocity();
        
        //System.out.println("Alte Position X: " + Object.getPosX() + " Alte Position Y: " + Object.getPosY());
        
        //Holt sich die Ausrichtungen
        float DirectionX = Object.getDirection()[0];
        float DirectionY = Object.getDirection()[1];

        //Satz des Pytagoras, ich normalisiere den Vektor wenn zwei Keys gleichzeitig gedrückt werden, weil sich sonst die Geschwindigkeit addiert
        float Normalization = (float)Math.sqrt(DirectionX * DirectionX + DirectionY * DirectionY);

        if (Normalization != 0) { //Wir wollen nicht durch null teilen
            DirectionX /= Normalization; //Richtung mit Normalisierung Dividieren, damit es nicht 1 auf beiden ist, sondern halt verteilt
            DirectionY /= Normalization;
        }
        
        //Position ausrechnen (alte Position + Geschwingikeit * Zeitunterschied pro Frame * Richtung), wenn Richtung 0, dann bewegen wir uns nicht
        Object.setPosX(Object.getPosX() + Velocity * deltaTime * DirectionX); //deltaTime, also Zeit zwischen zwei Frames brauchen wir, damit sich Objekte nicht auf wegniger FPS langsamer bewegen, weil sie weniger geupdated werden, stattdessen werden sie halt entsprechend hoch gescaled
        Object.setPosY(Object.getPosY() + Velocity * deltaTime * DirectionY);
        
        //System.out.println("Neue Position X: " + Object.getPosX() + " Neue Position Y: " + Object.getPosY());
    }
}
