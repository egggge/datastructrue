import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: egg
 * @Date: 2019-04-16 14:58
 */
public class ListDNG {

    Vertex[] vertexLists;
    //节点的个数
    int size;

    //邻接表节点类，单链表数据结构

    class Vertex{
        int ch;
        Vertex next;

        //构造函数

        Vertex(int ch){
            this.ch = ch;

        }
        //添加到链表结尾

        void add(int ch){
            Vertex node = this;
            while (node.next!=null){
                node = node.next;
            }
            node.next=new Vertex(ch);

        }

    }

    /**
     * 邻接表的构造函数
     * @param size
     * @param edges
     */
    public ListDNG(int size,int[][] edges,int edge ){
        this.vertexLists = new Vertex[size+1];
        //先把每个链表的表头做好
        for (int i=1;i<=size;i++){
            this.vertexLists[i] = new Vertex(i);
        }
        //存储边的信息
        for (int k=1;k<=edge;k++){
            int p1 = edges[k][0];
            vertexLists[p1].add(edges[k][1]);
        }

    }

    /**
     *
     * @return
     */
    public List safeCount(int size) {
        List<Integer> res = new ArrayList<Integer>();
        int i=1;
        while (i<=size){
            if (vertexLists[i].next == null) {
                res.add(i);

            }
            i++;

        }

        for (int j=1;j<=size;j++){
            Vertex temp = vertexLists[j];
            while (temp.next!=null){
                temp=temp.next;
                if (res.indexOf(temp.ch)==-1){
                    break;
                }
                else if (res.indexOf(temp.ch)!=-1&&temp.next==null){
                    res.add(j);
                    break;
                }
                else {temp=temp.next;}

            }

        }
//        Iterator it = res.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
        return res;

    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int edge = sc.nextInt();
//        int[][] edges = new int[][]{
//                {1,3},
//                {2,4},
//                {2,6},
//                {3,4},
//                {4,5},
//                {5,3},
//        };
        int[][] input = new int[edge+1][2];
        for (int i = 1;i<=edge;i++){
            input[i][0] = sc.nextInt();
            input[i][1] = sc.nextInt();
        }
//
//        for (int[] t:input){
//            System.out.println(t[0]+"+"+t[1]);
//
//        }
        ListDNG pg = new ListDNG(size,input,edge);
        Iterator iterator = pg.safeCount(size).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


//        long start = System.nanoTime();
//        long end = System.nanoTime();
//        System.out.println(end-start);
    }

}
