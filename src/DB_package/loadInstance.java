package DB_package;

import entity.Player;
import main.GamePanel;

public class loadInstance implements DB_Command {
    private GamePanel gamePanel;
    Database_Manager On_DB;
    public loadInstance(GamePanel gamePanel, Database_Manager DB) {
        //System.out.println("LoadInst Constructor");
        this.gamePanel = gamePanel;
        On_DB=DB;
    }
    @Override
    public void execute() {
        On_DB.loadInstance(gamePanel);
    }
}
