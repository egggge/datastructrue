import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: egg
 * @Date: 2019-04-21 11:03
 */
public class BinaryTree<E> {
    class TreeNode<E> {
        private E data;
        private TreeNode<E> rchild;
        private TreeNode<E> lchild;

        //构造函数，无参数
        TreeNode(){};

        TreeNode(E e){
            this.data = e;
        }

        TreeNode(E data,TreeNode<E> lchild, TreeNode<E> rchild){
            this.data = data;
            this.lchild = lchild;
            this.rchild = rchild;
        }
        public E getData(){
            return this.data;
        }

        public void setData(E data){
            this.data=data;

        }
        public void setLchild(TreeNode<E> lchild){
            this.lchild = lchild;
        }

        public TreeNode<E> getLchild(){
            return this.lchild;
        }

        public void setRchild(TreeNode<E> rchild){
            this.rchild = rchild;
        }

        public TreeNode<E> getRchild(){
            return this.rchild;
        }



    }
    //根节点

    private TreeNode<E> root;

    //链式存储二叉树节点

    private List<TreeNode> nodeList = null;

    public BinaryTree(){

    }

    public BinaryTree(TreeNode<E> root){
        this.root = root;
    }

    /**
     * 把数组转变成完全二叉树
     * @param array
     * @return
     */

    public TreeNode<E> buildTree(E[] array){
        nodeList = new LinkedList<TreeNode>();
        for (int i=0;i<array.length;i++){
            nodeList.add(new TreeNode(array[i]));

        }

        //左孩子：2*j+1
        //右孩子：2*j+2
        //注意j的取值
        for (int j=0;j<(array.length/2-1);j++){
            nodeList.get(j).setLchild(nodeList.get(2*j+1));
            nodeList.get(j).setRchild(nodeList.get(2*j+2));
        }

        //最后一个父节点，单独处理
        int index = array.length/2-1;
        nodeList.get(index).setLchild(nodeList.get(2*index+1));
        //如果长度是奇数才有右孩子
        if (array.length%2!=0){
            nodeList.get(index).setRchild(nodeList.get(2*index+2));
        }
        root = nodeList.get(0);
        return root;


    }

    /**
     * 递归求解树的高度
     * @param node
     * @return
     */


    public int getHigh(TreeNode<E> node){
        if (node==null){
            return 0;
        }
        else {
            int i = getHigh(node.getLchild());
            int j = getHigh(node.getRchild());
            return (i<j)?(j+1):(i+1);
        }

    }

    /**
     * 递归求解树的节点数
     * @param node
     * @return
     */

    public int getNodes(TreeNode<E> node){
        if (node==null){
            return 0;
        }
        else {
            return 1+getNodes(node.getLchild())+getNodes(node.getRchild());
        }

    }

    /**
     * 中序遍历，左-》根-》右
     * 递归实现
     * 同理，前序和后序
     */
    public void LNR(TreeNode<E> node){
        if (node==null){
            return;
        }
        else {
            LNR(node.getLchild());
            System.out.println(node.data);
            LNR(node.getRchild());
        }


    }

    /**
     * 非递归实现中序遍历
     * @param node
     */
    public void nonRecInOrder(TreeNode<E> node){
        //java栈（FILO）
        Stack<TreeNode<E>> nodeStack = new Stack<TreeNode<E>>();
        TreeNode<E> nodeTemp = node;
        //栈非空
        while(nodeTemp != null || !nodeStack.isEmpty()){
            //先遍历左子树，把根节点压栈
            if(nodeTemp != null){
                nodeStack.push(nodeTemp);
                nodeTemp = nodeTemp.getLchild();
            }else{
                //左子树为空
                nodeTemp = nodeStack.pop();
                System.out.print(nodeTemp.getData() +" ");
                nodeTemp = nodeTemp.getRchild();
            }
        }
    }

    /**
     * 非递归实现前序遍历
     * @param node
     */

    protected void nonRecPreOrder(TreeNode<E> node){
        Stack<TreeNode<E>> nodeStack = new Stack<TreeNode<E>>();
        TreeNode<E> nodetemp = node;
        while(nodetemp != null || !nodeStack.isEmpty()){
            //找到根节点
            if(nodetemp != null){
                System.out.println(nodetemp.getData()+" ");
                nodeStack.push(nodetemp);
                nodetemp = nodetemp.getLchild();
            }else{
                //左子树为空
                nodetemp = nodeStack.pop();
                nodetemp = nodetemp.getRchild();
            }
        }

    }

    /**
     * 层次遍历，用队列
     * @param node
     */

    public void levelOrder(TreeNode<E> node){
        Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
        //根节点入队
        queue.offer(node);
        while ( !queue.isEmpty()){
            TreeNode<E> nodetemp = queue.poll();
            System.out.println(nodetemp.getData()+" ");
            if (nodetemp.getLchild()!=null){
                queue.offer(nodetemp.getLchild());
            }
            if (nodetemp.getRchild()!=null){
                queue.offer(nodetemp.getRchild());
            }

            }





    }
    public static void main(String[] args){
        Object[] array = {1,2,3,4,5,6,7,8};
        BinaryTree bt = new BinaryTree();
        BinaryTree.TreeNode root = bt.buildTree(array);
//        System.out.print("树的高度：");
//        System.out.println(bt.getHigh(root));
        //bt.LNR(root);
        //bt.nonRecInOrder(root);
        bt.levelOrder(root);

    }


}
