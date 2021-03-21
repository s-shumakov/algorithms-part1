import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final boolean[] openedSites;
    private int openedSitesCount = 0;
    private final int virtualTopSiteId;
    private final int virtualBottomSiteId;
    private final WeightedQuickUnionUF unionUF;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be larger then 0");
        gridSize = n;
        virtualTopSiteId = n * n;
        virtualBottomSiteId = n * n  + 1;
        unionUF = new WeightedQuickUnionUF(n * n + 2);
        openedSites = new boolean[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        int siteId = rowColTo1D(row, col);
        if (!openedSites[siteId]) {
            openedSites[siteId] = true;
            openedSitesCount++;
            unionVirtualSites(row, col);
            unionNeighborsSite(row, col, row - 1, col);
            unionNeighborsSite(row, col, row + 1, col);
            unionNeighborsSite(row, col, row, col - 1);
            unionNeighborsSite(row, col, row, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openedSites[rowColTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && unionUF.find(rowColTo1D(row, col)) == unionUF.find(virtualTopSiteId);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionUF.find(virtualBottomSiteId) == unionUF.find(virtualTopSiteId);
    }

    private int rowColTo1D(int row, int col) {
        return (row - 1) * gridSize + col - 1;
    }

    private boolean isValid(int row, int col) {
        if (row > gridSize || row < 1) return false;
        if (col > gridSize || col < 1) return false;
        return true;
    }

    private void validateIndices(int row, int col) {
        if (row > gridSize || row < 1) throw new IllegalArgumentException("row " + row + " out of bounds");
        if (col > gridSize || col < 1) throw new IllegalArgumentException("col " + col + " out of bounds");
    }

    private void unionNeighborsSite(int row1, int col1, int row2, int col2) {
        if (isValid(row2, col2) && isOpen(row2, col2)) {
            unionUF.union(rowColTo1D(row1, col1), rowColTo1D(row2, col2));
        }
    }

    private void unionVirtualSites(int row, int col) {
        if (row == 1) {
            unionUF.union(rowColTo1D(row, col), virtualTopSiteId);
        }
        if (row == gridSize) {
            unionUF.union(rowColTo1D(row, col), virtualBottomSiteId);
        }
    }
}
