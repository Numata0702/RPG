package battle;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import livingThings.Origin;
//敵が入った配列から、体力が０以下でないキャラだけ描写
public class EnemyImgSet {

    public static BufferedImage SetImg(ArrayList<Origin> enemyList) {
        int width = 0;
        int height = 256;

        for (Origin enemy : enemyList) {
            width += enemy.getImg().getWidth();
        }


        BufferedImage enemyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = enemyImg.createGraphics();

        // 全体を透明で初期化
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.SrcOver);

        int startX = 0;
        for (Origin enemy : enemyList) {
            int imgWidth = enemy.getImg().getWidth();

            
            if (enemy.getHp() > 0) {
                g2.drawImage(enemy.getImg(), startX, 0, null);
            } else {
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(startX, 0, imgWidth, height);
                g2.setComposite(AlphaComposite.SrcOver); // 忘れずに戻す
            }

            startX += imgWidth;
        }

        g2.dispose();
        return enemyImg;
    }
}