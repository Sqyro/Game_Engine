//Vertex Shader für alle Texturen, Platziert die Texturen auf dem Bildschirm
#version 330 core

layout (location = 0) in vec2 Position; //Vertex Position
layout (location = 1) in vec2 texCoord; //Vertex Koordinate

out vec2 TexCoord; //Gib das dem Fragment Shader

uniform vec2 onTextureOffset; //Für Sprite Sheets, offset auf der Textur
uniform vec2 onTextureScale; //Für Sprite Sheets, scale auf der Textur

uniform vec2 Offset; //Offset von dem Objekt auf dem Bildschirm
uniform vec2 Scale; //Größe von dem Objekt
uniform vec2 ScreenSize; //Größe von dem Objekt auf dem Bildschirm

void main() {
    //Quadrat an die Richtige Stelle platzieren (Skalieren und offsetten)
    vec2 Pos = Position * Scale + Offset;

    //Von Bildschirm koordinaten (Pixel) in Normalisierte Device Koordinaten umwandeln
    vec2 NormalizedDeviceCoordinates;
    NormalizedDeviceCoordinates.x = (Pos.x / ScreenSize.x) * 2.0 - 1.0;
    NormalizedDeviceCoordinates.y = 1.0 - (Pos.y / ScreenSize.y) * 2.0;

    gl_Position = vec4(NormalizedDeviceCoordinates, 0.0, 1.0); //Position auf dem Clip Space
    TexCoord = texCoord * onTextureScale + onTextureOffset; //Eine Region im ganzen Spritesheet an Fragment Shader weitergeben
}