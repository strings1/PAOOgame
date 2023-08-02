package SuperObjects;

import jdk.jfr.Threshold;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Interactive_Object {
    public String name;
    public double extra_scale_x=1; //daca un tile e 32x32, usa noastra e 64 x 64
    public double extra_scale_y=1; //daca un tile e 32x32, usa noastra e 64 x 64
    public int x, y;
    public int CadranX, CadranY;
    boolean used=true;
    public Rectangle Hitbox;


    long lastTime;
    long threshold;
    long currentTime;


    public static enum Status{
        ACTIVE,
        INACTIVE
    }
    public enum type{
        Item,
        Solid
    }
    public Status state;
    public type obj_type;
    public boolean collision = false;

    public Interactive_Object(int x, int y, final String name, int CadX, int CadY)
    {
        lastTime=System.currentTimeMillis();
        currentTime=System.currentTimeMillis();
        threshold=1000; // 1 secunda ddd
        this.x=x;
        this.y=y;
        CadranX=CadX;
        CadranY=CadY;
        state=Status.INACTIVE;
        obj_type=type.Solid;
        Hitbox=new Rectangle(x,y,(int)extra_scale_x*96,(int)extra_scale_y*96);
        if(name!=null)
            this.name=name;
        else
            this.name="UNNAMED OBJECT";

    }
    abstract public void draw(Graphics2D g2);
    abstract public boolean use();
    abstract public boolean take();

}
