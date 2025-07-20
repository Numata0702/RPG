package livingThings;

import java.awt.image.BufferedImage;

public class Origin {
	int hp;
	int atk;
	int exp;
	String name = "キャラクター";
	public BufferedImage img;
	String[] attackMessage = { "ダミー" };
	String encounterMessage = "ダミー";
	int enemyVlue;
	private int imageWidth; 

	public BufferedImage getImg() {
		return this.img;
	}

	public int getEnemyValue() {
		return this.enemyVlue;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void attack(Origin o) throws Exception {
		o.setHp(o.getHp() - this.atk);
	}

	public int getExp() {
		return this.exp;
	}

	public String getName() {
		return this.name; // privateフィールドにアクセス
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return this.hp; // privateフィールドにアクセス
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return this.atk;
	}

	public boolean is_alive() {
		if (this.hp <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getLengthOfAttackMessage() {
		int lengthOfAttackMessage = this.attackMessage.length;
		return lengthOfAttackMessage;
	}

	public String getAttackMessage(int x) {
		return this.attackMessage[x];
	}

	public String getEncounterMessage() {
		return this.encounterMessage;
	}

	public int getImageWidth() {
		return this.imageWidth;
	}
}
