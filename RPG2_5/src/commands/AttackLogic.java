package commands;

import livingThings.Hero;
import livingThings.Origin;

public class AttackLogic {

	public static String showAttackMassage(Hero hero, Origin selectedEnemy) {
		String targetName = selectedEnemy.getName();
		int atk = hero.getAtk();
		String message = targetName + "に" + atk + "のダメージ";
		selectedEnemy.setHp(selectedEnemy.getHp() - hero.getAtk());
		return message;
	}



}
