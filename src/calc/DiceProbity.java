package calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * サイコロゲーム。
 * 
 * @author yaoroz
 *
 */
public class DiceProbity {
	/**
	 * 乱数
	 */
	private Random r = new Random();
	/**
	 * 実施ゲーム数
	 */
	private int execGames = 10000;
	/**
	 * 1ゲームあたりのサイコロ個数
	 */
	private int diceTimes = 4;
	/**
	 * サイコロの面数
	 */
	private int sided = 6;

	/**
	 * デフォルト:1ゲーム4個の6面サイコロを10000回実施する。
	 * 
	 */
	void games() {
		int quad = 0;
		int triple = 0;
		int doubles = 0;
		int special = 0;
		int none = 0;
		int money = 0;
		for (int games = 1; games <= execGames; games++) {
			List<Integer> diceList = new ArrayList<>();
			List<Integer> roleList = new ArrayList<>();
			for (int times = 1; times <= diceTimes; times++) {
				int dice = diceRole();
//				System.out.println("サイコロ:" + dice);
				diceList.add(dice);
			}
			roleList.add(strengeRole(diceList));
			for (int role : roleList) {
				// TODO:Enumにする
				if (role == 4) {
//					System.out.println("役:クアッズ");
					quad++;
					money += 5000;
				} else if (role == 3) {
//					System.out.println("役:トリプル");
					triple++;
					money += 2000;
				} else if (role == 2) {
//					System.out.println("役:ダブル");
					doubles++;
					money += 500;
				} else if (role == 9) {
//					System.out.println("役:特別賞");
					special++;
					money += 3000;
				} else {
//					System.out.println("役:なし");
					none++;
					money += 0;
				}
			}
			// 全ゲーム結果
			if (games == execGames) {
				System.out.println("--------------------------------------");
				System.out
						.println("クアッズ:" + quad + "回(" + String.format("%.3f", (double) quad / execGames * 100) + "%)");
				System.out.println(
						"トリプル:" + triple + "回(" + String.format("%.3f", (double) triple / execGames * 100) + "%)");
				System.out.println(
						"ダブル:" + doubles + "回(" + String.format("%.3f", (double) doubles / execGames * 100) + "%)");
				System.out.println(
						"特別賞:" + special + "回(" + String.format("%.3f", (double) special / execGames * 100) + "%)");
				System.out.println("なし:" + none + "回(" + String.format("%.3f", (double) none / execGames * 100) + "%)");
				System.out.println("ゲーム数:" + execGames + "回");
				System.out.println("獲得金額:" + money + "円");
				// 期待値:690円前後になる
				System.out.println("期待値:" + money / execGames + "円");
				System.out.println("--------------------------------------");
			}
		}
	}

	/**
	 * 役の強さを判定する。
	 * <p>
	 * 0:役なし 1:ぞろ目4つ 2:ぞろ目3つ 3:ぞろ目2つ 9:2つの目が2ペア
	 * </p>
	 * 
	 * @return 役
	 */
	private int strengeRole(List<Integer> diceList) {
		boolean pairFlag = false;
		boolean twoPairFlag = false;
		boolean doubleFlag = false;
		boolean tripleFlag = false;
		boolean quadFlag = false;
		List<Integer> noPairDiceList = new ArrayList<>();
		int pairDice = 0;
		for (int dice : diceList) {
			if (noPairDiceList.size() == 0) {
				noPairDiceList.add(dice);
				continue;
			}
			if (tripleFlag) {
				if (pairDice == dice) {
					quadFlag = true;
				}
			}
			if (doubleFlag) {
				if (pairDice == dice) {
					tripleFlag = true;
				}
			}
			for (int i = 0; i < noPairDiceList.size(); i++) {
//				System.out.println(noPairDiceList.stream().collect(Collectors.toList()));
				int noPairDice = noPairDiceList.get(i);
				if (noPairDice == dice) {
					if (pairFlag && pairDice != dice) {
						twoPairFlag = true;
					}
					if (pairDice == 0) {
						doubleFlag = true;
						pairFlag = true;
						pairDice = dice;
					}

					noPairDiceList.remove(i);
				}
			}
			noPairDiceList.add(dice);
		}

		// 役判定
//		System.out.println(" twoPairFlag=" + twoPairFlag);
//		System.out.println(" quadFlag=" + quadFlag);
//		System.out.println(" tripleFlag=" + tripleFlag);
//		System.out.println(" doubleFlag=" + doubleFlag);

		if (quadFlag) {
			return 4;
		} else if (tripleFlag) {
			return 3;
		} else if (twoPairFlag) {
			return 9;
		} else if (doubleFlag) {
			return 2;
		}
		return 0;
	}

	/**
	 * サイコロを振る。
	 * <p>
	 * 6面サイコロ
	 * </p>
	 * 
	 * @return サイコロを振った結果
	 */
	private int diceRole() {
		return r.nextInt(sided) + 1;
	}

	/**
	 * @return execGames
	 */
	public int getExecGames() {
		return execGames;
	}

	/**
	 * @param execGames セットする execGames
	 */
	public void setExecGames(int execGames) {
		this.execGames = execGames;
	}

	/**
	 * @return diceTimes
	 */
	public int getDiceTimes() {
		return diceTimes;
	}

	/**
	 * @param diceTimes セットする diceTimes
	 */
	public void setDiceTimes(int diceTimes) {
		this.diceTimes = diceTimes;
	}

	/**
	 * @return sided
	 */
	public int getSided() {
		return sided;
	}

	/**
	 * @param sided セットする sided
	 */
	public void setSided(int sided) {
		this.sided = sided;
	}
}
