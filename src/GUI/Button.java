package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Button extends InterfaceObject {

    //Butoanele sunt niste Interface Object care contin 3 imagini in loc de una.
    //Acestea se schimba in functie de starea mausului comform variabilelor de mai jos
    //          NORMAL,     HOVERED,    PRESSED;
    BufferedImage image_hovered;
    BufferedImage image_pressed;
    BufferedImage image_normal;
    Rectangle Hitbox;
    boolean clicked=false;
    boolean LastMouseState;
    enum state{
        NORMAL,
        HOVERED,
        PRESSED,
    }
    state ButtonState=state.NORMAL;
    public Button(int scene_number, int x, int y, int width, int height, double extra_scale_x, double extra_scale_y, String name, String path_normal, String path_hovered, String path_press)
    {
        super(scene_number,  x,  y,  width,  height, extra_scale_x, extra_scale_y,  name, path_normal);
        try
        {
            image_hovered=ImageIO.read(new FileInputStream(path_hovered));
            image_pressed=ImageIO.read(new FileInputStream(path_press));
            image_normal=ImageIO.read(new FileInputStream(path_normal));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Hitbox=new Rectangle(x,y,(int)extra_scale_x*width,(int)extra_scale_y*height);
    }

    //ObserveMouse priveste coordonatele unui "Cursor" si daca este apasat sau nu.
    //Schimba stateurile in functie de starea mausului
    public void ObserveMouse(Point MousePosition, boolean MouseClick)
    {
        try
        {
            if(Hitbox.contains(MousePosition))
            {
                if(MouseClick)
                {
                    ButtonState=state.PRESSED;
                }
                else
                {
                    ButtonState=state.HOVERED;
                }
            }
            else
            {
                if(ButtonState!=state.NORMAL)
                {
                    ButtonState=state.NORMAL;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Functia de update se foloseste de ObserveMouse pentru a deduce starea butonului
    //Cu ajutorul "LastMouseState" putem deduce daca s-a facut un click sau nu, daca s-a facut click atunci setam clicked=true;
    public void Update(Point MousePosition, boolean MouseClick)
    {
        ObserveMouse(MousePosition,MouseClick);
        switch (ButtonState)
        {
            case NORMAL -> {
                image=image_normal;
            }
            case HOVERED -> {
                image=image_hovered;
            }
            case PRESSED ->
            {
                image=image_pressed;
            }
            default -> {
                //SHOULD NOT HAPPEN!
                image=image_normal;
            }
        }

            if (LastMouseState == true && MouseClick == false && Hitbox.contains(MousePosition))   //Daca inainte am apasat si acum nu mai apas
            {
                clicked = true;
            }
            else {
                clicked = false;
            }

        LastMouseState=MouseClick;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void changeX(int x){
        this.x=x;
        Hitbox.x=x;
    }

    public void changeXby(int x){
        this.x+=x;
        Hitbox.x+=x;
    }

    public void changeY(int y){
        this.y=y;
        Hitbox.y=y;
    }

    public void changeYby(int y){
        this.y+=y;
        Hitbox.y+=y;
    }
}
