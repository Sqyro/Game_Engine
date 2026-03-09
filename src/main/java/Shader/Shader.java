package Shader;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    //Varibeln deklarieren
    
    //Shader Programm ID
    private int ShaderID;

    //Locations für Offsets und Scale auf einer textur für den Shader, kann benutzt werden, um nur bestimmte Pixel zu rendern
    public int onTextureOffsetLocation;
    public int onTextureScaleLocation;
    
    //Location von Variabeln für den Shader, wird in den Renderer (ImageHandler) gebraucht
    public int OffsetLocation;
    public int ScaleLocation;
    public int ScreenSizeLocation;
    public int ColorLocation;
    
    public Shader(String vertexPath, String fragmentPath) {

        try {

            //Liest den Schader Code und schreibt ihn in Strings
            String VertexShaderCode = new String(Files.readAllBytes(Paths.get(vertexPath)));
            String FragmentShaderCode = new String(Files.readAllBytes(Paths.get(fragmentPath)));

            //Macht nen Vertex Shader
            int VertexShader = glCreateShader(GL_VERTEX_SHADER);
            //Hängt den Code dran
            glShaderSource(VertexShader, VertexShaderCode);
            //Compiled den Shader
            glCompileShader(VertexShader);
            //Schaut nach Fehlern beim compilen
            if (glGetShaderi(VertexShader, GL_COMPILE_STATUS) == GL_FALSE)
                throw new RuntimeException(glGetShaderInfoLog(VertexShader)); //Schmeißt ne Exception wenn was nicht geklappt hat

            //Macht nen Fragment Shader
            int FragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
            //Hängt den Code dran
            glShaderSource(FragmentShader, FragmentShaderCode);
            //Compiled den Shader
            glCompileShader(FragmentShader);
            //Schaut nach Fehlern beim compilen
            if (glGetShaderi(FragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
                throw new RuntimeException(glGetShaderInfoLog(FragmentShader)); //Schmeißt ne Exception wenn was nicht geklappt hat

            //Macht nen Shader Programm
            ShaderID = glCreateProgram();
            //Hängt Vertex und Fragment Shader dran
            glAttachShader(ShaderID, VertexShader);
            glAttachShader(ShaderID, FragmentShader);
            //Beide Shader zusammenfügen, laufen hintereinander
            glLinkProgram(ShaderID);
            //Guckt wieder nach Errors und schmeißt entsprechend ne Exception
            if (glGetProgrami(ShaderID, GL_LINK_STATUS) == GL_FALSE)
                throw new RuntimeException(glGetProgramInfoLog(ShaderID));

            //Löscht die seperaten Shader, weil sie zusammengefügt wurden
            glDeleteShader(VertexShader);
            glDeleteShader(FragmentShader);
            
            //Qued die Locations für OpenGL von den häufiog verwendeten Variabeln, damit sie Effizienter gesetzt werden können
            OffsetLocation = glGetUniformLocation(ShaderID, "offset");
            ScaleLocation = glGetUniformLocation(ShaderID, "scale");
            ScreenSizeLocation = glGetUniformLocation(ShaderID, "screenSize");
            ColorLocation = glGetUniformLocation(ShaderID, "color");
            
            onTextureOffsetLocation = glGetUniformLocation(ShaderID, "ontextureOffset");
            onTextureScaleLocation = glGetUniformLocation(ShaderID, "ontextureScale");

        } catch (Exception e) {
            throw new RuntimeException("Shader load failed", e); //Nachricht für den Debug
        }
    }
    
    //Methoden um für die Shader in OpenGL Multidimensionale Floats und Integer rüberzureichen
    public void setUniform1f(String Name, float value) {
        int Locations = glGetUniformLocation(ShaderID, Name);
        glUniform1f(Locations, value);
    }
    
    public void setUniform2f(String Name, float X, float Y) {
        int Locations = glGetUniformLocation(ShaderID, Name);
        glUniform2f(Locations, X, Y);
    }

    public void setUniform3f(String Name, float X, float Y, float Z) {
        int Locations = glGetUniformLocation(ShaderID, Name);
        glUniform3f(Locations, X, Y, Z);
    }

    public void setUniform4f(String Name, float X, float Y, float Z, float A) {
        int Locations = glGetUniformLocation(ShaderID, Name);
        glUniform4f(Locations, X, Y, Z, A);
    }
    
    public void setUniform1i(String Name, int value) {
        int Locations = glGetUniformLocation(ShaderID, Name);
        glUniform1i(Locations, value);
    }

    //Aktiviert den Shader
    public void bind() {
        glUseProgram(ShaderID);
    }
    
    //Stopps alle Shader
    public void unbind() {
        glUseProgram(0);
    }
    
    //Wenn man sich die ID holen will
    public int getID() {
        return ShaderID;
    }
}