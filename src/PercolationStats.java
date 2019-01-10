
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
  private int[] thresholds;

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
    for (int i = 0; i < trials; i++) {
      trial(i);
    }
  }

  /**
   * performs a single trial
   * 
   * @param i
   */
  private void trial(int i) {
    Percolation perc = new Percolation(size);
    int k = 0;
    while (!perc.percolates() || k <= size) {
      int row = StdRandom.uniform(1, size + 1);
      int col = StdRandom.uniform(1, size + 1);
      StdOut.println("row: "+row+" col: "+col);
      perc.open(row, col);
      k++;
    }
    StdOut.println("Trial: "+i+" Threshold: "+k);
    thresholds[i] = k;
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
    return StdStats.stddev(thresholds);
  }

  /**
   * low  endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceLo() {
    return -1;
  }
  
  /**
   * high endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceHi() {
    return -1;
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
    // StdOut.println("uniform: "+ StdRandom.uniform(1, n + 1));
    StdOut.println("uniform: "+ StdRandom.uniform(1, n + 1));
    PercolationStats percStats = new PercolationStats(n, trials);
    StdOut.println("mean                    = "+ percStats.mean());
    StdOut.println("stddev                  = "+ percStats.stddev());
    StdOut.println("95% confidence interval = ["+ percStats.confidenceLo()+", "+percStats.confidenceHi()+"]");
  }
}
