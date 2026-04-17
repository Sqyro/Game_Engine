//Fragment Shader für alle Texturen
#version 330 core

in vec2 TexCoord; //Variable vom Vertexshader, damit wir wissen wo gezeichnet wird
out vec4 FragColor; //Farbe von diesem Pixel wird ausgegeben

uniform sampler2D textureSampler; //Die Textur die wir haben wollen
uniform vec4 Color; //Ein Multiplikator für die Textur, damit wir sie färben können
uniform float globalLight; //Die Globale Beleuchtung aus dem LightManager
uniform vec2 ScreenSize; //Bildschirmgröße

#define MAX_LIGHTS 64 //Maximalanzahl an Lichtern, weil OpenGl keine Dynamischen Arrays mag (Es muss ja beim compilen wissen, wie viel Platz da sein soll)

uniform int activeLights; //Aktives Licht
uniform vec2 lightPositions[MAX_LIGHTS]; //Positionen der Lichter in einem Vector2
uniform vec3 lightColors[MAX_LIGHTS]; //Farben der Lichter in nem Vector3
uniform float lightIntensities[MAX_LIGHTS]; //Lichtintensität/Reichweite

void main() {
    //Sample die Textur an den Gegebenen Koordinaten
    vec4 Texture = texture(textureSampler, TexCoord);

    //Normale farbe mit globaler Beleuchtung berechnen
    vec3 finalColor = Texture.rgb * Color.rgb * globalLight;

    // Aktuelle Pixel-Koordinaten (bei OpenGL ist y=0 unten, daher spiegeln wir y)
    vec2 FragmentCoord = vec2(gl_FragCoord.x, ScreenSize.y - gl_FragCoord.y);

    //Lichtberechnung für jedes Licht
    for (int i = 0; i < activeLights; i++) {
        vec2 lightPos = lightPositions[i]; // Position des aktuellen Lichts
        vec3 lightCol = lightColors[i]; // Farbe des aktuellen Lichts
        float Intensity = lightIntensities[i]; // Intensität/Reichweite des Lichts

        // Abstand vom Licht zum Pixel
        float LightDistance = length(FragmentCoord - lightPos);

        //Helligkeit Berechnen je nach Abstand zum Licht und der Stärke
        float extraBrightness = 1.0 - smoothstep(0.0, 1.0, LightDistance / Intensity);

        //Lichtfarbe und Helligkeit an die finale Farbe hinzufügen
        finalColor += Texture.rgb * lightCol * extraBrightness;
    }

    //Pixel Transparenz mit einbeziehen
    float Transparency = Texture.a * Color.a;
    
    //Pixel Farbe ausgeben
    FragColor = vec4(finalColor, Transparency);
}