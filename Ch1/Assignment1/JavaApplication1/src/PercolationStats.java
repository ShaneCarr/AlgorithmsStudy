import edu.princeton.cs.algs4.*;
public class PercolationStats {

    private int runs;
    private Percolation pr;
    private double[] fractions;

    /**
     * Performs T independent computational experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        runs = T;
        fractions = new double[runs];
        for (int exp = 0; exp < runs; exp++) {
            pr = new Percolation(N);
            int openedSites = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            
            // percentange of sites open
            double percentageOfOpensites = (double) openedSites / (N * N);
            fractions[exp] = percentageOfOpensites;
        }
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        // they do this for me.
        return StdStats.mean(fractions);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        // they do this for me.
        return StdStats.stddev(fractions);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - confidenceInterval95();
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + confidenceInterval95();
    }
    
    public double confidenceInterval95()
    {
        return ((1.96 * stddev()) / Math.sqrt(runs));
        
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int runs = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(N, runs);

        String confidence = perc.confidenceLo() + ", " + perc.confidenceHi();
        System.out.println("The Mean                    = " + perc.mean());
        System.out.println("The Standard Deviation                  = " + perc.stddev());
        System.out.println("The 95% Confidence Interval = " + confidence);
    }
}