package Totorial.RPG;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LightEffect {
    GamePanel gp;
    BufferedImage darkness;
    Color[] colors;
    float[] distance;
    RadialGradientPaint rPaint;

    public LightEffect(GamePanel gp) {
        this.gp = gp;
        setDark();
    }

    public void setDark() {
        darkness = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darkness.getGraphics();
        g2d.setColor(new Color(0, 0, 0, 0.95f));
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    public void setLight() {
        darkness = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darkness.getGraphics();
        int centerX = 265;
        int centerY = 168;
        colors = new Color[7];
        distance = new float[7];
        colors[0] = new Color(0, 0, 0, 0.25f);
        colors[1] = new Color(0, 0, 0, 0.5f);
        colors[2] = new Color(0, 0, 0, 0.75f);
        colors[3] = new Color(0, 0, 0, 0.80f);
        colors[4] = new Color(0, 0, 0, 0.85f);
        colors[5] = new Color(0, 0, 0, 0.90f);
        colors[6] = new Color(0, 0, 0, 0.95f);
        distance[0] = 0.25f;
        distance[1] = 0.5f;
        distance[2] = 0.75f;
        distance[3] = 0.80f;
        distance[4] = 0.85f;
        distance[5] = 0.90f;
        distance[6] = 0.95f;
        rPaint = new RadialGradientPaint(centerX, centerY, 150f, distance, colors);
        g2d.setPaint(rPaint);
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2d.dispose();
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(darkness, 0, 0, null);
    }
}