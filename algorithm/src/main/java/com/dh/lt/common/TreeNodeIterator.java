package com.dh.lt.common;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;

public class TreeNodeIterator {
    /**
     * 前序遍历 根节点->左节点->右节点
     *
     * @param node
     */
    void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历 左节点->根节点->右节点
     *
     * @param node
     */
    void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    /**
     * 后序遍历 左节点->右节点 -> 根节点
     *
     * @param node
     */
    void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");
    }

    /**
     * 按层遍历，根->第二层->第三层
     * 广度优先遍历
     * <p>
     * 使用队列的先进先出的特点来依次将 父节点->左孩子->右孩子的顺序将节点放入队列中，当队列为空时说明遍历结束
     *
     * 如果要实现打印时每一层占一行怎么实现呢？
     * 
     *
     * @param root
     */
    void bfsIterator(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val);
            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }
            System.out.println("\n");
        }

    }

    /**
     * --------- 4
     * ----- 2       6
     * -  1    3   5    7
     *
     * @return
     */
    private TreeNode initTestTree() {
        TreeNode root = new TreeNode(4);

        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(6);
        root.left = l1;
        root.right = r1;

        TreeNode l1c_L = new TreeNode(1);
        TreeNode l1c_R = new TreeNode(3);
        l1.left = l1c_L;
        l1.right = l1c_R;

        TreeNode r1c_L = new TreeNode(5);
        TreeNode r1c_R = new TreeNode(7);
        r1.left = r1c_L;
        r1.right = r1c_R;
        return root;
    }

    @Test
    public void testOrder() {
        TreeNode root = initTestTree();
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();
        bfsIterator(root);

    }
}
