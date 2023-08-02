package main;
import DB_package.*;
import GUI.Button;
import GUI.InterfaceObject;
import GUI.GUI_MANAGER;
import SuperObjects.ObjectManager;
import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

//GamePanelul o sa fie gamescreenul
public class GamePanel extends JPanel implements Runnable{

    //Setarile ecranului
    public int Current_Score;
    boolean loaded=false;
    public final int Default_Tile_Size =32; //32 x 32 tile Default
    public final int scale = 3; //32 x 3 = 96
    public final int Tile_size=Default_Tile_Size*scale; // 96 x 96 as scale=3;

    //Vrem ca jocul sa fie 16:9 ratio pe un ecran
    public final int maxScreenCol=16;
    public final int maxScreenRow=9;
    public final int ScreenWidth=Tile_size*maxScreenCol; //1536
    public final int ScreenHeight=Tile_size*maxScreenRow; //864
    int FPS=60;
    Database_Manager DB;


    //WORLD SETTINGS
    public int maxWorldRow;
    public int maxWorldCol; //32
    public int CadranX=0;
    public int CadranY=0;
    KeyboardHandler KeyH=new KeyboardHandler()  ;
    public MouseHandler MouseH=new MouseHandler();
    Button PlayButton;
    Button NextButton;
    Button Easy;
    Button Medium;
    Button Hard;
    Button SaveButton;
    Button LoadButton;
    Button SettingsButton;
    Button BackButton;
    Button RetryButton;
    Button[] Level_= new Button[3];

    Aatrox aatrox=new Aatrox(600,50,Tile_size*3,Tile_size*3,this,9,0);
    TileManager TileM;
    //OBJ_Door Dungeon_Door=new OBJ_Door(864,0,"Dungeon DOOR",this,0,0);
    public ObjectManager ObjManager;
    //Flying_Sword a_s=new Flying_Sword(this,"right",0,200,32*scale,64*scale);
    Flying_Sword a_s= (Flying_Sword) EnemyFactory.createEnemy("FlyingSword",this);
    Thread gameThread;
    public int difficulty=1;
    public int level=1;
    public ColisionManager CollisionM= new ColisionManager(this);
    public Player player;
    public GUI_MANAGER GM;
    public InterfaceObject vision;

    //LostSoul test;
    EnemyManager enemyManager=new EnemyManager(this);;
    public int Current_Scene=0;
    //SCENE:
    //0-MENU
    //1-GAME
    //2-...

    //Set Player pos
    //Point Player=new Point(100,100);
    public GamePanel()
    {
        Current_Score=0;
        DB= new Database_Manager();
        maxWorldRow=0;
        Current_Score =0;
        maxWorldCol=0;
        TileM=new TileManager(this); //LOADMAP
        GM=new GUI_MANAGER(this);
        //INITIALIZARE JOC
        this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.magenta);
        this.setDoubleBuffered(true); //if true -> toate desenele o sa fie facute intr un off-screen buffer
        this.addKeyListener(KeyH);
        this.setFocusable(true); //primeste input as long as e focused;
        this.addMouseListener(MouseH);
        ObjManager=new ObjectManager(this);
        //player.transformInto(Player.form.SHADOW_KAYN);
        vision=GM.getObject("Vision",1);
        player = new Player(this, KeyH);
        PlayButton=(Button)(GM.getObject("PlayButton",0));
        Easy=(Button)(GM.getObject("Easy",0));
        Medium=(Button)(GM.getObject("Medium",0));
        Hard=(Button)(GM.getObject("Hard",0));
        SettingsButton=(Button)(GM.getObject("Settings",0));
        LoadButton=(Button)(GM.getObject("Load",2));
        BackButton=(Button)(GM.getObject("back_arrow",2));
        RetryButton=(Button)(GM.getObject("Retry",3));
        NextButton=(Button)(GM.getObject("Next",4));
        Level_[0]=(Button)(GM.getObject("Level1",2));
        Level_[1]=(Button)(GM.getObject("Level2",2));
        Level_[2]=(Button)(GM.getObject("Level3",2));
        //DB=new Database_Manager(); //realizeaza conexiunea cu baza de date

    }

    public void IncreaseCurrentScore(int x)
    {
        Current_Score+=x;
    }
    public void InitializeAfterStart()
    {
        InitializeFirstTimeEnemies();
        InitializeVisionDifficulty();
    }

    private void InitializeVisionDifficulty() {
        try
        {
            switch (difficulty)
            {
                case 1 ->
                {
                    vision.ChangeImage("res/Player/Vision/Vision_difficulty1.png");
                }
                case 2 ->
                {
                    vision.ChangeImage("res/Player/Vision/Vision_difficulty2.png");
                }
                case 3 ->
                {
                    vision.ChangeImage("res/Player/Vision/Vision_difficulty3.png");
                }
                default ->
                {
                    vision.ChangeImage("res/Player/Vision/Vision_difficulty1.png");
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private void InitializeFirstTimeEnemies()
    {
        //TODO: ADD ENEMY MANAGER HERE
        enemyManager=new EnemyManager(this);
    }
    public void StartGameThread()
    {
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        //GAME LOOP

        double TimeInterval=1000000000/FPS; // 1 sec / 60 fps
        double Delta=0;
        long lastTime=System.nanoTime();
        long currentTime;
        while(gameThread != null)
        {
            currentTime=System.nanoTime();
            Delta += (currentTime-lastTime)/TimeInterval;
            lastTime= currentTime;
            if(Delta >=0 )
            {
                update();

                repaint(); //Asa se apeleaza functia paintcomponent
                Delta--;
            }


        }

    }

    public void update()
    {
        try //O sa dea o erroare pana cand se initializeaza inamicii
        {
            if(Current_Scene==1)
            {
                if(aatrox.CadranX != CadranX || aatrox.CadranY !=CadranY)
                    enemyManager.update();
                player.update();
                aatrox.update();
                //a_s.update();
                if(!player.isAlive())
                {
                    Current_Score=0;
                    Current_Scene=3;
                    player.resetViewers();
                    player.Rhaast=null;
                    CadranX=0;
                    CadranY=0;
                    System.gc();
                    ObjManager=new ObjectManager(this);
                }
                //player.Rhaast.update(MouseH.getMousePoint());

                //Daca am apasat click stanga, setez traiectoria si inUse pentru arma
                if(MouseH.click_stanga)
                    player.Rhaast.use(MouseH.getLastClicked());
                try{
                    if(player.Rhaast.isIn_use())
                    {
                        player.Rhaast.update();

                    }
                }
                catch(Exception e)
                {e.printStackTrace();
                }


            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public void paintComponent(Graphics g) //Graphics e o clasa care are multe functii de desenat!
    {
        //Super- parent class of this class (JPanel)
        //Asa ca noi cand apelam gamePanel.paintComponent sa se apeleze cea din Jpanel
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        int TempScene=Current_Scene;            //DACA LIPSESTE ASTA, CAND SE SCHIMBA SCENA La apasarea butonuluinu randeaza nimic inafara
                                                // de GUI-ul de la joc pentru un frame  deoarece nu intra in case 1 si nu mai deseneaza jocul
        switch (Current_Scene)
        {

            case 0->
            {


                //System.out.println(difficulty);
                try
                {
                    if(PlayButton.isClicked()) //Daca [Scena 0 Buttonul cu ID 1] Este apasat (PLAY) -> Schimba scena
                    {
                        ObjManager=new ObjectManager(this);
                        loadLevel();
                        Current_Scene=1;
                        InitializeAfterStart();
                        if(player == null)
                        {
                            player = new Player(this, KeyH);
                        }
                        GM.add(new Button(1,256,0,32,32,2,2,"Save",
                                "res/Sprites/Buttons/Save1.png","res/Sprites/Buttons/Save2.png","res/Sprites/Buttons/Save3.png"));
                        SaveButton=(Button)(GM.getObject("Save",1));


                    }

                    if(SettingsButton.isClicked())
                    {
                        Current_Scene=2;
                    }
                    if(Easy.isClicked())
                    {
                        System.out.println("Ai apasat butonul easy!");
                        Easy.changeXby(-10000);
                        difficulty=2;
                        Medium.changeXby(10000);
                    }
                    if (Medium.isClicked()) {
                        System.out.println("Ai apasat butonul Medium!");
                        difficulty=3;
                        Medium.changeXby(-10000);
                        Hard.changeXby(10000);
                    }
                    if (Hard.isClicked()) {
                        System.out.println("Ai apasat butonul Hard!");
                        difficulty=1;
                        Hard.changeXby(-10000);
                        Easy.changeXby(10000);

                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("Nu exista Butonul cerut!");
                }
            }

            case 1->
            {


                if(player.currentForm== Player.form.NORMAL)
                {
                    Button Shadow=(Button)(GM.getObject("Shadow_Ready",1)); //Apare butonul IncreaseTransformation
                    Button Rhaast=(Button)(GM.getObject("Rhaast_Ready",1));
                    //player.PointsToRhaast=100;
                    //System.out.println(Rhaast);
                    try
                    {
                        if(Shadow.isClicked())
                        {
                            player.transformInto(Player.form.SHADOW_KAYN);
                            System.out.println("Shadow!");
                        }
                    }catch (Exception e) // MISSINGH BUTTON EXCEPTION
                    {
                        //System.out.println("Lipseste un buton");
                    }
                    try
                    {
                        if(Rhaast.isClicked())
                        {
                            player.transformInto(Player.form.RHAAST);
                            System.out.println("Rhaast!");
                        }
                    }catch(Exception e)
                    {

                    }


                }
                else
                {
                    GM.RemoveObject("Rhaast_Ready",1);
                    GM.RemoveObject("Shadow_Ready",1);
                    GM.RemoveObject("ShadowTransformFrame",1);
                    GM.RemoveObject("RhaastTransformFrame",1);
                    GM.RemoveObject("ShadowTransformCover",1);
                    GM.RemoveObject("RhaastTransformCover",1);
                }

                if(SaveButton.isClicked())
                {
                    saveGame();
                }



                TileM.draw(g2);
                //ObjManager.draw(g2);      Pentru Better Visuals, facem ca functia obj manager sa primeasca un al 2-lea parametru care sa randeze doar obiectele din spatele/fata playerului
                ObjManager.draw_behind(g2,player);
                player.draw(g2);
                ObjManager.draw_above(g2,player);
                if(aatrox.CadranX != CadranX || aatrox.CadranY !=CadranY)
                    enemyManager.draw(g2);
                try
                {
                    if(player.Rhaast.isIn_use())
                        player.Rhaast.draw(g2);
                }catch(Exception e)
                {e.printStackTrace();}
                g2.setColor(new Color(255, 166, 0));
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
                g2.drawString("×"+Integer.toString(Current_Score),16*4+2,28*4);

                g2.setColor(new Color(255,252,161));

                g2.drawString("×"+Integer.toString(Current_Score),16*4,27*4);
                //a_s.draw(g2);
                aatrox.draw(g2);

                vision.changeX(player.pos.x-(int)((vision.width* vision.extra_scale_x)/2)+ Tile_size);
                vision.changeY(player.pos.y-(int)((vision.height* vision.extra_scale_y)/2)+ Tile_size);

            }

            case 2->
            {
                if(LoadButton.isClicked())
                {
                    loadGame();
                    loaded=true;

                }
                if(BackButton.isClicked())
                {
                    Current_Scene=0;
                }
                if(Level_[0].isClicked())
                {
                    level=1;
                    loaded=false;
                }
                if(Level_[1].isClicked())
                {
                    level=2;
                    loaded=false;
                }
                if(Level_[2].isClicked())
                {
                    level=3;
                    loaded=false;
                }

            }

            case 3->
            {
                if(RetryButton.isClicked())
                {
                    Current_Scene=0;
                    loaded=false;
                    player = new Player(this, KeyH);
                }
            }

            case 4->
            {
                if(NextButton.isClicked())
                {
                    Current_Scene=0;
                    loaded=false;
//                    level+=level<3?1:0;
                    player = new Player(this, KeyH);
                }
            }
            default->
            {
                Current_Scene=0;
            }

        }
        GM.draw(g2,TempScene);




        g2.dispose();
    }

    public void setPlayerPosition(int playerPosX, int playerPosY) {
        //System.out.println(playerPosX + "  "+ playerPosY);
        try
        {
            player.pos.x=playerPosX;
            player.pos.y=playerPosY;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //System.out.println( player.pos.x + "  "+  player.pos.y);

        //player.update(); //Sa setam si hitboxul
    }

    public int getLevel()
    {
        return level;
    }

    public void loadGame() {
        DB_Command loadCommand =new loadInstance(this, DB);
        try
        {
            loadCommand.execute();
        }
        catch (NoDatabaseFoundException e)
        {
            System.out.println(">EMERGENCY LOAD - [DATABASE NOT FOUND] [!!!]");
            //Emergency Load
            setPlayerPosition(200 , 200);
            CadranY=0;
            CadranX=0;
            level=1;

            player.transformInto(Player.form.NORMAL);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void saveGame() {
        DB_Command saveCommand =  new saveInstance(this,DB);
        try
        {
            saveCommand.execute();
        }catch (NoDatabaseFoundException e)
        {
            e.printStackTrace();
        }
        //catch(Alte exceptii)

    }

    public void loadLevel()
    {
        switch(level)
        {
            case 1->
            {
                TileM.loadMap("/maps/map1.txt");
                if(!loaded)
                {
                    CadranX=0;
                    CadranY=0;
                }
            }

            case 2->
            {
                ObjManager.Obiecte[1].CadranX=2;
                TileM.loadMap("/maps/map2.txt");
                if(!loaded)
                {
                    CadranX=0;
                    CadranY=1;
                }

            }
            case 3->
            {
                TileM.loadMap("/maps/map3.txt");
                if(!loaded)
                {
                    CadranX=4;
                    CadranY=2;
                }

            }
        }
    }

    public void levelWin() {
        System.out.println("You win!");
        level+=level<3?1:0;
        loaded=false;
        Current_Scene=4;
    }
}
