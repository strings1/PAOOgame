package SuperObjects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class TileObject extends Interactive_Object {
    public BufferedImage closed, opened;
    GamePanel gp;
    boolean ColAct;
    boolean ColInact;

    public TileObject(int x, int y, final String name, GamePanel gp, int CadX, int CadY,int ExtraScaleX,int ExtrascaleY,boolean collisionInactive,boolean collisionActive)
    {
        super(x,y,name,CadX,CadY);

        this.gp=gp;
        this.used=false;        //Variabila ce marcheaza daca e reutilizabil
        state=Status.INACTIVE;
        collision=true;
        getImage();
        extra_scale_x=ExtraScaleX; //daca un tile e 32x32, usa noastra e 64 x 64
        extra_scale_y=ExtrascaleY; //daca un tile e 32x32, usa noastra e 64 x 64
        Hitbox=new Rectangle(x-10,y-10,(int)(extra_scale_x*gp.Tile_size)+20,(int)(extra_scale_y*gp.Tile_size)+20);
        threshold=500; //0.5 secunde asteptare intre timp
        ColAct=collisionActive;
        ColInact=collisionInactive;//Tipul de coliziune atunci cand obiectul e activ sau inatciv

    }
    public void draw(Graphics2D g2)
    {
        //g2.fillRect(Hitbox.x, Hitbox.y, Hitbox.width, Hitbox.height); //DECOMENTEAZA-MA PENTRU A VEDEA HITBOXUL
        if(gp.CadranX==CadranX && gp.CadranY==CadranY)
        if(state==Status.INACTIVE) {
            g2.drawImage(closed,x,y,(int)(gp.Tile_size*extra_scale_x),(int)(gp.Tile_size*extra_scale_y),null);
            collision=ColInact;
        }
        else {
            g2.drawImage(opened,x,y,(int)(gp.Tile_size*extra_scale_x),(int)(gp.Tile_size*extra_scale_y),null);
            collision=ColAct;
        }
    }

    public void getImage()
    {
        //tile[0].image= ImageIO.read(new FileInputStream("res/Sprites/Wall.png")); tile[0].collision=true;
        try {
            switch(name)
            {
                case "Dungeon_Door"->
                {
                    closed=ImageIO.read(new FileInputStream("res/Sprites/Door.png"));
                    opened=ImageIO.read(new FileInputStream("res/Sprites/DoorOpen.png"));
                }
                case "Chest"->
                {
                    closed=ImageIO.read(new FileInputStream("res/Sprites/Red_Chest1.png"));
                    opened=ImageIO.read(new FileInputStream("res/Sprites/Red_Chest2.png"));
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //Un obiect de tip TILEObject (Precum o usa) poate fi folosit in sensul in care odata folosit, acesta ramane deblocat.
    //Deblocarea e marcata de var "used"
    //NeededObject reprezinta un obiect de tip "pickable". Acesta este initializat cu "ID-ul" fiecarui obiect necesar. Daca acesta se afla in inventarul playerului la apelul lui
    //"Use" si obiectul nu e inca deblocat, ii dau remove din inventar si deblochez obiectul respectiv
    public boolean use()
    {
        if(!used)
        {
            //Verifica daca playerul are obiectul necesar
            String NeededObject=null;
            try {
                switch (name) {
                    case "Dungeon_Door" -> {
                        NeededObject="Dungeon_Key";

                    }
                    case "Chest" -> {
                        NeededObject="Chest_Key";
                    }
                }

                int index;
                if ((index=gp.player.has(NeededObject))!=-1) //Dungeon Door Needs Dungeon Key (Ob[1])
                {
                    this.gp.player.Inventory.remove(index);      //remove
                    used = true;//unlock
                    gp.Current_Score+=100+(System.currentTimeMillis()%200);
                }
            }catch(Exception e)
            {e.printStackTrace();}




        }
        else
        {
            currentTime=System.currentTimeMillis();
            if(currentTime>lastTime+threshold)
            {
                if(state== Interactive_Object.Status.ACTIVE)
                {
                    state= Interactive_Object.Status.INACTIVE;
                }
                else state= Interactive_Object.Status.ACTIVE;
                lastTime=currentTime;
            }
            return true;
        }

        return false;
    }


    //Can't be taken
    @Override
    public boolean take() {
        return false;
    }
}
