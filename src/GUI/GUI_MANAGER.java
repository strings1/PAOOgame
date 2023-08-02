package GUI;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;


//GUI_MANAGER este o multitudine de scene -> o matrice (teoretic) de Obiecte pentru UserInterface
public class GUI_MANAGER {
    public ArrayList<Scene> GUI_SCENES;
    public int NUMBER_OF_SCENES=5;
    GamePanel container;
    public GUI_MANAGER(GamePanel gp)
    {
        GUI_SCENES =new ArrayList<Scene>();
        for (int i = 0; i <NUMBER_OF_SCENES ; i++) {
            GUI_SCENES.add(new Scene());
        }
        container=gp;
        getGUI();
    }

    public void draw(Graphics2D g2,int scene)
    {
        GUI_SCENES.get(scene).draw(g2,container.getMousePosition(),container.MouseH.click_stanga);
    }

    //Functia adauga un obiect in scena potrivita cu conditia sa fie unic
    //GetObjects cauta "name" in scena X, daca nu exista poate fi adaugat
    public void add(InterfaceObject obj)
    {
        try
        {   //Preia scena numarul x si incearca sa adauge in scena obiectul 'obj' daca nu exista

            if(getObject(obj.name,obj.scene_number)==null)
                GUI_SCENES.get(obj.scene_number).SCENE_OBJECTS.add(obj);
        }
        catch(Exception e)  //Daca obiectul e dat cu un numar de scene mai mare decat cel asteptat (Caz de eroare [IndexOutOfBounds]
        {
            e.printStackTrace();
            GUI_SCENES.add(new Scene());            //Creeam o noua scena
            obj.scene_number=GUI_SCENES.size()-1;   //Setam numarul scenei obiect ca facand parte din ultima scena
            NUMBER_OF_SCENES++;

        }

    }
//Scoate un obiect dintr-o scena;
    public void RemoveObject(String name, int from_scene)
    {
        try
        {
            for (int i = 0; i < GUI_SCENES.get(from_scene).SCENE_OBJECTS.size() ; i++) {
                if(GUI_SCENES.get(from_scene).SCENE_OBJECTS.get(i).name==name)
                {
                    GUI_SCENES.get(from_scene).SCENE_OBJECTS.remove(i);
                    return;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Functia cauta un obiect intr-o scena folosind numele lui. Intoarce obiectul gasit sau null in caz de nu a gasit nimic
    public InterfaceObject getObject(String name, int from_scene)
    {
        try
        {
            for (int i = 0; i < GUI_SCENES.get(from_scene).SCENE_OBJECTS.size() ; i++) {
                if(GUI_SCENES.get(from_scene).SCENE_OBJECTS.get(i).name==name)
                {
                    return GUI_SCENES.get(from_scene).SCENE_OBJECTS.get(i);
                }
            }
            //System.out.println("Couldn't find "+name+" Object");
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    //Seteaza UserInterfaceul pentru fiecare scena
    public void getGUI()
    {
        //Scena jocului
        add(new InterfaceObject(1,0,0,1000,800,4,4,"Vision","res/Player/Vision/Vision_difficulty1.png"));
        add(new InterfaceObject(1,0,0,64,32,4,4,"Health And Coins","res/Player/Health_bar_0.png"));
        add(new InterfaceObject(1,0,0,64,16,4,4,"Health","res/Player/Health_bar_1.png"));
        add(new InterfaceObject(1,1280,0,32,32,4,4,"Ability1","res/Player/Abilities/Kayn_Ability1_0.png"));
        add(new InterfaceObject(1,1408,0,32,32,4,4,"Ability2","res/Player/Abilities/Kayn_Ability2_0.png"));


        //0 - meniu principal
        //1 - game
        //2 - Settings
        //3 - Death Screen
        //4 - Win Screen
        add(new InterfaceObject(0,0,0,1536,864,1,1,"Background","res/Sprites/bkg.png"));
        add(new InterfaceObject(2,0,0,16,9,96,96,"Menu_Bkg","res/Sprites/menu_bkg.png"));
        add(new InterfaceObject(3,0,0,16,9,96,96,"Death_Bkg","res/Sprites/DeathScreen.png"));
        add(new InterfaceObject(4,0,0,16,9,96,96,"Win_Bkg","res/Sprites/WinScreen.png"));

        //Scena Meniului principal
        try
        {

            add(new Button(0,1536/2-32*4,864-200,64,32,4,4,"PlayButton",
                    "res/Sprites/Buttons/PLAY_0.png","res/Sprites/Buttons/PLAY_1.png","res/Sprites/Buttons/PLAY_2.png"));

            add(new Button(2,20,20,32,32,4,4,"back_arrow",
                    "res/Sprites/Buttons/back-arrow1.png","res/Sprites/Buttons/back-arrow2.png","res/Sprites/Buttons/back-arrow3.png"));

            add(new Button(2,1536/2-32*8,864-200,32,32,4,4,"Load",
                    "res/Sprites/Buttons/Load1.png","res/Sprites/Buttons/Load2.png","res/Sprites/Buttons/Load3.png"));

            add(new Button(2,1536/2-32*4,864-200,32,32,4,4,"Level1",
                    "res/Sprites/Buttons/Level1_1.png","res/Sprites/Buttons/Level1_2.png","res/Sprites/Buttons/Level1_3.png"));

            add(new Button(2,1536/2,864-200,32,32,4,4,"Level2",
                    "res/Sprites/Buttons/Level2_1.png","res/Sprites/Buttons/Level2_2.png","res/Sprites/Buttons/Level2_3.png"));

            add(new Button(2,1536/2+32*4,864-200,32,32,4,4,"Level3",
                    "res/Sprites/Buttons/Level3_1.png","res/Sprites/Buttons/Level3_2.png","res/Sprites/Buttons/Level3_3.png"));

            add(new Button(3,1536/2-32*2,864-200,32,32,4,4,"Retry",
                    "res/Sprites/Buttons/Retry1.png","res/Sprites/Buttons/Retry2.png","res/Sprites/Buttons/Retry3.png"));

            add(new Button(4,1536/2-32*2,864-200,32,32,4,4,"Next",
                    "res/Sprites/Buttons/next1.png","res/Sprites/Buttons/next2.png","res/Sprites/Buttons/next3.png"));



            add(new Button(0,1536/2-32*8,864-200,32,32,4,4,"Settings",
                    "res/Sprites/Buttons/Settings1.png","res/Sprites/Buttons/Settings2.png","res/Sprites/Buttons/Settings3.png"));

            add(new Button(0,1536/2+32*4,864-200,32,32,4,4,"Easy",
                    "res/Sprites/Buttons/easy.png","res/Sprites/Buttons/h_easy.png","res/Sprites/Buttons/p_easy.png"));
            add(new Button(0,1536/2+32*4-10000,864-200,32,32,4,4,"Medium",
                    "res/Sprites/Buttons/pixmedium.png","res/Sprites/Buttons/h_pixmedium.png","res/Sprites/Buttons/p_pixmedium.png"));
            add(new Button(0,1536/2+32*4-10000,864-200,32,32,4,4,"Hard",
                    "res/Sprites/Buttons/pixdemon.png","res/Sprites/Buttons/h_pixdemon.png","res/Sprites/Buttons/p_pixdemon.png"));


        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
