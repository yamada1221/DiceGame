package calc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

public class DiceProbityTest {
	public static void main(String[] args) throws Exception {
		int[] counts = new int[10];
		for (int a = 1; a <= 6; a++)
			for (int b = 1; b <= 6; b++)
				for (int c = 1; c <= 6; c++)
					for (int d = 1; d <= 6; d++)
						counts[DiceProbity.strongestRole(Arrays.asList(a, b, c, d))]++;
		check(counts[4] == 6 && counts[3] == 120 && counts[9] == 90 && counts[2] == 720 && counts[0] == 360,
				"four-dice outcomes must match all 1296 combinations");
		check(DiceProbity.strongestRole(Arrays.asList(1, 1, 2, 2, 2)) == 3, "later triple must beat an earlier pair");
		check(DiceProbity.strongestRole(Arrays.asList(1, 1, 2, 2, 2, 2)) == 4, "later quad must beat an earlier pair");

		DiceProbity large = new DiceProbity();
		large.setExecGames(500000);
		large.setSided(1);
		String largeOutput = capture(large::games);
		check(largeOutput.contains("獲得金額:2500000000円"), "payout must not overflow a signed int");

		DiceProbity fractional = new DiceProbity(new Random() {
			private final int[] rolls = {0, 0, 0, 1, 0, 1, 2, 3, 0, 1, 2, 3};
			private int index;
			@Override public int nextInt(int bound) { return rolls[index++]; }
		});
		fractional.setExecGames(3);
		check(capture(fractional::games).contains("期待値:666.667円"), "mean must retain fractional yen");

		for (int invalid : new int[] {0, -1}) {
			expectInvalid(() -> large.setExecGames(invalid));
			expectInvalid(() -> large.setDiceTimes(invalid));
			expectInvalid(() -> large.setSided(invalid));
		}
		expectInvalid(() -> DiceProbityMain.main(new String[] {"1", "4", "6", "extra"}));
		InputStream previous = System.in;
		try {
			System.setIn(new ByteArrayInputStream(new byte[0]));
			check(capture(() -> DiceProbityMain.main(new String[0])).contains("ゲーム数:10000回"),
					"closed stdin must retain defaults");
			System.setIn(new ByteArrayInputStream("\n\n\n".getBytes("UTF-8")));
			check(capture(() -> DiceProbityMain.main(new String[0])).contains("ゲーム数:10000回"),
					"blank interactive input must retain defaults");
		} finally { System.setIn(previous); }
		System.out.println("PASS: 1296 outcomes, multi-pair ordering, 2.5 billion yen total, fractional mean, invalid input, EOF and blanks");
	}

	interface Action { void run() throws Exception; }
	private static String capture(Action action) throws Exception {
		PrintStream previous = System.out;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (PrintStream stream = new PrintStream(bytes, true, "UTF-8")) {
			System.setOut(stream);
			action.run();
		} finally { System.setOut(previous); }
		return bytes.toString("UTF-8");
	}
	private static void expectInvalid(Action action) throws Exception {
		try { action.run(); } catch (IllegalArgumentException expected) { return; }
		throw new AssertionError("invalid input was accepted");
	}
	private static void check(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}
}
