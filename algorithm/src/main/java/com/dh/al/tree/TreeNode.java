package com.dh.al.tree;

import lombok.Data;

@Data
public class TreeNode<T> {
    T value;

    TreeNode<T> left;

    TreeNode<T> right;

    TreeNode<T> parent;

    public TreeNode() {
    }


    public TreeNode(T value, TreeNode<T> left, TreeNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value;
    }
}
