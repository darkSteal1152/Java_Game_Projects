import java.util.Scanner;

public class MancalaMain {
	
	class House {
		char side;
		int seeds = 4;
		House next;
		House opposite;
		boolean isEnd;
		Store store;
		boolean isEmpty = (seeds > 0 ? true : false);
		
		House(char s, House n, House o, boolean e, Store z) {
			side = s;
			next = n;
			opposite = o;
			isEnd = e;
			store = z;
		}
	}
	
	class Store {
		char side;
		int seeds;
		int total;
		
		Store(char s) {
			side = s;
		}
	}

	public static void main(String[] args) {
		MancalaMain m = new MancalaMain();
		
		boolean gameOver = false;
		int player = 0;
		int move;
		boolean turn = false;
		
		int winner;
		
		House[] h = m.CreateBoard();
		int Z[] = new int[2];
		
		int[] total = null;
		
		Scanner input = new Scanner(System.in);
		
		while (!gameOver) {
			m.Board(h, Z);
			System.out.print("Player " + (player + 1) + " turn: ");
			move = input.nextInt();
			
			turn = m.MoveSeed(h, Z, move, player);
			
			total = m.SeedsRemaining(h);
			
			if (total[0] <= 0 || total[1] <= 0) {
				gameOver = true;
			} else {
				System.out.println("Player 1 Remaining: " + total[0] + " || Player 2 Remaining: " + total[1]);
			}
			
			if (turn) {
				player = m.NextPlayer(player);
			}
		}
		
		if (total[0] <= 0) {
			Z[1] = Z[1] + total[1];
			
			for (int i = h.length/2; i < h.length; i++) {
				h[i].seeds = 0;
			}
		} else if (total[1] <= 0) {
			Z[0] = Z[0] + total[0];
			
			for (int j = 0; j < h.length/2; j++) {
				h[j].seeds = 0;
			}
		}
		
		if (Z[0] > Z[1]) {
			winner = 1;
		} else if (Z[1] > Z[0]) {
			winner = 2;
		} else {
			winner = 0;
		}
		
		m.Board(h, Z);
		
		System.out.println();
		System.out.println("+----+ GameOver +----+");
		
		if (winner > 0) {
			System.out.println("Player " + winner + " wins " + Z[0] + " - " + Z[1]);
		} else {
			System.out.println("Game was a tie");
		}
	}
	
	public void Board(House[] H, int Z[]){
		
		System.out.println("+----+----+----+----+----+----+----+----+\r\n"
				+ "|    | B6 | B5 | B4 | B3 | B2 | B1 |    |\r\n"
				+ "| HB | " + D(H[11].seeds) + " | " + D(H[10].seeds) + " | " + D(H[9].seeds) + " | " + D(H[8].seeds) + " | " + D(H[7].seeds) + " | " + D(H[6].seeds) + " | HA |\r\n"
				+ "|    +----+----+----+----+----+----+    |\r\n"
				+ "| " + D(Z[1]) + " | A1 | A2 | A3 | A4 | A5 | A6 | " + D(Z[0]) + " |\r\n"
				+ "|    | " + D(H[0].seeds) + " | " + D(H[1].seeds) + " | " + D(H[2].seeds) + " | " + D(H[3].seeds) + " | " + D(H[4].seeds) + " | " + D(H[5].seeds) + " |    |\r\n"
				+ "+----+----+----+----+----+----+----+----+");
	}
	
	public String D(int num) {
		return (num < 10 ? "0" : "") + num;
	}
	
	public House[] CreateBoard() {
		Store HA = new Store('A');
		Store HB = new Store('B');
		
		House A6 = new House('A', null, null, true, HA);
		House A5 = new House('A', A6, null, false, HA);
		House A4 = new House('A', A5, null, false, HA);
		House A3 = new House('A', A4, null, false, HA);
		House A2 = new House('A', A3, null, false, HA);
		House A1 = new House('A', A2, null, false, HA);
		
		House B6 = new House('B', A1, A1, true, HB);
		House B5 = new House('B', B6, A2, false, HB);
		House B4 = new House('B', B5, A3, false, HB);
		House B3 = new House('B', B4, A4, false, HB);
		House B2 = new House('B', B3, A5, false, HB);
		House B1 = new House('B', B2, A6, false, HB);
		
		A6.next = B1;
		
		A1.opposite = B6;
		A2.opposite = B5;
		A3.opposite = B4;
		A4.opposite = B3;
		A5.opposite = B2;
		A6.opposite = B1;
		
		House houseList[] = new House[12];
		
		int i = 0;
		houseList[i++] = A1;
		houseList[i++] = A2;
		houseList[i++] = A3;
		houseList[i++] = A4;
		houseList[i++] = A5;
		houseList[i++] = A6;
		houseList[i++] = B1;
		houseList[i++] = B2;
		houseList[i++] = B3;
		houseList[i++] = B4;
		houseList[i++] = B5;
		houseList[i++] = B6;
		
		return houseList;
		
	}
	
	public int NextPlayer(int p) {
		return (p + 1) % 2;
	}
	
	public boolean MoveSeed(House[] h, int[] z, int move, int p) {
		
		int toMove;
		
		move--;
		
		House next;
		House curr;
		
		if (move > 5 || move < 0) {
			return false;
		} else if (p == 0) {
			if (h[move].seeds <= 0) {
				return false;
			} else {
				toMove = h[move].seeds;
				h[move].seeds = 0;
				curr = h[move];
				
				if (curr.isEnd) {
					toMove--;
					z[0]++;
					
					if (toMove == 0) {
						return false;
					}
				}
				
				while(toMove > 0) {
					next = curr.next;
					next.seeds++;
					if (toMove == 1 && next.seeds == 1 && next.side == 'A' && next.opposite.seeds > 0) {
						z[0] = z[0] + next.seeds + next.opposite.seeds;
						next.seeds = 0;
						next.opposite.seeds = 0;
						return true;
					}
					if (next.isEnd && next.side == 'A') {
						if (toMove == 2) {
							toMove--;
							z[0]++;
							return false;
						} else if (toMove > 1) {
							toMove--;
							z[0]++;
						}
					} 
					curr = next;
					
					toMove--;
				}
				
				return true;
			}
		} else {
			if (h[move + 6].seeds <= 0) {
				return false;
			} else {
				move = move + 6;
				toMove = h[move].seeds;
				h[move].seeds = 0;
				curr = h[move];
				
				if (curr.isEnd) {
					toMove--;
					z[1]++;
					
					if (toMove == 0) {
						return false;
					}
				}
				
				while(toMove > 0) {
					next = curr.next;
					next.seeds++;
					if (toMove == 1 && next.seeds == 1 && next.side == 'B' && next.opposite.seeds > 0) {
						z[1] = z[1] + next.seeds + next.opposite.seeds;
						next.seeds = 0;
						next.opposite.seeds = 0;
						return true;
					}
					if (next.isEnd && next.side == 'B') {
						if (toMove == 2) {
							toMove--;
							z[1]++;
							return false;
						} else if (toMove > 1) {
							toMove--;
							z[1]++;
						}
					}
					curr = next;
					
					toMove--;
				}
				
				return true;
			}
		}
	}

	public int[] SeedsRemaining(House[] h) {
		int total[] = {0, 0};
		
		for (int i = 0; i < h.length/2; i++) {
			total[0] = total[0] + h[i].seeds;
		}
		
		for (int j = h.length/2; j < h.length; j++) {
			total[1] = total[1] + h[j].seeds;
		}
		
		return total;
	}
}
