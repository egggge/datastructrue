import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: egg
 * @Date: 2019-04-18 15:06
 */
public class MatrixUDG {
    private int mEdgNum;
    private char[] mVexs;
    private int[][] mMatrix;

    private static final int INF = Integer.MAX_VALUE;

    public MatrixUDG(){
        //键盘输入顶点和边数
        System.out.println("input vertex number:");
        int vlen = readInt();
        System.out.println("input edge number:");
        int elen = readInt();
        if (vlen<1||elen<1||elen>(vlen*(vlen-1))){
            System.out.println("input error: invalid parameters!\n");
            return;

        }
        //初始化顶点，给顶点编号
        mVexs = new char[vlen];
        for (int i=0;i<mVexs.length;i++){
            //注意println和printf的区别
            System.out.printf("vertex(%d): ",i);
            mVexs[i]=readChar();

        }

        //初始化边的权值,对角线为0，其余为无限值
        mEdgNum = elen;
        mMatrix = new int[vlen][vlen];
        for (int i=0;i<vlen;i++){
            for (int j=0;j<vlen;j++){
                if (i==j){
                    mMatrix[i][j]=0;
                }
                else {
                    mMatrix[i][j]=INF;
                }
            }
        }

        //根据用户输入值，初始化边权值
        for (int i=0;i<elen;i++) {
            //读取边的两点，和权值
            // 读取边的起始顶点,结束顶点,权值
            System.out.printf("edge(%d):", i);
            char c1 = readChar();       // 读取"起始顶点"
            char c2 = readChar();       // 读取"结束顶点"
            int weight = readInt();     // 读取"权值"

            int p1 = getPosition(c1);
            int p2 = getPosition(c2);
            if (p1 == -1 || p2 == -1) {
                System.out.printf("input error: invalid edge!\n");
                return;
            }
            mMatrix[p1][p2] = weight;
            mMatrix[p2][p1] = weight;
        }

    }

    //边的结构

    private static class EData{
        char start; // 边的起点
        char end;   // 边的终点
        int weight; // 边的权重

        public EData(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }




    /**
     * 读取数字
     * @return
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * 读取一个字符
     * @return
     */
    private char readChar() {
        char ch='0';

        do {
            try {
                ch = (char)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!((ch>='a'&&ch<='z') || (ch>='A'&&ch<='Z')));

        return ch;
    }

    //给节点编号

    private int getPosition(char ch){
        for (int i=0;i<mVexs.length;i++){
            if (mVexs[i]==ch){
                return i;
            }

        }
        return -1;
    }

    /**
     * kruskal最小生成树
     */

    public void kruskal(){
        int index = 0;
        // 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
        int[] vends = new int[mEdgNum];
        //结果数组，保存最小生成树的边
        EData[] rets = new EData[mEdgNum];
        EData[] edges;

        // 获取"图中所有的边"
        edges = getEdges();
        // 将边按照"权"的大小进行排序(从小到大)
        quickSort(edges, 0,mEdgNum);
        for (int i=0;i<mEdgNum;i++){
            //获取边的始终点
            int start = getPosition(edges[i].start);
            int end = getPosition(edges[i].end);

            int m = getEnd(vends,start);
            int n = getEnd(vends,end);

            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            //新加入的两个点终点不一样，说明他们不是连通的
            //如何保存每个点的终点呢
            if (m != n) {
                vends[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets[index++] = edges[i];           // 保存结果
            }
        }
        // 统计并打印"kruskal最小生成树"的信息
        int length = 0;
        for (int i = 0; i < index; i++)
        {length += rets[i].weight;}
        System.out.printf("Kruskal=%d: ", length);
        for (int i = 0; i < index; i++)
        {
            System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
        }

        System.out.printf("\n");
    }



    /**
     * 获取图中的边（A,B,value）
     * @return
     */
    private EData[] getEdges() {
        int index=0;
        EData[] edges;

        edges = new EData[mEdgNum];
        //遍历邻接矩阵
        for (int i=0; i < mVexs.length; i++) {
            for (int j=i+1; j < mVexs.length; j++) {
                if (mMatrix[i][j]!=INF) {
                    edges[index++] = new EData(mVexs[i], mVexs[j], mMatrix[i][j]);
                }
            }
        }

        return edges;
    }

    /**
     * 对边进行排序，用什么排序算法呢.我尝试使用快速排序
     * 从小到大进行排序
     * @param edges
     */
    private int sortEdgs(EData[] edges,int lo,int hi){
        while (lo<hi){
            while (lo<hi&&edges[hi].weight>=edges[0].weight){hi--;}
            if (lo<hi){edges[lo].weight=edges[hi].weight;lo++;}
            while (lo<hi&&edges[lo].weight<=edges[0].weight){lo++;}
            if (lo<hi){edges[hi].weight=edges[lo].weight;hi--;}
        }
        edges[lo].weight=edges[0].weight;
        return lo;

    }

    private void quickSort(EData[] edges,int s,int j){
        int i=0;
        while (s<j){
            i=sortEdgs(edges,s,j);
            quickSort(edges,s,i-1);
            quickSort(edges,i+1,j);

        }

    }

    /**
     *
     * @param vends
     * @param i
     * @return
     */
    private int getEnd(int[] vends, int i) {
        //这是一个循环
        while (vends[i] != 0){
            i = vends[i];

        }

        return i;
    }

}
