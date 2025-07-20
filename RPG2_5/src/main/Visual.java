package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import enemyControler.EnemySet;
import livingThings.Hero;
import livingThings.Origin;

public class Visual extends JPanel {
	int arrowPositionX;
	int enemyIndex = 0;
	public int commandIndex = 0;
	int battlePhase = 0;
	private int moveSpeed = 4; // 1フレームあたりの移動量（滑らかさ調整）
	private int targetX, targetY;
	private int offsetX = 0;
	private int offsetY = 0;
	private int moveDX = 0;
	private int moveDY = 0;
	final int size = 64; //タイルのサイズ
	final int viewHeight = 11; // マップの縦方向のマス数
	final int viewWidth = 15; // マップの横方向のマス数
	int viewRadiusX = 7; //（描画範囲の指定に使う
	int viewRadiusY = 5; //自分のマスからY軸で2マス分

	private Timer moveTimer;
	private Timer messageTimer;
	Timer timer;

	private boolean isMoving = false;//移動中かどうか判定、1マス移動しきるまでtrueとなる

	private Font gameFont;

	private Image grass, mountain, river, tree, home, player, arrow, background;
	private BufferedImage enemyImg, spriteSheet_hero, spriteSheet_arrow;

	public final int map[][] = {
			{ 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
			{ 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 4, 4, 4, 1 },
			{ 1, 1, 1, 4, 5, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 1 },
			{ 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 2, 4, 1, 1, 1, 4, 4, 4, 4, 1 },
			{ 1, 1, 1, 1, 1, 4, 4, 4, 4, 2, 2, 2, 4, 4, 1, 1, 4, 4, 4, 1 },
			{ 4, 1, 4, 1, 1, 1, 4, 4, 4, 4, 2, 2, 2, 4, 1, 1, 4, 4, 4, 1 },
			{ 4, 4, 4, 4, 1, 1, 4, 4, 1, 1, 4, 4, 4, 4, 1, 1, 4, 4, 1, 1 },
			{ 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1 },
			{ 4, 4, 4, 4, 4, 4, 1, 1, 3, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 4 },
			{ 3, 3, 4, 4, 4, 4, 4, 3, 3, 3, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4 },
			{ 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 3 },
			{ 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 3 },
			{ 3, 3, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4 },
			{ 4, 4, 4, 1, 1, 1, 4, 4, 3, 3, 1, 1, 1, 4, 4, 4, 4, 4, 1, 1 },
			{ 4, 4, 4, 1, 4, 4, 3, 3, 3, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1 },
			{ 4, 4, 4, 4, 4, 3, 3, 3, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 4, 4, 4, 4, 4, 4, 3, 3, 3, 4, 4, 4, 1, 1, 1, 1, 1, 1, 3, 3 },
			{ 3, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 3, 3, 3, 3 },
			{ 3, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 3, 3, 3, 3, 3, 3 },
			{ 3, 1, 4, 4, 1, 1, 4, 4, 1, 1, 1, 4, 3, 3, 3, 3, 3, 3, 3, 3 } };

	private int currentFrame = 0; //現在表示しているキャラクターのアニメーションのフレーム番号。たとえばスプライト画像が4枚あるなら、0～3を繰り返して切り替える。
	private Timer animationTimer;

	ArrayList<Hero> heroList = new ArrayList<>();
	ArrayList<Origin> enemyList;

	private String fullMessage = ""; // 表示したい全体メッセージ
	private String shownMessage = ""; // 今表示中のメッセージ（徐々に増える）
	String[] massageList;
	int massagePhase = 0;

	String[] commands = { "戦う", "スキル", "逃げる", "アイテム" };
	String[][] skillList;

	Hero hero = new Hero("ヒーローA");

	//enumはただの定数リスト以上のクラス的な性質を持つ特殊なクラス
	enum PhaseCategory {
		MAP, BATTLE, OTHER
	}

	enum gamePhase {
		WAITING_BATTLE(PhaseCategory.MAP), WAITING(PhaseCategory.MAP), MAP_MOVE(PhaseCategory.MAP), BATTLE_COMMAND(
				PhaseCategory.BATTLE), SELECT_SKILL(PhaseCategory.BATTLE), SELECT_ITEM(
						PhaseCategory.BATTLE), SELECT_ENEMY(PhaseCategory.BATTLE), SHOWING_MESSAGES_BATTLE(
								PhaseCategory.BATTLE), SHOWN_MESSAGES_BATTLE(PhaseCategory.BATTLE);

		private final PhaseCategory category;

		gamePhase(PhaseCategory category) {
			this.category = category;
		}

		public PhaseCategory getCategory() {
			return category;
		}
	}

	//書き方が特殊
	gamePhase currentPhase = gamePhase.MAP_MOVE;

	
	//コンストラクタ開始----------------------------------------------------------------
	public Visual() {

		heroList.add(hero);

		gameFont = new Font("メイリオ", Font.BOLD, 24);
		//JPanelやCanvasなどのコンポーネントの推奨サイズ（preferred size）を設定。ウィンドウサイズ自動調整（pack()）に対応
		setPreferredSize(new Dimension(size * viewWidth, size * viewHeight));
		setFocusable(true); //JPanelがキーボード入力を受け取れるようにする

		//つまり、Visual クラスのオブジェクトを KeyControl のコンストラクタに渡す
		KeyControl keyControl = new KeyControl(this);
		// 作ったキーボード操作オブジェクトを、キー入力イベントを受け取るリスナーとして登録する。
		addKeyListener(keyControl);
		try {
			background = ImageIO.read(new File("音源その他のデータ\\\\バトル画面1.png"));
			grass = ImageIO.read(new File("音源その他のデータ\\\\01.jpg"));
			mountain = ImageIO.read(new File("音源その他のデータ\\\\02.jpg"));
			river = ImageIO.read(new File("音源その他のデータ\\\\03.jpg"));
			home = ImageIO.read(new File("音源その他のデータ\\\\05.jpg"));
			tree = ImageIO.read(new File("音源その他のデータ\\\\04.jpg"));
			spriteSheet_hero = ImageIO.read(new File("音源その他のデータ\\\\現在地sp.png"));
			spriteSheet_arrow = ImageIO.read(new File("音源その他のデータ\\\\ターゲット印.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Color transparentColor = new Color(255, 0, 232);//Transparentは透明
		spriteSheet_hero = makeColorTransparent(spriteSheet_hero, transparentColor);
		spriteSheet_arrow = makeColorTransparent(spriteSheet_arrow, transparentColor);

		int frameCount = 4;
		int frameWidth = spriteSheet_hero.getWidth() / frameCount;
		int frameHeight = spriteSheet_hero.getHeight();

		BufferedImage[] playerFrames = new BufferedImage[frameCount];
		for (int i = 0; i < frameCount; i++) {
			playerFrames[i] = spriteSheet_hero.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
		}

		BufferedImage[] arrowFrames = new BufferedImage[frameCount];
		for (int i = 0; i < frameCount; i++) {
			//getSubimage(x, y, width, height)で画像を切り出すメソッド
			arrowFrames[i] = spriteSheet_arrow.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
		}

		player = playerFrames[0];//とりあえず0番の立ち絵をplayerに格納
		arrow = arrowFrames[0];

		// アニメーションタイマー
		animationTimer = new Timer(300, e -> {
			currentFrame = (currentFrame + 1) % frameCount;
			player = playerFrames[currentFrame];
			arrow = arrowFrames[currentFrame];
			repaint();
		});
		animationTimer.start();
	}

	//コンストラクタ終了---------------------------------------------------------------

	//セリフの表示と次の状態を指定するメソッド
		//第２引数でメッセージ出力後に遷移するgamePhaseを指定する
		/*
		1...WAITING 
		2...MAP_MOVE
		3...BATTLE_COMMAND 
		4...SELECT_SKILL
		5...SELECT_ITEM
		6...SELECT_ENEMY 
		7...SHOWING_MESSAGES_BATTLE
		8...SHOWN_MESSAGES_BATTLE
		9...WAITING_BATTLE 
		*/

		private void startBattle(Hero h) {
			currentPhase = gamePhase.SHOWING_MESSAGES_BATTLE;
			enemyList = EnemySet.Set();
			enemyImg = enemyControler.EnemyImgSet.SetImg(enemyList);
			massageList = new String[] { enemyList.get(0).getName() + "たちが現れた！", "コマンド？" };
			showMessageGradually(massageList[massagePhase], 3);
			repaint();
			//		battle.EnemyImgSet.SetImg(enemyList);

		}

		public void showMessageGradually(String text, int phaseNumber) {
			
			fullMessage = text;
			shownMessage = "";
			repaint();
			messageTimer = new Timer(50, e -> {
				int nextLength = shownMessage.length() + 1;
				if (nextLength <= fullMessage.length()) {
					shownMessage = fullMessage.substring(0, nextLength);
					repaint();
				} else {
					messageTimer.stop();
					// メッセージが最後だった場合、移行先を指定
					switch (phaseNumber) {
					case 1 -> currentPhase = gamePhase.WAITING;
					case 2 -> currentPhase = gamePhase.MAP_MOVE;
					case 3 -> currentPhase = gamePhase.BATTLE_COMMAND;
					case 4 -> currentPhase = gamePhase.SELECT_SKILL;
					case 5 -> currentPhase = gamePhase.SELECT_ITEM;
					case 6 -> currentPhase = gamePhase.SELECT_ENEMY;
					case 7 -> currentPhase = gamePhase.SHOWING_MESSAGES_BATTLE;
					case 8 -> currentPhase = gamePhase.SHOWN_MESSAGES_BATTLE;
					case 9 -> currentPhase = gamePhase.WAITING_BATTLE;
					}
				}

			});
			messageTimer.start();
		}

		//main
		public static void main(String[] args) {
			JFrame frame = new JFrame("RPGゲーム風ゲーム");
			Visual game = new Visual();

			// VisualのインスタンスをKeyControlに渡す
			KeyControl keyControl = new KeyControl(game);

			//		game.addKeyListener(keyControl); // もしくは someComponent に登録

			frame.add(game);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			// フォーカスをゲームパネルに設定（キー入力を受け取るため）
			game.setFocusable(true);
			game.requestFocusInWindow();
		}

	
	public static BufferedImage makeColorTransparent(BufferedImage image, Color color) {
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = image.getRGB(x, y);
				if ((pixel & 0x00FFFFFF) == (color.getRGB() & 0x00FFFFFF)) {
					// 指定色なら透明に
					transparentImage.setRGB(x, y, 0x00000000);
				} else {
					// それ以外は元の色
					transparentImage.setRGB(x, y, pixel);
				}
			}
		}
		return transparentImage;
	}

	public boolean isWalkable(int x, int y) {
		int tile = map[y][x];
		return tile != 2 && tile != 3; // 山(2)と川(3)は通行不可// 2（山）は通行不可、それ以外は通行可能
	}

	public void smoothMove(int dx, int dy) {
		int newX = (hero.getArrayPlayerX() + dx + map[0].length) % map[0].length;
		int newY = (hero.getArrayPlayerY() + dy + map.length) % map.length;

		if (isMoving) {
			return;
		}

		if (isWalkable(newX, newY) == false) {
			currentPhase = gamePhase.MAP_MOVE;
			return;
		}
		targetX = dx;
		targetY = dy;
		moveDX = dx * size; // タイル1つ分の移動距離（ピクセル単位）
		moveDY = dy * size;

		isMoving = true;

		moveTimer = new Timer(20, e -> {

			if (moveDX != 0) {
				int stepX = (moveDX > 0) ? moveSpeed : -moveSpeed;
				offsetX -= stepX; // ← 「-」に変更！キャラの移動方向に対して逆方向にオフセットを減らす
				moveDX -= stepX;
				if (Math.abs(moveDX) < moveSpeed) {
					offsetX -= moveDX; // ← ここも同様にマイナスに
					moveDX = 0;
				}
			}

			if (moveDY != 0) {
				int stepY = (moveDY > 0) ? moveSpeed : -moveSpeed;
				offsetY -= stepY;
				moveDY -= stepY;
				if (Math.abs(moveDY) < moveSpeed) {
					offsetY -= moveDY;
					moveDY = 0;
				}
			}

			repaint();

			if (moveDX == 0 && moveDY == 0) {
				moveTimer.stop();
				hero.setArrayPlayerX((hero.getArrayPlayerX() + targetX + map[0].length) % map[0].length);
				hero.setArrayPlayerY((hero.getArrayPlayerY() + targetY + map.length) % map.length);
				offsetX = 0;
				offsetY = 0;
				isMoving = false;

				int a = new java.util.Random().nextInt(3);
				if (a == 1) {
					currentPhase = gamePhase.WAITING_BATTLE;
					startBattle(hero);

				} else {
					currentPhase = gamePhase.MAP_MOVE;
				}
			}
		});

		moveTimer.start();
	}

	public void setEnemyImg(BufferedImage enemyImg) {
		this.enemyImg = enemyImg;
	}

	public void setFullMessage(String message) {
		this.fullMessage = message;
	}

	public int returnCommandIndex(int commandIndex, int select) {
		int index = commandIndex + select;

		if (select < 0) {

			if (index < 0 || (commandIndex == 2 && select == -1)) {
				index = commandIndex;
			}
			return index;
		} else {
			if (index > commands.length - 1 || (commandIndex == 1 && select == 1)) {
				index = commandIndex;
			}
			return index;
		}
	}

	public int getIndex() {
		return enemyIndex;
	}

	public void SetArrowPosition(ArrayList<Origin> enemyList, int enemyIndex) {
		// 画像が横並びなので、左端から選択した敵までの幅の合計を計算
		int arrowPositionX = 0;
		arrowPositionX += (size * viewWidth - enemyImg.getWidth()) / 2;
		for (int i = 0; i < enemyIndex; i++) {
			arrowPositionX += enemyList.get(i).getImageWidth();
		}

		// 選択した敵画像の中央に矢印を置きたいので幅の半分を足す
		arrowPositionX += enemyList.get(enemyIndex).getImageWidth() / 2;

		this.arrowPositionX = arrowPositionX;
	}

	protected void paintComponent(Graphics g) {
		gameFont = new Font("ＭＳ ゴシック", Font.PLAIN, 20);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(gameFont);
		//		  super.paintComponent(g); // ※super.paintComponent(g);で描画内容を初期状態に戻す!!!

		//自分を中心として、左右７マスずつ、上下５マスずつ描写するが、移動する時のために１マス分広く先に描写しておく
		for (int dy = -viewRadiusY - 1; dy <= viewRadiusY + 1; dy++) {
			for (int dx = -viewRadiusX - 1; dx <= viewRadiusX + 1; dx++) {
				int arrayMapX = (hero.getArrayPlayerX() + dx + map[0].length) % map[0].length;//プレイヤーの位置(配列基準)から見て、dxの位置にある配列のｘ座標を取得。はみ出す部分は配列に収まるように処理
				int arrayMapY = (hero.getArrayPlayerY() + dy + map.length) % map.length;

				int tile = map[arrayMapY][arrayMapX];

				Image tileImg;
				switch (tile) {
				case 1 -> tileImg = grass;
				case 2 -> tileImg = mountain;
				case 3 -> tileImg = river;
				case 4 -> tileImg = tree;
				case 5 -> tileImg = home;
				default -> tileImg = null;
				}
				//1回目-1×32pixel + offset(0~)...
				int drawStartPointX = (dx + viewRadiusX) * size + offsetX;//マップチップの描画開始位置を計算。offsetは元々いた位置(pixel)からどれだけ進んだか。
				int drawStartPointY = (dy + viewRadiusY) * size + offsetY;//-7or-5マス+offset分ずらしたとことを始点とする

				if (tileImg != null) {
					g.drawImage(tileImg, drawStartPointX, drawStartPointY, size, size, this);
				}
			}
		}

		int centerX = viewRadiusX * size;//7マス×サイズで8マス目
		int centerY = viewRadiusY * size;//5マス×サイズで6マス目
		g.drawImage(player, centerX, centerY, size, size, this);//中央にキャラを描写

		if (currentPhase.getCategory() == PhaseCategory.BATTLE) {
			g.drawImage(background, size * 2 + size / 2, size + size / 2, size * 10, size * 7, this);

			//文字出力用の下部の黒いメッセージボックスを配置
			g.setColor(Color.BLACK);
			g.fillRect(size + size / 2, size * 7 + size / 2, size * 11 + size / 2, size * 3);
			// メッセージ表示（shownMessage）
			g.setColor(Color.WHITE);
			g.drawString(shownMessage, size + size / 2 + size / 4, size * 8 + size / 8);

			g.drawImage(enemyImg, (size * viewWidth - enemyImg.getWidth()) / 2, 7 * size - enemyImg.getHeight(),
					enemyImg.getWidth(), enemyImg.getHeight(), this);
			// コマンド画面描画（背景黒、文字白）
			g.setColor(Color.BLACK);
			g.fillRect(7 * size + size / 2, size / 2, size * 7, size * 2);

			g.setColor(Color.WHITE); // 白文字
			g.drawString("コマンド", size * 8, size);

			for (int i = 0; i < commands.length; i++) {
				if (i == commandIndex) {
					g.setColor(Color.YELLOW);
					g.drawString("▶ " + commands[i], (size * 9 + size * 3 * (i % 2)),
							(size * 2 - size / 2 + size / 2 * (i / 2) + 5));
				} else {
					g.setColor(Color.WHITE);
					g.drawString("  " + commands[i], (size * 9 + size * 3 * (i % 2)),
							(size * 2 - size / 2 + size / 2 * (i / 2) + 5));
				}
			}

		}
		// 情報描画（背景黒、文字白）
		g.setColor(Color.BLACK);
		g.fillRect(size / 2, size / 2, size * 4, size * 2);

		g.setColor(Color.WHITE); // 白文字

		g.setFont(gameFont);
		g.drawString(hero.getName() + "  レベル" + hero.getLevel(), 50, 60);
		g.drawString("・ＨＰ   " + hero.getHp(), 50, 90);
		g.drawString("・ＭＰ   " + hero.getMp(), 50, 120);
		g.drawString("・ＥＸＰ " + hero.getExp(), 50, 150);
		g.drawString("現在のgamePhase... " + currentPhase, 450, 20);
		gameFont = new Font("ＭＳ ゴシック", Font.PLAIN, 14);
		if (currentPhase == gamePhase.SHOWN_MESSAGES_BATTLE) {
			g.setFont(gameFont);
			g.drawString("▼", 790, 650);
		}
		if (currentPhase == gamePhase.SELECT_ENEMY) {
			g.drawImage(arrow, arrowPositionX - 10, enemyImg.getHeight() - 50, 20, 20, this);
		}
	}
}
