package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    Tile MissingTile;

    private final int NUMBER_OF_TILES=27;
    GamePanel gp;
    public Tile [] tile;
    public int MapTileNum[][];
    public TileManager(GamePanel gp)
    {
        this.gp=gp;
        MissingTile=new Tile();
        try
        {
            MissingTile.image= ImageIO.read(new FileInputStream("res/Sprites/MISSINGTEXTURE.png"));
            MissingTile.collision=true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            MissingTile.image=null;
        }

        tile=new Tile[NUMBER_OF_TILES]; //10 types of Tiles , schimb in functie de cate am
        //MapTileNum=new int [gp.maxWorldRow][gp.maxWorldCol];
        getTileImage();
        loadMap("maps/map1.txt");

    }

    //Stocheaza tileurile
    public void getTileImage()
    {
        try
        {
            for(int i=0; i<NUMBER_OF_TILES;i++)
            {
                tile[i]=new Tile();
            }
            //WALLS
            tile[0].image= ImageIO.read(new FileInputStream("res/Sprites/Wall.png")); tile[0].collision=true;
            tile[1].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_up_topleft.png"));tile[1].collision=true;
            tile[2].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_up_topright.png"));tile[2].collision=true;
            tile[3].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_down_botleft.png"));tile[3].collision=true;
            tile[4].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_down_botright.png"));tile[4].collision=true;
            tile[5].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_left.png"));tile[5].collision=true;
            tile[6].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_top.png"));tile[6].collision=true;
            tile[7].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_right.png"));tile[7].collision=true;
            tile[8].image= ImageIO.read(new FileInputStream("res/Sprites/WallEnd_bot.png"));tile[8].collision=true;

            //DUNGEON TILES
            tile[9].image= ImageIO.read(new FileInputStream("res/Sprites/DungeonTileShadowLeft.png"));
            tile[10].image=ImageIO.read(new FileInputStream("res/Sprites/DungeonTileShadowLeftCorner.png"));
            tile[11].image=ImageIO.read(new FileInputStream("res/Sprites/DungeonTileShadowUp.png"));
            tile[12].image=ImageIO.read(new FileInputStream("res/Sprites/DungeonTile.png"));

            //CARPET
            tile[13].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_up_leftcorner.png"));
            tile[14].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_up_rightcorner.png"));
            tile[15].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_down_leftcorner.png"));
            tile[16].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_down_rightcorner.png"));
            tile[17].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_left.png"));
            tile[18].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_up.png"));
            tile[19].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_right.png"));
            tile[20].image=ImageIO.read(new FileInputStream("res/Sprites/carpet_down.png"));
            tile[21].image=ImageIO.read(new FileInputStream("res/Sprites/carpet.png"));
            //
            tile[22].image=ImageIO.read(new FileInputStream("res/Sprites/up_topleft_corner.png"));tile[22].collision=true;
            tile[23].image=ImageIO.read(new FileInputStream("res/Sprites/up_topright_corner.png"));tile[23].collision=true;
            tile[24].image=ImageIO.read(new FileInputStream("res/Sprites/left_corner.png"));tile[24].collision=true;
            tile[25].image=ImageIO.read(new FileInputStream("res/Sprites/right_corner.png"));tile[25].collision=true;
            tile[26].image=ImageIO.read(new FileInputStream("res/Sprites/right2_corner.png"));tile[26].collision=true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        int col=0;
        int row=0;
        int x=0;
        int y=0;
        int TileID;


        for(row=0;row<gp.maxScreenRow;row++)
        {
            for(col=0;col<gp.maxScreenCol;col++)
            {
                TileID=MapTileNum[row+ gp.maxScreenRow*gp.CadranY][col+gp.maxScreenCol*gp.CadranX];
                try
                {
                    g2.drawImage(tile[TileID].image,x,y,gp.Tile_size,gp.Tile_size,null);
                }catch(Exception e)
                {
                    g2.drawImage(MissingTile.image,x,y,gp.Tile_size,gp.Tile_size,null);
                }

                x+=gp.Tile_size;
            }
            y+=gp.Tile_size;
            x=0;
        }
    }

    //Functia primeste directorul unei harti si il ruleaza dupa cum trebuie
    //Daca intervine un IOException (Harta nu exista), atunci toata harta este initializata cu un MISSING_TILE.
    //Daca lipseste o imagine, atunci toate imaginile cu id >= cea care lipseste devin "MISSING TILE"
    public void loadMap(String map_path)
    {
        try
        {
            //Un format ca sa citim textfileul
            InputStream is = getClass().getResourceAsStream(map_path);
            BufferedReader br =new BufferedReader(new InputStreamReader(is));
            int col=0;
            int row=0;
            int num;
            String line= br.readLine();
            String numbers[]=line.split(" ");
            gp.maxWorldRow=Integer.parseInt(numbers[0]);
            gp.maxWorldCol=Integer.parseInt(numbers[1]);
            MapTileNum=new int [gp.maxWorldRow][gp.maxWorldCol];
            for(row=0;row<gp.maxWorldRow;row++)
            {
                line= br.readLine();
                numbers=line.split(" ");
                for(col=0;col<gp.maxWorldCol;col++)
                {
                    num=Integer.parseInt(numbers[col]);
                    MapTileNum[row][col]=num;
                }
            }


            br.close();
            is.close();
        }catch(Exception e)
        {
            System.out.println("FISIER NULL");
            gp.maxWorldRow=9;
            gp.maxWorldCol=16;
            MapTileNum=new int [gp.maxWorldRow][gp.maxWorldCol];
            for(int row=0;row<gp.maxWorldRow;row++)
            {
                for(int col=0;col<gp.maxWorldCol;col++)
                {
                    MapTileNum[row][col]=-1;
                }
            }

        }
    }
}
