package Totorial.RPG.Entity;


import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;
import Totorial.RPG.Obj.Chest;
import Totorial.RPG.Obj.House;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity {
    public final int screenX;
    public final int screenY;
    public boolean calculatorBuy;
    public boolean lanternBuy;
    public int index;
    public int index2;

    public int key = 2;
    public int coins = 30;
    public int diamonds = 0;
    public int manaCoins = 0;
    public int calculator = 0;
    public int lanternNum = 0;
    public HashMap<BufferedImage, Integer> inventory = new LinkedHashMap<>();
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
        playerInventory();
        setDefaultValues();
        getPlayerImage();

    }

    public void updateSlots(BufferedImage image, BufferedImage imageNull, int count) {
        if (count > 0) {
            inventory.put(image, inventory.getOrDefault(image, 0) + count);
        } else {
            inventory.put(imageNull, inventory.getOrDefault(imageNull, 0) + count);
        }
    }

    private void playerInventory() {
        updateSlots(coin.image, coin.coin, coins);
        updateSlots(keyObj.image, keyObj.key, key);
        updateSlots(mana.image, mana.manaCoin, manaCoins);
        updateSlots(diamond.image, diamond.diamond, diamonds);
        updateSlots(lantern.image, lantern.lantern, lanternNum);
        updateSlots(calcObj.image, calcObj.calculator, calculator);
    }

    public int getCoins() {
        return inventory.get(coin.image);
    }

    public void addCoins(int amount) {
        int currentCoins = inventory.getOrDefault(coin.image, 0);
        inventory.put(coin.image, currentCoins + amount);
    }

    public void subtractCoins(int amount, String currency) {
        int currentCoins = inventory.getOrDefault(coin.image, 0);
        inventory.put(coin.image, currentCoins - amount);
    }

    public void useInventory(BufferedImage image, int amount, boolean give) {
        if (give) {
            int currentAmount = inventory.getOrDefault(image, 0);
            int remainingItem = currentAmount - amount;

            if (remainingItem > 0) {
                inventory.put(image, remainingItem);
            } else {
                inventory.remove(image);
            }
        } else {
            inventory.merge(image, amount, Integer::sum);
        }
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
        if (gp.state == gp.playState) {
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
            index2 = gp.collisions.checkNpc(this);
            interactNpc(index2);
        }
    }

    private void interactObj(int index) {
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
                        } else if (inventory.getOrDefault(keyObj.image, 0) >= 1) {
                            gp.currentMap = gp.houseMap;
                            worldX = 1254;
                            worldY = 766;
                            gp.supObject[gp.exteriorMap][index].image = gp.house.visitedHouse;
                            houseX = gp.supObject[gp.exteriorMap][index].worldX;
                            houseY = gp.supObject[gp.exteriorMap][index].worldY;
                            useInventory(keyObj.image, 1, true);
                            currentHouse.visited = true;
                        } else {
                            gp.ui.message("You need a key to enter this house");
                        }
                    }
                }
                case "KEY" -> {
                    gp.ui.message(text + nameObj);
                    if (keys.interact) {
                        gp.supObject[gp.currentMap][index] = null;
                        useInventory(keyObj.image, 1, false);
                    }
                }
                case "DOOR" -> {
                    gp.ui.message(text3);
                    if (keys.teleport) {
                        outsideFromHouse();
                        gp.currentMap = gp.exteriorMap;
                    }
                }
                case "IRON-DOOR" -> {
                    gp.ui.message(text2 + " CASTLE");
                    if (keys.teleport) {
                        gp.ui.message("You need a key to enter in the castle");
                        if (inventory.getOrDefault(keyObj.image, 0) >= 1) {
                            gp.ui.message(" ");
                            gp.currentMap = gp.castleMap;
                            worldX = 590;
                            worldY = 189;
                            useInventory(keyObj.image, 1, true);
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
                        gp.supObject[gp.currentMap][index] = null;
                        useInventory(coin.image, 1, false);
                    }
                }
                case "CHEST" -> {
                    gp.ui.message("PRESS  'Q'  TO OPEN " + nameObj);
                    Chest currentChest = (Chest) gp.supObject[gp.currentMap][index];
                    if (currentChest.opened) {
                        gp.ui.message("This chest is already empty.");
                    } else if (keys.chestOpen) {
                        if (inventory.getOrDefault(keyObj.image, 0) >= 1) {
                            currentChest.opened = true;
                            chestFound();
                            gp.supObject[gp.currentMap][index].image = gp.chest.openChest;
                            useInventory(keyObj.image, 1, true);
                        } else {
                            gp.ui.message("you don't have any key to open this chest");
                        }
                    }
                }
            }
        }
    }

    private void outsideFromHouse() {
        gp.assetSetter.setItemsInHouse();
        Point house = new Point();
        int pointOfHouse = (int) house.distance(houseX, houseY);
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

    public void chestFound() {
        Random random = new Random();
        String item = gp.ui.randomObj();
        System.out.println(item);
        gp.state = gp.chestState;
        switch (item) {
            case "Key" -> {
                if (!inventory.containsKey(keyObj.image)) {
                    inventory.put(keyObj.image, inventory.getOrDefault(keyObj.image, 0));
                } else {
                    inventory.put(keyObj.image, inventory.get(keyObj.image) + random.nextInt(3) + 1);
                }
                System.out.println("keys" + inventory.getOrDefault(keyObj.image, 0));
            }
            case "Coins" -> {
                if (!inventory.containsKey(coin.image)) {
                    inventory.put(coin.image, inventory.getOrDefault(coin.image, 0) + random.nextInt(15) + 1);
                } else {
                    inventory.put(coin.image, inventory.get(coin.image) + random.nextInt(15) + 1);
                }
            }
            case "Manacoins" -> {
                if (!inventory.containsKey(mana.image)) {
                    inventory.put(mana.image, inventory.getOrDefault(mana.image, 0) + random.nextInt(5) + 1);
                } else {
                    inventory.put(mana.image, inventory.get(mana.image) + random.nextInt(5) + 1);
                }
            }
            case "Diamond" -> {
                if (!inventory.containsKey(diamond.image)) {

                    inventory.put(diamond.image, inventory.getOrDefault(diamond.image, 0) + random.nextInt(2) + 1);
                } else {
                    inventory.put(diamond.image, inventory.get(diamond.image) + random.nextInt(2) + 1);
                }

            }
            case "Lantern" -> {
                if (!inventory.containsKey(lantern.image)) {
                    lanternBuy = true;
                    inventory.put(lantern.image, inventory.getOrDefault(lantern.image, 0) + 1);
                } else {
                    gp.ui.message("You found another lantern, but you can only carry one!");
                }
            }
            case "Calculator" -> {
                if (!inventory.containsKey(calcObj.image)) {
                    calculatorBuy = true;
                    inventory.put(calcObj.image, inventory.getOrDefault(calcObj.image, 0) + 1);
                } else {
                    gp.ui.message("You found another calculator, but you can only carry one!");
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
                    Villager currentVillager = (Villager) gp.entities[gp.currentMap][index];
                    if (keys.talk) {
                        if (currentVillager.talked) {
                            gp.ui.message("you have been talked with the villager");
                        } else if (inventory.getOrDefault(coin.image, 0) >= 20) {
                            gp.state = gp.dialogueStateWithVillagers;
                            gp.entities[gp.currentMap][index].talked = true;
                            useInventory(coin.image, 20, true);
                        } else {
                            gp.ui.message("you can't start that dialog when your balance below 20 coins.");
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