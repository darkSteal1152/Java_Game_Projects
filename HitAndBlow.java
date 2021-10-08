import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HitAndBlow {

	public static void main(String[] args) {
		HitAndBlow m = new HitAndBlow();
		
		char[][] colors = m.setColors();
		char[][] tokens = m.setColors();
		char[] code = m.getCode();
		
		/*
		
		for (int i = 0; i < code.length; i++) {
			System.out.print(code[i] + " ");
		}
		
		*/
		
		//System.out.println();
		
		int current = 0;
		boolean gameEnd = false;
		
		System.out.println("Code Created. \n"
				+ "Input :: b g o p r y");
		
		Scanner input = new Scanner(System.in);
		String[] attempt;
		
		while (!gameEnd) {
			System.out.println();
			m.printBoard(gameEnd, colors, tokens, current, code);
			System.out.println("Input Colors. ");
			System.out.print("b g o p r y: ");
			attempt = input.nextLine().split(" ");
			for (int i = 0; i < colors[current].length; i++) {
				colors[current][i] = attempt[i].charAt(0);
			}
			tokens[current] = m.checkBlack(colors, current, code);
			gameEnd = m.checkGameEnd(colors, current, code);
			current++;
		}
		
		m.printBoard(gameEnd, colors, tokens, current, code);
		System.out.println("Game Complete");
		if (m.Total(tokens, current) == 100) {
			System.out.println("MISSION SUCCESS");
		} else {
			System.out.println("MISSION FAIL");
		}
	}
	
	public char[][] setColors() {
		char[][] colors = new char[13][4];
		
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colors[i].length; j++) {
				colors[i][j] = '.';
			}
		}
		
		return colors;
	}
	
	public char[] getCode() {
		char[] code = new char[4];
		
		for (int i = 0; i < code.length; i++) {
			code[i] = mapToColor((int)(Math.random() * 6));
		}
		
		return code;
	}
	
	public char mapToColor(int num) {
		char[] colors = {'b', 'g', 'o', 'p', 'r', 'y'};
		
		return colors[num];
	}
	
	public boolean checkGameEnd(char[][] colors, int current, char[] code) {
		
		if (current == 11) {
			return true;
		}
		
		for (int i = 0; i < colors[current].length; i++) {
			if (colors[current][i] != code[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public void printBoard(boolean gameCheck, char[][] color, char[][] token, int current, char[] code) {
		
		System.out.print("+--+---+---+---+---++---------+\n");
		for (int i = 0; i < color.length; i++) {
			if (i != 12) {
				System.out.print("|" + Format(i + 1) + "|");
			} else {
				System.out.print("|  |");
			}
			
			for (int j = 0; j < color[i].length; j++) {
				if (i != 12) {
					System.out.print(" " + color[i][j] + " |");
				} else {
					if (!gameCheck) {
						System.out.print(" * |");
					} else {
						System.out.print(" " + code[j] + " |");
					}
				}
			}
			
			System.out.print("|");
			
			if (i == 12) {
				System.out.print(Complete(gameCheck, token, current));
			}
			
			for (int k = 0; k < token[i].length; k++) {
				if (i != 12) {
					System.out.print(" " + token[i][k]);
				}
			}
			
			if (i != 12 && i != 11) {
				System.out.println(" |\n"
						+ "+--+---+---+---+---+|         |");
			} else {
				System.out.println(" |\n"
						+ "+--+---+---+---+---++---------+");
			}
		}
	}
	
	public String Format(int num) {
		if (num / 10 == 0) {
			return "0" + Integer.toString(num);
		} else {
			return Integer.toString(num);
		}
	}
	
	public String FormatPercentage(int num) {
		if (num < 10) {
			return "      " + num + "%";
		} else if (num < 100){
			return "     " + num + "%";
		} else {
			return "    " + num +"%";
		}
	}
	
	public String Complete(boolean end, char[][] run, int index) {
		
		return FormatPercentage((int)Total(run, index));
	}
	
	public double Total(char[][] color, int index) {
		double sum = 0;
		for (int i = 0; i < color[index].length; i++) {
			if (color[(index == 0) ? index : index - 1][i] == 'B') {
				sum += 0.25;
			} else if (color[(index == 0) ? index : index - 1][i] == 'W'){
				sum += 0.125;
			}
		}
		
		return sum * 100;
	}
	
	public char[] checkBlack(char[][] colors, int current, char[] code) {
		char[] check = new char[4];
		int init = 0;
		List<String> token = new ArrayList<>(), found = new ArrayList<>();
		
		for (int l = 0; l < colors[current].length; l++) {
			token.add(Character.toString(colors[current][l]));
		}
		
		for (int i = 0; i < check.length; i++) {
			if (colors[current][i] == code[i]) {
				check[init++] = 'B';
				found.add(Character.toString(i));
				token.remove(token.lastIndexOf(Character.toString(colors[current][i])));
			}
		}
		
		for (int k = 0; k < code.length; k++) {
			if (token.contains(Character.toString(code[k])) && !found.contains(Character.toString(k))) {
				check[init++] = 'W';
				token.remove(Character.toString(code[k]));
			}
		}
		
		for (int j = init; j < check.length; j++) {
			check[j] = '=';
		}
		
		return check;
	}
}