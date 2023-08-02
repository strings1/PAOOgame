package GUI;

import java.awt.*;
import java.util.ArrayList;

public class Scene {

    //O scena este o multitudine de Obiecte pentru UserInterface.
    public ArrayList<InterfaceObject> SCENE_OBJECTS;
    public Scene()
    {
        SCENE_OBJECTS=new ArrayList<InterfaceObject>();
    }



    public void draw(Graphics2D g2,Point MousePosition, boolean MouseState) {
        for (int i = 0; i < SCENE_OBJECTS.size(); i++) {
            if(SCENE_OBJECTS.get(i).getClass()== Button.class)
            {
                Button temp;
                temp=(Button)(SCENE_OBJECTS.get(i));
                temp.Update(MousePosition,MouseState);
            }
            SCENE_OBJECTS.get(i).draw(g2);
        }
    }

}
