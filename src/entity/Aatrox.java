package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Aatrox extends Entity{
    Flying_Sword Ability1;
    public int CadranX;
    boolean damaged=false;
    BufferedImage image;
    public int CadranY;
    GamePanel gp;
    int width;
    int height;
    public Aatrox(int x, int y, int w, int h,GamePanel gamePanel, int cx, int cy){
        HealthPoints=1000;
        CadranY=cy;
        CadranX=cx;

        gp=gamePanel;
        pos.x=x;
        pos.y=y;
        width=w;
        height=h;
        HitBox=new Rectangle(pos.x,pos.y,w,h);
        Ability1=(Flying_Sword) EnemyFactory.createEnemy("FlyingSword",gp);
        try
        {
            image= ImageIO.read(new FileInputStream("res/Enemy/Aatrox/Aatrox.png"));
        }
        catch(Exception e)
        {
            image=null;
            e.printStackTrace();
        }

    }
    @Override
    public boolean decreaseHealthBy(int x) {
        return super.decreaseHealthBy(x);
    }

    @Override
    public void draw(Graphics2D g2) {
        if(CadranX==gp.CadranX && CadranY== gp.CadranY)
        {
            try{
                Ability1.draw(g2);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //g2.fillRect(HitBox.x, HitBox.y, HitBox.width, HitBox.height); //DECOMENTEAZA-MA PENTRU A VEDEA HITBOXUL
            g2.setColor(Color.red);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
            String text = Integer.toString(HealthPoints);
            int text_x=this.pos.x+width;
            int text_y=this.pos.y+20;
            g2.drawString(text,text_x,text_y);
            g2.drawImage(image, pos.x, pos.y, width, height, null);
        }
        }


    @Override
    public void update() {
        if(CadranX==gp.CadranX && CadranY== gp.CadranY)
        {
            try
            {
                Ability1.update();
                if(Ability1.pos.x<-Ability1.width-100 || Ability1.pos.x>gp.ScreenWidth+Ability1.width+100)
                {
                    //Aici ar trb un randomizer
                    Ability1=(Flying_Sword) EnemyFactory.createEnemy("FlyingSword",gp);
                }

                if(gp.player.Rhaast.Hitbox.intersects(HitBox) && gp.player.Rhaast.isIn_use())      //Daca e lovit de secure && a fost aruncat (Rhaast ramane la pozitia playerului doar ca nu mai e desenat / updated)
                {
                    if(!damaged)        //Daca nu o fost lovit pana acum
                    {                   //Scade-i viata fantomei
                        if(decreaseHealthBy(gp.player.Rhaast.damage))   //functia returneaza true daca e mort, ii scade hpul
                        {
                            gp.IncreaseCurrentScore(10000);
                            pos.x=-10000;
                        }

                        //Si marcheaz-o ca lovita
                        damaged=true;
                    }
                }

                if(gp.player.HitBox.intersects(HitBox) && this.isAlive())
                {
                    gp.player.decreaseHealthBy(20);
                }
                else {
                    damaged=false;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }
}
