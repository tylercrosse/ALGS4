/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats
 *  Dependencies: Percolation
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
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