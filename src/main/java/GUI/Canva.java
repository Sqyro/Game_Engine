package GUI;

import Player.Player;
import Enemy.Enemy;
import static org.lwjgl.opengl.GL11.*;

public class Canva {
    
    Map.MapHandler Map;
    
    private int ScreenWidth;
    private int ScreenHeight;
    
    public Canva(int Width, int Height) {
        this.ScreenWidth = Width;
        this.ScreenHeight = Height;
        
        Map = new Map.MapHandler();
    }
    
    protected void drawNewFrame() {
        glClearColor(1f, 1f, 1f, 1f);
        glClear(GL_COLOR_BUFFER_BIT); //Hintergrund auf Weiß setzen
        
        int screenLeft   = (int) -Camera.PosX;
        int screenRight  = screenLeft + ScreenWidth;
        int screenTop    = (int) -Camera.PosY;
        int screenBottom = screenTop + ScreenHeight;

        glPushMatrix();
        glTranslatef((float) Camera.PosX, (float) Camera.PosY, 0f);
        Map.draw();
        
        //Render Enemies;
        for(int i = 0; i < Enemy.Enemies.size(); i++) {
            Enemy currentEnemy = Enemy.Enemies.get(i);
            float EnemyX = currentEnemy.getPosX();
            float EnemyY = currentEnemy.getPosY();
            
            if (EnemyX < screenLeft - 100 || EnemyX > screenRight + 100 ||EnemyY < screenTop - 100 || EnemyY > screenBottom + 100) { //Wenn dieser Enemie nicht auf dem Screen ist (Plus Minus 100, damit man es nicht merkt)
                continue; // Skippt diesen Enemy und macht mit dem nächsten weiter
            }
            
            GUI.ImageHandler.draw(currentEnemy.getTextureID(), (int)EnemyX, (int)EnemyY, currentEnemy.getObjLength(), currentEnemy.getObjHeight());
        }
        
        glPopMatrix();
        
        //Render Player
        
        Player player = Player.Player;
        GUI.ImageHandler.draw(player.getTextureID(), player.LocPosX - player.PlayerSizeX / 2, player.LocPosY - player.PlayerSizeY / 2, player.getObjLength(), player.getObjHeight());

        
        //Render HUD
        
        for(int i = 0; i < GUIHandler.HudElements.size(); i++) {
            HudElement currentHud = GUIHandler.HudElements.get(i);
            int HudX = currentHud.getPosX();
            int HudY = currentHud.getPosY();
            
            if(currentHud instanceof BarElement) { // Wenn es ein BarElement ist, dann zeichnet er die Farbe ("Bar darunter") mit dazu
                BarElement bar = (BarElement) currentHud;
                
                glColor4f(bar.getColor().getRed() / 255f,
                          bar.getColor().getGreen() / 255f,
                          bar.getColor().getBlue() / 255f,
                          BarElement.BarAlpha);
                drawQuad(HudX, HudY, bar.getHudLength() - bar.getBarDamage(), bar.getHudHeight());
                
                glColor4f(1f, 1f, 1f, 1f);
            }
                GUI.ImageHandler.draw(currentHud.getTextureID(), HudX, HudY, currentHud.getHudLength(), currentHud.getHudHeight());
        }
    }
    
    //Helfer Methode für Farben als quadrat
    private void drawQuad(float X, float Y, float Width, float Height) {
        glBegin(GL_QUADS); //Viereck
        glVertex2f(X, Y); //Links Oben
        glVertex2f(X + Width, Y); //Rechts Oben
        glVertex2f(X + Width, Y + Height); //Rechts Unten
        glVertex2f(X, Y + Height); //Links Unten
        glEnd();
    }
}