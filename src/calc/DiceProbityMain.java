package calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiceProbityMain {
	public static void main(String[] args) throws Exception {
		// �C���X�^���X����
		DiceProbity dp = new DiceProbity();
		// �f�t�H���g�`�F�b�N
		String execGamesStr = null;
		String diceTimesStr = null;
		String sidedStr = null;
		System.out.println("�ݒ�");
		System.out.println("--------------------------------------");
		System.out.println("���{�Q�[����:" + dp.getExecGames());
		System.out.println("1�Q�[��������̃T�C�R����:" + dp.getDiceTimes());
		System.out.println("�T�C�R���̖ʐ�:" + dp.getSided());
		System.out.println("--------------------------------------");
		if (args.length > 0) {
			// �����ݒ�
			System.out.println("�����̓��e��ݒ肵�܂��B");
			execGamesStr = args[0];
			if (args.length > 1) {
				diceTimesStr = args[1];
			} else {
				System.out.println("1�Q�[��������̃T�C�R�����̐ݒ�͐ݒ�Ȃ��̂��߃X�L�b�v���܂��B");
				diceTimesStr = String.valueOf(dp.getDiceTimes());
			}
			if (args.length > 2) {
				sidedStr = args[2];
			} else {
				System.out.println("�T�C�R���̖ʐ��̐ݒ�͐ݒ�Ȃ��̂��߃X�L�b�v���܂��B");
				sidedStr = String.valueOf(dp.getSided());
			}
		} else {
			// �Θb�C���^�[�t�F�C�X
			System.out.println("�ݒ�ύX(�ύX���Ȃ��ꍇ�G���^�[)");
			System.out.println("--------------------------------------");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("���{�Q�[����(����):");
				execGamesStr = in.readLine();
				System.out.println("1�Q�[��������̃T�C�R����(����):");
				diceTimesStr = in.readLine();
				System.out.println("�T�C�R���̖ʐ�(����):");
				sidedStr = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// �o���f�[�V����
		try {
			if (!"".equals(execGamesStr)) {
				int execGames = Integer.valueOf(execGamesStr);
				dp.setExecGames(execGames);
			}
			if (!"".equals(diceTimesStr)) {
				int diceTimes = Integer.valueOf(diceTimesStr);
				dp.setDiceTimes(diceTimes);
			}
			if (!"".equals(sidedStr)) {
				int sided = Integer.valueOf(sidedStr);
				dp.setSided(sided);
			}
		} catch (NumberFormatException e) {
			throw new Exception("���l����͂��Ă��������B", e);
		}
		// �Q�[���J�n
		dp.games();
	}

}
