//Vertex Shader für alle Texturen, Platziert die Texturen auf dem Bildschirm
#version 330 core

layout (location = 0) in vec2 Position; //Vertex Position
layout (location = 1) in vec2 texCoord; //Textur Position

out vec2 TexCoord; //Gib das dem Fragment Shader

uniform vec2 onTextureOffset; //Für Sprite Sheets, offset auf der Textur
uniform vec2 onTextureScale; //Für Sprite Sheets, scale auf der Textur

uniform vec2 Offset; //Position von dem Objekt auf dem Worldspace (Offset von 0, 0)
uniform vec2 Scale; //Größe von dem Objekt
uniform vec2 ScreenSize; //Größe von dem Bildschirm

void main() {
    //Punkte an die Richtige Stelle im Woldspace platzieren (Skalieren und Offsetten)
    vec2 Pos = Position * Scale + Offset;

    //Von Worldspace koordinaten in Normalisierte Device Koordinaten umwandeln
    vec2 NormalizedDeviceCoordinates;
    NormalizedDeviceCoordinates.x = (Pos.x / ScreenSize.x) * 2.0 - 1.0;
    NormalizedDeviceCoordinates.y = 1.0 - (Pos.y / ScreenSize.y) * 2.0;

    gl_Position = vec4(NormalizedDeviceCoordinates, 0.0, 1.0); //X, Y von NDC, Z Koordinate ist 0 und Perspektivverzerrung 1
    TexCoord = texCoord * onTextureScale + onTextureOffset; //Eine Region im ganzen Spritesheet an Fragment Shader weitergeben
}