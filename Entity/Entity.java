package Totorial.RPG.Entity;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Obj.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public Random random = new Random();
    public int actionNpc = 0;
    public int worldX, worldY;
    public int speed = 0;
    public BufferedImage dinoLeftUpLeft;
    public BufferedImage dinoLeftUpRight;
    public BufferedImage dinoRightUpRight;
    public BufferedImage dinoRightUpLeft;
    public BufferedImage npcUp1;
    public BufferedImage npcUp2;
    public BufferedImage npcDown1;
    public BufferedImage npcDown2;
    public BufferedImage npcLeft1;
    public BufferedImage npcLeft2;
    public BufferedImage npcRight1;
    public BufferedImage npcRight2;
    public BufferedImage npcUpTransparent1;
    public BufferedImage npcUpTransparent2;
    public BufferedImage npcDownTransparent1;
    public BufferedImage npcDownTransparent2;
    public BufferedImage npcLeftTransparent1;
    public BufferedImage npcLeftTransparent2;
    public BufferedImage npcRightTransparent1;
    public BufferedImage npcRightTransparent2;
    public BufferedImage merchant1;
    public BufferedImage merchant2;
    public String playerDirection;
    public String npcDirection;
    public BufferedImage image = null;
    public int spriteCounter = 1;
    public int spriteNum = 1;
    public Rectangle solid = new Rectangle(10, 16, 22, 22);
    public int solidDefaultX, solidDefaultY;
    public int dialogueIndex = 0;
    public int dialogueSet = 0;
    public String[][] questions = new String[10][10];
    public String[][][] answers = new String[10][10][5];
    public boolean[][][] isCorrect = new boolean[10][10][5];
    public boolean talked = false;
    public boolean collision = false;
    public String name;
    Coin coin;
    KeyObj keyObj;
    Lantern lantern;
    CalcObj calcObj;
    Mana mana;
    Diamond diamond;

    public Entity(GamePanel gp) {
        this.gp = gp;
        coin = new Coin(gp);
        keyObj = new KeyObj(gp);
        lantern = new Lantern(gp);
        calcObj = new CalcObj(gp);
        mana = new Mana(gp);
        diamond = new Diamond(gp);
    }

    public void setAction() {
        actionNpc++;
        if (actionNpc >= 60) {
            int i = random.nextInt(4);
            switch (i) {
                case 0 -> npcDirection = "up";
                case 1 -> npcDirection = "down";
                case 2 -> npcDirection = "left";
                case 3 -> npcDirection = "right";
            }
            actionNpc = 0;
        }
    }

    void spriteCount() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (gp.state == gp.dialogueStateWithVillagers
                || gp.state == gp.dialogueStateWithMerchant){
            spriteCounter = 0;
            spriteNum = 1;
            image = dinoLeftUpRight;
        }
    }

    void charactersImagesRenders(Graphics2D g2d, BufferedImage image) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2d.drawImage(image, screenX, screenY, gp.size, gp.size, null);
    }

    void update() {
    }

    public void draw(Graphics2D g2d, GamePanel gp) {
    }
}
