package SuperObjects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class PickableObject extends Interactive_Object{
    GamePanel gp;
    public boolean used=false;
    public BufferedImage image;
    PickableObject(int x, int y, final String name, GamePanel gp, int CadX, int CadY)
    {
        super(x,y,name,CadX,CadY);
        this.gp=gp;
        getImage(name);
        extra_scale_x=1; //daca un tile e 32x32, usa noastra e 64 x 64
        extra_scale_y=1; //daca un tile e 32x32, usa noastra e 64 x 64
        obj_type=type.Item;
    }
    public void draw(Graphics2D g2)
    {
        if(gp.CadranX==CadranX && gp.CadranY==CadranY)
                g2.drawImage(image,x,y,(int)(gp.Tile_size*extra_scale_x),(int)(gp.Tile_size*extra_scale_y),null);
                collision=false;
    }

    @Override
    public boolean use() {
        used=true;
        return used;
    }

    public void getImage(String name)
    {
        //tile[0].image= ImageIO.read(new FileInputStream("res/Sprites/Wall.png")); tile[0].collision=true;
        try {
            switch(name)
            {
                case "Dungeon_Key"->
                {
                    image= ImageIO.read(new FileInputStream("res/Sprites/DUNGEON_KEY.png"));
                }
                case "Chest_Key"->
                {
                    image= ImageIO.read(new FileInputStream("res/Sprites/CHEST_KEY.png"));
                }
                default->
                {
                    image=null;
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Daca cineva face obiect.take(), obiectul ajunge in inventar si dispare de pe harta. nu ii dam unload decat la folosire.
    public boolean take()
    {
        gp.player.Inventory.add(this);
        this.x=-1000; // Dispare de pe ecran;
        this.Hitbox.x=-1000;
        return true;
    }
}
