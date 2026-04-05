package Item;

import Registry.Registrable;

import java.io.Serializable;

public class Item implements Registrable, Serializable {
    private static final long serialVersionUID = 1L;
    
    public int TextureID;
    
    public int TextureWidth;
    public int TextureHeight;
    
    private String RegistryName;
    
    public Item(int TextureID, int TextureWidth, int TextureHeight, String RegistryName) {
        this.TextureID = TextureID;
        this.TextureWidth = TextureWidth;
        this.TextureHeight = TextureHeight;
        this.RegistryName = RegistryName;
    }

    @Override
    public String getRegistryName() {
        return RegistryName;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
   
    public void setTextureWidth(int newTextureWidth) {
        this.TextureWidth = newTextureWidth;
    }

    public void setTextureHeight(int newTextureHeight) {
        this.TextureHeight = newTextureHeight;
    }
}
