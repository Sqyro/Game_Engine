//Fragment Shader für alle Texturen
#version 330 core

in vec2 TexCoord; //Variable vom Vertexshader, damit wir wissen wo gezeichnet wird
out vec4 FragColor; //Farbe von diesem Pixel wird ausgegeben

uniform sampler2D textureSampler; //Die Textur die wir haben wollen
uniform vec4 color; //Ein Multiplikator für die Textur, damit wir sie färben können
uniform float globalLight; //Die Globale Beleuchtung aus dem LightManager
uniform vec2 screenSize; //Bildschirmgröße

#define MAX_LIGHTS 10 //Maximalanzahl an Lichtern, weil OpenGl keine Dynamischen Arrays mag

uniform int activeLights; //Aktives Licht
uniform vec2 lightPositions[MAX_LIGHTS]; //Positionen der Lichter in einem Vector2
uniform vec3 lightColors[MAX_LIGHTS]; //Farben der Lichter in nem Vector3
uniform float lightIntensities[MAX_LIGHTS]; //Lichtintensität/Reichweite

void main() {
    //Sample die Textur an den Gegebenen Koordinaten
    vec4 tex = texture(textureSampler, TexCoord);

    //Normale farbe mit globaler Beleuchtung berechnen
    vec3 finalColor = tex.rgb * color.rgb * globalLight;

    //Lichtberechnung für jedes Licht
    for (int i = 0; i < activeLights; i++) {
        vec2 lightPos = lightPositions[i]; // Position des aktuellen Lichts
        vec3 lightCol = lightColors[i]; // Farbe des aktuellen Lichts
        float intensity = lightIntensities[i]; // Intensität/Reichweite des Lichts

        // Aktuelle Pixel-Koordinaten (bei OpenGL ist y=0 unten, daher spiegeln wir y)
        vec2 frag = vec2(gl_FragCoord.x, screenSize.y - gl_FragCoord.y);

        // Abstand vom Licht zum Pixel
        float dist = length(frag - lightPos);

        //Helligkeit Berechnen je nach Abstand zum Licht und der Stärke
        float factor = clamp(1.0 - dist / intensity, 0.0, 1.0);

        //Lichtfarbe und Helligkeit an die finale Farbe hinzufügen
        finalColor += tex.rgb * lightCol * factor;
    }

    //Pixel Transparenz mit  einbeziehen
    float alpha = tex.a * color.a;
    
    //Pixel Farbe ausgeben
    FragColor = vec4(finalColor, alpha);
}