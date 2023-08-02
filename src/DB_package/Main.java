package DB_package;

public class Main {
    public static void main(String[] args) {
        // Create an instance of DB_Stuff.Database_Manager
        Database_Manager dbManager = new Database_Manager();

        // Save an instance to the database
        dbManager.saveInstance(10, 20, 800, 600, "Player Form 1", 1, 100);

        // Save another instance to the database
        dbManager.saveInstance(50, 70, 1024, 768, "Player Form 2", 2, 200);

        // Print the database contents
        dbManager.printDB();

        // Close the database connection
        dbManager.close();
    }
}
