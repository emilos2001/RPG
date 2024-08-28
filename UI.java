package Totorial.RPG;

import Totorial.RPG.Entity.Entity;
import Totorial.RPG.Entity.Merchant;
import Totorial.RPG.Entity.Player;
import Totorial.RPG.Entity.Villager;
import Totorial.RPG.Obj.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UI {
    public static double currentProgress;
    final int slotsX = 58;
    final int slotsY = 318;
    public int slotCol = 0;
    public int slotRow = 0;
    public int slotRow2 = 0;
    public int subState;
    public int choose = 1;
    public String currentDialogue = "";
    public int cursorX;
    public int cursorY;
    public String randomName = null;
    public BufferedImage randomImage = null;
    Villager villager;
    Merchant merchant;
    Mana mana;
    Lantern lantern;
    CalcObj calculator;
    Coin coin;
    KeyObj keyObj;
    Diamond diamondObj;
    Chest chest;
    GamePanel gp;
    Keys keys;
    Entity entity;
    SupObject supObject;
    Graphics2D g2d;
    BufferedImage dinoRightUpLeft, dinoLeftUpLeft, dinoLeftUpRight, dinoRightUpRight, npcLeft1, npcLeft2, merchant1, merchant2, dinCoinImg, manaCoinImg, keyImg, diamondImg, lanternImg, calculatorImg;
    List<Integer> listOfObjectStr = new ArrayList<>();
    boolean message = false;
    String messageShow = "";
    int counter = 0;
    int spriteNum = 1;
    int spriteCounter = 1;
    int spriteNum2 = 1;
    int spriteCounter2 = 1;

    public UI(GamePanel gp, Keys keys) {
        this.gp = gp;
        this.keys = keys;
        supObject = new SupObject(gp);
        entity = new Entity(gp);
        villager = new Villager(gp);
        npcLeft1 = villager.npcLeft1;
        npcLeft2 = villager.npcLeft2;
        merchant = new Merchant(gp);
        merchant1 = merchant.merchant1;
        merchant2 = merchant.merchant2;
        Player player = new Player(gp, keys);
        dinoLeftUpLeft = player.dinoLeftUpLeft;
        dinoRightUpLeft = player.dinoRightUpLeft;
        dinoRightUpRight = player.dinoRightUpRight;
        dinoLeftUpRight = player.dinoLeftUpRight;
        coin = new Coin(gp);
        chest = new Chest(gp);
        mana = new Mana(gp);
        keyObj = new KeyObj(gp);
        diamondObj = new Diamond(gp);
        lantern = new Lantern(gp);
        calculator = new CalcObj(gp);
    }

    private void dialoguesScreen(int x, int y, int width, int height) {
        g2d.setColor(new Color(26, 26, 0));
        g2d.fillRoundRect(x, y, width, height + 70, 25, 25);
        g2d.setColor(new Color(20, 82, 20));
        g2d.fillRoundRect(x + 5, y + 5, width - 10, height + 60, 30, 30);
        g2d.setStroke(new BasicStroke(3));
    }


    public void message(String text) {
        message = true;
        messageShow = text;
    }


    //gp.size, gp.size * 2, gp.size * 14, 630
    public void dialogueScreenWithVillager() {
        villager.setDialog();
        dialoguesScreen(5, gp.size * 2, 760, 640);
        g2d.setColor(Color.white);
        g2d.setFont(new Font(null, Font.ITALIC, 15));
        g2d.drawImage(dinCoinImg, 695, 100, 30, 30, null);
        g2d.drawString("X:" + gp.player.coin, 720, 120);
        String typeOfAnswers = "MULTIPLE ANSWERS";
        g2d.drawString(typeOfAnswers, 565, 790);
        if (spriteNum == 1) {
            g2d.drawImage(dinoLeftUpRight, 310, 750, 50, 50, null);
        }
        if (spriteNum == 2) {
            g2d.drawImage(dinoRightUpRight, 310, 750, 50, 50, null);
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
        if (spriteNum2 == 1) {
            g2d.drawImage(npcLeft1, 370, 750, 50, 50, null);
        }
        if (spriteNum2 == 2) {
            g2d.drawImage(npcLeft2, 370, 750, 50, 50, null);
        }
        spriteCounter2++;
        if (spriteCounter2 > 10) {
            if (spriteNum2 == 1) {
                spriteNum2 = 2;
            } else if (spriteNum2 == 2) {
                spriteNum2 = 1;
            }
            spriteCounter2 = 0;
        }
        questionAndAnswers();
    }

    public void questionAndAnswers() {
        int slotsX = 15;
        int slotsY = 388;
        g2d.setColor(Color.white);
        g2d.setFont(new Font(null, Font.ITALIC, 15));
        g2d.setColor(new Color(26, 26, 0));
        g2d.setStroke(new BasicStroke(3));
        cursorX = slotsX;
        cursorY = slotsY + (gp.size * slotRow2) - 145;// 243
        System.out.println(cursorY);
        g2d.drawRoundRect(cursorX, cursorY, 15, 15, 10, 10);
        g2d.setColor(Color.white);
        if (villager.questions[villager.dialogueSet][villager.dialogueIndex] != null) {
            String question = villager.questions[villager.dialogueSet][villager.dialogueIndex];
            nextLineText(question, 25, 140);
        } else {
            villager.dialogueIndex = 0;
            villager.dialogueSet++;
            if (villager.questions[villager.dialogueSet][0] == null) {
                villager.dialogueSet = 0;
            }
            if (gp.state == gp.dialogueStateWithVillagers) {
                gp.state = gp.playState;
            }
        }
        for (int i = 0; i < 6; i++) {
            int x = 35;
            int y = 230 + (i * 90);
            if (villager.answers[villager.dialogueSet][villager.dialogueIndex][i] != null) {
                String answer = villager.answers[villager.dialogueSet][villager.dialogueIndex][i];
                nextLineText(answer, x, y);
            }
        }
        if (villager.answers[villager.dialogueSet][0] == null) {
            villager.dialogueSet = 0;
        }
    }

    private void nextLineText(String text, int x, int y) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (fontMetrics.stringWidth(sb + " " + word) < 700) {
                sb.append(word).append(" ");
            } else {
                g2d.drawString(sb.toString(), x, y);
                y += fontMetrics.getHeight();
                sb = new StringBuilder(word + " ");
            }
        }
        g2d.drawString(sb.toString(), x, y);
    }

    private void dialogueScreenWithMerchant() {
        int x2 = 63;
        int y2 = 480;
        dialoguesScreen(gp.size, (gp.size * 12), (gp.size * 14), (gp.size * 14));
        g2d.setFont(new Font(null, Font.ITALIC, 20));
        g2d.setColor(Color.white);
        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, x2, y2 + 120);
            y2 += 30;
        }
        g2d.setFont(new Font(null, Font.ITALIC, 25));
        g2d.drawString("BUY", 630, 670);
        g2d.drawString("SELL", 630, 715);
        currentDialogue = """
                If you come to me,you ne to sell or buy something\s
                 what do you to want,
                 to buy or sell?""";
        if (choose == 1) {
            g2d.drawString(">", 610, 670);

            sell();
            buy();
            currentDialogue = """
                    If you come to me,you need to sell or buy something, \s
                    what do you to want to buy?
                    your choose, your money, our stuff
                    PRESS  'ESC'  TO EXIT""";

        }
        if (choose == 2) {
            g2d.drawString(">", 610, 715);
            sell();
            currentDialogue = """
                    If you come to me,you need to sell or buy something, \s
                    what do you to want to sell?\s
                    Let's see, what you got.
                    PRESS  'ESC'  TO EXIT""";
        }


        if (spriteNum == 1) {
            g2d.drawImage(dinoLeftUpRight, 310, 780, 50, 50, null);
        }
        if (spriteNum == 2) {
            g2d.drawImage(dinoRightUpRight, 310, 780, 50, 50, null);
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
        if (spriteNum2 == 1) {
            g2d.drawImage(merchant1, 370, 780, 50, 50, null);
        }
        if (spriteNum2 == 2) {
            g2d.drawImage(merchant2, 370, 780, 50, 50, null);
        }
        spriteCounter2++;
        if (spriteCounter2 > 10) {
            if (spriteNum2 == 1) {
                spriteNum2 = 2;
            } else if (spriteNum2 == 2) {
                spriteNum2 = 1;
            }
            spriteCounter2 = 0;
        }
    }

    private void cursor(int x, int y){
        g2d.setColor(new Color(26, 26, 0));
        cursorX = slotsX + (gp.size * slotCol) - 5;
        cursorY = slotsY + (gp.size * slotRow) - 5;
        g2d.drawRoundRect(cursorX + x, cursorY + y, 70, 65, 15, 15);
    }

    public void buy() {
        buySellWindow();
        g2d.drawString("Your Inventory", 270, 335);
        cursor(365, 20);
        itemBuy();
        String text = " ";
        //your inventory window
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            g2d.drawImage(gp.player.inventory.get(i).coin, 60, 310, 30, 30, null);
            g2d.setColor(Color.white);
            g2d.drawString(" : " + gp.player.coin, 85, 330);
            if (gp.player.key >= 1 || gp.player.keyBuy > 0) {
                g2d.drawImage(gp.player.inventory.get(i).key, slotsX + 15, slotsY + 30, 40, 40, null);
                g2d.drawString(" " + gp.player.key, slotsX + 40, slotsY + 70);
            }
            if (gp.player.key < 1) {
                g2d.drawImage(null, slotsX + 20, slotsY + 30, 40, 40, null);
                g2d.drawString(text, slotsX + 40, slotsY + 70);
            }
            if (gp.player.diamond >= 1 || gp.player.diamondBuy > 0) {
                g2d.drawImage(gp.player.inventory.get(i).diamond, slotsX + 202, slotsY + 30, 40, 40, null);
                g2d.drawString(" " + gp.player.diamond, slotsX + 235, slotsY + 65);
            }
            if (gp.player.diamond < 1) {
                g2d.drawImage(null, slotsX + 202, slotsY + 30, 40, 40, null);
                g2d.drawString(text, slotsX + 40, slotsY + 70);
            }
            if (gp.player.mana >= 0) {
                g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 107, slotsY + 125, 40, 40, null);
                g2d.drawString(" " + gp.player.mana, slotsX + 139, slotsY + 155);
            }
            if (gp.player.mana <= 0) {
                g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 107, slotsY + 125, 40, 40, null);
                g2d.drawString("0", slotsX + 139, slotsY + 155);
            }
            if (gp.player.calculatorBuy) {
                g2d.drawImage(gp.player.inventory.get(i).calculator, slotsX + 107, slotsY + 28, 40, 40, null);
            }
            if (gp.player.lanternBuy) {
                g2d.drawImage(gp.player.inventory.get(i).lantern, slotsX + 15, slotsY + 125, 40, 40, null);
            }
        }
    }

    public void sell() {
        //window1 your inventory
        buySellWindow();
        itemSell();
        //cursor on sell your items
        cursor(10, 25);
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            //items which you can sell
            //buy window
            g2d.drawImage(gp.player.inventory.get(i).key, slotsX + 375, slotsY + 25, 40, 40, null);
            g2d.drawImage(gp.player.inventory.get(i).calculator, slotsX + 467, slotsY + 23, 40, 40, null);
            g2d.drawImage(gp.player.inventory.get(i).diamond, slotsX + 559, slotsY + 25, 40, 40, null);
            g2d.drawImage(gp.player.inventory.get(i).lantern, slotsX + 375, slotsY + 115, 40, 40, null);
            g2d.drawImage(gp.player.inventory.get(i).coin, slotsX + 467, slotsY + 115, 40, 40, null);
            g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 559, slotsY + 115, 40, 40, null);
        }
    }

    private void buySellWindow() {
        dialoguesScreen(48, 308, gp.size * 7, gp.size * 4);
        dialoguesScreen(390, 308, gp.size * 7, gp.size * 4);
        g2d.setColor(Color.white);
        g2d.setFont(new Font(null, Font.ITALIC, 15));
        g2d.drawString("Your inventory", 270, 335);
        g2d.setStroke(new BasicStroke(3));
    }

    public void itemBuy() {
        int price;
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            //ITEMS WHICH YOU CAN BUY
            g2d.drawImage(gp.player.inventory.get(i).key, slotsX + 375, slotsY + 25, 40, 40, null);
            g2d.drawString("Merchant stuff", 680, 335);
            if (cursorX == 53 && cursorY == 313) {
                price = 50;
                g2d.setColor(Color.white);
                g2d.drawString("Key : " + price + " dincoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.coin) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                    g2d.drawImage(null, slotsX + 375, slotsY + 25, 40, 40, null);
                } else if (keys.sellBuy) {
                    gp.player.key++;
                    gp.player.coin = gp.player.coin - price;
                    keys.sellBuy = false;
                }

            }
            g2d.drawImage(gp.player.inventory.get(i).calculator, slotsX + 467, slotsY + 23, 40, 40, null);
            if (cursorX == 149 && cursorY == 313) {
                price = 42;
                g2d.setColor(Color.white);
                g2d.drawString("Calculator : " + price + " manacoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.coin) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                } else if (gp.player.calculatorBuy) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You have already a calculator", slotsX + 355, slotsY + 230);
                    keys.sellBuy = false;
                } else if (keys.sellBuy) {
                    gp.player.calculatorBuy = true;
                    gp.player.mana = gp.player.mana - price;
                }
            }
            g2d.drawImage(gp.player.inventory.get(i).diamond, slotsX + 559, slotsY + 25, 40, 40, null);
            if (cursorX == 245 && cursorY == 313) {
                price = 75;
                g2d.setColor(Color.white);
                g2d.drawString("Diamond : " + price + " dincoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.coin) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                    keys.sellBuy = false;
                } else if (keys.sellBuy) {
                    gp.player.diamond++;
                    gp.player.coin = gp.player.coin - price;
                }
            }
            g2d.drawImage(gp.player.inventory.get(i).lantern, slotsX + 375, slotsY + 115, 40, 40, null);
            if (cursorX == 53 && cursorY == 409) {
                price = 45;
                g2d.setColor(Color.white);
                g2d.drawString("Lantern : " + price + " manacoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.mana) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                } else if (gp.player.lanternBuy) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You have already a lantern", slotsX + 355, slotsY + 230);
                } else if (keys.sellBuy) {
                    gp.player.lanternBuy = true;
                    gp.player.mana = gp.player.mana - price;
                    keys.sellBuy = false;
                }
            }
            g2d.drawImage(gp.player.inventory.get(i).coin, slotsX + 467, slotsY + 115, 40, 40, null);
            if (cursorX == 149 && cursorY == 409) {
                price = 51;
                g2d.setColor(Color.white);
                g2d.drawString("Dincoin : " + price + " manacoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.mana) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                } else if (keys.sellBuy) {
                    gp.player.coin = gp.player.coin + 23;
                    gp.player.mana = gp.player.mana - price;
                    keys.sellBuy = false;
                }

            }
            g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 559, slotsY + 115, 40, 40, null);
            if (cursorX == 245 && cursorY == 409) {
                price = 42;
                g2d.setColor(Color.white);
                g2d.drawString("Manacoin : " + price + " dincoin", slotsX + 360, slotsY + 200);
                if (price > gp.player.coin) {
                    g2d.setColor(Color.white);
                    g2d.drawString("You don't have enough money for that item", slotsX + 355, slotsY + 230);
                } else if (keys.sellBuy) {
                    gp.player.mana = gp.player.mana + 33;
                    gp.player.coin = gp.player.coin - price;
                    keys.sellBuy = false;
                } else if (gp.player.mana <= 0) {
                    g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 107, slotsY + 125, 40, 40, null);
                    g2d.drawString("0", slotsX + 139, slotsY + 155);
                }
            }
        }
    }

    public void itemSell() {
        int price;
        int cursorX = slotsX + (gp.size * slotCol) - 5;
        int cursorY = slotsY + (gp.size * slotRow) - 5;
        System.out.println("X: " + cursorX);
        System.out.println("Y: " + cursorY);
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            //your dincoins
            g2d.drawImage(gp.player.inventory.get(i).coin, 60, 310, 30, 30, null);
            g2d.setColor(Color.white);
            g2d.drawString(" : " + gp.player.coin, 85, 330);
            //your items
            if (gp.player.key > 0 || gp.player.keyBuy >= 1) {
                g2d.drawImage(gp.player.inventory.get(i).key, slotsX + 15, slotsY + 30, 40, 40, null);
                g2d.drawString(" " + gp.player.key, slotsX + 40, slotsY + 70);
                if (cursorX == 53 && cursorY == 313) {
                    price = 25;
                    g2d.drawString("Key : " + price + "  dincoin", slotsX + 140, slotsY + 230);
                    if (keys.sellBuy) {
                        gp.player.coin = gp.player.coin + price;
                        gp.player.key--;
                        keys.sellBuy = false;
                    }
                    if (gp.player.key < 1) {
                        g2d.drawImage(null, slotsX + 15, slotsY + 30, 40, 40, null);
                    }
                }
            }
            if (gp.player.calculatorBuy) {
                g2d.drawImage(gp.player.inventory.get(i).calculator, slotsX + 107, slotsY + 28, 40, 40, null);
                if (cursorX == 149 && cursorY == 313) {
                    g2d.drawString("Calculator : This item cannot be sold", slotsX + 50, slotsY + 230);
                }
            }
            if (gp.player.diamond > 0) {
                g2d.drawImage(gp.player.inventory.get(i).diamond, slotsX + 202, slotsY + 30, 40, 40, null);
                g2d.drawString(" " + gp.player.diamond, slotsX + 235, slotsY + 65);
                if (cursorX == 245 && cursorY == 313) {
                    price = 50;
                    g2d.drawString("Diamond " + price + " : dincoin", slotsX + 140, slotsY + 230);
                    if (keys.sellBuy) {
                        gp.player.coin = gp.player.coin + price;
                        gp.player.diamond--;
                        keys.sellBuy = false;
                    }
                    if (gp.player.diamond <= 0) {
                        gp.player.inventory.get(i).diamond = null;
                    }
                }
            }
            if (gp.player.lanternBuy) {
                g2d.drawImage(gp.player.inventory.get(i).lantern, slotsX + 15, slotsY + 125, 40, 40, null);
                if (cursorX == 53 && cursorY == 409) {
                    g2d.drawString("Lantern : This item cannot be sold", slotsX + 70, slotsY + 230);
                }
            }
            if (gp.player.mana > 0) {
                g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 107, slotsY + 125, 40, 40, null);
                g2d.drawString(" " + gp.player.mana, slotsX + 139, slotsY + 155);
                if (cursorX == 149 && cursorY == 409) {
                    price = 25;
                    g2d.drawString("Manacoin : " + price + " dincoin", slotsX + 50, slotsY + 230);
                    if (keys.sellBuy) {
                        gp.player.mana--;
                        gp.player.coin = gp.player.coin + price;
                        keys.sellBuy = false;
                    }
                }
            }
        }
    }


    public void draw(Graphics2D g2d) {
        counter++;
        this.g2d = g2d;
        if (gp.state == gp.dialogueStateWithVillagers) {
            dialogueScreenWithVillager();
            progressBar(g2d, currentProgress);
        }
        if (gp.state == gp.dialogueStateWithMerchant) {
            dialogueScreenWithMerchant();
        }
        if (gp.state == gp.inventoryState) {
            inventory();
        }
        if (gp.state == gp.information) {
            information();
            progressBar(g2d, currentProgress);
        }
        if (gp.state == gp.dialogueScreen) {
            dialogueScreenWithMerchant();
        }
        if (gp.state == gp.chestState) {
            chestScreen();
        }

        if (message) {
            g2d.setFont(new Font(null, Font.BOLD | Font.ITALIC, 20));
            g2d.setColor(Color.white);
            g2d.drawString(messageShow, 10, 820);
        }
        if (counter > 5) {
            counter = 0;
            message = false;
        }
    }

    private void chestScreen() {
        int x = gp.size * 3;
        int y = gp.size * 7;
        int width = gp.size * 7;
        int height = gp.size;
        dialoguesScreen(x, y, width, height);
        chestScreenInfo();
    }

    private void chestScreenInfo() {
        int x = 305;
        int y = 410;
        if (keys.chestOpen) {
            keys.chestOpen = false;
            if (randomName == null) {
                randomName = randomObj();
            }
        }
        keyImg = keyObj.image;
        calculatorImg = calculator.image;
        dinCoinImg = coin.image;
        manaCoinImg = mana.image;
        lanternImg = lantern.image;
        diamondImg = diamondObj.image;
        switch (randomName) {
            case "Key" -> randomImage = keyImg;
            case "Coins" -> randomImage = dinCoinImg;
            case "Calculator" -> randomImage = calculatorImg;
            case "Lantern" -> randomImage = lanternImg;
            case "Manacoins" -> randomImage = manaCoinImg;
            case "Diamond" -> randomImage = diamondImg;
            default -> g2d.drawString("No Item In This Chest", x, y);
        }
        g2d.setColor(Color.white);
        g2d.setFont(new Font(null, Font.ITALIC, 20));
        g2d.drawString(randomName, x, y);
        g2d.drawImage(randomImage, x - 55, y - 35, 50, 50, null);
    }

    public String randomObj() {
        int key = 1;
        int calculator = 2;
        int lantern = 3;
        int coin = 4;
        int manaCoin = 5;
        int diamond = 6;
        listOfObjectStr.add(key);
        listOfObjectStr.add(calculator);
        listOfObjectStr.add(lantern);
        listOfObjectStr.add(coin);
        listOfObjectStr.add(manaCoin);
        listOfObjectStr.add(diamond);
        Random random = new Random();
        int randomObj = listOfObjectStr.get(random.nextInt(listOfObjectStr.size()));
        randomName = switch (randomObj) {
            case 1 -> "Key";
            case 2 -> "Calculator";
            case 3 -> "Lantern";
            case 4 -> "Coins";
            case 5 -> "Manacoins";
            case 6 -> "Diamond";
            default -> "Chest Is Empty";
        };
        return randomName;
    }

    private void inventory() {
        int x = gp.size * 3;
        int y = gp.size * 6;
        int width = gp.size * 9;
        int height = gp.size * 3;
        dialoguesScreen(x, y, width, height);
        final int slotsX = x + 20;
        final int slotsY = y + 20;
        g2d.setFont(new Font(null, Font.BOLD, 10));
        int cursorX = slotsX + (gp.size * slotCol) - 5;
        int cursorY = slotsY + (gp.size * slotRow) - 5;
        g2d.setColor(new Color(26, 26, 0));
        g2d.drawRoundRect(cursorX + 10, cursorY, 70, 70, 15, 15);
        int cursorWidth = 35;
        int cursorHeight = 35;
        playerInInventory();
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            if (gp.player.coin > 0) {
                g2d.drawImage(gp.player.inventory.get(i).coin, slotsX + 15, slotsY - 2, cursorWidth, cursorHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("COIN x" + gp.player.coin, slotsX + 12, slotsY + 55);
            }
            if (gp.player.key > 0) {
                g2d.drawImage(gp.player.inventory.get(i).key, slotsX + 107, slotsY + 99, cursorWidth, cursorHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("KEY x" + gp.player.key, slotsX + 107, slotsY + 155);
            }

            if (gp.player.mana > 0) {
                g2d.drawImage(gp.player.inventory.get(i).manaCoin, slotsX + 205, slotsY + 99, cursorWidth + 5, cursorHeight + 5, null);
                g2d.setColor(Color.white);
                g2d.drawString("MANA x" + gp.player.mana, slotsX + 205, slotsY + 155);
            }

            if (gp.player.diamond > 0) {
                g2d.drawImage(gp.player.inventory.get(i).diamond, slotsX + 202, slotsY - 2, cursorWidth, cursorHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("DIAMOND x" + gp.player.diamond, slotsX + 200, slotsY + 55);
            }
            if (gp.player.lanternBuy) {
                g2d.drawImage(gp.player.inventory.get(i).lantern, slotsX + 15, slotsY + 99, cursorWidth, cursorHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("LANTERN", slotsX + 15, slotsY + 155);
            }
            if (gp.player.calculatorBuy) {
                g2d.drawImage(gp.player.inventory.get(i).calculator, slotsX + 107, slotsY - 1, cursorWidth, cursorHeight, null);
                g2d.setColor(Color.white);
                g2d.drawString("CALCULATOR", slotsX + 100, slotsY + 55);
            }
        }
    }

    public void playerInInventory() {
        int width = gp.size;
        int height = gp.size;
        g2d.setColor(new Color(26, 26, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(480, 300, width + 40, height + 100, 15, 15);
        if (spriteNum == 1) {
            g2d.drawImage(dinoLeftUpLeft, 490, 340, 80, 80, null);
        }
        if (spriteNum == 2) {
            g2d.drawImage(dinoRightUpLeft, 490, 340, 80, 80, null);
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

    private void progressBar(Graphics2D g2d, double progress) {
        int x = 10;
        int y = 10;
        int width = (int) ((gp.screenWidth - 50) * progress);
        int height = 50;
        g2d.setColor(new Color(0, 26, 0, 190));
        g2d.fillRoundRect(x, y, gp.screenWidth - 50, height, 25, 25);
        g2d.setColor(new Color(0, 0, 0, 170));
        g2d.fillRoundRect(x + 5, y + 5, gp.screenWidth - 60, height - 10, 30, 30);
        g2d.setColor(new Color(0, 255, 0));
        g2d.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 30, 30);
        g2d.setFont(new Font("MV Boli", Font.BOLD, 25));
        g2d.setColor(Color.white);
        g2d.drawString(String.format("%.1f%%", progress * 100), (gp.screenWidth - 60) / 2, 45);
    }

    private void information() {
        int x = 400;
        int y = 750;
        int width = 335;
        int height = 50;
        g2d.setColor(new Color(102, 255, 102, 170));
        g2d.fillRoundRect(x, y, width, height, 25, 25);
        g2d.setColor(new Color(179, 255, 179, 170));
        g2d.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 30, 30);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString("X:" + gp.player.worldX, 420, 780);
        g2d.drawString("Y:" + gp.player.worldY, 520, 780);
        g2d.drawString("FPS:" + gp.count, 620, 780);
    }
}
