package entity;
import GUI.Button;
import GUI.InterfaceObject;
import SuperObjects.Interactive_Object;
import main.ColisionManager;
import main.GamePanel;
import main.KeyboardHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Player extends Entity{

    private long CooldownAbility1=10000;
    private long CooldownAbility2=1000;
    private long EmpowerDuration=1000;
    private long lastTimeAb1;
    private long lastTimeAb2;
    public void resetViewers() {
        try
        {
            HP_Bar_Viewer.changeWidth(HP_Bar_Viewer.base_width);
            if(Rhaast_Transform_Viewer!=null)
            {
                Rhaast_Transform_Viewer.changeHeight(Rhaast_Transform_Viewer.base_width);
            }
            if(Shadow_Transform_Viewer!=null);
            {
                Shadow_Transform_Viewer.changeHeight(Shadow_Transform_Viewer.base_height);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static enum form{RHAAST,NORMAL,SHADOW_KAYN};
    public form currentForm=form.NORMAL;
    GamePanel gp;
    InterfaceObject HP_Bar_Viewer;
    InterfaceObject Rhaast_Transform_Viewer;
    InterfaceObject Shadow_Transform_Viewer;
    InterfaceObject Ab1_view;
    BufferedImage Ab1_temp4,Ab1_temp3,Ab1_temp2,Ab1_temp1,Ab1_temp0;
    InterfaceObject Ab2_view;
    BufferedImage Ab2_temp4,Ab2_temp3,Ab2_temp2,Ab2_temp1,Ab2_temp0;
    public int PointsToRhaast;
    public int PointsToShadow;
    public ArrayList<Interactive_Object>Inventory;
    KeyboardHandler KeyH;
    public Scythe Rhaast;
    public Player(GamePanel gp, KeyboardHandler keyH)
    {
        super();

        gp.GM.add(new InterfaceObject(1,0,864-100,32,32,3,3,"ShadowTransformFrame","res/Sprites/Buttons/TransformShadow0.png"));
        gp.GM.add(new InterfaceObject(1,0,864-100,32,32,3,3,"ShadowTransformCover","res/Sprites/Buttons/TransformCover.png"));
        gp.GM.add(new InterfaceObject(1,1536-32*3,864-100,32,32,3,3,"RhaastTransformFrame","res/Sprites/Buttons/TransformRhaast0.png"));
        gp.GM.add(new InterfaceObject(1,1536-32*3,864-100,32,32,3,3,"RhaastTransformCover","res/Sprites/Buttons/TransformCover.png"));

        HealthPoints=100;
        this.Inventory=new ArrayList<Interactive_Object>();
        this.gp=gp;
        this.KeyH=keyH;
        Rhaast=new Scythe(this,10,1,10,4,gp.Tile_size,gp.Tile_size);

        SetDefaultVal();
        HitBox= new Rectangle(pos.x+gp.Tile_size/4,pos.y+gp.Tile_size/4+10* gp.scale,16*gp.scale,9*gp.scale);
        getPlayerFrame();
        HP_Bar_Viewer=gp.GM.getObject("Health",1);
        Rhaast_Transform_Viewer=gp.GM.getObject("RhaastTransformCover",1);
        Shadow_Transform_Viewer=gp.GM.getObject("ShadowTransformCover",1);
        Ab1_view=gp.GM.getObject("Ability1",1);
        Ab2_view=gp.GM.getObject("Ability2",1);
        PointsToRhaast=0;
        PointsToShadow=0;

        try
        {
            Ab1_temp0=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability1_0.png"));
            Ab1_temp1=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability1_1.png"));
            Ab1_temp2=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability1_2.png"));
            Ab1_temp3=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability1_3.png"));
            Ab1_temp4=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability1_4.png"));

            Ab2_temp0=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability2_0.png"));
            Ab2_temp1=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability2_1.png"));
            Ab2_temp2=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability2_2.png"));
            Ab2_temp3=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability2_3.png"));
            Ab2_temp4=ImageIO.read(new FileInputStream("res/Player/Abilities/Kayn_Ability2_4.png"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public void SetDefaultVal()
    {

        HealthPoints=100;
        speed=6;
        direction="down";
        SpriteCap=6;

    }



    public void getPlayerFrame() //Default Frames
    {
        try
        {

            up1= ImageIO.read(new FileInputStream("res/Player/back1.png"));
            up2= ImageIO.read(new FileInputStream("res/Player/back2.png"));
            up3= ImageIO.read(new FileInputStream("res/Player/back3.png"));
            up4= ImageIO.read(new FileInputStream("res/Player/back4.png"));

            down1= ImageIO.read(new FileInputStream("res/Player/face1.png"));
            down2= ImageIO.read(new FileInputStream("res/Player/face2.png"));
            down3= ImageIO.read(new FileInputStream("res/Player/face3.png"));
            down4= ImageIO.read(new FileInputStream("res/Player/face4.png"));

            left1= ImageIO.read(new FileInputStream("res/Player/side-left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Player/side-left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Player/side-left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Player/side-left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Player/side-right1.png"));
            right2= ImageIO.read(new FileInputStream("res/Player/side-right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Player/side-right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Player/side-right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getShadowKaynFrame() //Default Frames
    {
        try
        {
            up1= ImageIO.read(new FileInputStream("res/Player/shadow_back1.png"));
            up2= ImageIO.read(new FileInputStream("res/Player/shadow_back2.png"));
            up3= ImageIO.read(new FileInputStream("res/Player/shadow_back3.png"));
            up4= ImageIO.read(new FileInputStream("res/Player/shadow_back4.png"));

            down1= ImageIO.read(new FileInputStream("res/Player/shadow_face1.png"));
            down2= ImageIO.read(new FileInputStream("res/Player/shadow_face2.png"));
            down3= ImageIO.read(new FileInputStream("res/Player/shadow_face3.png"));
            down4= ImageIO.read(new FileInputStream("res/Player/shadow_face4.png"));

            left1= ImageIO.read(new FileInputStream("res/Player/shadow_side-left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Player/shadow_side-left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Player/shadow_side-left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Player/shadow_side-left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Player/shadow_side-right1.png"));
            right2= ImageIO.read(new FileInputStream("res/Player/shadow_side-right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Player/shadow_side-right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Player/shadow_side-right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getRhaastFrame() //Default Frames
    {
        try
        {
            up1= ImageIO.read(new FileInputStream("res/Player/rhaast_back1.png"));
            up2= ImageIO.read(new FileInputStream("res/Player/rhaast_back2.png"));
            up3= ImageIO.read(new FileInputStream("res/Player/rhaast_back3.png"));
            up4= ImageIO.read(new FileInputStream("res/Player/rhaast_back4.png"));

            down1= ImageIO.read(new FileInputStream("res/Player/rhaast_face1.png"));
            down2= ImageIO.read(new FileInputStream("res/Player/rhaast_face2.png"));
            down3= ImageIO.read(new FileInputStream("res/Player/rhaast_face3.png"));
            down4= ImageIO.read(new FileInputStream("res/Player/rhaast_face4.png"));

            left1= ImageIO.read(new FileInputStream("res/Player/rhaast_side-left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Player/rhaast_side-left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Player/rhaast_side-left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Player/rhaast_side-left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Player/rhaast_side-right1.png"));
            right2= ImageIO.read(new FileInputStream("res/Player/rhaast_side-right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Player/rhaast_side-right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Player/rhaast_side-right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void update() {

        collisionON=false;
        gp.CollisionM.Interact_With_Objects(this, KeyH);

        if (KeyH.up || KeyH.down || KeyH.left || KeyH.right) //Daca macar o tasta de miscaree apasata
        {
            gp.CollisionM.CheckTile(this,KeyH);
            gp.CollisionM.Interact_With_Objects(this, KeyH);
            if (KeyH.up)// && y>0)
            {
                direction = "up";
                if(!collisionON)
                    pos.y-=speed;
            }
            if (KeyH.down)//&&y<ScreenHeight-Tile_size)
            {
                direction = "down";
                if(!collisionON) {
                    pos.y += speed;
                }
                if(gp.CadranY +1 == gp.maxWorldRow/gp.maxScreenRow && pos.y> gp.ScreenHeight-gp.Tile_size) //daca e la ultimul cadran
                    pos.y-=speed;
            }
            if (KeyH.left)// && x>0)
            {
                direction = "left";
                if(!collisionON) {
                    pos.x -= speed;
                }

            }
            if (KeyH.right)// && x<ScreenWidth-Tile_size)
            {   direction = "right";
                if(!collisionON) {
                    pos.x += speed;
                }

                if(gp.CadranX +1 == gp.maxWorldCol/gp.maxScreenCol && HitBox.x+ HitBox.width+1> gp.ScreenWidth) //<<1 ?
                    pos.x-=speed;

            }


                this.UpdateCadrane();


            HitBox.y=pos.y+gp.Tile_size/4+10*gp.scale;
            HitBox.x=pos.x+gp.Tile_size/4;

            //IF THE COLLISION IS FALSE, PLAYER CAN MOVE

            SpriteCounter++;
            if(SpriteCounter>SpriteCap)
            {
                spriteNum++;
                spriteNum%=4;
                SpriteCounter=0;
            }
        }else spriteNum = 0;

        long currentTime=System.currentTimeMillis();

        if(KeyH.e)
        {
            Ab1_view.ChangeImage(Ab1_temp1);
            if(currentTime>lastTimeAb1+CooldownAbility1)
            {
                lastTimeAb1=currentTime;
                try
                {
                    Rhaast.empower(EmpowerDuration);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(KeyH.q)
        {
            if(currentTime>lastTimeAb2+CooldownAbility2)
            {
                lastTimeAb2=currentTime;
                try
                {
                    decreaseHealthBy(-20);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

        adjustAbilityImage();

    }
//Schimba pozele cu cele potrivite pentru cooldown
    private void adjustAbilityImage() {
        long currentTime=System.currentTimeMillis();
        if(currentTime>=(lastTimeAb1+CooldownAbility1))
        {
            Ab1_view.ChangeImage(Ab1_temp0);
        }
        else {
            if(currentTime+CooldownAbility1<(lastTimeAb1+CooldownAbility1))
            {
                Ab1_view.ChangeImage(Ab1_temp1);
            }
            else {
                if(currentTime+3*CooldownAbility1/4<(lastTimeAb1+CooldownAbility1))
                {
                    Ab1_view.ChangeImage(Ab1_temp2);
                }
                else
                    if(currentTime+CooldownAbility1/2<(lastTimeAb1+CooldownAbility1))
                    {
                        Ab1_view.ChangeImage(Ab1_temp3);
                    }
                    else
                        if(currentTime+CooldownAbility1/4<(lastTimeAb1+CooldownAbility1))
                        {
                            Ab1_view.ChangeImage(Ab1_temp4);
                        }
            }

        }
        if(currentTime>=(lastTimeAb2+CooldownAbility2))
        {
            Ab2_view.ChangeImage(Ab2_temp0);
        }
        else {
            if (currentTime + CooldownAbility2 < (lastTimeAb2 + CooldownAbility2)) {
                Ab2_view.ChangeImage(Ab2_temp1);
            } else {
                if (currentTime + 3 * CooldownAbility2 / 4 < (lastTimeAb2 + CooldownAbility2)) {
                    Ab2_view.ChangeImage(Ab2_temp2);
                } else if (currentTime + CooldownAbility2 / 2 < (lastTimeAb2 + CooldownAbility2)) {
                    Ab2_view.ChangeImage(Ab2_temp3);
                } else if (currentTime + CooldownAbility2 / 4 < (lastTimeAb2 + CooldownAbility2)) {
                    Ab2_view.ChangeImage(Ab2_temp4);
                }
            }
        }

    }


    //Functia schimba camera comform playerului
    public void UpdateCadrane() {
            //UPDATE CADRANE:
        try
        {
            if(HitBox.y>gp.ScreenHeight)
            {

                if(gp.CadranY<gp.maxWorldRow/gp.maxScreenRow)
                {
                    gp.CadranY++;
                    pos.y=0;
                    //Rhaast.pos.y-=gp.ScreenHeight;
                    Rhaast.AdjustPositionBy(0,-gp.ScreenHeight);
                }

            }

            if(HitBox.x>gp.ScreenWidth)
            {
                if(gp.CadranX<gp.maxWorldCol/gp.maxScreenCol)
                {
                    gp.CadranX++;
                    pos.x=0;
                    Rhaast.AdjustPositionBy(-gp.ScreenWidth,0);
                }

            }

            if(HitBox.x+ HitBox.width<0)
            {
                if(gp.CadranX>0)
                {
                    gp.CadranX--;
                    pos.x=gp.ScreenWidth- gp.Tile_size;
                    Rhaast.AdjustPositionBy(gp.ScreenWidth,0);

                }
                else {
                    pos.x=0;
                }

            }

            if(HitBox.y+ HitBox.height<0)
            {
                if(gp.CadranY>0)
                {
                    gp.CadranY--;
                    pos.y=gp.ScreenHeight- gp.Tile_size;
                    Rhaast.AdjustPositionBy(0,gp.ScreenHeight);
                }
                else {
                    pos.y=0;
                    gp.levelWin();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



        }
        @Override
        public void draw (Graphics2D g2)
        {
            //g2.setColor(Color.red);       //DECOMENTEAZA-MA PENTRU A VEDEA HITBOXUL
            //g2.fillRect(HitBox.x, HitBox.y, HitBox.width, HitBox.height);
            BufferedImage image = null;
            switch (direction) {
                case "up":
                    switch (spriteNum) {
                        case 0:
                            image = up1;
                            break;
                        case 1:
                            image = up2;
                            break;
                        case 2:
                            image = up3;
                            break;
                        case 3:
                            image = up4;
                            break;
                    }
                    break;
                case "down":
                    switch (spriteNum) {
                        case 0:
                            image = down1;
                            break;
                        case 1:
                            image = down2;
                            break;
                        case 2:
                            image = down3;
                            break;
                        case 3:
                            image = down4;
                            break;
                    }
                    break;
                case "left":
                    switch (spriteNum) {
                        case 0:
                            image = left1;
                            break;
                        case 1:
                            image = left2;
                            break;
                        case 2:
                            image = left3;
                            break;
                        case 3:
                            image = left4;
                            break;
                    }
                    break;
                case "right":
                    switch (spriteNum) {
                        case 0:
                            image = right1;
                            break;
                        case 1:
                            image = right2;
                            break;
                        case 2:
                            image = right3;
                            break;
                        case 3:
                            image = right4;
                            break;
                    }
                    break;
            }
            g2.drawImage(image, pos.x, pos.y, gp.Tile_size, gp.Tile_size, null);
        }

    public void transformInto(form Form) {

        switch (Form)
        {
            case RHAAST ->
            {
                currentForm=form.RHAAST;
                getRhaastFrame();

                HealthPoints=100;

            }

            case NORMAL ->
            {
                getPlayerFrame();
                currentForm=form.NORMAL;
                SetDefaultVal();
            }

            case SHADOW_KAYN ->
            {
                currentForm=form.SHADOW_KAYN;
                getShadowKaynFrame();
                //Speed attack and health.
                SpriteCap=4;    //Faster animation
                speed=8;
            }

            default ->
            {
                Form=form.NORMAL;
                getPlayerFrame();
                SetDefaultVal();
            }
        }

    }

    public boolean decreaseHealthBy(int x)
    {
        System.out.println(Inventory);
        HealthPoints-=currentForm==form.RHAAST?x/2:x; //Rhaast takes half damage
        //Aplicam regula de 3 simpla la health bar ca sa il scadem in procentaje
        // Daca 100 hp ------inseamna-------- base_width
        //      newHP -------inseamna--------    ???
        //      NewHp*BaseWidth/100;

        try
        {
            HP_Bar_Viewer.changeWidth((HealthPoints*HP_Bar_Viewer.base_width/100));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return HealthPoints<0;
    }

    public void IncreaseTransformation(int x, form Forma)
    {
        //DACA INCREASE-TRANSFORMATION => MICSORAM COVERUL
        InterfaceObject TransformCover=null;
        Integer PointsTo=null;
        if(Forma==form.RHAAST)
        {
            PointsToRhaast+=x;
            if(PointsToRhaast>=100)
            {
                TransformCover=Shadow_Transform_Viewer;
                PointsTo=PointsToShadow;
                PointsToShadow+=x;
            }
            else
            {
                TransformCover=Rhaast_Transform_Viewer;
                PointsTo=PointsToRhaast;
            }
        }
        if(Forma == form.SHADOW_KAYN)
            {

            PointsToShadow+=x;
            if(PointsToShadow>=100)
            {
                TransformCover=Rhaast_Transform_Viewer;
                PointsTo=PointsToRhaast;
                PointsToRhaast+=x;
            }
            else
            {
                TransformCover=Shadow_Transform_Viewer;
                PointsTo=PointsToShadow;
            }

            }

        if(PointsToShadow>=100) {
            gp.GM.add(new GUI.Button(1, 0, 864 - 100, 32, 32, 3, 3, "Shadow_Ready",
                    "res/Sprites/Buttons/S_Ready0.png", "res/Sprites/Buttons/S_Ready1.png", "res/Sprites/Buttons/S_Ready2.png"));
        }

        if(PointsToRhaast>=100) {
                gp.GM.add(new Button(1, 1536 - 32 * 3, 864 - 100, 32, 32, 3, 3, "Rhaast_Ready",
                        "res/Sprites/Buttons/R_Ready0.png", "res/Sprites/Buttons/R_Ready1.png", "res/Sprites/Buttons/R_Ready2.png"));
            }

        try
        {
            if(PointsTo<100)
                TransformCover.changeHeight(TransformCover.base_height-(PointsTo*TransformCover.base_height/100));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int has(String s) // if player has an item
    {
        int index=-1;
        for (int i = 0; i <Inventory.size() ; i++) {
            if(Inventory.get(i).name==s)
            {
                index=i;
                return index;
            }
        }
        return index;
    }

}

