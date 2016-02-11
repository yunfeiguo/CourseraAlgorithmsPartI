package HW1_Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 1; //1
    private static final byte CLOSED = 0; //0
    private static final byte FULL = 2; //10
    private static final byte CONNECTEDTOOPENBOTTOM = 4; //100
    private int N;
    private byte[][] grid; //2D array storing status
    private WeightedQuickUnionUF u; //UnionFind obj for storing connections
    private boolean isPercolate;
    //in row major order

    /**
     * Initializes a <tt>NXN<tt> grid with closed sites     
     *
     * @param  n column/row width
     */
    public Percolation(int n) {
        // create N-by-N grid, with all sites blocked   
        if (n <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("N<=0");
            throw(e);
        }
        N = n;
        //construct 2D array for storing closed/open status
        grid = new byte[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = CLOSED;
            }
        }
        //construct UnionFind obj for querying full status
        u = new WeightedQuickUnionUF(N * N);
    }
    /**
     * open a site if it is not
     * connect this site with neighboring open sites
     * @param i row index
     * @param j column index
     */

    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        if (i < 1 || i > N || j < 1 || j > N) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException();
            throw (e);
        }
        //if this site is open, just return
        if ((grid[i - 1][j - 1] & OPEN) != 0) return;
        //otherwise set status to OPEN
        grid[i - 1][j - 1] |= OPEN;
        boolean currentFull = false;
        boolean currentConnectedBottom = false;
        if (i == N) currentConnectedBottom = true; 
        //bottom row's root is always connected to open bottom
        if (i == 1) currentFull = true; //top row is always full for open sites
        //connect this open site with neighboring sites
        //up
        if (i - 1 >= 1 && isOpen(i - 1, j)) {
            if (isFull(i - 1, j)) currentFull = true;
            if (isConnectedToOpenBottom(i - 1, j)) currentConnectedBottom = true;
            u.union(N*(i - 2) + j - 1, N*(i - 1) + j - 1);
        }
        //down
        if (i + 1 <= N && isOpen(i + 1, j)) {
            if (isFull(i + 1, j)) currentFull = true;
            if (isConnectedToOpenBottom(i + 1, j)) currentConnectedBottom = true;
            u.union(N*i + j - 1, N*(i - 1) + j - 1);
        }
        //left
        if (j - 1 >= 1 && isOpen(i, j - 1)) {
            if (isFull(i, j - 1)) currentFull = true;
            if (isConnectedToOpenBottom(i, j - 1)) currentConnectedBottom = true;
            u.union(N*(i - 1) + j - 2, N*(i - 1) + j - 1);
        }
        //right
        if (j + 1 <= N && isOpen(i, j + 1)) {
            if (isFull(i, j + 1)) currentFull = true;
            if (isConnectedToOpenBottom(i, j + 1)) currentConnectedBottom = true;
            u.union(N*(i - 1) + j, N*(i - 1) + j - 1);
        }
        //if i,j is full
        //make its new root full
        int rootIdx = u.find(N*(i - 1) + j - 1);
        if (currentFull) {
            grid[rootIdx / N][rootIdx % N] |= FULL;
        }
        //if on bottom row and is full, mark its root as
        //connected to bottom
        if (currentConnectedBottom) {
            grid[rootIdx / N][rootIdx % N] |= CONNECTEDTOOPENBOTTOM;
        } 
        if (currentConnectedBottom && currentFull) isPercolate = true;
    }
    private boolean isConnectedToOpenBottom(int i, int j) {
        // is site (row i, column j) full?
        if (i < 1 || i > N || j < 1 || j > N) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException();
            throw (e);
        }
        int rootIdx = u.find(N*(i - 1) + j - 1);
        return ((grid[rootIdx / N][rootIdx % N] & CONNECTEDTOOPENBOTTOM) != 0);
    }
    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        if (i < 1 || i > N || j < 1 || j > N) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException();
            throw (e);
        }
        return (((grid[i - 1][j - 1] & FULL) != 0) || ((grid[i - 1][j - 1] & OPEN) != 0));
    }
    /**
     * determine if this site is open and connected to open sites
     * on top row
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        if (i < 1 || i > N || j < 1 || j > N) {
            IndexOutOfBoundsException e = new IndexOutOfBoundsException();
            throw (e);
        }
        int rootIdx = u.find(N*(i - 1) + j - 1);
        return ((grid[rootIdx / N][rootIdx % N] & FULL) != 0);
    }
    public boolean percolates()   {
        // does the system percolate?
        return (isPercolate);
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation test = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                test.open(i, j);
                System.out.println(test.isFull(i, j));
                System.out.println(test.percolates());
            }
        }
    }
}
