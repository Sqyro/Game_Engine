//Fragment Shader für HUD, sehr simpel, übersetzt einfach nur die farben der textur auf den Screen
#version 330 core

in vec2 TexCoord; //Variable vom Vertexshader, damit wir wissen wo gezeichnet wird
out vec4 FragColor; //Farbe von diesem Pixel wird ausgegeben

uniform sampler2D textureSampler; //Die Textur die wir haben wollen
uniform vec4 Color; //Ein Multiplikator für die Textur, damit wir sie färben können

void main() {
    //Sample die Textur an den Gegebenen Koordinaten
    vec4 Texure = texture(textureSampler, TexCoord);

    //Multipliziere die Farbe mit dem Farbmultiplikator (Färbe den Pixel ein)
    FragColor = Texure * Color;
}