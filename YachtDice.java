import java.util.Arrays;
import java.util.Scanner;

public class YachtDice {

	public static void main(String[] args) {
		YachtDice y = new YachtDice();
		int[] dice = new int[5];
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		char key;
		String[] indexes;
		int roll = 0, cat;
		
		System.out.print("Roll Dice: ");
		key = input.nextLine().charAt(0);
		
		dice = y.getDice();
		Arrays.sort(dice);
		String[] hand = y.getHands(dice), current = y.setHands();
		int[] check = y.setCheck();
		
		while (!y.CheckEnd(check)) {
			roll = 0;
		
			y.printCard(dice, hand, current, check);
			
			System.out.print("Roll or Choose: ");
			key = input.nextLine().charAt(0);
			while (Character.isLetter(key) && roll < 2) {
				int[] index = {-1, -1, -1, -1, -1};
				check[12] = roll + 1;
				
				System.out.print("Roll Dice Indexes: ");
				roll++;
				indexes = input.nextLine().split(" ");
				for (int i = 0; i < indexes.length; i++) {
					index[i] = Integer.parseInt(indexes[i]);
				}
				
				for (int i = 0; i < index.length; i++) {
					if (index[i] != -1) {
						dice[index[i]] = y.rollDice();
					}
				}
				
				Arrays.sort(dice);
				hand = y.getHands(dice);
				y.printCard(dice, hand, current, check);
				
				System.out.print("Roll or Choose: ");
				key = input.nextLine().charAt(0);
			}
			
			System.out.print("Choose Category: ");
			cat = input2.nextInt();
			
			while (check[cat - 1] == 1 || cat == 13) {
				System.out.print("Choose Category: ");
				cat = input2.nextInt();
			}
			
			current = y.setCurrent(current, cat - 1, Integer.valueOf(hand[cat - 1]));
			check[cat - 1] = 1;
			
			y.printCard(dice, hand, current, check);
			
			System.out.print("Roll Dice: ");
			key = input.nextLine().charAt(0);
			
			dice = y.getDice();
			Arrays.sort(dice);
			hand = y.getHands(dice);
		}
		
		System.out.println("End Total: " + current[12]);
	}
	
	public int[] getDice() {
		int[] dice = new int[5];
		
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rollDice();
		}
		
		return dice;
	}
	
	public int rollDice() {
		return (int)(Math.random() * 6) + 1;
	}
	
	public void printCard(int[] dice, String[] hands, String[] current, int[] check) {
		System.out.print("+++++-----+-----+-----+-----+-----+\n"
				+ "DICE|  0  |  1  |  2  |  3  |  4  |\n"
				+ "+++++-----+-----+-----+-----+-----+\n"
				+ "ROLL|  ");
		
		for (int i = 0; i < dice.length; i++) {
			System.out.print(dice[i] + "  |  ");
		}
		
		System.out.println("\n"
				+ "+++++-----+-----+-----+-----+-----+\n"
				+ "+++===============+=====+=====+===+\n"
				+ "01|Ones...........| " + hands[0] + " | " + current[0] + " | " + check[0] + " |\n"
				+ "02|Twos...........| " + hands[1] + " | " + current[1] + " | " + check[1] + " |\n"
				+ "03|Threes.........| " + hands[2] + " | " + current[2] + " | " + check[2] + " |\n"
				+ "04|Fours..........| " + hands[3] + " | " + current[3] + " | " + check[3] + " |\n"
				+ "05|Fives..........| " + hands[4] + " | " + current[4] + " | " + check[4] + " |\n"
				+ "06|Sixes..........| " + hands[5] + " | " + current[5] + " | " + check[5] + " |\n"
				+ "07|Full House.....| " + hands[6] + " | " + current[6] + " | " + check[6] + " |\n"
				+ "08|Four Of A Kind.| " + hands[7] + " | " + current[7] + " | " + check[7] + " |\n"
				+ "09|Little Straight| " + hands[8] + " | " + current[8] + " | " + check[8] + " |\n"
				+ "10|Big Straight...| " + hands[9] + " | " + current[9] + " | " + check[9] + " |\n"
				+ "11|Choice.........| " + hands[10] + " | " + current[10] + " | " + check[10] + " |\n"
				+ "12|Yacht..........| " + hands[11] + " | " + current[11] + " | " + check[11] + " |\n"
				+ "++|Total..........| " + hands[12] + " | " + current[12] + " | " + check[12] + " |\n"
				+ "+++===============+=====+=====+===+");
	}
	
	public String[] getHands(int[] dice) {
		String[] hand = new String[13];
		
		hand[0] = Format(Ones(dice));
		hand[1] = Format(Twos(dice));
		hand[2] = Format(Threes(dice));
		hand[3] = Format(Fours(dice));
		hand[4] = Format(Fives(dice));
		hand[5] = Format(Sixes(dice));
		hand[6] = Format(FullHouse(dice));
		hand[7] = Format(FourOfAKind(dice));
		hand[8] = Format(LittleStraight(dice));
		hand[9] = Format(BigStraight(dice));
		hand[10] = Format(Choice(dice));
		hand[11] = Format(Yacht(dice));
		int sum = Ones(dice) + Twos(dice) + Threes(dice) + Fours(dice) + Fives(dice) + Sixes(dice)
			+ FullHouse(dice) + FourOfAKind(dice) + LittleStraight(dice) + BigStraight(dice) + Choice(dice) + Yacht(dice);
		hand[12] = Format(sum);
		
		return hand;
	}
	
	public String[] setHands() {
		String[] hand = new String[13];
		
		for (int i = 0; i < hand.length; i++) {
			hand[i] = "000";
		}
		
		return hand;
	}
	
	public int[] setCheck() {
		int[] check = new int[13];
		
		for (int i = 0; i < check.length; i++) {
			check[i] = 0;
		}
		
		return check;
	}
	
	public String[] setCurrent(String[] hands, int index, int value) {
		int sum = 0;
		hands[index] = Format(value);
		for (int i = 0; i < hands.length - 1; i++) {
			sum += Integer.valueOf(hands[i]);
		}
		
		hands[12] = Format(sum);
		
		return hands;
	}
	
	public boolean CheckEnd(int[] check) {
		for (int i = 0; i < check.length - 1; i++) {
			if (check[i] == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public String Format(int num) {
		
		if (num < 10) {
			return "00" + Integer.toString(num);
		} else if (num < 100){
			return "0" + Integer.toString(num);
		} else {
			return Integer.toString(num);
		}
	}

	public int Ones(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 1) {
				sum += 1;
			}
		}
		
		return sum;
	}
	
	public int Twos(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 2) {
				sum += 2;
			}
		}
		
		return sum;
	}
	
	public int Threes(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 3) {
				sum += 3;
			}
		}
		
		return sum;
	}
	
	public int Fours(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 4) {
				sum += 4;
			}
		}
		
		return sum;
	}
	
	public int Fives(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 5) {
				sum += 5;
			}
		}
		
		return sum;
	}
	
	public int Sixes(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == 6) {
				sum += 6;
			}
		}
		
		return sum;
	}
	
	public int FullHouse(int[] dice) {
		int sum = 0;
		
		if (dice[0] == dice[1] && dice[2] == dice[3] && dice[3] == dice[4]) {
			for (int i = 0; i < dice.length; i++) {
				sum += dice[i];
			}
		} else if (dice[0] == dice[1] && dice[1] == dice[2] && dice[3] == dice[4]) {
			for (int j = 0; j < dice.length; j++) {
				sum += dice[j];
			}
		}
		
		return sum;
	}
	
	public int FourOfAKind(int[] dice) {
		int j = 0, sum = 0, count = 0;
		
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i] != dice[i + 1]) {
				if (j > 1) {
					return 0;
				} else {
					j++;
				}
			} else {
				if (count < dice.length) {
					sum += dice[i];
				}
				
				count++;
			}
		}
		
		if (dice[1] != dice[3]) {
			return 0;
		}
		
		return sum + dice[dice.length - 1];
	}
	
	public int LittleStraight(int[] dice) {
		if (dice[0] != 1) {
			return 0;
		}
		
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i + 1] != dice[i] + 1) {
				return 0;
			}
		}
		
		return 30;
	}
	
	public int BigStraight(int[] dice) {
		if (dice[0] != 2) {
			return 0;
		}
		
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i + 1] != dice[i] + 1) {
				return 0;
			}
		}
		
		return 30;
	}
	
	public int Choice(int[] dice) {
		int sum = 0;
		
		for (int i = 0; i < dice.length; i++) {
			sum += dice[i];
		}
		
		return sum;
	}
	
	public int Yacht(int[] dice) {
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i] != dice[i + 1]) {
				return 0;
			}
		}
		
		return 50;
	}

}