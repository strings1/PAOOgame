package entity;

import main.GamePanel;

import java.util.Random;

public class EnemyFactory {
    public static Entity createEnemy(String EntityName, GamePanel gp)
    {
        switch (EntityName)
        {
            case "LostSoul"->
            {
                return LostSoul.create(gp);
            }

            case "Emp_LostSoul"->
            {
                return Empowered_Soul.create(gp);
            }
            case "AatroxAOE"->
            {
                //
                ;
            }
            case "FlyingSword"->
            {
                Random random=new Random();

                return Flying_Sword.create(gp,random.nextInt()%2==0?"right":"left",-1,random.nextInt()%2==0?200:(gp.ScreenHeight- 64*4),32*4,64*4);
            }
            default ->
            {
                break;
            }
        }
        return null;

    }
}
