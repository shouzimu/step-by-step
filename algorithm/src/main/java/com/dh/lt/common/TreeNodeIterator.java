package com.dh.lt.common;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

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
     * <p>
     * 如果要实现打印时每一层占一行怎么实现呢？
     *
     * @param root
     */
    public void bfsIterator(TreeNode root) {
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
            System.out.print(" ");
        }

    }


    /**
     * 深度度优先搜索，可以理解为一条路走到头
     *
     * @param node
     */
    void dfsIterator(TreeNode node) {
        if (null != node) {
            System.out.println(node.val);
        } else {
            return;
        }
        dfsIterator(node.left);
        dfsIterator(node.right);
    }


    /**
     * 深度优先搜索的栈实现，递归本质上就是使用栈来实现的
     * 这个地方我们手动入栈和出栈
     *
     * @param node
     */
    void dfsStackIterator(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            TreeNode tNode = stack.pop();
            System.out.println(tNode.val);

            if (tNode.right != null) {
                stack.push(tNode.right);
            }

            if (tNode.left != null) {
                stack.push(tNode.left);
            }
        }
    }


    /**
     * -----------------------10
     * ---------------8               12
     * --------- 4       9      11         14
     * ----- 2       6                 13       15
     * -  1    3   5    7
     *
     * @return
     */
    public static TreeNode initTestTree() {
        TreeNode root = new TreeNode(10);
        TreeNode node2 = new TreeNode(8);
        TreeNode node3 = new TreeNode(12);
        root.left = node2;
        root.right = node3;

        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(9);
        node2.left = node4;
        node2.right = node5;


        TreeNode node6 = new TreeNode(2);
        TreeNode node7 = new TreeNode(6);
        node4.left = node6;
        node4.right = node7;


        TreeNode node8 = new TreeNode(1);
        TreeNode node9 = new TreeNode(3);
        node6.left= node8;
        node6.right= node9;

        TreeNode node10 = new TreeNode(5);
        TreeNode node11 = new TreeNode(7);
        node7.left = node10;
        node7.right = node11;


        TreeNode node12 = new TreeNode(11);
        TreeNode node13 = new TreeNode(14);
        node3.left = node12;
        node3.right = node13;


        TreeNode node14 = new TreeNode(13);
        TreeNode node15 = new TreeNode(15);
        node13.left = node14;
        node13.right = node15;
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
        System.out.println();
        dfsIterator(root);
        System.out.println();
        dfsIterator(root);
        System.out.println();
        dfsStackIterator(root);

    }
}
