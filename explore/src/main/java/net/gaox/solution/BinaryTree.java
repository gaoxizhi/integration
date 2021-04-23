package net.gaox.solution;

/**
 * <p> 二叉树节点 </p>
 *
 * @author gaox·Eric
 * @date 2021/4/23 21:45
 */
class BinaryTree {

    /**
     * 需要根节点
     */
    private BinaryTree.Node root;
    /**
     * 统计个数
     */
    private int count;
    private int foot;
    private Object[] retObj;

    @SuppressWarnings("rawtypes")
    public void add(Object data) {
        Comparable com = (Comparable) data;
        // 设置节点是为了排序
        BinaryTree.Node newNode = new BinaryTree.Node(com);
        // 表示现在没有根节点
        if (this.root == null) {
            this.root = newNode;
        } else {
            // 需要交给Node处理，考虑大小关系
            this.root.addNode(newNode);
        }
        this.count++;
    }


    public Object[] toArray() {
        if (this.root == null) {
            return null;
        }
        this.foot = 0;
        this.retObj = new Object[this.count];
        this.root.toArrayNode();
        return this.retObj;
    }

    /**
     * 树中需要保存数据以及各节点的关系
     */
    private class Node {
        // 只是做一个数据的载体，好确认关系
        private Comparable data;
        private BinaryTree.Node left;
        private BinaryTree.Node right;

        private Node(Comparable data) {
            this.data = data;
        }

        @SuppressWarnings("unchecked")
        public void addNode(BinaryTree.Node newNode) {
            if (this.data.compareTo(newNode.data) < 0) {
                if (this.right == null) {
                    this.right = newNode;
                } else {
                    this.right.addNode(newNode);
                }
            } else {
                if (this.left == null) {
                    this.left = newNode;
                } else {
                    this.left.addNode(newNode);
                }
            }
        }

        public void toArrayNode() {
            if (this.left != null) {
                this.left.toArrayNode();
            }
            BinaryTree.this.retObj[BinaryTree.this.foot++] = this.data;
            if (this.right != null) {
                this.right.toArrayNode();
            }
        }
    }

}
