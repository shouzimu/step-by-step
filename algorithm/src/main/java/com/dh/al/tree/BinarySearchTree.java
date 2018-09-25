package com.dh.al.tree;

import org.junit.Test;

import java.util.stream.IntStream;

public class BinarySearchTree {

    TreeNode root;

    /**
     * 生成/插入树
     *
     * @param tree
     * @param z
     */
    public void insertBST(BinarySearchTree tree, TreeNode z) {
        TreeNode x = tree.root;
        TreeNode y = null;
        while (x != null) {
            y = x;
            if (z.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            tree.root = z;
        } else if (z.value < y.value) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    TreeNode treeSearch(TreeNode x, int k) {
        if (x == null || k == x.value) {
            return x;
        }
        if (k < x.value) {
            return treeSearch(x.left, k);
        } else {
            return treeSearch(x.right, k);
        }
    }

    void inorderTreeWalk(TreeNode x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.println(x);
            inorderTreeWalk(x.right);
        }
    }

    @Test
    public void testInsertBst() {
        BinarySearchTree tree = new BinarySearchTree();

        IntStream.range(1, 10).forEach(i -> {
            TreeNode t = new TreeNode();
            t.value = i;
            insertBST(tree, t);
        });

        System.out.println("---treeSearch---");
        TreeNode search = treeSearch(tree.root, 5);
        System.out.println(search);
        System.out.println("---inorderTreeWalk---");
        inorderTreeWalk(search);
    }
}
