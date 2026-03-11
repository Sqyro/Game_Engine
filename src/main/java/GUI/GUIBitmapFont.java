package GUI;

public class GUIBitmapFont {
    
    //Anzahl der Reihen und Spalten auf der Font Map/Textur. hab für Reihen auch 16, obwohl es nur 5 sind, weil die Textur immernoch 1:1 Verhältniss hat.
    public static final int COLUMNS = 16;
    public static final int ROWS = 16;

    //Methoden, um die Position von dem Buchstaben (Character) auf der Textur zu finden
    public static float getTextureX(char Character) {
        int i = Character - 32; // Buchstaben in Ascii lesen (-32, wegen offset, wir skippen die, die man nicht schreiben kann, also die keine richtigen Zeichen sind, wie TAB oder Null)

        int Column = i % COLUMNS; //Rest von Anzahl der Spalten. In welcher Spalte befindet sich der Character. Spalte 1: 1-16, Spalte 2: 17-32 -> 1-16

        return Column / (float)COLUMNS; //Koordinaten normalisieren für OpenGL
    }

    public static float getTextureY(char Character) {
        int i = Character - 32; // Buchstaben in Ascii lesen (-32, wegen offset, wir skippen die, die man nicht schreiben kann, also die keine richtigen Zeichen sind, wie TAB oder Null)

        int Row = i / COLUMNS; //Durch die Anzahl an Spalten. In welcher Reihe befindet sich der Character. Reihe 1: 1-16, Reihe 2: 17-32 -> 1-16

        return Row / (float)ROWS; //Koordinaten normalisieren für OpenGL
    }

    
    //Helfer Methoden, geben die Größe auf der Textur in 0-1 an, also Prozent von der Textur
    public static float getWidth() {
        return 1f / COLUMNS;
    }

    public static float getHeight() {
        return 1f / ROWS;
    }
}
