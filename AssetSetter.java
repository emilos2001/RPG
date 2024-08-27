package Totorial.RPG;

import Totorial.RPG.Entity.Merchant;
import Totorial.RPG.Entity.Villager;
import Totorial.RPG.Obj.*;

public class AssetSetter {
    GamePanel gp;
    Keys key = new Keys(gp);

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setIronDoor() {
        gp.supObject[gp.exteriorMap][13] = new IronDoor(gp);
        gp.supObject[gp.exteriorMap][13].worldX = 2015;
        gp.supObject[gp.exteriorMap][13].worldY = 424;
        gp.supObject[gp.castleMap][14] = new Stairs(gp);
        gp.supObject[gp.castleMap][14].worldX = 530;
        gp.supObject[gp.castleMap][14].worldY = 189;
    }

    public void setHouses() {
        gp.supObject[gp.exteriorMap][3] = new House(gp);
        gp.supObject[gp.exteriorMap][3].worldX = 774;
        gp.supObject[gp.exteriorMap][3].worldY = 143;
        gp.supObject[gp.exteriorMap][4] = new House(gp);
        gp.supObject[gp.exteriorMap][4].worldX = 348;
        gp.supObject[gp.exteriorMap][4].worldY = 720;
        gp.supObject[gp.exteriorMap][5] = new House(gp);
        gp.supObject[gp.exteriorMap][5].worldX = 338;
        gp.supObject[gp.exteriorMap][5].worldY = 880;
        gp.supObject[gp.exteriorMap][6] = new House(gp);
        gp.supObject[gp.exteriorMap][6].worldX = 386;
        gp.supObject[gp.exteriorMap][6].worldY = 1436;
        gp.supObject[gp.exteriorMap][7] = new House(gp);
        gp.supObject[gp.exteriorMap][7].worldX = 1058;
        gp.supObject[gp.exteriorMap][7].worldY = 1888;
        gp.supObject[gp.exteriorMap][8] = new House(gp);
        gp.supObject[gp.exteriorMap][8].worldX = 2258;
        gp.supObject[gp.exteriorMap][8].worldY = 1904;
        gp.supObject[gp.exteriorMap][9] = new House(gp);
        gp.supObject[gp.exteriorMap][9].worldX = 998;
        gp.supObject[gp.exteriorMap][9].worldY = 1108;
        gp.supObject[gp.exteriorMap][10] = new House(gp);
        gp.supObject[gp.exteriorMap][10].worldX = 2546;
        gp.supObject[gp.exteriorMap][10].worldY = 191;
        gp.supObject[gp.exteriorMap][11] = new House(gp);
        gp.supObject[gp.exteriorMap][11].worldX = 1298;
        gp.supObject[gp.exteriorMap][11].worldY = 300;
        gp.supObject[gp.houseMap][12] = new Door(gp);
        gp.supObject[gp.houseMap][12].worldX = 1250;
        gp.supObject[gp.houseMap][12].worldY = 815;
    }

    public void setChestAndCoin() {
        gp.supObject[gp.exteriorMap][0] = new Coin(gp);
        gp.supObject[gp.exteriorMap][0].worldX = 478;
        gp.supObject[gp.exteriorMap][0].worldY = 144;
        gp.supObject[gp.houseMap][1] = new Chest(gp);
        gp.supObject[gp.houseMap][1].worldX = 1258;
        gp.supObject[gp.houseMap][1].worldY = 344;
        gp.supObject[gp.castleMap][2] = new Chest(gp);
        gp.supObject[gp.castleMap][2].worldX = 960;
        gp.supObject[gp.castleMap][2].worldY = 999;
        gp.supObject[gp.castleMap][3] = new Chest(gp);
        gp.supObject[gp.castleMap][3].worldX = 1174;
        gp.supObject[gp.castleMap][3].worldY = 1140;
        gp.supObject[gp.castleMap][4] = new Chest(gp);
        gp.supObject[gp.castleMap][4].worldX = 1506;
        gp.supObject[gp.castleMap][4].worldY = 292;
        gp.supObject[gp.castleMap][5] = new Chest(gp);
        gp.supObject[gp.castleMap][5].worldX = 1258;
        gp.supObject[gp.castleMap][5].worldY = 21812;
        gp.supObject[gp.castleMap][5].worldX = 1046;
        gp.supObject[gp.castleMap][5].worldY = 1652;
        gp.supObject[gp.castleMap][6] = new Chest(gp);
        gp.supObject[gp.castleMap][6].worldX = 486;
        gp.supObject[gp.castleMap][6].worldY = 1164;
        gp.supObject[gp.castleMap][7] = new Chest(gp);
        gp.supObject[gp.castleMap][7].worldX = 610;
        gp.supObject[gp.castleMap][7].worldY = 1832;
        gp.supObject[gp.castleMap][8] = new Chest(gp);
        gp.supObject[gp.castleMap][8].worldX = 826;
        gp.supObject[gp.castleMap][8].worldY = 2292;
        gp.supObject[gp.houseMap][9] = new Coin(gp);
        gp.supObject[gp.houseMap][9].worldX = 1010;
        gp.supObject[gp.houseMap][9].worldY = 754;
        gp.supObject[gp.exteriorMap][10] = new Coin(gp);
        gp.supObject[gp.exteriorMap][10].worldX = 674;
        gp.supObject[gp.exteriorMap][10].worldY = 1776;
        gp.supObject[gp.exteriorMap][11] = new Chest(gp);
        gp.supObject[gp.exteriorMap][11].worldX = 1050;
        gp.supObject[gp.exteriorMap][11].worldY = 1744;
        gp.supObject[gp.exteriorMap][12] = new Coin(gp);
        gp.supObject[gp.exteriorMap][12].worldX = 674;
        gp.supObject[gp.exteriorMap][12].worldY = 1776;
        gp.supObject[gp.castleMap][13] = new Coin(gp);
        gp.supObject[gp.castleMap][13].worldX = 622;
        gp.supObject[gp.castleMap][13].worldY = 884;
        gp.supObject[gp.houseMap][14] = new Coin(gp);
        gp.supObject[gp.houseMap][14].worldX = 1142;
        gp.supObject[gp.houseMap][14].worldY = 656;
        gp.supObject[gp.castleMap][15] = new Coin(gp);
        gp.supObject[gp.castleMap][15].worldX = 2354;
        gp.supObject[gp.castleMap][15].worldY = 620;
        gp.supObject[gp.castleMap][16] = new Coin(gp);
        gp.supObject[gp.castleMap][16].worldX = 998;
        gp.supObject[gp.castleMap][16].worldY = 2156;
        gp.supObject[gp.castleMap][17] = new Coin(gp);
        gp.supObject[gp.castleMap][17].worldX = 710;
        gp.supObject[gp.castleMap][17].worldY = 1136;
        gp.supObject[gp.castleMap][17] = new Coin(gp);
        gp.supObject[gp.castleMap][17].worldX = 790;
        gp.supObject[gp.castleMap][17].worldY = 1748;
        gp.supObject[gp.castleMap][18] = new Coin(gp);
        gp.supObject[gp.castleMap][18].worldX = 998;
        gp.supObject[gp.castleMap][18].worldY = 1820;
        gp.supObject[gp.houseMap][27] = new KeyObj(gp);
        gp.supObject[gp.houseMap][27].worldX = 954;
        gp.supObject[gp.houseMap][27].worldY = 338;
        gp.supObject[gp.houseMap][28] = new Coin(gp);
        gp.supObject[gp.houseMap][28].worldX = 1010;
        gp.supObject[gp.houseMap][28].worldY = 754;
        gp.supObject[gp.houseMap][27] = new KeyObj(gp);
        gp.supObject[gp.houseMap][27].worldX = 954;
        gp.supObject[gp.houseMap][27].worldY = 338;
        gp.supObject[gp.houseMap][27] = new KeyObj(gp);
        gp.supObject[gp.houseMap][27].worldX = 1530;
        gp.supObject[gp.houseMap][27].worldY = 338;
        gp.supObject[gp.houseMap][28] = new Coin(gp);
        gp.supObject[gp.houseMap][28].worldX = 1010;
        gp.supObject[gp.houseMap][28].worldY = 754;
        gp.supObject[gp.houseMap][29] = new KeyObj(gp);
        gp.supObject[gp.houseMap][29].worldX = 954;
        gp.supObject[gp.houseMap][29].worldY = 338;
        gp.supObject[gp.houseMap][28] = new Coin(gp);
        gp.supObject[gp.houseMap][28].worldX = 1010;
        gp.supObject[gp.houseMap][28].worldY = 754;
    }

    public void setNpc() {
        //villagers
        gp.entities[gp.exteriorMap][0] = new Villager(gp);
        gp.entities[gp.exteriorMap][0].worldX = 682;
        gp.entities[gp.exteriorMap][0].worldY = 188;
        gp.entities[gp.exteriorMap][1] = new Villager(gp);
        gp.entities[gp.exteriorMap][1].worldX = 1922;
        gp.entities[gp.exteriorMap][1].worldY = 1184;
        gp.entities[gp.exteriorMap][2] = new Villager(gp);
        gp.entities[gp.exteriorMap][2].worldX = 2238;
        gp.entities[gp.exteriorMap][2].worldY = 248;
        gp.entities[gp.houseMap][3] = new Villager(gp);
        gp.entities[gp.houseMap][3].worldX = 1466;
        gp.entities[gp.houseMap][3].worldY = 384;
        gp.entities[gp.castleMap][4] = new Villager(gp);
        gp.entities[gp.castleMap][4].worldX = 690;
        gp.entities[gp.castleMap][4].worldY = 196;
        gp.entities[gp.castleMap][5] = new Villager(gp);
        gp.entities[gp.castleMap][5].worldX = 1162;
        gp.entities[gp.castleMap][5].worldY = 2200;
        gp.entities[gp.castleMap][6] = new Villager(gp);
        gp.entities[gp.castleMap][6].worldX = 1586;
        gp.entities[gp.castleMap][6].worldY = 1700;
        gp.entities[gp.castleMap][7] = new Villager(gp);
        gp.entities[gp.castleMap][7].worldX = 1502;
        gp.entities[gp.castleMap][7].worldY = 753;
        gp.entities[gp.exteriorMap][14] = new Villager(gp);
        gp.entities[gp.exteriorMap][14].worldX = 998;
        gp.entities[gp.exteriorMap][14].worldY = 1916;
        //merchant1
        gp.entities[gp.exteriorMap][8] = new Merchant(gp);
        gp.entities[gp.exteriorMap][8].worldX = 1255;
        gp.entities[gp.exteriorMap][8].worldY = 475;
        gp.entities[gp.exteriorMap][9] = new Merchant(gp);
        gp.entities[gp.exteriorMap][9].worldX = 1230;
        gp.entities[gp.exteriorMap][9].worldY = 908;
        gp.entities[gp.houseMap][10] = new Merchant(gp);
        gp.entities[gp.houseMap][10].worldX = 994;
        gp.entities[gp.houseMap][10].worldY = 528;
        gp.entities[gp.castleMap][11] = new Merchant(gp);
        gp.entities[gp.castleMap][11].worldX = 2122;
        gp.entities[gp.castleMap][11].worldY = 612;
        gp.entities[gp.castleMap][12] = new Merchant(gp);
        gp.entities[gp.castleMap][12].worldX = 2366;
        gp.entities[gp.castleMap][12].worldY = 1388;
        gp.entities[gp.castleMap][13] = new Merchant(gp);
        gp.entities[gp.castleMap][13].worldX = 1066;
        gp.entities[gp.castleMap][13].worldY = 2128;
    }
}