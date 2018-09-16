package com.dh.al.tree;

import org.junit.Test;

import java.util.stream.IntStream;

public class BinarySearchTree {

    TreeNode<Integer> root;

    public void insertBST(BinarySearchTree tree, TreeNode<Integer> z) {
        TreeNode<Integer> x = tree.root;
        TreeNode<Integer> y = null;
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

    @Test
    public void testInsertBst() {
        BinarySearchTree tree = new BinarySearchTree();

        IntStream.range(1, 10).forEach(i -> {
            TreeNode t = new TreeNode();
            t.value = i;
            insertBST(tree, t);
        });

        System.out.println(true);
    }
}
