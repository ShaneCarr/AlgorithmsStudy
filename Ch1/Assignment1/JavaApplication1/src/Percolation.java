
import ClassLibrary.*;
import java.util.Random;

public class Percolation {

    private int N = 0; 
    public GridUnionData[][] Data;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("N");
        }

        this.N = n;

        this.Data = new GridUnionData[n][n];
        for (int r = 0; r < this.Data.length; r++) {
            for (int c = 0; c < this.Data.length; c++) {
                GridUnionData d = new GridUnionData();

                // probably dont need this. open is there is a parent id or part of a node set (connected to something)
                // the idea is we'll start at one of the ediges and walk down the node to see if we can end up at the other edge. 
                d.State = SiteState.Blocked;

                // initially set parent id the same. 
                d.ParentId = new SiteId(r, c);
                d.Id = new SiteId(r, c);
                this.Data[r][c] = d;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        // this would call the "connected code for the  code around if they are open.
        this.Data[row][col].State = SiteState.Open;

        // connect if you can to the other open items
        int lowerRow = row - 1;
        int higherrow = row + 1;
        int lowerColumn = col - 1;
        int highercolumn = col + 1;

        // up
        checkAndConnect(row, col, higherrow, col);

        // down
        checkAndConnect(row, col, lowerRow, col);

        // left
        checkAndConnect(row, col, row, highercolumn);

        //right
        checkAndConnect(row, col, row, lowerColumn);

    }

    // check and connect to the adjacent as defined by the problem.
    private void checkAndConnect(int row, int column, int newrow, int newcolumn) {
        SiteId s = new SiteId(row, column);
        SiteId sn = new SiteId(newrow, newcolumn);

        if (newrow >= 0 && newcolumn >= 0) {
            // if we are greter tahn zero
            if (this.Data[newrow][newcolumn].State == SiteState.Open) {
                connected(s, sn);
            }
        }
    }
    
    public SiteId GetNextRandomSite()
    {
        Random m = new Random(System.currentTimeMillis());
        
        
        int column = m.nextInt(this.N-1);
        int row = m.nextInt(this.N-1);
        
        // no poit in returning this.
        while(this.Data[row][column].State == SiteState.Open)
        {
            column = m.nextInt(this.N-1);
            row = m.nextInt(this.N-1);
        }
            
        SiteId s = new SiteId(row, column);
        return s;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.Data[row][col].State == SiteState.Open;

    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        // A full site is an open site that can be connected to an open site in the top row via a chain of neighboring 
        // (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. 

        // check if is connected to for all the "n elements in row 0"
        SiteId s = new SiteId(row, col);

        for (int i = 0; i < N; i++) {
            boolean connectedToRoot = this.connected(s, this.Data[0][i].Id);

            if (connectedToRoot) {
                return true;
            }
        }

        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int bottom = 0; bottom < N; bottom++) {
            for (int top = 0; top < N; top++) {
                // last row
                GridUnionData lastItems = this.Data[N - 1][bottom];
                GridUnionData firstItem = this.Data[0][top];

                // can quit as soon as we find one.
                if (connected(lastItems.Id, firstItem.Id)) {
                    return true;
                }
            }
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(100);
        
    }

    public boolean connected(SiteId p, SiteId q) {
        return root(p).dataItem.Equals(root(q).dataItem);
    }

    public void union(SiteId p, SiteId q) {
        RootResult rootP = root(p);
        RootResult rootQ = root(q);

        // if p's depth is less than qs point p to q. This minimizes the size
        // if q's dept is less then use point to to P's root. 
        // this is becase your starting point the root it is alwasy better to add to teh smaller of the two. rather than the big one.
        // link root to the smaller tree to the larger.
        if (rootP.DepthToCurrent < rootQ.DepthToCurrent) {
            this.Data[rootP.dataItem.Row][rootP.dataItem.Column].ParentId.Row = this.Data[rootQ.dataItem.Row][rootQ.dataItem.Column].Id.Row;
            this.Data[rootP.dataItem.Row][rootP.dataItem.Column].ParentId.Column = this.Data[rootQ.dataItem.Row][rootQ.dataItem.Column].Id.Column;
        } else {
            this.Data[rootQ.dataItem.Row][rootQ.dataItem.Column].ParentId.Row = this.Data[rootP.dataItem.Row][rootP.dataItem.Column].Id.Row;
            this.Data[rootQ.dataItem.Row][rootQ.dataItem.Column].ParentId.Column = this.Data[rootP.dataItem.Row][rootP.dataItem.Column].Id.Column;
        }
    }

    public RootResult root(SiteId id) {
        int debth = 0;
        GridUnionData curr = this.Data[id.Row][id.Column];

        // parent is current this is a root. 
        if (curr.ParentId.Equals(id)) {
            return new RootResult(id, debth);
        }

        RootResult finalRoot = this.rootHelper(this.Data[curr.Id.Row][curr.Id.Column].ParentId /* next level */, debth);

        //flatten. this will tell me if they are connected since this is the "shared node". for a fast search we want as flat a tree as possible.
        this.Data[id.Row][id.Column].ParentId.Row = finalRoot.dataItem.Row;
        this.Data[id.Row][id.Column].ParentId.Column = finalRoot.dataItem.Column;

        return finalRoot;
    }

    protected RootResult rootHelper(SiteId id, int depth) {
        // point to itself so we are done. this is a root.
        if (this.Data[id.Row][id.Column].equals(id)) {
            depth++;
            return new RootResult(id, depth);
        }

        depth++;

        // go to the parent
        return rootHelper(this.Data[id.Row][id.Column].ParentId /* next level */, depth);
    }
}
