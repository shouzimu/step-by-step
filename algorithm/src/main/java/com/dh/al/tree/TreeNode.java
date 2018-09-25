package com.dh.al.tree;

import lombok.Data;

@Data
public class TreeNode {
    int value;

    TreeNode left;

    TreeNode right;

    TreeNode parent;

    public TreeNode() {
    }


    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value+"}";
    }
}
