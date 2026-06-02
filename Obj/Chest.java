package Totorial.RPG.Obj;

import Totorial.RPG.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;


public class Chest extends SupObject {
    GamePanel gp;
    public boolean opened = false;
    public Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "CHEST";
        getChestImages();
        image = closedChest;
    }

    public void getChestImages() {
        try {
            closedChest = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("chest.png")));
            openChest = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("chest_opened.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}