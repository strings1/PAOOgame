package DB_package;

import entity.Player;
import main.GamePanel;

import java.sql.*;

public class Database_Manager {
    private Connection connection;
    private Statement statement;

    public Database_Manager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Saves.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            String sql = """
                    CREATE TABLE IF NOT EXISTS "SAVES" (
                    	"PLAYER_POSITION_X"	                INTEGER,
                    	"PLAYER_POSITION_Y"	                INTEGER,
                    	"PANEL_X"	        INTEGER,
                    	"PANEL_Y"	        INTEGER,
                    	"PLAYER_FORM"	TEXT,
                    	"LEVEL"	            INTEGER,
                    	"SCORE"         	INTEGER)
                    """.stripIndent();
            statement.execute(sql);
            System.out.println("Started database successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveInstance(int playerPosX, int playerPosY, int panelX, int panelY, String playerForm, int level, int score) {
        try {
            String sql = "INSERT INTO SAVES (PLAYER_POSITION_X, PLAYER_POSITION_Y, PANEL_X, PANEL_Y, PLAYER_FORM, LEVEL, SCORE) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playerPosX);
            preparedStatement.setInt(2, playerPosY);
            preparedStatement.setInt(3, panelX);
            preparedStatement.setInt(4, panelY);
            preparedStatement.setString(5, playerForm);
            preparedStatement.setInt(6, level);
            preparedStatement.setInt(7, score);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Instance saved successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save instance", e);
        }
    }

    protected void loadInstance(GamePanel gamePanel) {
        try {
            String sql = "SELECT * FROM SAVES ORDER BY ROWID DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int playerPosX = resultSet.getInt("PLAYER_POSITION_X");
                int playerPosY = resultSet.getInt("PLAYER_POSITION_Y");
                int panelX = resultSet.getInt("PANEL_X");
                int panelY = resultSet.getInt("PANEL_Y");
                String playerForm = resultSet.getString("PLAYER_FORM");
                int level = resultSet.getInt("LEVEL");
                int score = resultSet.getInt("SCORE");

                gamePanel.setPlayerPosition(playerPosX, playerPosY);
                gamePanel.CadranY=panelY;
                gamePanel.CadranX=panelX;
                gamePanel.level=level;
                System.out.println(playerForm);
                switch (playerForm)
                {
                    case "Rhaast"->
                    {
                        gamePanel.player.transformInto(Player.form.RHAAST);
                    }
                    case "Normal"->
                    {
                        gamePanel.player.transformInto(Player.form.NORMAL);
                    }
                    case "Shadow_Kayn" ->
                    {
                        gamePanel.player.transformInto(Player.form.SHADOW_KAYN);
                    }
                    default ->
                    {
                        gamePanel.player.transformInto(Player.form.NORMAL);
                    }
                }
                //gamePanel.setLevel(level);
                gamePanel.Current_Score=score;
                System.out.println("Instance loaded successfully");
            } else {
                System.out.println("No saved instances found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load instance", e);
        }
    }

    public void printDB() {
        try {
            String sql = "SELECT * FROM SAVES";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int playerPosX = resultSet.getInt("PLAYER_POSITION_X");
                int playerPosY = resultSet.getInt("PLAYER_POSITION_Y");
                int panelX = resultSet.getInt("PANEL_X");
                int panelY = resultSet.getInt("PANEL_Y");
                String playerForm = resultSet.getString("PLAYER_FORM");
                int level = resultSet.getInt("LEVEL");
                int score = resultSet.getInt("SCORE");

                System.out.println("Player Position: (" + playerPosX + ", " + playerPosY + ")");
                System.out.println("Panel Size: (" + panelX + ", " + panelY + ")");
                System.out.println("Player Form: " + playerForm);
                System.out.println("Level: " + level);
                System.out.println("Score: " + score);
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to print database", e);
        }
    }

    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }
}
