package calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** サイコロゲーム。 */
public class DiceProbity {
	private final Random r;
	private int execGames = 10000;
	private int diceTimes = 4;
	private int sided = 6;

	public DiceProbity() {
		this(new Random());
	}

	DiceProbity(Random random) {
		this.r = random;
	}

	void games() {
		int quad = 0;
		int triple = 0;
		int doubles = 0;
		int special = 0;
		int none = 0;
		long money = 0;
		for (int game = 0; game < execGames; game++) {
			List<Integer> diceList = new ArrayList<>();
			for (int times = 0; times < diceTimes; times++) {
				diceList.add(r.nextInt(sided) + 1);
			}
			switch (strongestRole(diceList)) {
			case 4:
				quad++;
				money += 5000;
				break;
			case 3:
				triple++;
				money += 2000;
				break;
			case 9:
				special++;
				money += 3000;
				break;
			case 2:
				doubles++;
				money += 500;
				break;
			default:
				none++;
			}
		}
		System.out.println("--------------------------------------");
		printRole("クアッズ", quad);
		printRole("トリプル", triple);
		printRole("ダブル", doubles);
		printRole("特別賞", special);
		printRole("なし", none);
		System.out.println("ゲーム数:" + execGames + "回");
		System.out.println("獲得金額:" + money + "円");
		System.out.println("期待値:" + String.format(java.util.Locale.ROOT, "%.3f", (double) money / execGames) + "円");
		System.out.println("--------------------------------------");
	}

	private void printRole(String name, int count) {
		System.out.println(name + ":" + count + "回("
				+ String.format(java.util.Locale.ROOT, "%.3f", (double) count / execGames * 100) + "%)");
	}

	/** 4:クアッズ、3:トリプル、9:特別賞、2:ダブル、0:役なし。 */
	static int strongestRole(List<Integer> diceList) {
		Map<Integer, Integer> counts = new HashMap<>();
		for (int dice : diceList) {
			counts.merge(dice, 1, Integer::sum);
		}
		int largest = 0;
		int pairs = 0;
		for (int count : counts.values()) {
			largest = Math.max(largest, count);
			if (count >= 2) pairs++;
		}
		if (largest >= 4) return 4;
		if (largest >= 3) return 3;
		if (pairs >= 2) return 9;
		if (pairs == 1) return 2;
		return 0;
	}

	public int getExecGames() {
		return execGames;
	}

	public void setExecGames(int execGames) {
		if (execGames <= 0) throw new IllegalArgumentException("実施ゲーム数は1以上を指定してください。");
		this.execGames = execGames;
	}

	public int getDiceTimes() {
		return diceTimes;
	}

	public void setDiceTimes(int diceTimes) {
		if (diceTimes <= 0) throw new IllegalArgumentException("サイコロ個数は1以上を指定してください。");
		this.diceTimes = diceTimes;
	}

	public int getSided() {
		return sided;
	}

	public void setSided(int sided) {
		if (sided <= 0) throw new IllegalArgumentException("サイコロの面数は1以上を指定してください。");
		this.sided = sided;
	}
}
