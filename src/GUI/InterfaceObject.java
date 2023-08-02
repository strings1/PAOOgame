package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

//Obiect menit pentru User Interface
public class InterfaceObject implements GUI {
    int scene_number;
    public int x;
    public int y;

    public double extra_scale_x;
    public double extra_scale_y;
    public double width;
    public double height;
    public double base_width;
    public double base_height;
    public BufferedImage image;
    public String name;
    InterfaceObject()
    {
        extra_scale_x=1;
        extra_scale_y=1;
        scene_number=0;
        x=0;
        y=0;
        width=0;
        height=0;
        base_width=0;
        base_height=0;
        image=null;
        name="UNKNOWN OBJECT INTERFACE";
    }

    //Fiecare obiect apartine unei "Scene":

    //0 - meniu principal                             Pe langa parametrii Obvious de care are nevoie pentru a fi
    //1 - game                                       randat corect, este parametrul "name" ce ne ajuta la identificarea
    //2 - Settings                                   anumitor obiecte(spre exemplu la transformare). Functia specificata
    //3 - Death Screen                                  Apare in GuiManager
    //4 - Win Screen
    public InterfaceObject(int scene_number, int x, int y, int width, int height,double extra_scale_x,double extra_scale_y, String name, String path)
    {
        this.extra_scale_x=extra_scale_x;
        this.extra_scale_y=extra_scale_y;
        this.scene_number=scene_number;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        base_height=height;
        base_width=width;
        this.name=name;
        try
        {
            image= ImageIO.read(new FileInputStream(path));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            image=null;
        }
    }


    //Nimic special mai jos
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image,x,y,(int)(extra_scale_x*width),(int)(extra_scale_y*height),null);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void changeHeight(double X)
    {
        height=X;
    }
    public void changeWidth(double X)
    {
        width=X;
    }

    public void changeX(int x){
        this.x=x;
    }

    public void changeY(int y){
        this.y=y;
    }

    public void AddtoX(int x)
    {
        this.x+=x;
    }

    public void AddtoY(int y)
    {
        this.y+=y;
    }

    public void ChangeImage(String path)
    {
        BufferedImage temp;
        try{
            temp= ImageIO.read(new FileInputStream(path));
        }catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        image=temp;
    }

    public void ChangeImage(BufferedImage temp)
    {
        image=temp;
    }
}
