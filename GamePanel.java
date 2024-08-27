package Totorial.RPG;

import Totorial.RPG.Entity.Entity;
import Totorial.RPG.Entity.Player;
//import Totorial.RPG.Menu.MenuUI;
import Totorial.RPG.Menu.MenuUI;
import Totorial.RPG.Obj.Chest;
import Totorial.RPG.Obj.Lantern;
import Totorial.RPG.Obj.SupObject;
import Totorial.RPG.Titles.Collisions;
import Totorial.RPG.Titles.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable, MouseMotionListener, MouseListener {
    public final int maxWorldCol = 65;
    public final int maxWorldRow = 65;
    public final int maxNumberMaps = 3;
    public final int exteriorMap = 0;
    public final int houseMap = 1;
    public final int castleMap = 2;
    public final int maxScreenRow = 20;
    public final int maxScreenCol = 16;
    public final int playState = 1;
    public final int dialogueStateWithVillagers = 2;
    public final int dialogueStateWithMerchant = 3;
    public final int information = 4;
    public final int inventoryState = 5;
    public final int dialogueScreen = 6;
    public final int chestState = 7;
    final int originalSize = 16;
    final int scale = 3;
    public final int size = originalSize * scale;//48
    public final int screenWidth = size * maxScreenCol;//768
    public final int screenHeight = size * maxScreenRow;//576
    public int currentMap;
    public Collisions collisions = new Collisions(this);
    public Manager manager = new Manager(this);
    public Keys keys = new Keys(this);
    public Thread gameThread;
    public MenuUI menu = new MenuUI(this);
    public Player player = new Player(this, keys);
    public Entity[][] entities;
    public SupObject[][] supObject;
    public Chest chest;
    public UI ui;
    public AssetSetter assetSetter = new AssetSetter(this);
    public LightEffect lightEffect;
    public Lantern lantern;
    public int state;
    BufferedImage temp;
    Graphics2D g2d;
    int fps = 60;
    int count = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keys);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setFocusable(true);
        chest = new Chest(this);
        lightEffect = new LightEffect(this);
        lantern = new Lantern(this);
        entities = new Entity[maxNumberMaps][50];
        supObject = new SupObject[maxNumberMaps][100];
        ui = new UI(this, keys);
    }


    public void setupGame() {
        assetSetter.setIronDoor();
        assetSetter.setHouses();
        assetSetter.setChestAndCoin();
        assetSetter.setNpc();
        state = playState;
        temp = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) temp.getGraphics();

    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double interval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                count++;
            }
            if (timer >= 1000000000) {
                count = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (state == playState
                || state == information) {
            player.update();
            /*if (keys.joinButtonClicked) {
                player.update();
            } /*lse {
                player.cameraMove();
            }*/
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        manager.draw(g2);
        for (SupObject object : supObject[currentMap]) {
            if (object != null) {
                object.draw(g2, this);
            }
        }
        for (Entity entity : entities[currentMap]) {
            if (entity != null) {
                entity.draw(g2, this);
            }
        }

        if (currentMap == castleMap) {
            lightEffect.draw(g2);
            if (player.lanternBuy) {
                lightEffect.setLight();
            }
        }
        player.draw(g2);
        // menu.isMenu = false;
        /*if (keys.joinButtonClicked) {
            menu.isMenu = false;
            keys.join = false;
            player.draw(g2);
        }*/
        ui.draw(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        if (x > 30 && x < 130) {
            keys.join();
        }
        if (x > 230 && x < 330) {
            keys.createGame();
        }
        if (x > 430 && x < 530) {
            keys.signIn();
        }
        if (x > 630 && x < 730) {
            keys.signUp();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
       updateMousePosition(e);
    }

    private void updateMousePosition(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (y > 340 && y < 440) {
            if (x > 30 && x < 130) {
                menu.x = 30;
            }
            if (x > 230 && x < 330) {
                menu.x = 230;
            }
            if (x > 430 && x < 530) {
                menu.x = 430;
            }
            if (x > 630 && x < 730) {
                menu.x = 630;
            }
        }
    }
}