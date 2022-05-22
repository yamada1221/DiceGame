package calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * �T�C�R���Q�[���B
 * 
 * @author yaoroz
 *
 */
public class DiceProbity {
	/**
	 * ����
	 */
	private Random r = new Random();
	/**
	 * ���{�Q�[����
	 */
	private int execGames = 10000;
	/**
	 * 1�Q�[��������̃T�C�R����
	 */
	private int diceTimes = 4;
	/**
	 * �T�C�R���̖ʐ�
	 */
	private int sided = 6;

	/**
	 * �f�t�H���g:1�Q�[��4��6�ʃT�C�R����10000����{����B
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
//				System.out.println("�T�C�R��:" + dice);
				diceList.add(dice);
			}
			roleList.add(strengeRole(diceList));
			for (int role : roleList) {
				// TODO:Enum�ɂ���
				if (role == 4) {
//					System.out.println("��:�N�A�b�Y");
					quad++;
					money += 5000;
				} else if (role == 3) {
//					System.out.println("��:�g���v��");
					triple++;
					money += 2000;
				} else if (role == 2) {
//					System.out.println("��:�_�u��");
					doubles++;
					money += 500;
				} else if (role == 9) {
//					System.out.println("��:���ʏ�");
					special++;
					money += 3000;
				} else {
//					System.out.println("��:�Ȃ�");
					none++;
					money += 0;
				}
			}
			// �S�Q�[������
			if (games == execGames) {
				System.out.println("--------------------------------------");
				System.out
						.println("�N�A�b�Y:" + quad + "��(" + String.format("%.3f", (double) quad / execGames * 100) + "%)");
				System.out.println(
						"�g���v��:" + triple + "��(" + String.format("%.3f", (double) triple / execGames * 100) + "%)");
				System.out.println(
						"�_�u��:" + doubles + "��(" + String.format("%.3f", (double) doubles / execGames * 100) + "%)");
				System.out.println(
						"���ʏ�:" + special + "��(" + String.format("%.3f", (double) special / execGames * 100) + "%)");
				System.out.println("�Ȃ�:" + none + "��(" + String.format("%.3f", (double) none / execGames * 100) + "%)");
				System.out.println("�Q�[����:" + execGames + "��");
				System.out.println("�l�����z:" + money + "�~");
				// ���Ғl:690�~�O��ɂȂ�
				System.out.println("���Ғl:" + money / execGames + "�~");
				System.out.println("--------------------------------------");
			}
		}
	}

	/**
	 * ���̋����𔻒肷��B
	 * <p>
	 * 0:���Ȃ� 1:�����4�� 2:�����3�� 3:�����2�� 9:2�̖ڂ�2�y�A
	 * </p>
	 * 
	 * @return ��
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

		// �𔻒�
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
	 * �T�C�R����U��B
	 * <p>
	 * 6�ʃT�C�R��
	 * </p>
	 * 
	 * @return �T�C�R����U��������
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
	 * @param execGames �Z�b�g���� execGames
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
	 * @param diceTimes �Z�b�g���� diceTimes
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
	 * @param sided �Z�b�g���� sided
	 */
	public void setSided(int sided) {
		this.sided = sided;
	}
}
