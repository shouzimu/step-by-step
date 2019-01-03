package com.dh.lt.one;

import com.dh.lt.common.TreeNode;
import com.dh.lt.common.TreeNodeIterator;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的最大深度
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * ....3
 * .../ \
 * ..9  20
 * ..../  \
 * ..15   7
 * <p>
 * 1、_102_BinaryTreeLevelOrderTraversal 中bfs遍历的方式遍历数，记下层级
 */
public class _104_MaximumDepthofBinaryTree {
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        int level = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (null != node.left) {
                    queue.add(node.left);
                }
                if (null != node.right) {
                    queue.add(node.right);
                }
            }
            level++;
        }
        return level;
    }

    @Test
    public void testMaxDepth() {
        TreeNode root = TreeNodeIterator.initTestTree();
        System.out.println(maxDepth(root));
    }
}
