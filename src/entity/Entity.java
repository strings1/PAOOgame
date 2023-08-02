package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

//Nimic special decat definirea unei entitati,
//Are viata, 4 directii a cate 4 frameuri fiecare
abstract public class Entity {
    public Point pos;
    protected int HealthPoints;
    public int speed;
    public Rectangle HitBox;
    public boolean collisionON=false;
    public BufferedImage up1, up2, up3,up4, //back?.png
                         down1, down2, down3, down4, //face?.png
                         left1, left2, left3, left4,
                         right1, right2, right3, right4;

    public String direction="down";
    public int SpriteCounter=0;
    public int spriteNum=0;
    public int SpriteCap=6;

    public Entity()
    {
        pos=new Point(400,400);
        speed=4;
        HealthPoints=100;
    }

    public boolean decreaseHealthBy(int x)
    {
        HealthPoints-=x;
        return HealthPoints<=0;
    }

    abstract public void draw(Graphics2D g2);

    abstract public void update();

    public boolean isAlive()
    {
        return HealthPoints>0;
    }
}
