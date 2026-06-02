package Totorial.RPG.Obj;

import Totorial.RPG.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class House extends SupObject{
    GamePanel gp;
    public boolean visited = false;
    public House(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "HOUSE";
        getHouseImages();
        image = nonVisitedHouse;
    }

    private void getHouseImages() {
        try{
            visitedHouse = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("house.png")));
            nonVisitedHouse = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("enterhouse.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
