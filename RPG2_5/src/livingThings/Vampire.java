package livingThings;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Visual;

public class Vampire extends Origin {
	int hp = 10;
	int atk = 5;
	int exp = 10;
	private int imageWidth; 
	BufferedImage img;
	String name;

	String attackMessage;
	int enemyVlue = 2;
	
	
	public Vampire(String name) {
		this.name = name;

		attackMessage = this.name+"の攻撃"+ this.atk+"のダメージを受けた";
		Color transparentColor = new Color(255, 0, 232);

		try {
			img = ImageIO.read(new File("音源その他のデータ\\\\ヴァンパイア.png"));
			img = Visual.makeColorTransparent(img, transparentColor);
			setZombieImage(img);
			imageWidth = img.getWidth();

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	public void attack(Hero h) {
		h.setHp(h.getHp() - this.atk);
	}

	public int getExp() {
		return this.exp;
	}
	
	public void setZombieImage(BufferedImage img) {
		this.img = img;
	}

	public BufferedImage getImg() {
		return this.img;
	}

	public String getName() {
		return this.name;
	}
	
	public int getHp() {
		return this.hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getAttackMessage(int x) {
		return this.attackMessage;
	}
	
	public String getEncounterMessage() {
		return this.encounterMessage;
	}
	public int getEnemyValue() {
		return this.enemyVlue;
	}
	
	public int getImageWidth() {
		return this.imageWidth;
	}
}
