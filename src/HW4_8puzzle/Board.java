package HW4_8puzzle;
import java.util.LinkedList;
import java.util.Queue;


public class Board {
    private int[][] blocks;
    private int N;
   
    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        assert (blocks != null && blocks.length > 0);
        assert (blocks.length == blocks[0].length);
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assert (blocks[i][j] >= 0 && blocks[i][j] < N*N);
                this.blocks[i][j] = blocks[i][j];
            }
        }        
    }
                                           
    public int dimension() {
        // board dimension N
        return N;
    }
    public int hamming() {
        // number of blocks out of place
        int hammingDistance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i*N + j + 1) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattanDistance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0) {
                    int currentVal = blocks[i][j];
                    int correctRow = (currentVal - 1) / N;
                    int correctCol = (currentVal - 1) % N;
                    manhattanDistance += Math.abs(correctRow - i) + Math.abs(correctCol - j);
                }
            }
        }
        return manhattanDistance;
    }
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i*N + j + 1) {
                    return false;
                }
            }
        }
        return true;        
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        //swap the two blocks on top left
        if (N <= 1) {
            return null; //nothing to return when there is only one block
        }
        Board sisterBoard = null;
        if (blocks[0][0] == 0 || blocks[0][1] == 0) {
            swap(blocks, 1, 0, 1, 1);
            sisterBoard = new Board(blocks);
            swap(blocks, 1, 0, 1, 1);
        } else {
            swap(blocks, 0, 0, 0, 1);
            sisterBoard = new Board(blocks);
            swap(blocks, 0, 0, 0, 1);
        }
        return sisterBoard;
    }
    public boolean equals(Object y) {
        // does this board equal y?
        // this looks simple, but is actually tricky
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y; //guaranteed to succeed
        return this.toString().equals(that.toString());
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> q = new LinkedList<Board>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    //there are four directions that the blank block can move
                    //check each of them
                    //left
                    if (j > 0) {
                        swap(blocks, i, j - 1, i, j);
                        q.offer(new Board(blocks));
                        //swap back
                        swap(blocks, i, j - 1, i, j);
                    }
                    //right
                    if (j < N - 1) {
                        swap(blocks, i, j + 1, i, j);
                        q.offer(new Board(blocks));
                        //swap back
                        swap(blocks, i, j + 1, i, j);
                    }
                    //up
                    if (i > 0) {
                        swap(blocks, i - 1, j, i, j);
                        q.offer(new Board(blocks));
                        //swap back
                        swap(blocks, i - 1, j, i, j);
                    }
                    //down
                    if (i < N - 1) {
                        swap(blocks, i + 1, j, i, j);
                        q.offer(new Board(blocks));
                        //swap back
                        swap(blocks, i + 1, j, i, j);
                    }
                }
            }
        }   
        return q;
    }
    private void swap(int[][] a, int x1, int y1, int x2, int y2) {
        int tmp = a[x1][y1];
        a[x1][y1] = a[x2][y2];
        a[x2][y2] = tmp;
    }
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder result = new StringBuilder();
        result.append(N + "\n");
        for (int i = 0; i < N; i++) {            
            for (int j = 0; j < N; j++) {
                result.append(" " + blocks[i][j] + " ");
            }                       
            result.append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = new int[3][3];
        blocks[0] = new int[] {2, 5, 1};
        blocks[1] = new int[] {7, 8, 0};
        blocks[2] = new int[] {3, 4, 6};
        Board test = new Board(blocks);
        System.out.println(test.hamming() == 8);
        System.out.println(test.manhattan() == 13);
        System.out.print(test);
        Board test2 = test;
        Board test3 = null;       
        Board test4 = new Board(blocks);
        //System.out.println(test.equals(test2));
        //System.out.println(!test.equals(test3));
        System.out.println(test.equals(test4));
        System.out.print("neighbors for \n" + test + "...\n");
        for (Board b : test.neighbors()) {
            System.out.print(b);
        }
        System.out.print("twin for \n" + test + "...\n");
        System.out.println(test.twin());
    }
}
