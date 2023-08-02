package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ca sa inchidem jocul properly de pe " X "
        window.setResizable(false);
        window.setTitle("The Ruined One");
        System.out.println("HELLO!");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();


        window.setLocationRelativeTo(null); //O sa fie in centru
        window.setVisible(true);
        gamePanel.StartGameThread();
    }
}