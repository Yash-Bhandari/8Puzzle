import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private final int[][] board;
    private int zeroX = 0;
    private int zeroY = 0;
    private int hamming;
    private int manhattan;

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
        setManhattan();
        setHamming();
        assert (board[zeroY][zeroX] == 0);
    }

    public int dimension() {
        return board[0].length;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }
    
    public int hamming() {
        return hamming;
    }
    
    public int manhattan() {
        return manhattan;
    }

    public Board twin() {
        int[][] out = copy(board);
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

    private void setHamming() {
        int total = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int num = board[i][j];
                int temp = 0;
                if (num != 0) {
                    temp += Math.abs(i - (num - 1) / dimension());
                    temp += Math.abs(j - (num - 1) % dimension());
                }
                total += temp == 0 ? 0 : 1;
            }
        }
        hamming = total;
    }

    private void setManhattan() {
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
        manhattan = total;
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
        if (zeroX < dimension() - 1) {
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
        if (zeroY < dimension() - 1) {
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

    public Iterable<Board> neighbors() {
        ArrayList<Board> adj = new ArrayList<Board>();
        Board left = left();
        Board right = right();
        Board above = above();
        Board below = below();
        if (left != null)
            adj.add(left);
        if (right != null)
            adj.add(right);
        if (above != null)
            adj.add(above);
        if (below != null)
            adj.add(below);
        return adj;
    }

    public static void main(String[] args) {
        int[][] a = { { 4, 8, 2 }, { 3, 6, 5 }, { 1, 7, 0 } };
        Board b = new Board(a);
        System.out.println(b);
        System.out.println(b.manhattan());
    }

}
