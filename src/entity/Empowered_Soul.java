package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;

public class Empowered_Soul extends LostSoul {
    protected Empowered_Soul(GamePanel gp)
    {
        super(gp);
        damage+=15;
        speed+=2;
        getFrame();
    }

    public void getFrame() //Default Frames
    {
        try
        {
            up1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_up1.png"));
            up2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_up2.png"));
            up3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_up3.png"));
            up4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_up4.png"));

            down1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_down1.png"));
            down2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_down2.png"));
            down3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_down3.png"));
            down4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_down4.png"));

            left1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_left1.png"));
            left2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_left2.png"));
            left3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_left3.png"));
            left4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_left4.png"));

            right1= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_right1.png"));
            right2= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_right2.png"));
            right3= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_right3.png"));
            right4= ImageIO.read(new FileInputStream("res/Enemy/LostSoul/Emp_LostSoul_right4.png"));


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //O fantoma care este shaky, o face mai greu de lovit si da mai mult damage
    @Override
    public void update()
    {
        super.update();
        if(direction=="up" || direction =="down")
        {
            pos.x+=(spriteNum%2==0)?4:-8;
        }
    }

    protected static Empowered_Soul create(GamePanel gp)
    {
        return new Empowered_Soul(gp);
    }
}
