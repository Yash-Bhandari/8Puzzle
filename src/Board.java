import java.util.Iterator;

public class Board {
	private final int[][] board;
	private int zeroX = 0;
	private int zeroY = 0;

	public Board(int[][] blocks) {
		board = blocks;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				if (board[i][j] == 0) {
					zeroX = j;
					zeroY = i;
				}
			}
		}
		assert (board[zeroY][zeroX] == 0);
	}

	public int dimension() {
		return board[0].length;
	}

	public int hamming() {
		int total = 0;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				int num = board[i][j];
				int temp = 0;
				if (num != 0) {
					temp += Math.abs(i - (num - 1) / 3);
					temp += Math.abs(j - (num - 1) % 3);
				}
				total += temp == 0 ? 0 : 1;
			}
		}
		return total;
	}

	public int manhattan() {
		int total = 0;
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				int num = board[i][j];
				if (num != 0) {
					total += Math.abs(i - (num - 1) / dimension());
					total += Math.abs(j - (num - 1) % dimension());
				}
			}
		}
		return total;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	public Board twin() {
		int[][] out = board;
		int temp;
		int i1 = 0, j1 = 0, i2 = 0, j2 = 0;
		while (out[i1][j1] == 0) {
			i1 = (int) (Math.random() * dimension());
			j1 = (int) (Math.random() * dimension());
		}
		while (out[i2][j2] == 0 || (i2 == i1 && j2 == j1)) {
			i2 = (int) (Math.random() * dimension());
			j2 = (int) (Math.random() * dimension());
		}
		temp = out[i1][j1];
		out[i1][j1] = out[i2][j2];
		out[i2][j2] = temp;
		return new Board(out);
	}

	@Override
	public boolean equals(Object Y) {
		if (Y instanceof Board) {
			Board a = (Board) Y;
			if (a.dimension() == dimension()) {
				boolean equal = true;
				for (int i = 0; i < dimension(); i++) {
					for (int j = 0; j < dimension(); j++) {
						if (board[i][j] != a.board[i][j])
							equal = false;
					}
				}
				return equal;
			}
		}
		return false;
	}

	public class BoardIterator implements Iterator<Board> {
		int n = 0;
		Board base;

		public BoardIterator(Board base) {
			this.base = base;
		}

		public boolean hasNext() {
			return n < 4;
		}

		public Board next() {
			return null;

		}
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				out += board[i][j];
				out += " ";
			}
			out += "\n";
		}
		return out;
	}

	// Creates board by moving element left of 0 to 0's index
	private Board left() {
		if (zeroX > 0) {
			int[][] temp = copy(board);
			temp[zeroY][zeroX] = temp[zeroY][zeroX - 1];
			temp[zeroY][zeroX - 1] = 0;
			return new Board(temp);
		}
		return null;
	}

	// Creates board by moving element right of 0 to 0's index
	private Board right() {
		if (zeroX < dimension()) {
			int[][] temp = copy(board);
			temp[zeroY][zeroX] = temp[zeroY][zeroX + 1];
			temp[zeroY][zeroX + 1] = 0;
			return new Board(temp);
		}
		return null;
	}

	// Creates board by moving element above 0 to 0's index
	private Board above() {
		if (zeroY > 0) {
			int[][] temp = copy(board);
			temp[zeroY][zeroX] = temp[zeroY - 1][zeroX];
			temp[zeroY - 1][zeroX] = 0;
			return new Board(temp);
		}
		return null;
	}

	// Creates board by moving element below 0 to 0's index
	private Board below() {
		if (zeroY < dimension()) {
			int[][] temp = copy(board);
			temp[zeroY][zeroX] = temp[zeroY + 1][zeroX];
			temp[zeroY + 1][zeroX] = 0;
			return new Board(temp);
		}
		return null;
	}

	private int[][] copy(int[][] a) {
		int[][] b = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				b[i][j] = a[i][j];
			}
		}
		return b;
	}

	public static void main(String[] args) {

		int[][] a = { { 1, 2, 3 }, { 0, 5, 6 }, { 7, 8, 4 } };
		Board b = new Board(a);
		System.out.println("original \n" + b);
		System.out.println("left \n" + b.left());
		System.out.println("right \n" + b.right());
		System.out.println("above \n" + b.above());
		System.out.println("below \n" + b.below());
	}

}
