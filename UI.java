package Totorial.RPG;

import Totorial.RPG.Obj.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class UI {
    public static double currentProgress = 0.0;
    int[] selectedAnswerIndex = new int[5];
    int currentSelection;
    public int slotCol = 0;
    public int slotRow = 0;
    public int choose = 1;
    public String currentDialogue = "";
    public BufferedImage randomImage = null;
    public List<BufferedImage> items;
    public List<Integer> amount;
    List<BufferedImage> buyItems;
    List<Integer> listOfObjectStr = new ArrayList<>();
    List<BufferedImage> itemsToSell;
    int spriteNum = 1;
    int spriteCounter = 0;
    GamePanel gp;
    Keys keys;
    SupObject supObject;
    Graphics2D g2d;
    Mana mana;
    Lantern lantern;
    CalcObj calculator;
    Coin coin;
    KeyObj keyObj;
    Diamond diamondObj;
    String message = "";
    boolean messageShow = false;
    int counter = 0;
    int col;
    int row;
    int spaceBetween = 3;
    public String randomName = "";

    public UI(GamePanel gp, Keys keys) {
        this.gp = gp;
        this.keys = keys;
        supObject = new SupObject(gp);
        buyItems = new ArrayList<>();
        items = new ArrayList<>(gp.player.inventory.keySet());
        amount = new ArrayList<>(gp.player.inventory.values());
        itemsToSell = new ArrayList<>(gp.player.inventory.keySet());
        coin = new Coin(gp);
        mana = new Mana(gp);
        keyObj = new KeyObj(gp);
        diamondObj = new Diamond(gp);
        lantern = new Lantern(gp);
        calculator = new CalcObj(gp);
        buyItems.add(keyObj.image);
        buyItems.add(coin.image);
        buyItems.add(mana.image);
        buyItems.add(diamondObj.image);
        buyItems.add(lantern.image);
        buyItems.add(calculator.image);
    }

    public void message(String text) {
        messageShow = true;
        message = text;
    }

    private int selectedSlot(int cursorX, int cursorY) {
        col = (cursorX + slotCol) / 125;
        row = (cursorY + slotRow) / 125;
        return col + (row * spaceBetween);
    }

    private void cursor(int cursorX, int cursorY, int width, int height, boolean itemBuy) {
        g2d.setColor(new Color(26, 26, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(cursorX + slotCol, cursorY + slotRow, width, height, 15, 15);
        int selectedItem = selectedSlot(cursorX, cursorY);
        if (itemBuy) {
            switch (selectedItem) {
                case 12 -> purchaseSellItems(0, 23, true);
                case 13 -> purchaseSellItems(1, 15, true);
                case 14 -> purchaseSellItems(2, 7, true);
                case 15 -> purchaseSellItems(3, 12, true);
                case 16 -> purchaseSellItems(4, 6, true);
                case 17 -> purchaseSellItems(5, 10, true);
            }
        } else {
            
        }
    }


    private void purchaseSellItems(int item, int price, boolean itemBuy) {
        int coins = gp.player.inventory.getOrDefault(gp.player.coin.image, gp.player.coins);
        int manaCoins = gp.player.inventory.getOrDefault(gp.player.mana.image, gp.player.manaCoins);
        if (itemBuy) {
            if (keys.sellBuy) {
                switch (item) {
                    case 0 ->
                            transactionsBuySell(gp.player.coin.image, gp.player.keyObj.image, keyObj.name, price, coins, true);
                    case 1 ->
                            transactionsBuySell(gp.player.mana.image, gp.player.coin.image, coin.name, price, manaCoins, true);
                    case 2 ->
                            transactionsBuySell(gp.player.coin.image, gp.player.mana.image, mana.name, price, coins, true);
                    case 3 ->
                            transactionsBuySell(gp.player.coin.image, gp.player.diamond.image, diamondObj.name, price, coins, true);
                    case 4 ->
                            transactionsBuySell(gp.player.mana.image, gp.player.lantern.image, lantern.name, price, manaCoins, true);
                    case 5 ->
                            transactionsBuySell(gp.player.mana.image, gp.player.calcObj.image, calculator.name, price, manaCoins, true);
                }
                keys.sellBuy = false;
            }
        }
    }

    private void transactionsBuySell(BufferedImage imageMoney, BufferedImage imageItem, String name, int price, int currency, boolean buy) {
        int itemCount;
        int index;
        if (buy) {
            if (currency < price) {
                System.out.println("you don't have enough money");
            }
            int remainingMoney = currency - price;
            itemCount = gp.player.inventory.getOrDefault(imageItem, 0);
            gp.player.inventory.put(imageItem, itemCount + 1);
            if (remainingMoney > 0) {
                gp.player.inventory.put(imageMoney, remainingMoney);
                if (!itemsToSell.contains(imageItem)) {
                    itemsToSell.add(imageItem);
                }
            } else {
                gp.player.inventory.remove(imageMoney);
            }
            index = itemsToSell.indexOf(imageItem);
        } else {

        }
    }

    private void dialoguesInventoryScreen(int x, int y, int width, int height) {
        g2d.setColor(new Color(26, 26, 0));
        g2d.fillRoundRect(x, y, width, height + 70, 25, 25);
        g2d.setColor(new Color(20, 82, 20));
        g2d.fillRoundRect(x + 5, y + 5, width - 10, height + 60, 30, 30);
    }

    private void inventory() {
        dialoguesInventoryScreen(144, 288, 432, 144);
        cursor(160, 310, 60, 60, false);
        playerInInventory();
        items(25, 38, 0, 0, items, amount, true);
    }

    private void playerInInventory() {
        g2d.setColor(new Color(26, 26, 0));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(480, 300, 88, 148, 15, 15);
        charactersInWindows(490, 340, 80, 80, gp.player.dinoLeftUpLeft, gp.player.dinoRightUpLeft);
    }

    private void charactersInWindows(int x, int y, int width, int height, BufferedImage image1, BufferedImage image2) {
        if (spriteNum == 1) {
            g2d.drawImage(image1, x, y, width, height, null);
        }
        if (spriteNum == 2) {
            g2d.drawImage(image2, x, y, width, height, null);
        }
        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    private void items(int x, int y, int width, int height, List<BufferedImage> images, List<Integer> amount, boolean isPlayerInventory) {
        for (int i = 0; i < images.size(); i++) {
            col = i % spaceBetween;
            row = i / spaceBetween;
            int itemCol = 164 + (col * 125);
            int itemRow = 315 + (row * 125);
            g2d.drawImage(images.get(i), itemCol + width, itemRow + height, 40, 40, null);
            g2d.setColor(Color.WHITE);
            this.amount = new ArrayList<>(gp.player.inventory.values());
            this.items = new ArrayList<>(gp.player.inventory.keySet());
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            if (isPlayerInventory) {
                g2d.drawImage(images.get(i), itemCol + width, itemRow + height, 40, 40, null);
                g2d.drawString("X" + amount.get(i), itemCol + x, itemRow + y);
            }
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


    private void dialogueScreenWithVillager() {
        gp.villager.setDialogue(gp.villager.questions, gp.villager.answers, gp.villager.isCorrect, gp.menu.pinGame());
        dialoguesInventoryScreen(15, 240, 750, 630);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 17));
        g2d.drawImage(coin.image, 685, 250, 30, 30, null);
        g2d.drawString("x:" + gp.player.getCoins(), 710, 270);
        charactersInWindows(325, 860, 50, 50, gp.player.dinoLeftUpRight, gp.player.dinoRightUpRight);
        charactersInWindows(395, 860, 50, 50, gp.villager.npcLeft1, gp.villager.npcLeft2);
        cursor(19, 310, 40, 40, false);
        questionAndAnswers();
    }

    public void questionAndAnswers() {
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        if (gp.villager.questions[gp.villager.dialogueSet][gp.villager.dialogueIndex] != null) {
            String question = gp.villager.questions[gp.villager.dialogueSet][gp.villager.dialogueIndex];
            nextLineText(question, 45, 280);
        } else {
            gp.villager.dialogueIndex = 0;
            gp.villager.dialogueSet++;
            if (gp.villager.questions[gp.villager.dialogueSet][0] == null) {
                gp.villager.dialogueSet = 0;
            }
            if (gp.state == gp.dialogueStateWithVillagers) {
                gp.state = gp.playState;
            }
        }
        for (int i = 0; i < 5; i++) {
            int x = 70;
            int y = 340 + (i * 90);
            if (gp.villager.answers[gp.villager.dialogueSet][gp.villager.dialogueIndex][i] != null) {
                String answer = gp.villager.answers[gp.villager.dialogueSet][gp.villager.dialogueIndex][i];
                nextLineText(answer, x, y);
            }
        }
        if (gp.villager.answers[gp.villager.dialogueSet][0] == null) {
            gp.villager.dialogueSet = 0;
        }
        nextDialogue();
    }

    private void nextDialogue() {
        if (!keys.nextDialogue) {
            return;
        }
        int currentSet = gp.villager.dialogueSet;
        int currentIndex = gp.villager.dialogueIndex;
        selectedAnswerIndex[currentSet] = currentSelection;
        if (selectedAnswerIndex[currentSet] == -1) {
            return;
        }
        int playerChoice = selectedAnswerIndex[currentSet];
        boolean isCorrect = gp.villager.isCorrect[currentSet][currentIndex][playerChoice];
        if (isCorrect) {
            gp.player.addCoins(5);
            currentProgress = Math.max(0.0, currentIndex + 0.15);
        }
        System.out.println((int) (currentProgress * 100) + "%");
        int nextSet = currentSet + 1;
        if (nextSet >= gp.villager.questions.length || gp.villager.questions[nextSet][currentIndex] == null) {
            gp.villager.dialogueSet = 0;
            gp.villager.dialogueIndex++;
            gp.state = gp.playState;
        } else {
            gp.villager.dialogueSet = nextSet;
        }
        currentSelection = 0;
        keys.nextDialogue = false;
    }

    private void dialogueScreenWithMerchant() {
        dialoguesInventoryScreen(48, 500, gp.size * 7, gp.size * 3);
        dialoguesInventoryScreen(390, 500, gp.size * 7, gp.size * 3);
        buySell();
        dialoguesInventoryScreen(48, 720, 686, 150);
        g2d.setFont(new Font(null, Font.ITALIC, 20));
        g2d.setColor(Color.white);
        int i = 670;
        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, 70, i + 120);
            i += 30;
        }
        g2d.setFont(new Font(null, Font.ITALIC, 20));
        g2d.drawString("SELL", 620, 810);
        g2d.drawString("BUY", 620, 845);
        currentDialogue = """
                If you come to me,you need to sell or buy something\s
                 what do you to want,
                 to buy or sell?""";
        if (choose == 1) {
            cursor(405, 525, 70, 70, false);
            g2d.setColor(Color.white);
            g2d.drawString(">", 600, 810);
        } else if (choose == 2) {
            cursor(60, 525, 60, 60, true);
            g2d.setColor(Color.white);
            g2d.drawString(">", 600, 845);
        }
        charactersInWindows(320, 870, 45, 45, gp.player.dinoLeftUpRight, gp.player.dinoRightUpRight);
        charactersInWindows(380, 870, 45, 45, gp.merchant.merchant1, gp.merchant.merchant2);
    }

    public void buySell() {
        items(0, 0, -100, 220, buyItems, null, false);
        items(278, 265, 250, 220, items, amount, true);
        g2d.setFont(new Font(null, Font.ITALIC, 15));
        g2d.drawString("Your inventory", 615, 520);
        g2d.drawString("Merchant stuff", 75, 520);
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
        if (gp.state == gp.chestState) {
            chestScreen();
        }
        if (messageShow && gp.player.index != 999) {
            g2d.setFont(new Font(null, Font.BOLD | Font.ITALIC, 20));
            g2d.setColor(Color.white);
            g2d.drawString(message, 10, 520);
        }
        if (counter > 5) {
            messageShow = false;
            message = "";
        }
    }

    private void chestScreen() {
        dialoguesInventoryScreen(144, 336, 336, 48);
        chestScreenInfo();
    }

    public String randomObj() {
        int key = 1;
        int calculator = 2;
        int lantern = 3;
        int coin = 4;
        int manaCoin = 5;
        int diamond = 6;
        int empty = 7;
        listOfObjectStr.add(key);
        listOfObjectStr.add(calculator);
        listOfObjectStr.add(lantern);
        listOfObjectStr.add(coin);
        listOfObjectStr.add(manaCoin);
        listOfObjectStr.add(diamond);
        listOfObjectStr.add(empty);
        Random random = new Random();
        int randomObj = listOfObjectStr.get(random.nextInt(listOfObjectStr.size()));
        randomName = switch (randomObj) {
            case 1 -> "Key";
            case 2 -> "Calculator";
            case 3 -> "Lantern";
            case 4 -> "Coins";
            case 5 -> "Manacoins";
            case 6 -> "Diamond";
            case 7 -> "Chest Is Empty";
            default -> throw new IllegalStateException("Unexpected value: " + randomObj);
        };
        return randomName;
    }

    private void chestScreenInfo() {
        if (keys.chestOpen) {
            keys.chestOpen = false;
        }
        String randomName = randomObj();
        switch (randomName) {
            case "Key" -> randomImage = keyObj.image;
            case "Coins" -> randomImage = coin.image;
            case "Calculator" -> randomImage = calculator.image;
            case "Lantern" -> randomImage = lantern.image;
            case "Manacoins" -> randomImage = mana.image;
            case "Diamond" -> randomImage = diamondObj.image;
            case "" -> g2d.drawString("No Item In This Chest", 305, 410);
        }
        g2d.setColor(Color.white);
        g2d.setFont(new Font(null, Font.ITALIC, 20));
        g2d.drawImage(randomImage, 250, 385, 50, 50, null);
    }

    private void progressBar(Graphics2D g2d, double progress) {
        int x = 10;
        int y = 10;
        int height = 50;
        int maxBarWidth = gp.screenWidth - 50;
        int width = (int) (maxBarWidth * progress);
        g2d.setColor(new Color(0, 26, 0, 190));
        g2d.fillRoundRect(x, y, maxBarWidth, height, 25, 25);
        g2d.setColor(new Color(0, 0, 0, 170));
        g2d.fillRoundRect(x + 5, y + 5, maxBarWidth - 10, height - 10, 30, 30);
        if (width > 10) {
            g2d.setColor(new Color(0, 255, 0));
            g2d.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 30, 30);
        }
        g2d.setFont(new Font("MV Boli", Font.BOLD, 25));
        g2d.setColor(Color.white);
        String progressText = String.format("%.1f%%", progress * 100);
        g2d.drawString(progressText, (gp.screenWidth - 60) / 2, 45);
    }

    private void information() {
        g2d.setColor(new Color(102, 255, 102, 170));
        g2d.fillRoundRect(375, 750, 360, 50, 25, 25);
        g2d.setColor(new Color(179, 255, 179, 170));
        g2d.fillRoundRect(380, 755, 355, 40, 30, 30);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        g2d.drawString("X:" + gp.player.worldX, 420, 780);
        g2d.drawString("Y:" + gp.player.worldY, 520, 780);
        g2d.drawString("FPS:" + gp.count, 620, 780);
    }
}
