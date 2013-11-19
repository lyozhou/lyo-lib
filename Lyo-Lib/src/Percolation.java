

public class Percolation {
	private static final int OPEN = 1;

	private int[][] grid;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF backwash;
	private int n;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		n = N;
		// StdOut.println(n+"*"+n+" Matix");
		grid = new int[N][N];
		uf = new WeightedQuickUnionUF(N * N + 1);
		backwash = new WeightedQuickUnionUF(N * N + 2);
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		int row = i - 1;
		int col = j - 1;
		if (i < 0 || i > n || j < 0 || j > n) {
			throw new java.lang.IndexOutOfBoundsException("out of bound");
		}
		grid[row][col] = OPEN;
		if(row == 0){
			uf.union(xyTonum(row, col), 0);
			backwash.union(xyTonum(row, col), 0);
		}
		if(row == n-1){
			backwash.union(xyTonum(row, col), n*n+1);
		}
		if ((row + 1) < n && isOpen(i + 1, j)) {
			uf.union(xyTonum(row, col), xyTonum(row + 1, col));
			backwash.union(xyTonum(row, col), xyTonum(row + 1, col));
		}
		if ((row - 1) >= 0 && isOpen(i - 1, j)) {
			uf.union(xyTonum(row, col), xyTonum(row - 1, col));
			backwash.union(xyTonum(row, col), xyTonum(row - 1, col));
		}
		if ((col + 1) < n && isOpen(i, j + 1)) {
			uf.union(xyTonum(row, col), xyTonum(row, col + 1));
			backwash.union(xyTonum(row, col), xyTonum(row, col + 1));
		}
		if ((col - 1) >= 0 && isOpen(i, j - 1)) {
			uf.union(xyTonum(row, col), xyTonum(row, col - 1));
			backwash.union(xyTonum(row, col), xyTonum(row, col - 1));
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		int row = i - 1;
		int col = j - 1;
		if (i < 0 || i > n || j < 0 || j > n) {
			throw new java.lang.IndexOutOfBoundsException("out of bound");
		}
		return grid[row][col] == OPEN;
	}

	public boolean isFull(int i, int j) {
		int row = i - 1;
		int col = j - 1;
		if (i <= 0 || i > n || j <= 0 || j > n) {
			throw new java.lang.IndexOutOfBoundsException("out of bound");
		}
		if (uf.connected(xyTonum(row,col), 0)) {
			//StdOut.println("yes");
			return true;
		} else
			return false;
	}

	// does the system percolate?
	public boolean percolates() {
		return backwash.connected(0, n * n + 1);
	}

	private int xyTonum(int i, int j) {
		return n * i + j + 1;
	}

	private void printArr() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == OPEN)
					StdOut.print("o ");
				else
					StdOut.print("x ");
			}
			StdOut.println(" ");
		}
	}

	public static void main(String[] args) {
		// int N = StdIn.readInt();
		Percolation per = new Percolation(3);
		per.open(1, 3);
		per.open(1, 2);
		per.open(2, 3);
		per.open(3, 3);
		per.open(3, 1);
		while (!StdIn.isEmpty()) {
			int i = StdIn.readInt();
			int j = StdIn.readInt();

			per.isFull(i, j);
			// if(per.percolates()){
			// StdOut.println("Finished");
			// break;
			// }
		}
	}
}
