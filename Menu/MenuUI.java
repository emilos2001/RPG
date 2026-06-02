package Totorial.RPG.Menu;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MenuUI{
    public int x = 230;
    public int y = 350;
    public final int BUTTON_Y = 350;
    public final int[] BUTTON_X = {230, 430};
    public int currentSlot = 0;
    GamePanel gp;
    BufferedImage create, join;
    Graphics2D g2d;

    public MenuUI(GamePanel gp) {
        this.gp = gp;
        loadImages();

    }
    public String pinGame() {
        char[] c;
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "1234567890";
        String combination = upperCase + numbers;
        int length = 5;
        Random random = new Random();
        c = new char[length];
        for (int i = 0; i < length; i++) {
            c[i] = combination.charAt(random.nextInt(combination.length()));
        }
        return String.valueOf(c);
    }

    public String currentPin = pinGame();

    private void loadImages() {
        try {
            join = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("join.png")));
            create = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("create.png")));
        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading menu images!");
            e.printStackTrace();
        }
    }

    public void update() {
        if (gp.state == gp.menuState){
            if (gp.keys.left) {
                currentSlot--;
                if (currentSlot < 0) currentSlot = 0;
                gp.keys.left = false;
            }
            if (gp.keys.right) {
                currentSlot++;
                if (currentSlot > 1) currentSlot = 1;
                gp.keys.right = false;
            }
            if (gp.keys.join) {
                if (currentSlot == 0) {
                    gp.keys.joinInGame();
                } else if (currentSlot == 1) {
                    gp.keys.createGame();
                }
            }
            x = BUTTON_X[currentSlot];

        }
    }

    public void cursor(int x, int y, int width, int height) {
        if (gp.state == gp.menuState) {
            g2d.setColor(new Color(26, 26, 0));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRoundRect(x, y, width, height, 25, 25);
        }
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;
        if (gp.state == gp.menuState) {
            joinInAGame();
            createGame();
            titleForStartGame();
            cursor(x, y, 100, 100);
        }
    }

    private void titleOfMenu(String tittle, int i, int x) {
        if (gp.state == gp.menuState) {
            g2d.setColor(new Color(20, 82, 20, 190));
            g2d.setFont(new Font("Sans-serif", Font.BOLD, 15));
            g2d.fillRoundRect(BUTTON_X[i], BUTTON_Y, 100, 100, 25, 25);
            g2d.setColor(Color.white);
            g2d.drawString(tittle, BUTTON_X[i] + x, BUTTON_Y + 125);
        }
    }

    public void joinInAGame() {
        titleOfMenu("Join in a game", 0, 0);
        if (join != null) {
            g2d.drawImage(join, 250, 365, 60, 60, null);
        }
    }

    public void createGame() {
        titleOfMenu("Create a game", 1, 1);
        if (create != null) {
            g2d.drawImage(create, 450, 365, 60, 60, null);
        }
    }

    public void titleForStartGame(){
        if (Keys.verifyChanges) {
            g2d.setColor(Color.white);
            g2d.setFont(new Font("Sans-serif", Font.BOLD, 25));
            g2d.drawString("PRESS ESC TO START", 250, 320);
        }
    }
}