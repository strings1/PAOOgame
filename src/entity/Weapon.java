package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class Weapon {
    public double speed;
    public int size_x;
    public int size_y;
    public double acceleration;
    public int cooldown_in_milliseconds;
    public long LastFrameChange;
    public long FrameCooldown;
    public int damage;

    public Rectangle Hitbox;
    public Point pos;
    public BufferedImage[] frames;
    public int current_frame;

    Weapon(int s, int cd, int dmg, int nr_frames)
    {
        size_x=32*3;
        size_y=32*3;
        speed=s;
        cooldown_in_milliseconds=cd;
        LastFrameChange=System.currentTimeMillis();
        FrameCooldown=90; //
        damage=dmg;
        acceleration=1;
        current_frame=0;
        pos=new Point(200,200);
        frames=new BufferedImage[nr_frames];
        setFrames();
        Hitbox=new Rectangle(pos.x,pos.y,96,96);
    }

    public abstract void use(Point WhereToGo);
    public abstract void changeFrame();
    public abstract void update();
    public abstract void draw(Graphics2D g2);


    public abstract void setFrames();

    public void MoveTowards(Point go_to_point)
    {
        //double distance = Math.sqrt(Math.pow(go_to_point.x - pos.x, 2) + Math.pow(go_to_point.y - pos.y, 2));
        //double distance = go_to_point.distance(pos);

        //normalizam vectorul, si ca sa mearga cu o anumita viteza, il scalam
        double directionX = (go_to_point.x - pos.x) / go_to_point.distance(pos);
        double directionY = (go_to_point.y - pos.y) / go_to_point.distance(pos);

        pos.x += directionX * speed * acceleration;
        pos.y += directionY * speed * acceleration;
    }

    public double distanceTo(Point other) {
        //double dx = pos.x - other.getX();
        //double dy = pos.y - other.getY();
        //return Math.sqrt(dx * dx + dy * dy);
        return other.distance(pos);
    }
}
