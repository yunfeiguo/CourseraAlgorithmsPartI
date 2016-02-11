package HW1_Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] a; //store Monte Carlo results
    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            IllegalArgumentException e = new IllegalArgumentException("Must see: N<=0,T<=0");
            throw(e);
        }
        a = new double[T];
        //StdRandom.setSeed(0); //repeated calls to setSeed may lead to bias 
        for (int i = 0; i < T; i++) {
            Percolation exp = new Percolation(N);
            int count = 0;
            while (!exp.percolates()) {
                int j = StdRandom.uniform(1, N + 1);
                int k = StdRandom.uniform(1, N + 1);
                if (!exp.isOpen(j, k)) {
                    exp.open(j, k);
                    count++; //open site count
                }
            }
            a[i] = 1.0 * count / N / N;
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return (StdStats.mean(a));
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return (StdStats.stddev(a));
    }
    public double confidenceLo()  {
        // low  endpoint of 95% confidence interval
        // x_bar - 1.96*s/sqrt(n)
        return (mean() - 1.96 * stddev() / Math.sqrt(1.0 * a.length));
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return (mean() + 1.96 * stddev() / Math.sqrt(1.0 * a.length));
    }

    public static void main(String[] args)   {
        // test client (described below)
        PercolationStats test = new PercolationStats(500, 200);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }

}
