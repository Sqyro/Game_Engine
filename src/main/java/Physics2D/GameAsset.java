package Physics2D;

public class GameAsset {
    public int PosX;
    public int PosY;
    public int TextureID;

    public GameAsset(int PosX, int PosY, int TextureID) {
        this.PosX = PosX;
        this.PosY = PosY;
        this.TextureID = TextureID;
    }

    public void setPosX(int newPosX) {
        this.PosX = newPosX;
    }

    public void setPosY(int newPosY) {
        this.PosY = newPosY;
    }

    public void setTextureID(int newTextureID) {
        this.TextureID = newTextureID;
    }
}
