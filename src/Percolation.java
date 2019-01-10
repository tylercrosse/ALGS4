
/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation n
 *  Dependencies: WeightedQuickUnionUF, algs4.StdOut, algs4.WeightedQuickUnionUF
 *
 *  This program takes the grid size n as a command-line argument.
 *  Then, the user repeatedly clicks sites to open with the mouse.
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The Percolation class models percolation system using an n-by-n grid of
 * sites. Each site is either open or blocked. A full site is an open site that
 * can be connected to an open site in the top row via a chain of neighboring
 * (left, right, up, down) open sites. We say the system percolates if there is
 * a full site in the bottom row. In other words, a system percolates if we fill
 * all open sites connected to the top row and that process fills some open site
 * on the bottom row.
 *
 * @author Tyler Crosse
 */
public class Percolation {
    /**
     * Dimensions of square grid.
     */
    private int size;
    /**
     * Matrix representation of n by n grid of booleans.
     */
    private boolean[][] grid;
    /**
     * Instnace of Weighted Quick Union Find.
     */
    private WeightedQuickUnionUF wqf;
    /**
     * The number of open sites.
     */
    private int numOpen;

    /**
     * Create n-by-n grid, with all sites blocked.
     *
     * @param n size
     */
    public Percolation(final int n) {
        size = n;
        grid = new boolean[n][n];
        wqf = new WeightedQuickUnionUF(n * n);
    }

    /**
     * Open site (row, col) if it is not open already.
     *
     * @param row the row number
     * @param col the col number
     */
    public void open(final int row, final int col) {
        checkBounds(row, col);
        if (!isOpen(row, col)) {
            numOpen++;
            grid[row - 1][col - 1] = true;
            int oneDsite = xyTo1D(row, col);
            // above
            if (row + 1 <= size && isOpen(row + 1, col)) {
                // StdOut.println("connecting ["+row+", "+col+"] with
                // ["+(row + 1)+", "+col+"]");
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
     * Is site (row, col) open?
     *
     * @param row the row number
     * @param col the col number
     * @return boolean
     */
    public boolean isOpen(final int row, final int col) {
        return grid[row - 1][col - 1];
    }

    /**
     * Is site (row, col) full?
     *
     * @param row the row number
     * @param col the col number
     * @return boolean
     */
    public boolean isFull(final int row, final int col) {
        if (isOpen(row, col)) {
            // loop through first row to see
            for (int k = 0; k < size; k++) {
                if (wqf.connected(xyTo1D(row, col), k)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Number of open sites.
     *
     * @return int
     */
    public int numberOfOpenSites() {
        return numOpen;
    }

    /**
     * Does the system percolate?
     *
     * a system percolates if we fill all open sites connected to the top row
     * and that process fills some open site on the bottom row
     *
     * @return boolean
     */
    public boolean percolates() {
        // bottom row (grid[n - 1][0] ... grid[n - 1][n - 1])
        for (int j = 1; j <= size; j++) {
            // StdOut.println("j: "+j+" isOpen: "+ isOpen(size, j) +
            // " isFull: " + isFull(size, j));
            if (isFull(size, j)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Map from a 2-dimensional (row, column) pair to a 1-dimensional union find
     * object index.
     *
     * @param row the row number
     * @param col the col number
     * @return int
     */
    private int xyTo1D(final int row, final int col) {
        return (row - 1) * size + (col - 1);
    }

    /**
     * Throws an error if row or column are not inside the grid.
     *
     * @param row the row number
     * @param col the col number
     */
    private void checkBounds(final int row, final int col) {
        if (row <= 0 || row > size) {
            throw new IndexOutOfBoundsException(
                "row index " + row + " out of bounds"
            );
        }
        if (col <= 0 || col > size) {
            throw new IndexOutOfBoundsException(
                "col index " + col + " out of bounds"
            );
        }
    }

    /**
     * Test client.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(n + " by " + n + " grid");
        Percolation perc = new Percolation(n);
        perc.open(1, 1);
        perc.open(2, 1);
        StdOut.println("percolates?" + perc.percolates());
    }
}
