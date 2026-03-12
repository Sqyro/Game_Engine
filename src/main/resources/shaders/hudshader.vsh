//Vertex Shader für HUD, platziert das Hud mit Größen und Positionen auf den Bildschirm
#version 330 core

layout (location = 0) in vec2 position; //Vertex Position
layout (location = 1) in vec2 texCoord; //Vertex Koordinate

out vec2 TexCoord; //Gib das dem Fragment Shader

uniform vec2 ontextureOffset; //Für Sprite Sheets, offset auf der Textur
uniform vec2 ontextureScale; //Für Sprite Sheets, scale auf der Textur

uniform vec2 offset; //Offset von dem Objekt auf dem Bildschirm
uniform vec2 scale; //Größe von dem Objekt
uniform vec2 screenSize; //Größe von dem Objekt auf dem Bildschirm

void main() {
    //Quadrat an die Richtige Stelle platzieren (Skalieren und offsetten)
    vec2 pos = position * scale + offset;

    //Von Bildschirm koordinaten in Normalisierte Device Koordinaten umwandeln (NDC), weil OpenGL Clipspace braucht
    vec2 ndc;
    ndc.x = (pos.x / screenSize.x) * 2.0 - 1.0;
    ndc.y = 1.0 - (pos.y / screenSize.y) * 2.0;

    gl_Position = vec4(ndc, 0.0, 1.0); //Position auf dem Clip Space
    TexCoord = texCoord * ontextureScale + ontextureOffset; //Eine Region im ganzen Spritesheet an Fragment Shader weitergeben
}