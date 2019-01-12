
/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats
 *  Dependencies: Percolation, algs4.StdOut, algs4.StdRandom, algs4.StdStats
 *
 *  This program method that takes two command-line arguments n and T,
 *  performs T independent computational experiments (discussed above) on an
 *  n-by-n grid, and prints the sample mean, sample standard deviation,
 *  and the 95% confidence interval for the percolation threshold.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This class takes two command-line arguments n and T, performs T
 * independent computational experiments (discussed above) on an n-by-n grid,
 * and prints the sample mean, sample standard deviation, and the 95% confidence
 * interval for the percolation threshold.
 *
 * @author Tyler Crosse
 */
public class PercolationStats {
  /**
   * Dimensions of percolation grid to test.
   */
  private int size;
  /**
   * Number of trials.
   */
  private int t;
  /**
   * Threshold for each experiment.
   */
  private double[] thresholds;

  /**
   * Perform trials independent experiments on an n-by-n grid.
   *
   * @param n dimnesions of n by n grid
   * @param trials number of trials to run
   */
  public PercolationStats(final int n, final int trials) {
    validate(n, trials);
    size = n;
    t = trials;
    thresholds = new double[t];

    for (int i = 0; i < trials; i++) {
      thresholds[i] = trial();
    }
  }

  /**
   * Performs a single trial. Returns the threshold.
   *
   * @return threshold for trial
   */
  private double trial() {
    Percolation perc = new Percolation(size);
    int row;
    int col;
    int runs = 0;
    while (!perc.percolates()) {
      do {
        row = StdRandom.uniform(size) + 1;
        col = StdRandom.uniform(size) + 1;
      } while (perc.isOpen(row, col));
      perc.open(row, col);
      runs++;
    }
    return runs / (Math.pow(size, 2));
  }

  /**
   * Sample mean of percolation threshold.
   * @return mean of percolation thresholds
   */
  public double mean() {
    return StdStats.mean(thresholds);
  }

  /**
   * Sample standard deviation of percolation threshold.
   * @return standard deviation of thresholds from all the completed trials
   */
  public double stddev() {
    if (t == 1) {
      return Double.NaN;
    }
    return StdStats.stddev(thresholds);
  }

  /**
   * Low endpoint of 95% confidence interval.
   * @return Low endpoint of 95% confidence interval
   */
  public double confidenceLo() {
    double confidence95Z = 1.96;
    return mean() - (confidence95Z * stddev() / Math.sqrt(t));
  }

  /**
   * High endpoint of 95% confidence interval.
   *
   * @return High endpoint of 95% confidence interval
   */
  public double confidenceHi() {
    double confidence95Z = 1.96;
    return mean() + (confidence95Z * stddev() / Math.sqrt(t));
  }

  /**
   * Validates that the dimensions and  number of trials are greater than zero.
   * @param n dimensions
   * @param trials number of trials
   */
  private void validate(final int n, final int trials) {
    // java.lang.IllegalArgumentException if either n ≤ 0 or trials ≤ 0.
    if (n <= 0) {
      throw new IllegalArgumentException(
        "the size needs to be greater than 0"
      );
    }
    if (trials <= 0) {
      throw new IllegalArgumentException(
        "the number of trials needs to be greater than 0"
      );
    }
  }

  /**
   * Test client (described below).
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percStats = new PercolationStats(n, trials);
    StdOut.println("mean                    = " + percStats.mean());
    StdOut.println("stddev                  = " + percStats.stddev());
    StdOut.println(
      "95% confidence interval = ["
      + percStats.confidenceLo() + ", "
      + percStats.confidenceHi() + "]"
    );
  }
}
