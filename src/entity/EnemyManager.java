package entity;

import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {
    GamePanel gp;
    ArrayList<Entity> Enemy_Array;
    public EnemyManager(GamePanel gp)
    {
        this.gp=gp;
        Enemy_Array=new ArrayList<Entity>();
        getEnemies();
    }

    public void update()
    {
        for (int i = 0; i < gp.level*2 ; i++) {
            Enemy_Array.get(i).update();
        }
    }

    public void draw(Graphics2D g2)
    {
        for (int i = 0; i < gp.level*2 ; i++) {
            Enemy_Array.get(i).draw(g2);
        }
    }
    public void getEnemies()
    {
        Enemy_Array.add((LostSoul) EnemyFactory.createEnemy("LostSoul", gp));
        Enemy_Array.add((LostSoul) EnemyFactory.createEnemy("LostSoul", gp));
        Enemy_Array.add((Empowered_Soul) EnemyFactory.createEnemy("Emp_LostSoul", gp));
        Enemy_Array.add((Empowered_Soul) EnemyFactory.createEnemy("Emp_LostSoul", gp));
        Enemy_Array.add((LostSoul) EnemyFactory.createEnemy("LostSoul", gp));
        Enemy_Array.add((Empowered_Soul) EnemyFactory.createEnemy("Emp_LostSoul", gp));
    }
}
