package Totorial.RPG;

import javax.swing.*;

public class Main {
    //хуйня мой проиект знаю.

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePanel gp = new GamePanel();
        frame.setResizable(false);
        frame.add(gp);
        frame.pack();
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gp.setupGame();
        gp.startGame();
    }
}