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
    public int coin = 25;
    public boolean calculatorBuy;
    public boolean lanternBuy;
    public int mana = 1;
    public int index;
    Merchant merchant;
    Villager villager;
    GamePanel gp;
    Keys keys;
    BufferedImage image;
    int houseX;
    int houseY;

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
                        House currentHouse = (House) gp.supObject[gp.currentMap][index];
                        if (currentHouse.visited) {
                            gp.ui.message("This house has been already visited");
                        } else if (key > 0) {
                            key--;
                            gp.currentMap = gp.houseMap;
                            worldX = 1254;
                            worldY = 766;
                            gp.supObject[gp.exteriorMap][index].image = gp.house.visitedHouse;
                            houseX = gp.supObject[gp.exteriorMap][index].worldX;
                            houseY = gp.supObject[gp.exteriorMap][index].worldY;
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
                        outsideFromHouse();
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
                    Chest currentChest = (Chest) gp.supObject[gp.currentMap][index];
                    if (currentChest.opened) {
                        gp.ui.message("This chest is already empty.");
                    } else if (keys.chestOpen) {
                        if (key > 0) {
                            currentChest.opened = true;
                            chestFound();
                            key -= 1;
                            gp.supObject[gp.currentMap][index].image = gp.chest.openChest;
                        } else {
                            gp.ui.message("you don't have any key to open this chest");
                        }
                        System.out.println("chest" + currentChest.opened);
                    }
                }
            }
        }
    }

    private void outsideFromHouse() {
        gp.assetSetter.setItemsInHouse();
        Point house = new Point();
        int pointOfHouse = (int) house.distance(houseX, houseY);
        System.out.println("point OF house" + pointOfHouse);
        switch (pointOfHouse) {
            case 787 -> {
                worldX = 778;
                worldY = 224;
            }
            case 789 -> {
                worldX = 318;
                worldY = 728;
            }
            case 799 -> {
                worldX = 302;
                worldY = 734;
            }
            case 942 -> {
                worldX = 290;
                worldY = 894;
            }
            case 1486 -> {
                worldX = 326;
                worldY = 1438;
            }
            case 2164 -> {
                worldX = 1014;
                worldY = 1894;
            }
            case 2951 -> {
                worldX = 2242;
                worldY = 1906;
            }
            case 2953 -> {
                worldX = 2158;
                worldY = 1906;
            }
            case 2954 -> {
                worldX = 2174;
                worldY = 2906;
            }
            case 1491 -> {
                worldX = 990;
                worldY = 1158;
            }
        }
    }

    private void chestFound() {
        Random random = new Random();
        String item = gp.ui.randomName;
        gp.state = gp.chestState;
        switch (item) {
            case "Key" -> key += random.nextInt(10);
            case "Coins" -> coin += random.nextInt(6);
            case "Manacoins" -> mana += random.nextInt(5);
            case "Diamond" -> diamond += random.nextInt(2);
            case "Lantern" -> lanternBuy = true;
            case "Calculator" -> calculatorBuy = true;
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
                        if (gp.entities[gp.currentMap][index].talked) {
                            gp.ui.message("you have been talked with the villager");
                            gp.state = gp.playState;
                        } else if (!gp.entities[gp.currentMap][index].talked && coin >= 25) {
                            coin -= 25;
                            gp.state = gp.dialogueStateWithVillagers;
                            gp.entities[gp.currentMap][index].talked = true;
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


    public void draw(Graphics2D g2) {
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
