package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import livingThings.Hero;
import main.Visual.gamePhase;

public class KeyControl implements KeyListener {

	private String[][] skillList;

	private Visual visual;

	public KeyControl(Visual visual) {
		this.visual = visual;
	}

	public int returnCommandIndex(int commandIndex, int select) {
		int index = commandIndex + select;

		if (select < 0) {
			if (index < 0 || (commandIndex == 2 && select == -1)) {
				index = commandIndex;
			}
			return index;
		} else {
			if (index > visual.commands.length - 1 || (commandIndex == 1 && select == 1)) {
				index = commandIndex;
			}
			return index;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (visual.currentPhase) {
		case WAITING, WAITING_BATTLE, SELECT_SKILL, SELECT_ITEM, SHOWING_MESSAGES_BATTLE -> {

		}

		case BATTLE_COMMAND -> {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT -> {
				int select = -1;
				visual.commandIndex = returnCommandIndex(visual.commandIndex, select);
				visual.repaint();
			}
			case KeyEvent.VK_RIGHT -> {
				int select = 1;
				visual.commandIndex = returnCommandIndex(visual.commandIndex, select);
				visual.repaint();
			}
			case KeyEvent.VK_UP -> {
				int select = -2;
				visual.commandIndex = returnCommandIndex(visual.commandIndex, select);
				visual.repaint();
			}
			case KeyEvent.VK_DOWN -> {
				int select = 2;
				visual.commandIndex = returnCommandIndex(visual.commandIndex, select);
				visual.repaint();
			}

			case KeyEvent.VK_ENTER -> {
				switch (visual.commandIndex) {
				//バトルコマンド0（たたかう）
				case 0 -> {
					visual.hero.attack();
					visual.SetArrowPosition(visual.enemyList, visual.enemyIndex);
					visual.repaint();
					visual.currentPhase = gamePhase.SELECT_ENEMY;
				}
				//バトルコマンド1（スキル）
				case 1 -> {
					visual.hero.skill_A();
					skillList = visual.hero.getSkill();
					System.out.println("今使えるスキルは");
					for (String[] row : skillList) {
						if (Boolean.parseBoolean(row[1])) {
							System.out.println(row[0]);
						}
					}
				}
				//バトルコマンド2（逃げる）
				case 2 -> {
					visual.hero.run();
					// showMessageGradually("逃げ切れた");

					visual.commandIndex = 0;
					visual.currentPhase = gamePhase.MAP_MOVE;
				}
				//バトルコマンド3（アイテム）
				case 3 -> {
					visual.hero.item();
				}
				}
				visual.repaint();
			}
			}
		}

		case SELECT_ENEMY -> {

			switch (e.getKeyCode()) {
			//左右ボタンでenemyIndexを更新
			case KeyEvent.VK_RIGHT -> {
				//まず、インデックスを+１
				visual.enemyIndex += 1;
				//敵が倒されていたらさらに+1

				if (visual.enemyList.size() <= visual.enemyIndex) {
					visual.enemyIndex -= 1;
				}
				visual.SetArrowPosition(visual.enemyList, visual.enemyIndex);
				visual.repaint();
			}
			//左右ボタンでenemyIndexを更新
			case KeyEvent.VK_LEFT -> {
				visual.enemyIndex += -1;

				if (visual.enemyIndex < 0) {
					visual.enemyIndex += 1;
				}
				visual.SetArrowPosition(visual.enemyList, visual.enemyIndex);
				visual.repaint();
			}
			case KeyEvent.VK_ENTER -> {
				//体力０の相手を選択したらスルー。
				if (visual.enemyList.get(visual.enemyIndex).getHp() <= 0) {
					return;
				}

				System.out.println("選んだ敵の残り体力" + visual.enemyList.get(visual.enemyIndex).getHp());
				String message = commands.AttackLogic.showAttackMassage(visual.hero,
						visual.enemyList.get(visual.enemyIndex));
				//				visual.showMessageGradually(message, 3);
				visual.currentPhase = gamePhase.BATTLE_COMMAND;
				//敵の体力を減らし、攻撃時メッセージを返して出力
				commands.AttackLogic.showAttackMassage(visual.hero, visual.enemyList.get(visual.enemyIndex));

				//ここに敵の状態判定・倒した場合は経験値の獲得＋
				//レベルアップ判定+画像の書き換え//
				if (visual.enemyList.get(visual.enemyIndex).getHp() <= 0) {
					System.out.println(visual.enemyList.get(visual.enemyIndex).getName() + "を倒した");

					visual.hero.setExp(visual.hero.getExp() + visual.enemyList.get(visual.enemyIndex).getExp());
					System.out.println(visual.enemyList.get(visual.enemyIndex).getExp() + "の経験値を獲得");

					if (visual.hero.getExp() >= visual.hero.getExpNextLevel() && visual.hero.getMaxLevel() != true) {
						checkLevel(visual.hero);
					}

					visual.setEnemyImg(enemyControler.EnemyImgSet.SetImg(visual.enemyList));
					visual.repaint();
					
					
					boolean LivingEnemy = enemyControler.EnemyCheckLogic.IsLivingEnemys(visual.enemyList);
					System.out.println(LivingEnemy);
					if (LivingEnemy == false) {
						//					visual.showMessageGradually("敵をすべて倒した！", 2);
						visual.enemyIndex = 0;
						visual.currentPhase = gamePhase.MAP_MOVE;
					}
					
					if (visual.hero.getMaxLevel() == true) {
						System.out.println("最大Levelにつき経験値は獲得しない");
						
						LivingEnemy = enemyControler.EnemyCheckLogic.IsLivingEnemys(visual.enemyList);
						System.out.println(LivingEnemy);
						if (LivingEnemy == false) {
							//					visual.showMessageGradually("敵をすべて倒した！", 2);
							visual.enemyIndex = 0;
							visual.currentPhase = gamePhase.MAP_MOVE;
						}
						break;
					}

					
					
				}


			}
			}
		}
		//		}

		case SHOWN_MESSAGES_BATTLE -> {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				new javax.swing.Timer(10, evt -> {
					visual.currentPhase = gamePhase.BATTLE_COMMAND;
					((javax.swing.Timer) evt.getSource()).stop(); // タイマーを止める
				}).start();
			}
		}

		case MAP_MOVE -> {
			visual.currentPhase = gamePhase.WAITING;
			int dx = 0, dy = 0;
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP -> dy = -1;
			case KeyEvent.VK_DOWN -> dy = 1;
			case KeyEvent.VK_LEFT -> dx = -1;
			case KeyEvent.VK_RIGHT -> dx = 1;
			default -> {
				visual.currentPhase = gamePhase.MAP_MOVE;
				return;
			}
			}
			visual.smoothMove(dx, dy);
		}
		}
	}

	public static void checkLevel(Hero h) {

		if (h.getMaxLevel() != true) {
			h.setLevel(h.getLevel() + 1);

			h.setExpNextLevel(h.level_status[h.getLevel() - 1][0]);
			h.setAtk(h.level_status[h.getLevel() - 1][1]);
			h.setSpd(h.level_status[h.getLevel() - 1][2]);
			h.setHp(h.level_status[h.getLevel() - 1][3] - (h.level_status[h.getLevel() - 2][3] - h.getHp()));
			h.setMp(h.level_status[h.getLevel() - 1][4] - (h.level_status[h.getLevel() - 2][4] - h.getMp()));

			System.out.println(" ");
			String text = "レベルアップ！ レベルが" + h.getLevel() + "になりました！\n";

			if (h.level_status[h.getLevel() - 1][5] != h.level_status[h.getLevel() - 2][5]) {
				text = "新しいスキル" + h.getSkill()[h.level_status[h.getLevel() - 1][5]][0] + "を覚えた！\n";
				h.skill[h.level_status[h.getLevel() - 1][5]][1] = "true";
			}
			if (h.getLevel() == h.level_status.length) {
				h.setMaxLevel(true);
			}

			if (h.getExp() >= h.getExpNextLevel()) {
				checkLevel(h);
			}
		}
	}
}