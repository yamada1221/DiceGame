package calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DiceProbityMain {
	public static void main(String[] args) throws Exception {
		if (args.length > 3) {
			throw new IllegalArgumentException("引数はゲーム数・サイコロ個数・面数の3つまでです。");
		}
		// インスタンス生成
		DiceProbity dp = new DiceProbity();
		// デフォルトチェック
		String execGamesStr = null;
		String diceTimesStr = null;
		String sidedStr = null;
		System.out.println("設定");
		System.out.println("--------------------------------------");
		System.out.println("実施ゲーム数:" + dp.getExecGames());
		System.out.println("1ゲームあたりのサイコロ個数:" + dp.getDiceTimes());
		System.out.println("サイコロの面数:" + dp.getSided());
		System.out.println("--------------------------------------");
		if (args.length > 0) {
			// 引数設定
			System.out.println("引数の内容を設定します。");
			execGamesStr = args[0];
			if (args.length > 1) {
				diceTimesStr = args[1];
			} else {
				System.out.println("1ゲームあたりのサイコロ個数の設定は設定なしのためスキップします。");
				diceTimesStr = String.valueOf(dp.getDiceTimes());
			}
			if (args.length > 2) {
				sidedStr = args[2];
			} else {
				System.out.println("サイコロの面数の設定は設定なしのためスキップします。");
				sidedStr = String.valueOf(dp.getSided());
			}
		} else {
			// 対話インターフェイス
			System.out.println("設定変更(変更しない場合エンター)");
			System.out.println("--------------------------------------");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("実施ゲーム数(入力):");
			execGamesStr = in.readLine();
			System.out.println("1ゲームあたりのサイコロ個数(入力):");
			diceTimesStr = in.readLine();
			System.out.println("サイコロの面数(入力):");
			sidedStr = in.readLine();
		}
		// バリデーション
		try {
			if (execGamesStr != null && !execGamesStr.trim().isEmpty()) {
				int execGames = Integer.parseInt(execGamesStr.trim());
				dp.setExecGames(execGames);
			}
			if (diceTimesStr != null && !diceTimesStr.trim().isEmpty()) {
				int diceTimes = Integer.parseInt(diceTimesStr.trim());
				dp.setDiceTimes(diceTimes);
			}
			if (sidedStr != null && !sidedStr.trim().isEmpty()) {
				int sided = Integer.parseInt(sidedStr.trim());
				dp.setSided(sided);
			}
		} catch (NumberFormatException e) {
			throw new Exception("数値を入力してください。", e);
		}
		// ゲーム開始
		dp.games();
	}

}
