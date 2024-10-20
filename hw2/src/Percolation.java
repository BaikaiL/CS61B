import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // TODO: Add any necessary instance variables.
    //虚拟顶点
    private int virtualTop;

    //虚拟低点
    private int virtualBottom;

    //open状态的格子的数量
    private int opensiteNumber;

    //uf来判断是否链接
    private WeightedQuickUnionUF uf;

    //一个没有virtualBottom的uf，来防止出现回流状况
    private WeightedQuickUnionUF noBackWashUf;

    //整个网格的大小
    private int N;

    //记录格子是否打开
    private boolean[][] grid;

    public Percolation(int N) {
        //判断N值是否合法
        if (N < 1){
            throw new IllegalArgumentException();
        }

        //初始化grid数组
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        this.N = N;
        // index==N*N 来存储virtualTop，index==N*N+1 来储存virtualBottom
        uf = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = N*N;
        virtualBottom = N*N+1;
        opensiteNumber = 0;
        //只要Top，没有Bottom
        noBackWashUf = new WeightedQuickUnionUF(N * N + 1);
    }

    private int xyto1D(int r,int c){
        return r * N + c;
    }

    public void open(int row, int col) {
        //判断row，col是否合法
        if(row < 0 || row >= N || col < 0 || col >= N){
            throw new IndexOutOfBoundsException();
        }

        //改变grid数组的值
        grid[row][col] = true;

        //处于打开状态的节点数增加
        opensiteNumber++;

        //链接周围open的节点
        UnionNeighbor(row,col);

    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        return noBackWashUf.connected(xyto1D(row,col), virtualTop);
    }

    public int numberOfOpenSites() {
        return opensiteNumber;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }


    private void UnionNeighbor(int row, int col){
        //链接上节点，如果是第一行，则与虚拟顶点链接
        if(row == 0){
            uf.union(xyto1D(row,col), virtualTop);
            noBackWashUf.union(xyto1D(row,col), virtualTop);
        }
        else if(isOpen(row-1,col)){
            uf.union(xyto1D(row-1,col), xyto1D(row,col));
            noBackWashUf.union(xyto1D(row-1,col), xyto1D(row,col));
        }
        //链接左节点
        if(col > 0 && isOpen(row,col-1)){
            uf.union(xyto1D(row,col-1), xyto1D(row,col));
            noBackWashUf.union(xyto1D(row,col-1), xyto1D(row,col));
        }
        //链接右节点
        if(col < N-1 && isOpen(row,col+1)){
            uf.union(xyto1D(row,col+1), xyto1D(row,col));
            noBackWashUf.union(xyto1D(row,col+1), xyto1D(row,col));
        }
        //链接下节点，如果是最后一行，则与虚拟低点链接
        if(row == N-1){
            uf.union(xyto1D(row,col), virtualBottom);
        }
        else if(isOpen(row+1,col)){
            uf.union(xyto1D(row+1,col), xyto1D(row,col));
            noBackWashUf.union(xyto1D(row+1,col), xyto1D(row,col));
        }
    }

}
