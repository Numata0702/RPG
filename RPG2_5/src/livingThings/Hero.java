package livingThings;

import java.awt.image.BufferedImage;

public class Hero extends Origin {
	BufferedImage heroImg;
	
	public int[][] level_status = {
			//要素：次のレベル必要経験値[0],ATK[1],SPD[2],HP[3],MP[4],覚えるスキル(skill)配列の番号[5]
			{ 7, 3, 3, 15, 0, 0 },
			{ 23, 4, 3, 22, 0, 0 },
			{ 47, 6, 5, 24, 5, 1 }, //このレベルになったときに回復技覚える
			{ 110, 6, 7, 31, 16, 1 }, //攻撃技覚える
			{ 220, 10, 9, 35, 20, 2 },
			{ 220, 10, 9, 35, 20, 2 } };








	public void setLevel_status(int[][] level_status) {
		this.level_status = level_status;
	}



	public BufferedImage getHeroImg() {
		return heroImg;
	}

	public String[][] skill = {
			{ "攻撃スキル", "true" },
			{ "回復スキル", "false" },
			{ "確定で倒すスキル", "false" } };

	String name;
	int level = 1;
	int exp = 0;
	int expNextLevel = level_status[level - 1][0];
	int atk = level_status[level - 1][1];
	int spd = level_status[level - 1][2];
	int hp = level_status[level - 1][3];
	int mp = level_status[level - 1][4];
	
	private int arrayPlayerX = 4;
	private int arrayPlayerY = 4;

//	ArrayList<Item> belongings = new ArrayList<>();

	boolean maxLevel = false;

	
//---------------------ここからメソッド----------------------------------------------
	public Hero(String name) {
		this.name = name;
	}
	

	
	public void attack(Origin o) {
	System.out.println("たたかうメソッド");
//		o.setHp(o.getHp()-this.atk);
//		System.out.println(o.getName()+"に"+this.atk+"のダメージを与えた！");
//		System.out.println(o.getName()+"の残り体力は"+o.getHp());
	}
	public void attack() {
	System.out.println("戦うメソッド");
	}
	
	public void run() {
	System.out.println("逃げるメソッド");
	}

	public void skill_A() {
	System.out.println("スキルAメソッド");
	}
	public void skill_B() {
	System.out.println("スキルBメソッド");
	}
	
	public void item() {
	System.out.println("アイテムメソッド");
	}
	

	
	
	
	
	public String getName() {
		return this.name;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getExp() {
		return this.exp;
	}
	
	public int getExpNextLevel() {
		return this.expNextLevel;
	}
	public int getAtk() {
		return this.atk;
	}

	public int getSpd() {
		return this.spd;
	}

	public int getHp() {
		return this.hp;
	}
	public int getMp() {
		return this.mp;
	}
	public int getArrayPlayerX() {
		return this.arrayPlayerX;
	}
	
	public int getArrayPlayerY() {
		return this.arrayPlayerY;
	}
	
	public boolean getMaxLevel() {
		return this.maxLevel;
	}
	
	public void setMaxLevel(boolean trueOrFalse) {
		this.maxLevel = trueOrFalse;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public void setExpNextLevel(int expNextLevel) {
		this.expNextLevel = expNextLevel;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public void setSpd(int spd) {
		this.spd = spd;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public void setArrayPlayerX(int arrayPlayerX) {
		this.arrayPlayerX = arrayPlayerX;
	}
	
	public void setArrayPlayerY(int arrayPlayerY) {
		this.arrayPlayerY = arrayPlayerY;
	}
	
	public void setMp(int mp) {
		this.mp = mp;
	}
	
	
	public String[][] getSkill() {
		return skill;
	}


	public void setSkill(String[][] skill) {
		this.skill = skill;
	}

	public boolean is_alive() {
		if (this.hp <= 0) {
			return false;
		} else {
			return true;
		}
	}
}
