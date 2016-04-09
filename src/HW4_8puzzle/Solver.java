package HW4_8puzzle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private ArrayList<Board> solution;
    private boolean solvable = true;
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new ManhattanComparator());
        MinPQ<SearchNode> sisterPQ = new MinPQ<SearchNode>(new ManhattanComparator());
        SearchNode lastSearchNode = null;
        SearchNode lastSisterSearchNode = null;
        solution = new ArrayList<Board>();
        pq.insert(new SearchNode(initial, null, 0));
        sisterPQ.insert(new SearchNode(initial.twin(), null, 0));
        while (true) {
            lastSearchNode = searchNext(pq);
            if (lastSearchNode != null) {
                break;
            }
            lastSisterSearchNode = searchNext(sisterPQ);
            if (lastSisterSearchNode != null) {
                solvable = false;
                break;
            }
        }
        while (lastSearchNode != null) {
            solution.add(lastSearchNode.board);
            lastSearchNode = lastSearchNode.previous;
        }
        Collections.reverse(solution);
    }
    /*
     * return null if we reach the goal, otherwise a MinPQ object
     */
    private SearchNode searchNext(MinPQ<SearchNode> pq) {
        SearchNode current = pq.delMin();   
        if (current.board.isGoal()) {
            return current;
        } else {
            for (Board b : current.board.neighbors())
                if (current.previous == null || !b.equals(current.previous.board)) {                    
                    pq.insert(new SearchNode(b, current, current.moves + 1));
                }
            return null;
        }
    }
    private class SearchNode {
        private Board board;
        private SearchNode previous;        
        private int moves;
        private int hamming;
        private int manhattan;
        public SearchNode(Board b, SearchNode p, int m) {
            this.previous = p;
            this.board = b;            
            this.moves = m;
            this.hamming = b.hamming();
            this.manhattan = b.manhattan();            
        }
    }
    private static class HammingComparator implements Comparator<SearchNode> {        
        public int compare(SearchNode s1, SearchNode s2) {
            return s1.hamming + s1.moves - s2.hamming - s2.moves;
        }
    }
    private static class ManhattanComparator implements Comparator<SearchNode> {
        public int compare(SearchNode s1, SearchNode s2) {
            return s1.manhattan + s1.moves - s2.manhattan - s2.moves;
        }
    }
    public boolean isSolvable() {
        // is the initial board solvable?        
        return solvable;
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable()) return -1;
        return solution.size() - 1;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        return solution;
    }
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

