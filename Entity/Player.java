package Totorial.RPG.Entity;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;
import Totorial.RPG.Obj.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;


public class Player extends Entity {
    public final int screenX;
    public final int screenY;
    public int key = 16;
    public int keyBuy;
    public int diamond = 1;
    public int diamondBuy;
    public int coin = 1000;
    public boolean calculatorBuy;
    public boolean lanternBuy;
    public int mana = 1;
    public int index;
    Merchant merchant;
    Villager villager;
    GamePanel gp;
    Keys keys;
    BufferedImage image;

    public Player(GamePanel gp, Keys keys) {
        super(gp);
        this.gp = gp;
        this.keys = keys;
        screenX = 258;
        screenY = 122;
        merchant = new Merchant(gp);
        villager = new Villager(gp);
        solid = new Rectangle(10, 16, 32, 32);
        solidDefaultX = solid.x;
        solidDefaultY = solid.y;
        setDefaultValues();
        getPlayerImage();
        items();
    }

    public void items() {
        inventory.add(new KeyObj(gp));
        inventory.add(new Diamond(gp));
        inventory.add(new Lantern(gp));
        inventory.add(new CalcObj(gp));
        inventory.add(new Coin(gp));
        inventory.add(new Mana(gp));
    }

    public void getPlayerImage() {
        try {
            dinoLeftUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Dino-left-up-left.png")));
            dinoLeftUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Dino-left-up-right.png")));
            dinoRightUpLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Dino-right-up - left.png")));
            dinoRightUpRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Dino-right-up - right.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = 546;
        worldY = 232;
        speed = 4;
        playerDirection = "up";
    }

    public void update() {
        if (keys.up || keys.down || keys.left || keys.right) {
            if (keys.up) {
                playerDirection = "up";
            } else if (keys.down) {
                playerDirection = "down";
            } else if (keys.left) {
                playerDirection = "left";
            } else {
                playerDirection = "right";
            }
            collision = false;
            gp.collisions.checkTileForPlayer(this);
            if (!collision) {
                switch (playerDirection) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        index = gp.collisions.checkObj(this);
        interactObj(index);
        int index2 = gp.collisions.checkNpc(this);
        interactNpc(index2);
    }
    private void interactObj(int index) {
       /*if(keys.joinButtonClicked){
            screenX = 258;
            screenY = 122;
        }*/
        if (index != 999) {
            String text = "PRESS  'E'  TO INTERACT WITH ";
            String text2 = "PRESS  'H'  TO ENTER IN ";
            String text3 = "PRESS  'H'  TO GO OUTSIDE";
            String nameObj = gp.supObject[gp.currentMap][index].name;
            switch (nameObj) {
                case "HOUSE" -> {
                    gp.ui.message(text2 + nameObj);
                    if (keys.teleport) {
                        if (key > 0) {
                            key--;
                            gp.currentMap = gp.houseMap;
                            worldX = 1254;
                            worldY = 766;
                        } else {
                            gp.ui.message("You need a key to enter in this house");
                        }
                    }
                }
                case "KEY" -> {
                    gp.ui.message(text + nameObj);
                    if (keys.interact) {
                        key += 1;
                        gp.supObject[gp.currentMap][index] = null;
                    }
                }
                case "DOOR" -> {
                    gp.ui.message(text3);
                    if (keys.teleport) {
                        gp.currentMap = gp.exteriorMap;
                        fromHouseOutside();
                    }
                }
                case "IRON-DOOR" -> {
                    gp.ui.message(text2 + " CASTLE");
                    if (keys.teleport) {
                        gp.ui.message("You need a key to enter in the castle");
                        if (key > 0) {
                            gp.ui.message(" ");
                            key--;
                            gp.currentMap = gp.castleMap;
                            worldX = 590;
                            worldY = 189;
                        }
                    }
                }
                case "STAIRS" -> {
                    gp.ui.message(text3);
                    if (keys.teleport) {
                        gp.currentMap = gp.exteriorMap;
                        worldX = 2022;
                        worldY = 370;
                    }
                }
                case "DINCOIN" -> {
                    gp.ui.message(text + nameObj);
                    if (keys.interact) {
                        coin += 1;
                        gp.supObject[gp.currentMap][index] = null;
                    }
                }
                case "CHEST" -> {
                    gp.ui.message("PRESS  'Q'  TO OPEN " + nameObj);
                    if (keys.chestOpen) {
                        if (key > 0) {
                            gp.state = gp.chestState;
                            chestFound();
                            key -= 1;
                            gp.supObject[gp.currentMap][index] = null;
                        } else {
                            gp.ui.message("you don't have any key to open this chest");
                        }
                    }
                }
            }
        }
    }


    public void interactNpc(int index) {
        if (index != 999) {
            String text = "PRESS  'T'  TO TALK WITH ";
            String name = gp.entities[gp.currentMap][index].name;
            switch (name) {
                case "VILLAGER" -> {
                    gp.ui.message(text + name);
                    if (keys.talk) {
                        if (coin >= 25) {
                            gp.state = gp.dialogueStateWithVillagers;
                            gp.entities[gp.currentMap][index] = null;
                        } else {
                            gp.ui.message("you can't start that dialog when your balance below 25 coins.");
                        }
                    }
                }
                case "MERCHANT" -> {
                    gp.ui.message(text + name);
                    if (keys.talk) {
                        gp.state = gp.dialogueStateWithMerchant;
                    }
                }
            }
        }
    }

    private void chestFound() {
        Random random = new Random();
        String item = gp.ui.randomName;
        switch (item) {
            case "Key" -> key += random.nextInt(10);
            case "Coins" -> coin += random.nextInt(6);
            case "Manacoins" -> mana += random.nextInt(5);
            case "Diamond" -> diamond += random.nextInt(2);
            case "Lantern" -> lanternBuy = true;
            case "Calculator" -> calculatorBuy = true;
        }
    }

    private void fromHouseOutside() {

    }

//    public void cameraMove() {
//        if (worldX < 2000 && worldY < 2000) {
//            cameraDirection = "right";
//        } else if (worldY < 2000) {
//            cameraDirection = "down";
//        } else if (worldX > 546) {
//            cameraDirection = "left";
//        } else {
//            worldY -= speed * 0.4;
//            return;
//        }
//        switch (cameraDirection) {
//            case "down" -> worldY += speed * 0.4;
//            case "left" -> worldX -= speed * 0.4;
//            case "right" -> worldX += speed * 0.4;
//        }
//    }

    public void draw(Graphics2D g2) {
        g2.setFont(new Font(null, Font.BOLD, 10));
        g2.setColor(Color.white);
        switch (playerDirection) {
            case "up", "down" -> {
                if (spriteNum == 1) {
                    image = dinoLeftUpLeft;
                }
                if (spriteNum == 2) {
                    image = dinoRightUpLeft;
                }

                if (spriteNum == 1) {
                    image = dinoRightUpRight;
                }
                if (spriteNum == 2) {
                    image = dinoLeftUpRight;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = dinoLeftUpLeft;
                } else if (spriteNum == 2) {
                    image = dinoRightUpLeft;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = dinoRightUpRight;
                } else if (spriteNum == 2) {
                    image = dinoLeftUpRight;
                }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.size, gp.size, null);
    }
}