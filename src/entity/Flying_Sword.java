package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Flying_Sword extends Entity{
    GamePanel gp;
    boolean active=false;
    int damage;
    int width;
    int height;
    double acceleration=0;

    private Flying_Sword(GamePanel gp, String direction, int x, int y,int width, int height) {
        super();
        if(x==-1)//daca e optiunea -1 atunci sabia incepe dintr un colt al camerei
        {
            x=(direction=="right")?-width/4: gp.ScreenWidth-width;
        }
        active=true;
        damage=30;
        speed=4;
        this.gp=gp;
        pos=new Point(x,y);
        this.width=width;
        this.height=height;
        HitBox=new Rectangle(x,y,width,height);
        SpriteCap=5;
        this.direction=direction; // Directia in care se duce
        getFrame();
    }

    protected static Flying_Sword create(GamePanel gp,String direction, int x, int y,int width, int height)
    {
        return new Flying_Sword(gp,direction,x,y,width,height);
    }

    @Override
    public boolean decreaseHealthBy(int x) {
        return false;
    }

    public void getFrame() //Default Frames
    {
        try
        {
            left1= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_left1.png"));
            right2= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/FlyingSword_right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        try{
            BufferedImage image = null;

                if(acceleration>4)
                {
                    switch (spriteNum)
                    {
                        case 0,1->
                        {
                            image=(direction=="right")?right2:left2;
                        }
                        case 2->
                        {
                            image=(direction=="right")?right3:left3;
                        }
                        case 3->
                        {
                            image=(direction=="right")?right4:left4;
                        }
                        default->
                        {
                            image=(direction=="right")?right3:left3;
                        }
                    }
                }
                else {
                    spriteNum=0;
                    image=(direction=="right")?right1:left1;
                }
                SpriteCounter++;
                if(SpriteCounter>SpriteCap)
                {
                    spriteNum++;
                    spriteNum%=4;
                    SpriteCounter=0;
                }

            g2.drawImage(image, pos.x, pos.y, width, height, null);
        }catch(Exception E)
        {
            E.printStackTrace();
        }

    }

    @Override
    public void update() {
        if(direction=="right")
        {
            pos.x+=speed*acceleration;
        }
        else
        {
            pos.x-=speed*acceleration;
        }
        if(HitBox.intersects(gp.player.HitBox)&& active)
        {
            gp.player.decreaseHealthBy(damage*gp.difficulty);
            active=false;
        }
        HitBox.x=pos.x;
        acceleration+=0.1;


    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }
}
