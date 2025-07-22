package commands;

import livingThings.Hero;
import livingThings.Origin;

public class AttackLogic {

	public static String showAttackMassage(Hero hero, Origin selectedEnemy) {
		String targetName = selectedEnemy.getName();
		System.out.println("敵の体力"+selectedEnemy.getHp());
		int atk = hero.getAtk();
		System.out.println(targetName + "に" + atk + "のダメージ");
		String message = targetName + "に" + atk + "のダメージ";
		
		selectedEnemy.setHp(selectedEnemy.getHp() - hero.getAtk());
		System.out.println("敵の体力"+selectedEnemy.getHp());
		return message;
	}



}
