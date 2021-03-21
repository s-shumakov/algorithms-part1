import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolationThresholds;
    private final int trials;
    private final int gridSize;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) throw new IllegalArgumentException("arguments must be larger then 0");
        percolationThresholds = new double[trials];
        this.trials = trials;
        gridSize = n;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);         // n-by-n percolation system
        int t = Integer.parseInt(args[1]);         // trials
        PercolationStats stats = new PercolationStats(n, t);

        for (int i = 0; i < stats.trials; i++) {
            Percolation percolation = new Percolation(stats.gridSize);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            stats.percolationThresholds[i] = percolation.numberOfOpenSites() * 1.0/ (n * n);
        }

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
