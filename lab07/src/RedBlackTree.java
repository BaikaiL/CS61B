public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     *
     * 翻转节点及其子节点的颜色。假设 NODE 同时具有 left 和 right 子项
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     *
     * 将给定的节点向右旋转。返回此子树的新根节点。对于此实现，请确保交换新根和旧根的颜色！
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        // 修改颜色
        RBTreeNode<T> temp = node.left;
        boolean tempIsBlack = temp.isBlack;
        boolean nodeIsBlack = node.isBlack;
        node.isBlack = tempIsBlack;
        temp.isBlack = nodeIsBlack;


        // 把父节点的左子树修改为左孩子的右子树
        node.left = temp.right;

        // 把左孩子的右子树修改为父节点
        temp.right = node;

        return temp;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     *
     * 将给定的节点向左旋转。返回此子树的新根节点。对于此实现，请确保交换新根和旧根的颜色！
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        // 修改颜色
        RBTreeNode<T> temp = node.right;
        boolean tempIsBlack = temp.isBlack;
        boolean nodeIsBlack = node.isBlack;
        node.isBlack = tempIsBlack;
        temp.isBlack = nodeIsBlack;

        // 把父节点的右子树修改为右孩子的左子树
        node.right = temp.left;

        // 把右孩子的左子树修改为父节点
        temp.left = node;

        return temp;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     *
     *
     * 将给定节点插入到此 Red Black Tree 中。已提供评论以帮助解决问题。
     * 对于每种情况，请考虑执行这些操作所需的方案。请务必查看本课程中的其他方法！
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // 递归方法插入新子树
        if (node == null) {
            return new RBTreeNode<>(false, item, null, null);
        }
        // 递归找到插入的位置
        if(item.compareTo(node.item) < 0) {
            node.left = insert(node.left, item);
        }
        else if(item.compareTo(node.item) > 0) {
            node.right = insert(node.right, item);
        }

        // 参考了别人的代码，三个代办其实就是给你提示处理三中错误的情况
        // 为什么是这个顺序？ -> （反正他是这么给我的就这个顺序写）

        // 三种操作分别对于lab中提出的三种不正确的情况
        // 1.If a node has one red child, it must be on the left
        // 如果有红孩子，那么红孩子一定在左侧 -> LLRBTree
        if(!isRed(node.left) && isRed(node.right)) {
            node = rotateLeft(node);
        }
        // 2.No red node can have a red parent (every red node’s parent is black)
        // 红孩子不能做红孩子的父节点
        if(isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        // 3.No node can have two red children
        // 左右孩子不可能同时为红孩子
        if(isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node; //fix this return statement
    }

}
