package DB_package;

import entity.Player;
import main.GamePanel;

import java.sql.Connection;
import java.sql.Statement;

public class saveInstance implements DB_Command{
    private GamePanel gamePanel;
    Database_Manager On_DB;
    public saveInstance(GamePanel gamePanel, Database_Manager DB) {
        this.gamePanel = gamePanel;
        On_DB=DB;
    }
    @Override
    public void execute() throws NoDatabaseFoundException {

        if(On_DB!=null)
        {
            int x = gamePanel.player.pos.x;
            int y = gamePanel.player.pos.y;
            int cadranX = gamePanel.CadranX;
            int cadrany = gamePanel.CadranY;
            String transformation = (gamePanel.player.currentForm== Player.form.NORMAL)?"Normal":((gamePanel.player.currentForm == Player.form.RHAAST) ? "Rhaast" : "Shadow_Kayn");
            int level = gamePanel.level;
            int score = gamePanel.Current_Score;
            On_DB.saveInstance(x, y, cadranX, cadrany, transformation, level, score);
        }
        else throw new NoDatabaseFoundException("Nu am gasit baza de date");


    }
}
