package enemyControler;

import java.util.ArrayList;

import livingThings.Origin;

public class EnemyCheckLogic {

	//敵が１体でも残っていたらTrueを返す
	public static boolean IsLivingEnemys(ArrayList<Origin> enemyList) {
		int livingEnemy = 0;

		for (Origin enemy : enemyList) {
			if (enemy.getHp() > 0) {
				livingEnemy += 1;
			}
		}
		return (livingEnemy > 0);
	}
}
