package battle;

import java.util.ArrayList;

import livingThings.Origin;
import livingThings.Vampire;
import livingThings.Zombie;

public class EnemySet {

	public static ArrayList<Origin> Set() {
		ArrayList<Origin> enemyList = new ArrayList<>(); //敵を入れる配列
		//		int r = new java.util.Random().nextInt(6) + 1;// 1~5のランダム
		int enemyVlues = 0;
		boolean born = true;
		int numberOfZombie = 1;
		int numberOfVampire = 1;
		while (born == true) {

			int a = new java.util.Random().nextInt(3); //0 - 2のランダム

			switch (a) {
			case 1 -> {
				String name = "Zombie";
				name += (numberOfZombie );
				Origin e = new Zombie(name);
				enemyVlues += e.getEnemyValue() ;
				if (enemyVlues > 4) {
					born = false;
					break;
				}
				

				enemyList.add(e);
				numberOfZombie += 1;
			}

			case 0 -> {
				String name = "Vampire";
				name += (numberOfVampire);
				Origin e = new Vampire(name);
				enemyVlues += e.getEnemyValue() ;
				if (enemyVlues > 4) {
					born = false;
					break;
				}
		
				
				enemyList.add(e);
				numberOfVampire += 1;

			}

			default -> {
				if (enemyVlues != 0)
					born = false;
			}
			}
		}
		return enemyList;
	}
}
