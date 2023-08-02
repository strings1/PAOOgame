package main;

import SuperObjects.Interactive_Object;
import SuperObjects.ObjectManager;
import entity.Entity;

import java.awt.*;
import java.io.IOException;

public class ColisionManager {
    private GamePanel gp;
    public ColisionManager(GamePanel gp)
    {
        this.gp=gp;
    }

    // Functia verifica daca hitboxul cuiva se suprapune cu un obiect
    //Aceasta testeaza cu un hitbox mai mare decat cel dat pentru a trata obiectele care se afla in perete, precum usile
    public boolean OverlapsObjects(Rectangle hitbox, int objectIndex)
    {

        //MARIM OLEACA HITBOXUL LA ENTITY , UN HITBOX DE INTERACTIUNE IL NUMIM
        //Rectangle h1=new Rectangle(entity.HitBox.x-10,entity.HitBox.y-10,entity.HitBox.width+20,entity.HitBox.height+20); // un Hitbox de interactiune
        Rectangle h2= new Rectangle(gp.ObjManager.Obiecte[objectIndex].x,gp.ObjManager.Obiecte[objectIndex].y,(int)gp.ObjManager.Obiecte[objectIndex].extra_scale_x* gp.Tile_size,
                (int)gp.ObjManager.Obiecte[objectIndex].extra_scale_y* gp.Tile_size);
        return doRectanglesOverlap(hitbox, h2);
    }

    //Same thing doar ca nu mai face funny marire, e folosit pt pickup la obiecte
    public boolean OverlapsObjectHitbox(Rectangle hitbox, int objectIndex)
    {
        return doRectanglesOverlap(hitbox, gp.ObjManager.Obiecte[objectIndex].Hitbox);
    }

    //WALL OF SHAME [m-am chinuit fara sa stiu de .intersects()]

    /*public boolean doRectanglesOverlap(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        int r1Left = x1;
        int r1Right = x1 + w1;
        int r1Top = y1;
        int r1Bottom = y1 + h1;

        int r2Left = x2;
        int r2Right = x2 + w2;
        int r2Top = y2;
        int r2Bottom = y2 + h2;

        if (r1Left < r2Right && r1Right > r2Left && r1Top < r2Bottom && r1Bottom > r2Top) {
            return true;
        } else {
            return false;
        }
    }*/
    public boolean doRectanglesOverlap(Rectangle h1, Rectangle h2)
    {
        return h1.intersects(h2);
    }

    // Functia verifica daca o entitate se suprapune cu un obiect si a activat un BOOL pentru pick (space)
    // Teoretic fiecare entitate ar avea KeyHandlerul ei, chiar daca sunt NPC uri.
    // In joc, monstrii nu pot da pick la obiecte, but maybe in the future they will be able to. It's a nice to have imo.
    public void Interact_With_Objects(Entity entity, KeyboardHandler KeyH)
    {
        if(KeyH.space)
        {

            int i;
            for(i=0;i<gp.ObjManager.NUMBER_OF_OBJECTS;++i)
            {
                //Daca Aria playerului se suprapune cu arie Obiectului si se afla in acelasi cadran, activate it
                if(OverlapsObjectHitbox(entity.HitBox,i))
                {
                    //Daca se afla in acelasi cadran
                    if(gp.ObjManager.Obiecte[i].CadranX==gp.CadranX && gp.ObjManager.Obiecte[i].CadranY==gp.CadranY)
                    {
                        if(gp.ObjManager.Obiecte[i].obj_type== Interactive_Object.type.Solid)   //Daca e ceva solid gen torta, usa, etc
                            gp.ObjManager.Obiecte[i].use();                                     //use() -> cauta obiectul necesar si il sterge din inventar, seteaza "reutilizabil"=true; pentru a-l folosi fara obiect dupa
                        else if (gp.ObjManager.Obiecte[i].obj_type== Interactive_Object.type.Item)
                        {
                            gp.ObjManager.Obiecte[i].take();
                        }
                    }

                }
            }
        }
    }

    // Calculeaza fiecare colt al playerului (Hitbox) si dupa in functie de directia in care se misca face
    // un predict pentru a verifica daca playerul va lovi un perete sau nu
    // Q: Care este consecinta daca nu dau predict la movement?
    // A: Daca playerul se duce in perete la maxim (in sus sa zicem), acesta nu se v-a putea misca stanga/dreapta deoarece coltul de sus atinge se afla in perete.
    //      Therefore, daca dau predict, coltul se scoate singur din perete.            (Sper ca am fost clar)
    public void CheckTile(Entity entity, KeyboardHandler KeyH)
    {
       //Putem sa le initializam si sa calculam cele de care avem nevoie in case , dar arata urat codul dupa :P
        Rectangle temp_hb;
        int ColS=entity.HitBox.x/gp.Tile_size + (gp.maxScreenCol*gp.CadranX);
        int ColD=(entity.HitBox.x+entity.HitBox.width)/gp.Tile_size + (gp.maxScreenCol*gp.CadranX);
        int RowS=(entity.HitBox.y/gp.Tile_size) + (gp.maxScreenRow*gp.CadranY);
        int RowJ=((entity.HitBox.y+entity.HitBox.height)/gp.Tile_size) + (gp.maxScreenRow*gp.CadranY);
        //Daca player up trb sa verificam doar SUS si Stanga/DREAPTA

        int Tile1, Tile2;
        try
        {
            if(KeyH.up) {
                RowS=((entity.HitBox.y- entity.speed)/gp.Tile_size) + (gp.maxScreenRow*gp.CadranY); //Dam predict cu un moment in fata
                Tile1=   gp.TileM.MapTileNum[RowS][ColS];
                Tile2=   gp.TileM.MapTileNum[RowS][ColD];

                if(gp.TileM.tile[Tile1].collision==true || gp.TileM.tile[Tile2].collision==true)
                {entity.collisionON  =true;}
                temp_hb=new Rectangle(entity.HitBox);
                temp_hb.y-=10;

                for (int i = 0; i < gp.ObjManager.NUMBER_OF_OBJECTS; i++) {
                    if(OverlapsObjects(temp_hb,i)                                                                                //Daca Overlaps si nu e activ
                            &&gp.ObjManager.Obiecte[i].CadranX==gp.CadranX && gp.ObjManager.Obiecte[i].CadranY==gp.CadranY      //Si se afla in acelasi cadran
                            && gp.ObjManager.Obiecte[i].collision==true)                                                        //Si obiectul e collisionable
                    {
                        entity.collisionON=true;
                    }
                }

            }

            if(KeyH.down) {
                RowJ=((entity.HitBox.y+entity.HitBox.height+ entity.speed)/gp.Tile_size) + (gp.maxScreenRow*gp.CadranY);
                Tile1=   gp.TileM.MapTileNum[RowJ][ColS];
                Tile2=   gp.TileM.MapTileNum[RowJ][ColD];

                if(gp.TileM.tile[Tile1].collision==true || gp.TileM.tile[Tile2].collision==true)
                {entity.collisionON  =true;}
                temp_hb=new Rectangle(entity.HitBox);
                temp_hb.y+=10;
                for (int i = 0; i < gp.ObjManager.NUMBER_OF_OBJECTS; i++) {
                    if(OverlapsObjects(temp_hb,i)                                                                                //Daca Overlaps si nu e activ
                            &&gp.ObjManager.Obiecte[i].CadranX==gp.CadranX && gp.ObjManager.Obiecte[i].CadranY==gp.CadranY      //Si se afla in acelasi cadran
                            && gp.ObjManager.Obiecte[i].collision==true)                                                        //Si obiectul e collisionable
                    {
                        entity.collisionON=true;
                    }
                }

            }


            if(KeyH.left) {
                ColS=((entity.HitBox.x- entity.speed)/gp.Tile_size) + (gp.maxScreenCol*gp.CadranX);
                Tile1=   gp.TileM.MapTileNum[RowS][ColS];
                Tile2=   gp.TileM.MapTileNum[RowJ][ColS];

                if(gp.TileM.tile[Tile1].collision==true || gp.TileM.tile[Tile2].collision==true)
                {entity.collisionON  =true;}
                temp_hb=new Rectangle(entity.HitBox);
                temp_hb.x-=10;
                for (int i = 0; i < gp.ObjManager.NUMBER_OF_OBJECTS; i++) {
                    if(OverlapsObjects(temp_hb,i)                                                                                //Daca Overlaps si nu e activ
                            &&gp.ObjManager.Obiecte[i].CadranX==gp.CadranX && gp.ObjManager.Obiecte[i].CadranY==gp.CadranY      //Si se afla in acelasi cadran
                            && gp.ObjManager.Obiecte[i].collision==true)                                                        //Si obiectul e collisionable
                    {
                        entity.collisionON=true;
                    }
                }
            }


            if(KeyH.right) {
                ColD=(entity.HitBox.x+entity.HitBox.width+ entity.speed)/gp.Tile_size + (gp.maxScreenCol*gp.CadranX);
                Tile1=   gp.TileM.MapTileNum[RowS][ColD];
                Tile2=   gp.TileM.MapTileNum[RowJ][ColD];

                if(gp.TileM.tile[Tile1].collision==true || gp.TileM.tile[Tile2].collision==true)
                {entity.collisionON  =true;}
                temp_hb=new Rectangle(entity.HitBox);
                temp_hb.x+=10;
                for (int i = 0; i < gp.ObjManager.NUMBER_OF_OBJECTS; i++) {
                    if(OverlapsObjects(temp_hb,i)                                                                                //Daca Overlaps si nu e activ
                            &&gp.ObjManager.Obiecte[i].CadranX==gp.CadranX && gp.ObjManager.Obiecte[i].CadranY==gp.CadranY      //Si se afla in acelasi cadran
                            && gp.ObjManager.Obiecte[i].collision==true)                                                        //Si obiectul e collisionable
                    {
                        entity.collisionON=true;
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            entity.collisionON=true;
        }
    }
}
