package Totorial.RPG.Menu;

import Totorial.RPG.Entity.Entity;
import Totorial.RPG.GamePanel;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MenuUI {
    public int x = 30;
    public int y = 350;
    public String mess = "PRESS ENTER TO OPEN MENU";
    public String alert = "YOU ARE THE GUEST";
    public boolean isMenu = true;
    public boolean isLogged = false;

    GamePanel gp;
    BufferedImage create;
    BufferedImage join;
    BufferedImage signIn;
    BufferedImage signUp;
    Graphics2D g2d;
    Font font = new Font("Sans-serif", Font.BOLD, 12);


    public MenuUI(GamePanel gp) {
        this.gp  = gp;
    }
    Color standard =  new Color(20, 82, 20,  190);

    public void logged() {
        g2d.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        g2d.setColor(Color.white);
        if (!isLogged) {
            g2d.drawString("GUEST MODE", gp.screenWidth - 150, 30);
        } else {
            g2d.drawString("HELLO", gp.screenWidth - 150, 30);
        }
        if (x == 230) {
            if (!isLogged) {
                g2d.setFont(new Font("Arial", Font.BOLD, 18));
                g2d.drawString(alert, 200, 530);
            }
        }
    }

    public void cursor() {
        if (isMenu) {
            g2d.setColor(new Color(26, 26, 0));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(x, y, 100, 100, 50, 15);
        }
    }

    public void signIn(Color color) {
        if (isMenu) {
            g2d.setColor(color);
            g2d.fillRoundRect(430, 350, 100, 100, 25, 25);
            g2d.setFont(font);
            g2d.setColor(Color.white);
            try {
                signIn = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("signin.png")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            g2d.drawImage(signIn, 455, 365, 60, 60, null);
            g2d.drawString("JOIN IN YOUR ACCOUNT", 400, 480);
        }
    }

    public void signUp(Color color) {
        if (isMenu) {
            g2d.setColor(color);
            g2d.fillRoundRect(630, 350, 100, 100, 25, 25);
            g2d.setFont(font);
            g2d.setColor(Color.white);
            try {
                signUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("signup.png")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            g2d.drawImage(signUp, 645, 365, 60, 60, null);
            g2d.drawString("CREATE ACCOUNT", 600, 480);
        }
    }

    public void admin(Color color) {
        if (isMenu) {
            if (!isLogged) {
                g2d.setColor(new Color(20, 82, 20, 50));
            } else {
                g2d.setColor(color);
            }
            g2d.fillRoundRect(230, 350, 100, 100, 25, 25);
            g2d.setFont(font);
            g2d.setColor(Color.white);
            try {
                create = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("create.png")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            g2d.drawImage(create, 250, 365, 60, 60, null);
            g2d.drawString("CREATE YOUR OWN GAME", 220, 480);
        }
    }

    public void player(Color color) {
        if (isMenu) {
            g2d.setColor(color);
            g2d.fillRoundRect(30, 350, 100, 100, 25, 25);
            g2d.setFont(font);
            g2d.setColor(Color.white);
            try {
                join = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("join.png")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            g2d.drawImage(join, 50, 365, 60, 60, null);
            g2d.drawString("JOIN IN A GAME", 30, 480);
        }
    }

    public void draw(Graphics2D g2d) {
        this.g2d = g2d;
        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString(mess, gp.screenWidth - 280, gp.screenHeight - 140);
        player(standard);
        admin(standard);
        signIn(standard);
        signUp(standard);
        logged();
        cursor();
    }
}