package Item;

public class Item{
    private int TextureID;
    
    private int TextureWidth;
    private int TextureHeight;
    
    private String RegistryName;
    
    public Item(int TextureID, int TextureWidth, int TextureHeight, String RegistryName) {
        this.TextureID = TextureID;
        this.TextureWidth = TextureWidth;
        this.TextureHeight = TextureHeight;
        this.RegistryName = RegistryName;
    }
    
    public int getTextureID() {
        return TextureID;
    }
   
    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }

    public int getTextureWidth() {
        return TextureWidth;
    }
   
    public void setTextureWidth(int newTextureWidth) {
        this.TextureWidth = newTextureWidth;
    }
    
    public int getTextureHeight() {
        return TextureHeight;
    }
   
    public void setTextureHeight(int newTextureHeight) {
        this.TextureHeight = newTextureHeight;
    }
    
    public String getRegistryName() {
        return RegistryName;
    }
}
