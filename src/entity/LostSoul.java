package entity;

import SuperObjects.Interactive_Object;
import main.GamePanel;
import main.KeyboardHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class LostSoul extends Entity{

    public Point Victim_pos;
    private boolean damaged=false; //Pentru a nu da damageul armei la fiecare frame. Noi vrem sa dam damage doar la prima intersectie
    GamePanel gp;
    int damage=10;
    protected LostSoul(GamePanel gp)
    {
        super();
        HitBox= new Rectangle(pos.x+gp.Tile_size/4,pos.y,gp.Tile_size/2,gp.Tile_size);
        getFrame();
        this.gp=gp;
        direction="down";
        restoreDefaultVals();
    }

    //Metoda ce doar fabricile din package o pot folosi
    protected static LostSoul create(GamePanel gp)
    {
        return new LostSoul(gp);
    }
    //Initializeaza frameurile pentru fantome
    public void getFrame() //Default Frames
    {
        try
        {
            up1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_up1.png"));
            up2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_up2.png"));
            up3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_up3.png"));
            up4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_up4.png"));

            down1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_down1.png"));
            down2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_down2.png"));
            down3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_down3.png"));
            down4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_down4.png"));

            left1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_right1.png"));
            right2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/LostSoul_right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2)
    {
        /*
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "HELLOW WORLD!@#!";
        int text_x=this.pos.x;
        int text_y=this.pos.y;
        g2.drawString(text,text_x,text_y);
        */
        //g2.fillRect(HitBox.x, HitBox.y, HitBox.width, HitBox.height); //DECOMENTEAZA-MA PENTRU A VEDEA HITBOXUL
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

    //Oleaca de matematica si putem determina vectorul pentru directie, pe care ulterior il scalam cu viteza
    //Ifurile respective sunt pentru o animatie mai frumoasa a fantomelor. Daca fantoma e damaged atunci inversam directia lor *-1
    public void MoveTowards(Point go_to_point)
    {
        //System.out.println(HealthPoints);
        //double distance = Math.sqrt(Math.pow(go_to_point.x - pos.x, 2) + Math.pow(go_to_point.y - pos.y, 2));

        double directionX = (go_to_point.x - pos.x) / pos.distance(go_to_point);
        double directionY = (go_to_point.y - pos.y) / pos.distance(go_to_point);

        if(abs(directionX)>abs(directionY)) //vad care e mai predominanta (Chestie de detaliu ca sa fie mai frumoasa animatia)
        {
            if(directionX<0)
            {
                direction="left";
            }
            else {
                direction="right";
            }
        }
        else {
            if(directionY<0)
            {
                direction="up";
            }
            else {
                direction="down";
            }

        }
        if(damaged)
        {
            directionX*=-1; //Daca ii damaged, schimbam directia vectorului aka il impingem
            directionY*=-1;
        }


        pos.x += directionX * speed;
        pos.y += directionY * speed;

    }

    public boolean CanAttack(Rectangle HitBox_Victim) {
       return HitBox_Victim.intersects(HitBox);
    }
    public double distanceTo(Point other) {
        double dx = pos.x - other.getX();
        double dy = pos.y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    //Indreapta fantoma spre player
    //Este marcata ca lovita atunci cand intersecteaza securea pentru prima data,
    //Se demarcheaza atunci cand securea nu mai are contact cu ea
    @Override
    public void update() {
        collisionON=false;
        Victim_pos=gp.player.pos;

        MoveTowards(Victim_pos);


        SpriteCounter++;
        if(SpriteCounter>SpriteCap)
        {
            spriteNum++;
            spriteNum%=4;
            SpriteCounter=0;
        }
        try
        {
            if(CanAttack(gp.player.HitBox))
            {
                //System.out.println("I can attack!");
                attack(gp.player);
            }

            if(gp.player.Rhaast.Hitbox.intersects(HitBox) && gp.player.Rhaast.isIn_use())      //Daca e lovit de secure && a fost aruncat (Rhaast ramane la pozitia playerului doar ca nu mai e desenat / updated)
            {
                if(!damaged)        //Daca nu o fost lovit pana acum
                {                   //Scade-i viata fantomei
                    if(decreaseHealthBy(gp.player.Rhaast.damage))   //functia returneaza true daca e mort, ii scade hpul
                    {
                        gp.IncreaseCurrentScore(10+(gp.difficulty<<1));
                        restoreDefaultVals();
                        gp.player.IncreaseTransformation(10*gp.difficulty, spriteNum%2==0?Player.form.RHAAST: Player.form.SHADOW_KAYN);
                    }

                    //Si marcheaz-o ca lovita
                    damaged=true;
                }
            }
            else {
                damaged=false;
            }
        }catch (Exception e)
        {e.printStackTrace();
            }

        HitBox.y=pos.y;
        HitBox.x=pos.x+gp.Tile_size/4;
    }

    public void attack(Player player) {//Attack and teleport
        player.decreaseHealthBy(10);
        restoreDefaultVals();

    }

    public void restoreDefaultVals()
    {
        Random random=new Random();
        pos.x=random.nextInt()%2==0?-100:gp.ScreenWidth+100;
        pos.y=random.nextInt()%2==0?-100:gp.ScreenHeight+100;;
        HitBox.x=pos.x+gp.Tile_size/4;
        HitBox.y=pos.y;
        damaged=false;
        HealthPoints=100*gp.difficulty;
    }
}
