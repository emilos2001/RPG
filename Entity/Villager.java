package Totorial.RPG.Entity;

import Totorial.RPG.GamePanel;
import Totorial.RPG.Keys;
import Totorial.RPG.Menu.MyJdbc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Villager extends Entity {
    Keys keys;
    MyJdbc myJdbc;

    public Villager(GamePanel gp) {
        super(gp);
        keys = new Keys(gp);
        myJdbc = new MyJdbc();
        npcDirection = "right";
        speed = 1;
        name = "VILLAGER";
        getVillagerImages();
    }

    public void getVillagerImages() {
        try {
            npcUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_up_1.png")));
            npcUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_up_2.png")));
            npcDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_down_1.png")));
            npcDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_down_2.png")));
            npcLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_left_1.png")));
            npcLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_left_2.png")));
            npcRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_right_1.png")));
            npcRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_right_2.png")));
            npcUpTransparent1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_up_1_transparent.png")));
            npcUpTransparent2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_up_2_transparent.png")));
            npcDownTransparent1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_down_1_transparent.png")));
            npcDownTransparent2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_down_2_transparent.png")));
            npcLeftTransparent1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_left_1_transparent.png")));
            npcLeftTransparent2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_left_2_transparent.png")));
            npcRightTransparent1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_right_1_transparent.png")));
            npcRightTransparent2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("oldman_right_2_transparent.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue(String[][] questions, String[][][] answers, boolean[][][] isCorrect, String currentPin) {
        Map<String, List<MyJdbc.AnswerData>> data = myJdbc.getQuestionsAndAnswer(currentPin);
        List<Map.Entry<String, List<MyJdbc.AnswerData>>> map = new ArrayList<>(data.entrySet());
        int entryCounter = 0;
        int i = gp.villager.dialogueSet;
        for (int q = 0; q < questions[i].length; q++) {
            if (entryCounter < map.size()) {
                Map.Entry<String, List<MyJdbc.AnswerData>> currentEntry = map.get(entryCounter);
                questions[i][q] = currentEntry.getKey();
                List<MyJdbc.AnswerData> currentAnswers = currentEntry.getValue();
                for (int a = 0; a < 5; a++) {
                    if (a < currentAnswers.size()) {
                        answers[i][q][a] = currentAnswers.get(a).text();
                        isCorrect[i][q][a] = currentAnswers.get(a).isCorrect();
                    } else {
                        answers[i][q][a] = null;
                        isCorrect[i][q][a] = false;
                    }
                }
                entryCounter++;
            } else {
                questions[i][q] = null;
            }
        }
    }

    public void update() {
        collision = false;
        setAction();
        gp.collisions.checkTileForNpcs(this);
    }

    public void draw(Graphics2D g2d, GamePanel gp) {
        update();
        if (!collision) {
            switch (npcDirection) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = talked ? npcUpTransparent1 : npcUp1;
                        worldY -= speed;
                    } else if (spriteNum == 2) {
                        image = talked ? npcUpTransparent2 : npcUp2;
                        worldY -= speed;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = talked ? npcDownTransparent1 : npcDown1;
                        worldY += speed;
                    } else if (spriteNum == 2) {
                        image = talked ? npcDownTransparent2 : npcDown2;
                        worldY += speed;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        image = talked ? npcLeftTransparent1 : npcLeft1;
                        worldX -= speed;
                    } else if (spriteNum == 2) {
                        image = talked ? npcLeftTransparent2 : npcLeft2;
                        worldX -= speed;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        image = talked ? npcRightTransparent1 : npcRight1;
                        worldX += speed;
                    } else if (spriteNum == 2) {
                        image = talked ? npcRightTransparent2 : npcRight2;
                        worldX += speed;
                    }
                }
            }
            spriteCount();
        }
        charactersImagesRenders(g2d, image);
    }
}