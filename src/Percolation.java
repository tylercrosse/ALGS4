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
    private int size; // size of grid
    private boolean[][] grid;
    public WeightedQuickUnionUF wqf;
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
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            numOpen++;
            grid[row - 1][col - 1] = true;
            int oneDsite = xyTo1D(row, col);
            // above
            if (row + 1 <= size && isOpen(row + 1, col)) {
                // StdOut.println("connecting ["+row+", "+col+"] with ["+(row + 1)+", "+col+"]");
                wqf.union(oneDsite, xyTo1D(row + 1, col));
            }
            // right
            if (col + 1 <= size && isOpen(row, col + 1)) {
                wqf.union(oneDsite, xyTo1D(row, col + 1));
            }
            // below
            if (row - 1 >= 1 && isOpen(row - 1, col)) {
                wqf.union(oneDsite, xyTo1D(row - 1, col));
            }
            // left
            if (col - 1 >= 1 && isOpen(row, col - 1)) {
                wqf.union(oneDsite, xyTo1D(row, col - 1));
            }
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
            // loop through first row to see
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
     * a system percolates if we fill all open sites connected to the top row 
     * and that process fills some open site on the bottom row
     *
     * @return boolean
     */
    public boolean percolates() {
        // bottom row (grid[n - 1][0] ... grid[n - 1][n - 1])
        for (int j = 1; j <= size; j++) {
            // StdOut.println("j: "+j+" isOpen: "+ isOpen(size, j) + " isFull: " + isFull(size, j));
            if (isFull(size, j)) return true;
        }
        return false;
    }

    /**
     * map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     *
     * @param row
     * @param col
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    /**
     * throws an error if row or column are not inside the grid
     * 
     * @param row
     * @param col
     */
    private void checkBounds(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IndexOutOfBoundsException("row index "+ row +" out of bounds");
        }
        if (col <= 0 || col > size) {
            throw new IndexOutOfBoundsException("col index "+ col +" out of bounds");
        }
    }

    /**
     * test client
     *
     * @param args
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(n + " by " + n + " grid");
        Percolation perc = new Percolation(n);
        perc.open(1, 1);
        perc.open(2, 1);
        // StdOut.println(perc.isOpen(1, 1));
        // StdOut.println(perc.isOpen(1, 2));
        StdOut.println("percolates?" + perc.percolates());
        // StdOut.println(perc.wqf.connected(perc.xyTo1D(1, 1), perc.xyTo1D(1, 2)));
        // StdOut.println(perc.wqf.connected(perc.xyTo1D(2, 1), perc.xyTo1D(1, 2)));
    }
}
