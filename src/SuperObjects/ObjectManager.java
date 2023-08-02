package SuperObjects;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

interface ObjM
{

    public void getObjects();

    public  void draw_behind(Graphics2D g2, Entity entity);

    public  void draw_above(Graphics2D g2, Entity entity);
}

public class ObjectManager implements ObjM {
    public final int NUMBER_OF_OBJECTS=14;
    GamePanel gp;
    public Interactive_Object [] Obiecte;

    public ObjectManager(GamePanel gp)
    {
        this.gp=gp;
        Obiecte=new Interactive_Object[NUMBER_OF_OBJECTS];
        getObjects();
    }

    //Initialize all objects
    public void getObjects()
    {
        try
        {
            Random random=new Random();
            Obiecte[0]=new TileObject(864-2*gp.Tile_size,0,"Dungeon_Door",gp,0,0,2,2,true,false);
            Obiecte[1]=new PickableObject(864,300,"Dungeon_Key",gp,0,0);
            Obiecte[2]=new TileObject(864-2*gp.Tile_size,0,"Dungeon_Door",gp,0,1,2,2,true,false);
            Obiecte[3]=new TileObject(864,400,"Chest",gp,0,0,1,1,true,true);
            Obiecte[4]=new PickableObject(864,600,"Chest_Key",gp,0,0);
            Obiecte[5]=new PickableObject(864,300,"Dungeon_Key",gp,0,1);

            Obiecte[6]=new TileObject(0+96*(random.nextInt()%14+1),200,"Chest",gp,0,1,1,1,true,true);
            Obiecte[7]=new PickableObject(96*(random.nextInt()%14+1),600,"Chest_Key",gp,0,1);
            Obiecte[8]=new TileObject(96*(random.nextInt()%14+1),400,"Chest",gp,1,0,1,1,true,true);
            Obiecte[9]=new PickableObject(96*(random.nextInt()%14+1),600,"Chest_Key",gp,1,0);
            Obiecte[10]=new TileObject(96*(random.nextInt()%14+1),400,"Chest",gp,1,1,1,1,true,true);
            Obiecte[11]=new PickableObject(96*(random.nextInt()%14+1),600,"Chest_Key",gp,1,1);

            Obiecte[12]=new PickableObject(864,300,"Dungeon_Key",gp,9,1);
            //Obiecte[13]=new TileObject(864-2*gp.Tile_size,0,"Dungeon_Door",gp,9,0,2,2,true,false);
            Obiecte[13]=new TileObject(864-2*gp.Tile_size,0,"Dungeon_Door",gp,9,1,2,2,true,false);

        }catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    //Functiile urmatoare sunt pentru a desena obiectele din spatele si din fata playerului
    //Sunt doar pentru estetica.
    public void draw_behind(Graphics2D g2, Entity entity)
    {
        for(int i=0;i<NUMBER_OF_OBJECTS;++i)
        {
            if(entity.HitBox.y+entity.HitBox.height>Obiecte[i].y+gp.Tile_size*Obiecte[i].extra_scale_y) {
                Obiecte[i].draw(g2);
            }
        }
    }

    public void draw_above(Graphics2D g2, Entity entity)
    {
        for(int i=0;i<NUMBER_OF_OBJECTS;++i)
        {
            if(entity.HitBox.y+entity.HitBox.height<=Obiecte[i].y+gp.Tile_size*Obiecte[i].extra_scale_y) {
                Obiecte[i].draw(g2);
            }
        }
    }

}

