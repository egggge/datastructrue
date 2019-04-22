/**
 * @Author: egg
 * @Date: 2019-04-16 10:41
 */
public class AdMatrix {
    //顶点个数

    int size;

    //顶点名称

    char[] vertexs;

    //关系矩阵

    int[][] matrix;
    //构造函数没有返回值

    public  AdMatrix(char[] vertexs,char[][] edgs) {
        size = vertexs.length;
        matrix = new int[size][size];
        this.vertexs = vertexs;

        for (char[] c:edgs){
            //确定矩阵中关联边在矩阵中顶点的位置
            int p1 = getPosition(c[0]);
            int p2 = getPosition(c[1]);

            matrix[p1][p2] = 1;
            //有向图没有这一句
            matrix[p2][p1] = 1;

        }
    }
    //给节点编号

    private int getPosition(char ch){
        for (int i=0;i<vertexs.length;i++){
            if (vertexs[i]==ch){
                return i;
            }

        }
        return -1;
    }

    /**
     * 图的遍历
     */
    public void print(){
        for (int[] i:matrix){
            for (int j:i){
                System.out.println(j+"");
            }
            System.out.println();
        }

    }

    public static void main(String[] args){
        char[] vexs = {'A','B','C','D','E','F','G','H','I','J','K'};
        char[][] edges = new char[][]{
                {'A','C'},
                {'A','D'},
                {'A','F'},
                {'B','C'},
                {'C','D'},
                {'E','G'},
                {'D','G'},
                {'I','J'},
                {'J','G'},
        };

        long start = System.nanoTime();

        AdMatrix pG = new AdMatrix(vexs,edges);
        pG.print();

        long end = System.nanoTime();
        System.out.println(end-start);
    }
}
