/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation n
 *  Dependencies: WeightedQuickUnionUF
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF wqf;
    private int numOpen;

    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n size
     */
    public Percolation(int n) {
        size = n;
        grid = new boolean[n][n];
        wqf = new WeightedQuickUnionUF(n * n);
    }

    /**
     * open site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            numOpen++
        }
    }

    /**
     * is site (row, col) open?
     *
     * @param row
     * @param col
     * @return boolean
     */
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1];
    }

    /**
     * is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            for (int k = 0; k < size; k++) {
                if (wqf.connected(xyTo1D(row, col), k)) return true;
            }
        }
        return false;
    }

    /**
     * number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return numOpen;
    }

    /**
     * does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return true;
    }

    /**
     * map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     *
     * @param row
     * @param col
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * (col - 1) * size;
    }

    /**
     * test client
     *
     * @param args
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(n);
        // Percolation p = new Percolation(n);
        StdOut.println(n + " by " + n + " grid");
    }
}
