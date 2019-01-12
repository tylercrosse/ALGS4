
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

public class PercolationStats {
  private int size;
  private int t;
  private double[] thresholds;

  /**
   * perform trials independent experiments on an n-by-n grid
   * 
   * @param n
   * @param trials
   */
  public PercolationStats(int n, int trials) {
    validate(n, trials);
    size = n;
    t = trials;
    thresholds = new double[t];
    
    for (int i = 0; i < trials; i++) {
      thresholds[i] = trial();
    }
  }

  /**
   * performs a single trial
   * 
   * @param i
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
   * sample mean of percolation threshold
   * 
   * @return
   */
  public double mean() {
    return StdStats.mean(thresholds);
  }
  
  /**
   * sample standard deviation of percolation threshold
   * @return
   */
  public double stddev() {
    if (t == 1) {
      return Double.NaN;
    }
    return StdStats.stddev(thresholds);
  }

  /**
   * low  endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceLo() {
    return mean() - (1.96 * stddev() / Math.sqrt(t));
  }
  
  /**
   * high endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceHi() {
    return mean() + (1.96 * stddev() / Math.sqrt(t));
  }

  private void validate(int n, int trials) {
    // java.lang.IllegalArgumentException if either n ≤ 0 or trials ≤ 0.
    if (n <= 0) {
      throw new IllegalArgumentException("the size needs to be greater than 0");
    }
    if (trials <= 0) {
      throw new IllegalArgumentException("the number of trials needs to be greater than 0");
    }
  }

  /**
   * test client (described below)
   * 
   * @param args
   * @return
   */
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percStats = new PercolationStats(n, trials);
    StdOut.println("mean                    = "+ percStats.mean());
    StdOut.println("stddev                  = "+ percStats.stddev());
    StdOut.println("95% confidence interval = ["+ percStats.confidenceLo()+", "+percStats.confidenceHi()+"]");
  }
}
