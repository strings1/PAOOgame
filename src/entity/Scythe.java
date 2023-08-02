package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Scythe extends Weapon{
    private Point come_back_point;
    private double threshhold;
    private long timeOfEmpowerment;
    private long EmpowerDuration;
    private Point go_to_point;
    private Entity Owner;
    private  boolean in_use;
    private boolean goingToPoint;
    private int width;
    private int height;
    private int offset_w;
    private int offset_h;
    public boolean isIn_use() {
        return in_use;
    }


    public Scythe(Entity Owner, int speed, int cd, int dmg, int nr_frames,int width_oftile,int height_oftile)
    {
        super(speed,cd,dmg,nr_frames);
        acceleration=3;
        //come_back_point=new Point();
        in_use=false;
        threshhold=50;
        this.Owner=Owner;
        pos=new Point(-100,-100);
        go_to_point=pos;
        come_back_point=pos;
        this.width=width_oftile>>1;
        this.height=height_oftile>>1;
        offset_h=height_oftile>>2;
        offset_w=width_oftile>>2;

        Hitbox=new Rectangle(-100,-100,width,height);
    }



    public void AdjustPositionBy(int x, int y) //Cand playerul schimba cadranele, mutam securea cu un cadran
    {
        pos.x+=x;
        pos.y+=y;
        go_to_point.x+=x;
        go_to_point.y+=y;
    }
    public Point getUserPoint(Entity p)
    {
        return p.pos;
    }

    @Override
    public void setFrames() {
        try {
            super.frames[0]= ImageIO.read(new FileInputStream("res/Player/Scythe.png"));
            super.frames[1]= ImageIO.read(new FileInputStream("res/Player/Scythe1.png"));
            super.frames[2]= ImageIO.read(new FileInputStream("res/Player/Scythe2.png"));
            super.frames[3]= ImageIO.read(new FileInputStream("res/Player/Scythe3.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }

    @Override
    public void use(Point WhereToGo) {
        if(!in_use)
        {
            in_use=true;
            goingToPoint=true;
            go_to_point=WhereToGo;
            //come_back_point=getUserPoint(Owner);
            pos=new Point(Owner.pos);
        }

    }

    @Override
    public void update() {
        if(System.currentTimeMillis()<EmpowerDuration+timeOfEmpowerment)
        {
            damage=50;
        }
        else
        {
            damage=10;
        }

            if(in_use)
            {
                changeFrame();
                if(goingToPoint)
                    //acceleration/=acceleration>0.9?1.02:1;
                    acceleration/=1.02;
                else
                    acceleration*=acceleration<3?1.04:1;
                //acceleration=acceleration==0.5?1:acceleration;

                if(acceleration<0.7) //RANGe
                    goingToPoint=false;

                if (goingToPoint) {
                    this.MoveTowards(go_to_point);
                    if (this.distanceTo(go_to_point)<threshhold) {
                        goingToPoint = false;
                    }
                } else
                {
                    this.MoveTowards(Owner.pos);
                    if (this.distanceTo(Owner.pos)<threshhold) {
                        in_use=false;
                        acceleration=3;
                    }
                }

                Hitbox.x=pos.x+offset_w;
                Hitbox.y=pos.y+offset_h;
            }

    }

    @Override
    public void changeFrame() {
        if(System.currentTimeMillis()-FrameCooldown/(acceleration*1.5)>LastFrameChange)
        {
            current_frame++;
            current_frame%=frames.length;
            LastFrameChange=System.currentTimeMillis();
            size_x=96;
            size_y=96;
        }

    }

    @Override
    public void draw(Graphics2D g2)
    {
        //g2.setColor(Color.red);
        //g2.fillRect(Hitbox.x, Hitbox.y, Hitbox.width, Hitbox.height);  //DECOMENTEAZA-MA PENTRU A VEDEA HITBOXUL
        g2.drawImage(frames[current_frame], pos.x, pos.y, size_x, size_y, null);
    }

    void empower(long time)
    {
        timeOfEmpowerment=System.currentTimeMillis();
        EmpowerDuration=time;
    }
}
