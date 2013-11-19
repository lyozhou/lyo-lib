public class PercolationStats {
	private double sum;
	private double meanNum;
	private double std;
	private double[] result;
	private int tryTime;

	// perform T independent computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException(
					"Illegal parameter values");
		}
		tryTime = T;
		result = new double[T];
		int count;
		int k = 0;
		sum = 0.0;
		while (k < T) {
			Percolation pc = new Percolation(N);
			count = 0;
			while (!pc.percolates()) {
				int i = StdRandom.uniform(N + 1);
				if (i == 0)
					i++;
				int j = StdRandom.uniform(N + 1);
				if (j == 0)
					j++;
				if (!pc.isOpen(i, j)) {
					pc.open(i, j);
					count++;
				}
			}
			// StdOut.println("count =" + count );
			result[k] = (double) count / (N * N);
			sum += result[k];
			k++;
		}
		// pc.printArr();

	}

	// StdOut.println("sum =" + sum );

	// sample mean of percolation threshold
	public double mean() {
		meanNum = (double) sum / tryTime;
		// StdOut.println("meanNum = " + meanNum);
		return meanNum;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double stdSum = 0.0;
		double num = 0.0;
		for (int i = 0; i < tryTime; i++) {
			num = (double) Math.pow((result[i] - mean()), 2);
			stdSum += num;
		}
		// StdOut.println("std = " + stdSum);
		std = Math.sqrt((double) stdSum / (tryTime - 1));
		return std;
	}

	// returns lower bound of the 95% confidence interval
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(tryTime));
	}

	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(tryTime));
	}

	// test client, described below
	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats pcs = new PercolationStats(N, T);
		StdOut.println("% java PercolationStats " + N + " " + T);
		StdOut.println("95% confidence interval = " + pcs.confidenceLo() + " "+ pcs.confidenceHi());
		StdOut.println("stddev                  = " + pcs.stddev());
		StdOut.println("mean                    = " + pcs.mean());
	}
}
