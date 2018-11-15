import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
public class Solver {
	private boolean solvable;
	private MinPQ<Node> pq;
	//private Comparator<Node> hamming = new Hamming();
	//private Comparator<Node> manhattan = new Manhattan();
	
	public Solver(Board initial) {
		pq = new MinPQ<Node>();
		pq.insert(new Node(null, initial));
		delete();
		for (Node n : pq) {
			System.out.println(n.val);
		}
	}
	
	public boolean isSolvable() {
		return solvable;
	}
	
	private class Node implements Comparable<Node>{
		public Board val;
		private Node prev;
		
		public Node(Node prev, Board val) {
			this.prev = prev;
			this.val = val;
		}

		public int compareTo(Node n) {
			return this.val.hamming() - n.val.hamming();
		}

	
	}
	
	/*private class Hamming implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			return n1.val.hamming() - n2.val.hamming();
		}
	}
	
	private class Manhattan implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			return n1.val.manhattan() - n2.val.manhattan();
		}
	}*/
	
	private void delete() {
		Node low = pq.delMin();
		Iterator<Board> i = low.val.iterator();
		while (i.hasNext()) {
			pq.insert(new Node(low, i.next()));
		}
	}
	
	public static void main(String[] args) {
		int[][] a = { 
				{ 1, 2, 3 }, 
				{ 0, 5, 6 }, 
				{ 7, 8, 4 } };
		Board b = new Board(a);
		Solver s = new Solver(b);
	}
	
	
}
