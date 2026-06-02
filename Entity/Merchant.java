package Totorial.RPG.Entity;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;
import Totorial.RPG.Obj.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

public class Merchant extends Entity {
    Keys keys;
    KeyObj keyObj;
    Diamond diamond;
    Lantern lantern;
    CalcObj calcObj;
    Coin coin;
    Mana mana;
    public HashMap<BufferedImage, Integer> items = new HashMap<>();
    public Merchant(GamePanel gp) {
        super(gp);
        name = "MERCHANT";
        npcDirection = "right";
        speed = 1;
        keyObj = new KeyObj(gp);
        diamond = new Diamond(gp);
        lantern = new Lantern(gp);
        calcObj = new CalcObj(gp);
        coin = new Coin(gp);
        mana = new Mana(gp);
        keys = new Keys(gp);
        getMerchantImages();
        items();
    }

    public void update() {
        collision = false;
        setAction();
        gp.collisions.checkTileForNpcs(this);
    }

    public void getMerchantImages() {
        try {
            merchant1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("merchant_down_1.png")));
            merchant2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("merchant_down_2.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void items() {
        items.put(keyObj.image, items.getOrDefault(keyObj.image, 0)+ 1);
        items.put(diamond.image, items.getOrDefault(diamond.image, 0)+1);
        items.put(coin.image, items.getOrDefault(coin.image, 0)+1);
        items.put(mana.image, items.getOrDefault(mana.image, 0)+1);
        items.put(calcObj.image,  items.getOrDefault(calcObj.image, 0)+1);
        items.put(lantern.image,  items.getOrDefault(lantern.image, 0)+1);
    }

    void merchantMovement() {
        if (spriteNum == 1) {
            image = merchant1;
        } else if (spriteNum == 2) {
            image = merchant2;
        }
    }

    void merchantWhileDialogue() {
        spriteCounter = 0;
        merchantMovement();
    }


    public void draw(Graphics2D g2d, GamePanel gp) {
        merchantMovement();
        update();
        if (!collision) {
            switch (npcDirection) {
                case "up" -> {
                    if (spriteNum == 1) {
                        worldY -= speed;
                    } else if (spriteNum == 2) {
                        worldY -= speed;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        worldY += speed;
                    } else if (spriteNum == 2) {
                        worldY += speed;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        worldX -= speed;
                    } else if (spriteNum == 2) {
                        worldX -= speed;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        worldY += speed;
                    }
                }
            }
            spriteCount();
        }
        if (gp.state == gp.dialogueStateWithMerchant) {
            merchantWhileDialogue();
        }

        charactersImagesRenders(g2d, image);
    }
}